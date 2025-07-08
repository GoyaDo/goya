package com.ysmjjsy.goya.security.core.constants;

import com.ysmjjsy.goya.component.dto.constants.GoyaConstants;
import com.ysmjjsy.goya.component.dto.constants.SymbolConstants;

import static com.ysmjjsy.goya.component.cache.constants.CacheConstants.CACHE_PREFIX;

/**
 * <p>认证相关</p>
 *
 * @author goya
 * @since 2025/7/8 21:32
 */
public interface SecurityConstants {


    String PROPERTY_PREFIX_SECURITY = GoyaConstants.GOYA + ".security";
    String PROPERTY_PREFIX_SECURITY_ENDPOINT = PROPERTY_PREFIX_SECURITY + ".endpoint";

    String PROPERTY_PREFIX_SECURITY_AUTHORIZATION = PROPERTY_PREFIX_SECURITY + ".authorization";
    String ITEM_AUTHORIZATION_VALIDATE = PROPERTY_PREFIX_SECURITY_AUTHORIZATION + ".validate";


    String AUTHORIZATION_ENDPOINT = "/oauth2/authorize";
    String PUSHED_AUTHORIZATION_REQUEST_ENDPOINT = "/oauth2/par";
    String TOKEN_ENDPOINT = "/oauth2/token";
    String TOKEN_REVOCATION_ENDPOINT = "/oauth2/revoke";
    String TOKEN_INTROSPECTION_ENDPOINT = "/oauth2/introspect";
    String DEVICE_AUTHORIZATION_ENDPOINT = "/oauth2/device_authorization";
    String DEVICE_VERIFICATION_ENDPOINT = "/oauth2/device_verification";
    String JWK_SET_ENDPOINT = "/oauth2/jwks";
    String OIDC_CLIENT_REGISTRATION_ENDPOINT = "/connect/register";
    String OIDC_LOGOUT_ENDPOINT = "/connect/logout";
    String OIDC_USER_INFO_ENDPOINT = "/userinfo";

    String AUTHORIZATION_CONSENT_URI = "/oauth2/consent";
    String DEVICE_ACTIVATION_URI = "/oauth2/device_activation";
    String DEVICE_VERIFICATION_SUCCESS_URI = "/device_activated";

    String BEARER_TYPE = "Bearer";
    String BEARER_TOKEN = BEARER_TYPE + SymbolConstants.SPACE;

    String BASIC_TYPE = "Basic";
    String BASIC_TOKEN = BASIC_TYPE + SymbolConstants.SPACE;
    String AUTHORITIES = "authorities";
    String AVATAR = "avatar";
    String LICENSE = "license";
    String OPEN_ID = "openid";
    String PRINCIPAL = "principal";
    String ROLES = "roles";
    String SOURCE = "source";
    String USERNAME = "username";
    String PRODUCT_ID = "product_id";

    String INVALID_REQUEST = "invalid_request";
    String UNAUTHORIZED_CLIENT = "unauthorized_client";
    String ACCESS_DENIED = "access_denied";
    String UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";
    String INVALID_SCOPE = "invalid_scope";
    String INSUFFICIENT_SCOPE = "insufficient_scope";
    String INVALID_TOKEN = "invalid_token";
    String SERVER_ERROR = "server_error";
    String TEMPORARILY_UNAVAILABLE = "temporarily_unavailable";
    String INVALID_CLIENT = "invalid_client";
    String INVALID_GRANT = "invalid_grant";
    String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";
    String UNSUPPORTED_TOKEN_TYPE = "unsupported_token_type";
    String INVALID_REDIRECT_URI = "invalid_redirect_uri";

    String ACCOUNT_EXPIRED = "AccountExpiredException";
    String ACCOUNT_DISABLED = "DisabledException";
    String ACCOUNT_LOCKED = "LockedException";
    String ACCOUNT_ENDPOINT_LIMITED = "AccountEndpointLimitedException";
    String BAD_CREDENTIALS = "BadCredentialsException";
    String CREDENTIALS_EXPIRED = "CredentialsExpiredException";
    String USERNAME_NOT_FOUND = "UsernameNotFoundException";

    String SESSION_EXPIRED = "SessionExpiredException";

    String NOT_AUTHENTICATED = "Not Authenticated";

    String CACHE_SECURITY_PREFIX = CACHE_PREFIX + "security:";
    String CACHE_SECURITY_METADATA_PREFIX = CACHE_SECURITY_PREFIX + "metadata:";

    String CACHE_NAME_SECURITY_METADATA_ATTRIBUTES = CACHE_SECURITY_METADATA_PREFIX + "attributes:";
    String CACHE_NAME_SECURITY_METADATA_INDEXES = CACHE_SECURITY_METADATA_PREFIX + "indexes:";
    String CACHE_NAME_SECURITY_METADATA_INDEXABLE = CACHE_SECURITY_METADATA_PREFIX + "indexable:";
    String CACHE_NAME_SECURITY_METADATA_COMPATIBLE = CACHE_SECURITY_METADATA_PREFIX + "compatible:";
}
