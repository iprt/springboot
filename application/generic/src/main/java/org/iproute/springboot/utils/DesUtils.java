package org.iproute.springboot.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

/**
 * DesUtil
 *
 * @author tech@intellij.io
 * @since 2022/6/30
 */
public class DesUtils {
    private static final String DES = "DES";
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * 加密
     *
     * @param input 明文
     * @param key   密钥
     * @return 密文 string
     * @throws GeneralSecurityException the general security exception
     */
    public static String encrypt(String input, String key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(DES);
        SecretKey keySpec = new SecretKeySpec(key.getBytes(CHARSET), DES);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] data = cipher.doFinal(input.getBytes(CHARSET));
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 解密
     *
     * @param input 密文
     * @param key   密钥
     * @return 明文 string
     * @throws GeneralSecurityException the general security exception
     */
    public static String decrypt(String input, String key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(DES);
        SecretKey keySpec = new SecretKeySpec(key.getBytes(CHARSET), DES);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] data = cipher.doFinal(Base64.getDecoder().decode(input));
        return new String(data, CHARSET);
    }
}
