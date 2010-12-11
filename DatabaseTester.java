/* This class simply uses the other classes to take in the username/password
 * and show how everything works.
 */

import java.util.Scanner;

public class DatabaseTester{
  public static void main(String[] args) throws Exception{
    Scanner keyboard = new Scanner(System.in);
    System.out.println("DatabaseTester - Let's encrypt and store some words");
    
    System.out.print("Enter the site name: ");
    String siteName = keyboard.nextLine();
    
    System.out.print("Enter your username: ");
    String username = keyboard.nextLine();
    
    System.out.print("Enter your password: ");
    String password = keyboard.nextLine();
    
    System.out.println("Site Name: " + siteName + "Your username: " + username + "\nYour password: " + password);
    
    // the Password class automatically encrypts the password when you instantiate it
    // the constructor takes a string
    Password passwd = new Password(password);
    
    // Password's toString method returns the encrypted password
    System.out.println("Your encrypted password: " + passwd);
    
    // This is how you decrypt the password stored in the object
    System.out.println("Your decrypted password: " + passwd.getDecryptedPassword());
    
    // Password also has a static method to decrypt a formerly encrypyted/stored string
    System.out.println("Your decrypted password with Password's static method: " + Password.decryptPassword(passwd.toString()));
    
    // when we first run the program, we'll need to create the database
    // createDatabase() only creates it if it doesn't already exist
    // Database is abstract, so don't try to instantiate it
    Database.createDatabase();
    
    // to add a username and password to the database, just use this method
    Database.addUsernameAndPasswordToDatabase(siteName, username, passwd.toString());
    
    // this method will return all the rows of the database in a 2-dimensional array
    String passwords[][] = Database.getAllUsernameAndPasswordPairs();
    
    // this just prints the pairs
    System.out.println("Here's all the pairs in the database!!");
    for(int i=0; i<passwords.length; i++){
      for(int j=0; j<passwords[i].length; j++){
        System.out.print(passwords[i][j] + "\t");
      }
      System.out.print("\n");
    }
    
    // Master Password stuff
    System.out.println("Master Password exists in db? " + Database.hasMasterPassword());
    
    System.out.print("Enter new Master Password: ");
    String masterPassword = keyboard.nextLine();
    
    Password masterP = new Password(masterPassword);
    
    Database.setMasterPasswordInDB(masterP.toString());
        
    String masterPasswordFromDB = Database.getMasterPassword();
    System.out.println("Master Password from DB: " + masterPasswordFromDB);
    
    System.out.print("Enter Master Password: ");
    String possibleMasterPassword = keyboard.next();
    
    if(Password.isCorrectPassword(masterPasswordFromDB, possibleMasterPassword)){
      System.out.println("That password is correct");
    } else {
      System.out.println("That password is incorrect");
    }
    
  }
}