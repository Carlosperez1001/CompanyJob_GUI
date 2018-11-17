package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

  protected static Connection conn;
  private static String url = "jdbc:derby:C:\\Users\\Carlos Perez\\OneDrive - Florida Gulf Coast "
      + "University\\COP 3003\\CompanyDatabase\\lib\\CompanyDB";
  private static String user = "Deitel";
  private static String pass = "Deitel";

  public static Connection connect() throws SQLException {
    try{
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

    }catch(ClassNotFoundException cnfe){
      System.err.println("Error: "+cnfe.getMessage());
    }catch(InstantiationException ie){
      System.err.println("Error: "+ie.getMessage());
    }catch(IllegalAccessException iae){
      System.err.println("Error: "+iae.getMessage());
    }

    conn = DriverManager.getConnection(url,user,pass);
    return conn;
  }
  public static Connection getConnection() throws SQLException, ClassNotFoundException{
    if(conn !=null && !conn.isClosed())
      return conn;
    connect();
    return conn;

  }

}
