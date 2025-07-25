package com.ysmjjsy.goya.component.crypto.processor;

import com.ysmjjsy.goya.component.crypto.definition.AsymmetricCryptoProcessor;
import com.ysmjjsy.goya.component.crypto.domain.SecretKey;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.dromara.hutool.core.codec.binary.HexUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.crypto.asymmetric.KeyType;
import org.dromara.hutool.crypto.asymmetric.SM2;
import org.dromara.hutool.crypto.bc.ECKeyUtil;
import org.dromara.hutool.crypto.bc.SmUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: 国密 SM2 算法处理 </p>
 * <p>
 * 主要用于前后端数据加密处理，与 sm-crypto 交互
 *
 * @author goya
 * @since 2022/5/1 19:29
 */
public class SM2CryptoProcessor implements AsymmetricCryptoProcessor {

    private static final Logger log = LoggerFactory.getLogger(SM2CryptoProcessor.class);

    @Override
    public SecretKey createSecretKey() {
        // 随机生成秘钥
        SM2 sm2 = SmUtil.sm2();
        // sm2的加解密时有两种方式即 C1C2C3、 C1C3C2，
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        // 生成私钥
        String privateKey = HexUtil.encodeStr(ECKeyUtil.encodeECPrivateKey(sm2.getPrivateKey()));
        // 生成公钥
        String publicKey = HexUtil.encodeStr(((BCECPublicKey) sm2.getPublicKey()).getQ().getEncoded(false));

        SecretKey secretKey = new SecretKey();
        secretKey.setPrivateKey(privateKey);
        secretKey.setPublicKey(publicKey);
        return secretKey;
    }

    @Override
    public String decrypt(String content, String privateKey) {
        // 可用的 Hutool SM2 解密
        SM2 sm2 = SmUtil.sm2(privateKey, null);
        sm2.setMode(SM2Engine.Mode.C1C3C2);

        String result = StrUtil.utf8Str(sm2.decrypt(content, KeyType.PrivateKey));
        log.trace("[Goya] |- SM2 crypto decrypt data, value is : [{}]", result);

        return result;
    }

    @Override
    public String encrypt(String content, String publicKey) {
        SM2 sm2 = SmUtil.sm2(null, publicKey);

        String result = sm2.encryptHex(content, KeyType.PublicKey);
        log.trace("[Goya] |- SM2 crypto encrypt data, value is : [{}]", result);
        return result;
    }

}
