package cn.veasion.auto.utils;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSAUtils
 * <p>
 * RSA 和 RSA2 加密、解密、签名、验签<br>
 * RSA: SHA1WithRSA 对 RSA 密钥的长度不限制，推荐使用 2048 位以上<br>
 * RSA2: SHA256WithRSA 强制要求 RSA 密钥的长度至少为 2048<br><br>
 * 1024 位的证书，加密时最大支持117个字节，解密时为128<br>
 * 2048 位的证书，加密时最大支持245个字节，解密时为256<br><br>
 * JAVA RSA 私钥格式必须为PKCS8格式，安卓和其它语言大部分为 PKCS1格式（不一致是需要转换成PKCS8）
 * </p>
 *
 * @author luozhuowei
 */
public class RSAUtils {

    private static final String CHARSET_NAME = "UTF-8";

    private static final int RSA_KEY_SIZE = 1024;
    private static final int RSA2_KEY_SIZE = 2048;

    private static final String KEY_ALGORITHM = "RSA";
    public static final String KEY_ALGORITHM_ANDROID = "RSA/ECB/PKCS1Padding";

    public static final String ALGORITHM_MD5 = "MD5withRSA";
    public static final String ALGORITHM_RSA1 = "SHA1WithRSA";
    public static final String ALGORITHM_RSA2 = "SHA256withRSA";

    /**
     * 签名
     *
     * @param content    内容
     * @param privateKey 公钥
     * @param charset    字符编码(默认utf-8)
     * @param rsa2       是否RSA2，true: SHA256withRSA; false: SHA1WithRSA
     * @return 签名
     */
    public static String sign(String content, String privateKey, String charset, boolean rsa2) {
        String signature = null;
        byte[] signed;
        try {
            PrivateKey pk = getPrivateKey(privateKey);
            Signature sign = Signature.getInstance(rsa2 ? ALGORITHM_RSA2 : ALGORITHM_RSA1);
            sign.initSign(pk);
            sign.update(content.getBytes(charset == null ? CHARSET_NAME : charset));
            signed = sign.sign();
            signature = base64Encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }

    /**
     * 验签
     *
     * @param content   内容
     * @param sign      签名
     * @param publicKey 公钥
     * @param charset   字符编码(默认utf-8)
     * @param rsa2      是否RSA2，true: SHA256withRSA; false: SHA1WithRSA
     * @return 是否验签成功
     */
    public static boolean verifySign(String content, String sign, String publicKey, String charset, boolean rsa2) {
        boolean verifySignSuccess = false;
        try {
            PublicKey pk = getPublicKey(publicKey);
            Signature verifySign = Signature.getInstance(rsa2 ? ALGORITHM_RSA2 : ALGORITHM_RSA1);
            verifySign.initVerify(pk);
            verifySign.update(content.getBytes(charset == null ? CHARSET_NAME : charset));
            verifySignSuccess = verifySign.verify(base64Decode(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verifySignSuccess;
    }

    /**
     * 公钥加密
     *
     * @param content   内容
     * @param publicKey 公钥
     * @param charset   字符编码(默认utf-8)
     * @param rsa2      是否RSA2
     * @return 密文
     */
    public static String encrypt(String content, String publicKey, String charset, boolean rsa2) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        InputStream inputStream = new ByteArrayInputStream(content.getBytes(charset == null ? CHARSET_NAME : charset));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // RSA加密的字节大小最多是117，将需要加密的内容，按117位拆开解密
        // 1024 / 8 - 11 = 117
        // RSA2加密的字节大小最多是245，将需要加密的内容，按245位拆开解密
        // 2048 / 8 - 11 = 245
        int len;
        byte[] buf = new byte[rsa2 ? 245 : 117];
        while ((len = inputStream.read(buf)) != -1) {
            byte[] block;
            if (buf.length == len) {
                block = buf;
            } else {
                block = Arrays.copyOf(buf, len);
            }
            outputStream.write(cipher.doFinal(block));
        }
        return base64Encode(outputStream.toByteArray());
    }

    /**
     * 私钥解密
     *
     * @param content    密文
     * @param privateKey 私钥
     * @param charset    字符编码(默认utf-8)
     * @param rsa2       是否RSA2
     * @return 明文
     */
    public static String decrypt(String content, String privateKey, String charset, boolean rsa2) throws Exception {
        PrivateKey key = getPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        InputStream inputStream = new ByteArrayInputStream(base64Decode(content));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // RSA解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        // 1024 / 8 = 128
        // RSA2解密的字节大小最多是256，将需要解密的内容，按256位拆开解密
        // 2048 / 8 = 256
        int len;
        byte[] buf = new byte[rsa2 ? 256 : 128];
        while ((len = inputStream.read(buf)) != -1) {
            byte[] block;
            if (buf.length == len) {
                block = buf;
            } else {
                block = Arrays.copyOf(buf, len);
            }
            outputStream.write(cipher.doFinal(block));
        }
        return new String(outputStream.toByteArray(), charset == null ? CHARSET_NAME : charset);
    }

    /**
     * 随机生成密钥对
     *
     * @param rsa2 是否RSA2: true RSA2密钥长度2048; false RSA密钥长度1024
     * @return 密钥对 publicKey / privateKey
     */
    public static Map<String, String> genKeyPair(boolean rsa2) {
        return genKeyPair(rsa2 ? RSA2_KEY_SIZE : RSA_KEY_SIZE);
    }

    /**
     * 随机生成密钥对
     *
     * @param keySize 密钥长度（512/1024/2048/4096）RSA2 必须大于等于 2048
     * @return 密钥对 publicKey / privateKey
     */
    public static Map<String, String> genKeyPair(int keySize) {
        Map<String, String> keyMap = new HashMap<>();
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(keySize, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            String publicKeyString = base64Encode(publicKey.getEncoded());
            String privateKeyString = base64Encode((privateKey.getEncoded()));
            keyMap.put("publicKey", publicKeyString);
            keyMap.put("privateKey", privateKeyString);
            return keyMap;
        } catch (Exception e) {
            e.printStackTrace();
            return keyMap;
        }
    }

    private static PublicKey getPublicKey(String key) {
        try {
            key = trimKey(key, "-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----");
            byte[] byteKey = base64Decode(key);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PrivateKey getPrivateKey(String key) {
        try {
            key = trimKey(key, "-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----");
            byte[] byteKey = base64Decode(key);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String trimKey(String key, String start, String end) {
        int sIndex = key.indexOf(start);
        if (sIndex == -1) {
            return key;
        }
        int eIndex = key.indexOf(end, sIndex);
        if (eIndex == -1) {
            throw new RuntimeException(end + "不存在");
        }
        key = key.substring(sIndex + start.length(), eIndex);
        String[] split = key.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : split) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] base64Decode(String str) {
        if (str.contains("\n")) {
            str = str.replace("\r\n", "").replace("\n", "");
        }
        return Base64.getDecoder().decode(str);
    }

    public static void main(String[] args) throws Exception {
        boolean rsa2 = true;
        Map<String, String> map = genKeyPair(rsa2);
        String publicKey = map.get("publicKey");
        System.out.println("公钥: " + publicKey);
        String privateKey = map.get("privateKey");
        System.out.println("私钥: " + privateKey);
        String content = "123456";
        System.out.println("明文: " + content);
        String encrypt = encrypt("123456", publicKey, null, rsa2);
        System.out.println("加密: " + encrypt);
        String decrypt = decrypt(encrypt, privateKey, null, rsa2);
        System.out.println("解密: " + decrypt);
    }

}