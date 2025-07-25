package com.ysmjjsy.goya.component.web.crypto;

import com.ysmjjsy.goya.component.cache.jetcache.stamp.AbstractStampManager;
import com.ysmjjsy.goya.component.crypto.definition.AsymmetricCryptoProcessor;
import com.ysmjjsy.goya.component.crypto.definition.SymmetricCryptoProcessor;
import com.ysmjjsy.goya.component.crypto.domain.SecretKey;
import com.ysmjjsy.goya.component.exception.request.RequestInvalidException;
import com.ysmjjsy.goya.component.exception.stamp.StampHasExpiredException;
import com.ysmjjsy.goya.component.web.constants.WebConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.data.id.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * <p>Description: 接口加密解密处理器 </p>
 *
 * @author goya
 * @since 2021/10/4 14:29
 */
public class HttpCryptoProcessor extends AbstractStampManager<String, SecretKey> {

    private static final Logger log = LoggerFactory.getLogger(HttpCryptoProcessor.class);

    private final AsymmetricCryptoProcessor asymmetricCryptoProcessor;

    private final SymmetricCryptoProcessor symmetricCryptoProcessor;

    public HttpCryptoProcessor(AsymmetricCryptoProcessor asymmetricCryptoProcessor, SymmetricCryptoProcessor symmetricCryptoProcessor) {
        super(WebConstants.CACHE_NAME_TOKEN_SECURE_KEY);
        this.asymmetricCryptoProcessor = asymmetricCryptoProcessor;
        this.symmetricCryptoProcessor = symmetricCryptoProcessor;
    }

    public String encrypt(String identity, String content) {
        try {
            SecretKey secretKey = getSecretKey(identity);
            String result = symmetricCryptoProcessor.encrypt(content, secretKey.getSymmetricKey());
            log.debug("[Goya] |- Encrypt content from [{}] to [{}].", content, result);
            return result;
        } catch (StampHasExpiredException e) {
            log.warn("[Goya] |- Session has expired, need recreate, Skip encrypt content [{}].", content);
            return content;
        } catch (Exception e) {
            log.warn("[Goya] |- Symmetric can not Encrypt content [{}], Skip!", content);
            return content;
        }
    }

    public String decrypt(String identity, String content) {
        try {
            SecretKey secretKey = getSecretKey(identity);

            String result = symmetricCryptoProcessor.decrypt(content, secretKey.getSymmetricKey());
            log.debug("[Goya] |- Decrypt content from [{}] to [{}].", content, result);
            return result;
        } catch (StampHasExpiredException e) {
            log.warn("[Goya] |- Session has expired, need recreate, Skip decrypt content [{}].", content);
            return content;
        } catch (Exception e) {
            log.warn("[Goya] |- Symmetric can not Decrypt content [{}], Skip!", content);
            return content;
        }
    }

    /**
     * 根据requestId创建SecretKey {@link SecretKey}。如果前端有可以唯一确定的requestId，并且使用该值，则用该值创建SecretKey。否则就由后端动态生成一个requestId。
     *
     * @param identity                   requestId，可以为空。
     * @param accessTokenValiditySeconds Session过期时间，单位秒
     * @return {@link SecretKey}
     */
    public SecretKey createSecretKey(String identity, Duration accessTokenValiditySeconds) {
        // 前端如果设置requestId，则由后端生成
        if (StringUtils.isBlank(identity)) {
            identity = IdUtil.fastUUID();
        }

        // 根据Token的有效时间设置
        Duration expire = getExpire(accessTokenValiditySeconds);
        return this.create(identity, expire);
    }

    @Override
    public SecretKey nextStamp(String key) {
        SecretKey secretKey = asymmetricCryptoProcessor.createSecretKey();
        String symmetricKey = symmetricCryptoProcessor.createKey();
        secretKey.setSymmetricKey(symmetricKey);
        secretKey.setIdentity(key);
        secretKey.setState(IdUtil.fastUUID());

        log.debug("[Goya] |- Generate secret key, value is : [{}]", secretKey);
        return secretKey;
    }

    private boolean isSessionValid(String identity) {
        return this.containKey(identity);
    }

    private SecretKey getSecretKey(String identity) throws StampHasExpiredException {
        if (isSessionValid(identity)) {
            SecretKey secretKey = this.get(identity);
            if (ObjectUtils.isNotEmpty(secretKey)) {
                log.trace("[Goya] |- Decrypt Or Encrypt content use param identity [{}], cached identity is [{}].", identity, secretKey.getIdentity());
                return secretKey;
            }
        }

        throw new StampHasExpiredException("SecretKey key is expired!");
    }

    private Duration getExpire(Duration accessTokenValiditySeconds) {
        if (ObjectUtils.isEmpty(accessTokenValiditySeconds) || accessTokenValiditySeconds.isZero()) {
            return Duration.ofHours(2L);
        } else {
            return accessTokenValiditySeconds;
        }
    }

    /**
     * 用后端非对称加密算法私钥，解密前端传递过来的、用后端非对称加密算法公钥加密的前端非对称加密算法公钥
     *
     * @param privateKey 后端非对称加密算法私钥
     * @param content    传回的已加密前端非对称加密算法公钥
     * @return 前端非对称加密算法公钥
     */
    private String decryptFrontendPublicKey(String content, String privateKey) {
        String frontendPublicKey = asymmetricCryptoProcessor.decrypt(content, privateKey);
        log.debug("[Goya] |- Decrypt frontend public key, value is : [{}]", frontendPublicKey);
        return frontendPublicKey;
    }

    /**
     * 用前端非对称加密算法公钥加密后端生成的对称加密算法 Key
     *
     * @param symmetricKey 对称算法秘钥
     * @param publicKey    前端非对称加密算法公钥
     * @return 用前端前端非对称加密算法公钥加密后的对称算法秘钥
     */
    private String encryptBackendKey(String symmetricKey, String publicKey) {
        String encryptedAesKey = asymmetricCryptoProcessor.encrypt(symmetricKey, publicKey);
        log.debug("[Goya] |- Encrypt symmetric key use frontend public key, value is : [{}]", encryptedAesKey);
        return encryptedAesKey;
    }

    /**
     * 前端获取后端生成 AES Key
     *
     * @param identity     Session ID
     * @param confidential 前端和后端加解密结果都
     * @return 前端 PublicKey 加密后的 AES KEY
     * @throws RequestInvalidException requestId不可用，无法从缓存中找到对应的值
     */
    public String exchange(String identity, String confidential) {
        try {
            SecretKey secretKey = getSecretKey(identity);
            String frontendPublicKey = decryptFrontendPublicKey(confidential, secretKey.getPrivateKey());
            return encryptBackendKey(secretKey.getSymmetricKey(), frontendPublicKey);
        } catch (StampHasExpiredException e) {
            throw new RequestInvalidException();
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
