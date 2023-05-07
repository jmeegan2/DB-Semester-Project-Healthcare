import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.*;

public class DatabaseConnection {

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            // Load the MySQL driver class
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new SQLException("MySQL JDBC Driver not found.", e);
            }

            // Set up the connection to the local MySQL database on port 3306
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false",
                    "root",
                    "FoxHound422"
            );
        }

        return connection;
    }
}
