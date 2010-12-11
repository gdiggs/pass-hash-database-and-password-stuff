/* This class encrypts a password upon object creation and 
 * can decrypt it as well. It also decrypts a string.
 */

import org.jasypt.util.text.*;

public class Password{
  private String password;
  private static final String ENCRYPTOR_PASSWORD = "passhash and kittens 4ever";
  private BasicTextEncryptor encryptor = new BasicTextEncryptor();
  
  public Password(String newPassword){
    encryptor.setPassword(ENCRYPTOR_PASSWORD);
    String encryptedPassword = encryptor.encrypt(newPassword);
    password = encryptedPassword;
  }

  public String getDecryptedPassword(){
    return (String) encryptor.decrypt(this.password);
  }
  
  public String getEncryptedPassword(){
    return (String) password;
  }
  
  public String toString(){
    return (String) this.getEncryptedPassword();
  }
  
  public static boolean isCorrectPassword(String encryptedPassword, String toValidate){
    String decryptedPassword = Password.decryptPassword(encryptedPassword);
    return toValidate.equals(decryptedPassword);
  }
  
  public static String decryptPassword(String encryptedPassword){
    BasicTextEncryptor encryptor2 = new BasicTextEncryptor();
    encryptor2.setPassword(ENCRYPTOR_PASSWORD);
    return (String) encryptor2.decrypt(encryptedPassword);
  }
}