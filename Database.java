
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Database{
  
  // add the username and password pair to the database
  public static void addUsernameAndPasswordToDatabase(String username, String password) throws Exception{
    Class.forName("org.sqlite.JDBC");
    Connection conn = DriverManager.getConnection("jdbc:sqlite:pass_hash.db");
    
    PreparedStatement statement = conn.prepareStatement("insert into passwords values (?, ?);");
    statement.setString(1, username);
    statement.setString(2, password);
    statement.addBatch();
    
    conn.setAutoCommit(false);
    statement.executeBatch();
    conn.setAutoCommit(true);
    
    conn.close();
  }
  
  // create the database and tables if they don't already exist
  public static void createDatabase() throws Exception{
    Class.forName("org.sqlite.JDBC");
    Connection conn = DriverManager.getConnection("jdbc:sqlite:pass_hash.db");
    Statement stat = conn.createStatement();
    stat.executeUpdate("create table if not exists passwords (username, password);");    
    conn.close();
  }
  
  public static String[][] getAllUsernameAndPasswordPairs() throws Exception{
    int numRows = Database.numRowsInDB();
    String result[][] = new String[numRows][2];    
    int index = 0;
    
    Class.forName("org.sqlite.JDBC");
    Connection conn = DriverManager.getConnection("jdbc:sqlite:pass_hash.db");
    Statement stat = conn.createStatement();
    
    ResultSet rs = stat.executeQuery("select * from passwords;");
    
    while (rs.next() && index<numRows){
      result[index][0] = rs.getString("username");
      result[index][1] = rs.getString("password");
      index++;
    }
    
    rs.close();
    conn.close();
    
    return result;
  }
  
  public static int numRowsInDB() throws Exception{
    Class.forName("org.sqlite.JDBC");
    Connection conn = DriverManager.getConnection("jdbc:sqlite:pass_hash.db");
    Statement stat = conn.createStatement();
    ResultSet rs = stat.executeQuery("select count(*) from passwords;");
    
    int result = rs.getInt(1);
    
    rs.close();
    conn.close();
    
    return result;
  }
}