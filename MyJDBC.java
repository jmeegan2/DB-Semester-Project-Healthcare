import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyJDBC {
    public static void main(String[] args) {


        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "FoxHound422");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM mydb.Patient");


            while (resultSet.next()) {
                System.out.println(resultSet.getString("Patient_ID"));
            }
        } catch (Exception e ){
            e.printStackTrace();
        }

    }
}
