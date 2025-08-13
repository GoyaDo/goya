package com.ysmjjsy.goya.component.crypto.processor;

import cn.hutool.v7.core.codec.binary.Base64;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.util.ByteUtil;
import cn.hutool.v7.core.util.RandomUtil;
import cn.hutool.v7.crypto.SecureUtil;
import cn.hutool.v7.crypto.symmetric.AES;
import com.ysmjjsy.goya.component.crypto.definition.SymmetricCryptoProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Description: AES 加密算法处理器 </p>
 *
 * @author goya
 * @since 2022/5/2 16:56
 */
@Slf4j
public class AESCryptoProcessor implements SymmetricCryptoProcessor {

    @Override
    public String createKey() {
        return RandomUtil.randomStringUpper(16);
    }

    @Override
    public String decrypt(String data, String key) {
        AES aes = SecureUtil.aes(ByteUtil.toUtf8Bytes(key));
        byte[] result = aes.decrypt(Base64.decode(ByteUtil.toUtf8Bytes(data)));
        log.trace("[Goya] |- AES crypto decrypt data, value is : [{}]", result);
        return StrUtil.utf8Str(result);
    }

    @Override
    public String encrypt(String data, String key) {
        AES aes = SecureUtil.aes(ByteUtil.toUtf8Bytes(key));
        byte[] result = aes.encrypt(ByteUtil.toUtf8Bytes(data));
        log.trace("[Goya] |- AES crypto encrypt data, value is : [{}]", result);
        return StrUtil.utf8Str(result);
    }
}
