package com.hasaker.common.utils;

import cn.hutool.core.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * @package com.hasaker.common.utils
 * @author 余天堂
 * @create 2020/2/27 08:45
 * @description AESEncryptUtils
 */
public class AESEncryptUtils {

    private static final String AES = "AES";
    private static final String UTF_8 = "utf-8";
    // 参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHM_PARAMS = "AES/GCM/NoPadding";

    // AES加密String型数据 采用Base64算法进行转码,避免出现中文乱码
    public static String encrypt(String value, String key) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM_PARAMS);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), AES));
        byte[] b = cipher.doFinal(value.getBytes(UTF_8));

        return Base64.encode(b);
    }

    // AES加密Long型数据
    public static String encrypt(Long value, String key) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM_PARAMS);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), AES));
        byte[] b = cipher.doFinal(String.valueOf(value).getBytes(UTF_8));

        return Base64.encode(b);
    }

    // AES解密String类型数据
    public static String decrypt(String value, String key) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM_PARAMS);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), AES));

        byte[] encryptBytes = Base64.decode(value.getBytes());
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes);
    }
}
