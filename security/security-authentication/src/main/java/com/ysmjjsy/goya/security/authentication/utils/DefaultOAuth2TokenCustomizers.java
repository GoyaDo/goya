package com.ysmjjsy.goya.security.authentication.utils;

import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenExchangeActor;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenExchangeCompositeAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.*;

import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/2/26 16:47
 */
public class DefaultOAuth2TokenCustomizers {


    private DefaultOAuth2TokenCustomizers() {
    }

    static OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return (context) -> context.getClaims().claims((claims) -> customize(context, claims));
    }

    static OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer() {
        return (context) -> context.getClaims().claims((claims) -> customize(context, claims));
    }

    private static void customize(OAuth2TokenContext tokenContext, Map<String, Object> claims) {
        // Add 'cnf' claim for Mutual-TLS Client Certificate-Bound Access Tokens
        if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenContext.getTokenType())
                && tokenContext.getAuthorizationGrant() != null && tokenContext.getAuthorizationGrant()
                .getPrincipal() instanceof OAuth2ClientAuthenticationToken clientAuthentication) {

            if ((ClientAuthenticationMethod.TLS_CLIENT_AUTH.equals(clientAuthentication.getClientAuthenticationMethod())
                    || ClientAuthenticationMethod.SELF_SIGNED_TLS_CLIENT_AUTH
                    .equals(clientAuthentication.getClientAuthenticationMethod()))
                    && tokenContext.getRegisteredClient().getTokenSettings().isX509CertificateBoundAccessTokens()) {

                X509Certificate[] clientCertificateChain = (X509Certificate[]) clientAuthentication.getCredentials();
                try {
                    String sha256Thumbprint = computeSHA256Thumbprint(clientCertificateChain[0]);
                    Map<String, Object> x5tClaim = new HashMap<>();
                    x5tClaim.put("x5t#S256", sha256Thumbprint);
                    claims.put("cnf", x5tClaim);
                } catch (Exception ex) {
                    OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                            "Failed to compute SHA-256 Thumbprint for client X509Certificate.", null);
                    throw new OAuth2AuthenticationException(error, ex);
                }
            }
        }

        // Add 'act' claim for delegation use case of Token Exchange Grant.
        // If more than one actor is present, we create a chain of delegation by nesting
        // "act" claims.
        if (tokenContext
                .getPrincipal() instanceof OAuth2TokenExchangeCompositeAuthenticationToken compositeAuthenticationToken) {
            Map<String, Object> currentClaims = claims;
            for (OAuth2TokenExchangeActor actor : compositeAuthenticationToken.getActors()) {
                Map<String, Object> actorClaims = actor.getClaims();
                Map<String, Object> actClaim = new LinkedHashMap<>();
                actClaim.put(OAuth2TokenClaimNames.ISS, actorClaims.get(OAuth2TokenClaimNames.ISS));
                actClaim.put(OAuth2TokenClaimNames.SUB, actorClaims.get(OAuth2TokenClaimNames.SUB));
                currentClaims.put("act", Collections.unmodifiableMap(actClaim));
                currentClaims = actClaim;
            }
        }
    }

    private static String computeSHA256Thumbprint(X509Certificate x509Certificate) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(x509Certificate.getEncoded());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

}
