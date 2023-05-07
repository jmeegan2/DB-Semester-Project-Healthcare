import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DoctorGUI extends JFrame {

    private JButton showButton;
    private JTable table;
    private Connection connection;

    public DoctorGUI() {
        setTitle("Doctor GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the "Show" button
        showButton = new JButton("Show Doctors");
        showButton.addActionListener(new ActionListener() {
            private boolean tableVisible = false; // Keep track of whether the table is visible

            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the database connection
                    connection = DatabaseConnection.getConnection();

                    // Create a statement object
                    Statement statement = connection.createStatement();

                    // Execute the query and get the result set
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM mydb.Doctor");

                    // Create a JTable and set its model to display the result set data
                    if (!tableVisible) {
                        // Table is not visible, so display it
                        table.setModel(buildTableModel(resultSet));
                        tableVisible = true;
                    } else {
                        // Table is visible, so hide it
                        table.setModel(new DefaultTableModel());
                        tableVisible = false;
                    }

                    // Close the resources
                    resultSet.close();
                    statement.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(DoctorGUI.this, "Database connection error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create the JTable with an empty model
        table = new JTable(new DefaultTableModel());

        // Add the components to the frame
        add(showButton, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

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
