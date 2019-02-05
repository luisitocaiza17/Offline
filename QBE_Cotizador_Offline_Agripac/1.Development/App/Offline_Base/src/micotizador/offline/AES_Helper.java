package micotizador.offline;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

public class AES_Helper {

    private static byte[] key = {
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F, 0x70
    }; //"A0J9VS9D32AAS09J";

    private static byte[] iv = {
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F, 0x70
    }; //"A0J9VS9D32AAS09J";

    private static IvParameterSpec ivspec = new IvParameterSpec(iv);

    public static String encrypt(String strToEncrypt) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
        final String encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
        return encryptedString;
        
    }

    public static String decrypt(String strToDecrypt) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
        final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
        return decryptedString;
    }

    public static String prepare(String decrypted) {
        return new String(Base64.decodeBase64(decrypted), Charset.forName("UTF-8"));
    }


    public static String padString(String source) {
        char paddingChar = ' ';
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;

        for (int i = 0; i < padLength; i++) source += paddingChar;

        return source;
    }
}
