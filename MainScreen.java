import java.sql.*;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainScreen extends JFrame {

    private Connection connection;

    public MainScreen() {
        setTitle("Patient");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            // Get the database connection
            connection = DatabaseConnection.getConnection();

            // Create a statement object
            Statement statement = connection.createStatement();

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery("SELECT Patient_ID FROM mydb.Patient");

            // Create a JTable and set its model to display the result set data
            JTable table = new JTable(buildTableModel(resultSet));
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane);

            // Close the resources
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Helper method to convert a ResultSet to a DefaultTableModel
    public static DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        // Get the number of columns in the result set
        int columnCount = metaData.getColumnCount();

        // Create a vector to hold the column names and data
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        // Add the column name to the vector
        columnNames.add("Patient_ID");

        // Add the data to the vector
        while (resultSet.next()) {
            Vector<Object> row = new Vector<>();
            row.add(resultSet.getObject("Patient_ID"));
            data.add(row);
        }

        // Create a DefaultTableModel with the column names and data vectors
        return new DefaultTableModel(data, columnNames);
    }

    public static void main(String[] args) {
        new MainScreen();
    }
}

