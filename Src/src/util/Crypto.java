package util;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;  
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.postgresql.util.Base64;  

/**
 * Possui todos os métodos referentes a criptografia de senhas.
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi
 * @version 1.0
 */
public class Crypto {
	private static Cipher cipher;
    private static SecretKey secretKey;
    
    /**
     * Possui o metodo com a chave da criptografia
     * @throws Exception
     */
    public Crypto() throws Exception {
    	String key = "374T4mGQWtMkHG8i.1B]J$vc";
    	byte[] arrayBytes = key.getBytes("UTF8");
    	KeySpec ks = new DESedeKeySpec(arrayBytes);
    	SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
    	secretKey = skf.generateSecret(ks);
    	cipher = Cipher.getInstance("DESede");
    }
    
    /**
     * Criptografa uma String passada
     * @param unencryptedString String a ser criptografada
     * @return Retorna a String encriptografada
     * @throws Exception
     */
    public String encrypt(String unencryptedString) throws Exception {
    	String encryptedString = null;
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] plainText = unencryptedString.getBytes("UTF8");
        byte[] encryptedText = cipher.doFinal(plainText);
        encryptedString = new String(Base64.encodeBytes(encryptedText));
        return encryptedString;
    }

    /**
     * Descriptografar uma String 
     * @param encryptedString String a ser Descriptografado
     * @return Retorna a String descriptografada
     * @throws Exception
     */
    public String decrypt(String encryptedString) throws Exception {
    	String decryptedText = null;
    	cipher.init(Cipher.DECRYPT_MODE, secretKey);
    	byte[] encryptedText = Base64.decode(encryptedString);
    	byte[] plainText = cipher.doFinal(encryptedText);
    	decryptedText= new String(plainText);
    	return decryptedText;
    }
}