package SQL;

import java.sql.*;
/*Prints out DB to console*/

public class SQL_Test {

    public static void main(String args[]) {
      final String DATABASE_URL = "jdbc:derby:lib\\CompanyDB";
       final String SELECT_QUERY = "SELECT C_NAME, C_CEO, C_LOCATION  FROM COMPANY";

       try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch(ClassNotFoundException e) {
            System.out.println("Class not found "+ e);
        }
        try {
            Connection con = DriverManager.getConnection(
                    DATABASE_URL,"deitel", "deitel");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT C_NAME,C_CEO,C_LOCATION FROM COMPANY");

            System.out.println("1");
            while (rs.next()) {
                String id = rs.getString("C_NAME");
                String name = rs.getString("C_CEO");
                String job = rs.getString("C_LOCATION");
                System.out.println(id+"   "+name+"    "+job);
            }


        } catch(SQLException e) {
            System.out.println("SQL exception occured" + e);
        }
    }

    }
