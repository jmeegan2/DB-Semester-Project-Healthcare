import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PatientGUI extends JFrame {



    // Helper method to show the patients
    private void showPatients() {
        try {
            // Get the database connection
            Connection connection = DatabaseConnection.getConnection();

            // Create a statement object
            Statement statement = connection.createStatement();

            // Execute the queries and get the result sets
            ResultSet patientResultSet = statement.executeQuery("SELECT * FROM mydb.Patient");
            ResultSet diagnosisResultSet = statement.executeQuery("SELECT * FROM mydb.Diagnosis");

            // Create table models with the result set data
            DefaultTableModel patientTableModel = buildTableModel(patientResultSet);
            DefaultTableModel diagnosisTableModel = buildTableModel(diagnosisResultSet);

            // Set the patient table model to the table model with the result set data
            JTable patientTable = (JTable) getContentPane().getComponent(1);
            patientTable.setModel(patientTableModel);

            // Set the diagnosis table model to the table model with the result set data
            JTable diagnosisTable = (JTable) getContentPane().getComponent(2);
            diagnosisTable.setModel(diagnosisTableModel);

            // Close the resources
            patientResultSet.close();
            diagnosisResultSet.close();
            statement.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to convert a ResultSet to a DefaultTableModel
    public static DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        // Get the number of columns in the result set
        int columnCount = metaData.getColumnCount();

        // Create a vector to hold the column names and data
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        // Add the column names to the vector
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnLabel(i));
        }

        // Add the data to the vector
        while (resultSet.next()) {
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getObject(i));
            }
            data.add(row);
        }

        // Create a DefaultTableModel with the column names and data vectors
        return new DefaultTableModel(data, columnNames);
    }
}



