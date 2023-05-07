import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudyGUI extends JFrame {

    private JTable studyTable;
    private Connection connection;

    public StudyGUI() {
        setTitle("Study GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the "Show" button
        JButton showButton = new JButton("Show Studies");
        showButton.addActionListener(new ActionListener() {
            private boolean tableVisible = false; // Keep track of whether the table is visible

            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the database connection
                    connection = DatabaseConnection.getConnection();

                    // Create a statement object
                    Statement statement = connection.createStatement();

                    // Execute the query and get the result set
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM mydb.Study");

                    // Create a JTable and set its model to display the result set data
                    if (!tableVisible) {
                        // Table is not visible, so display it
                        studyTable.setModel(buildTableModel(resultSet));
                        tableVisible = true;
                    } else {
                        // Table is visible, so hide it
                        studyTable.setModel(new DefaultTableModel());
                        tableVisible = false;
                    }

                    // Close the resources
                    resultSet.close();
                    statement.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(StudyGUI.this, "Database connection error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create the JTable with an empty model
        studyTable = new JTable(new DefaultTableModel());

        // Add the components to the frame
        add(showButton, BorderLayout.NORTH);
        add(new JScrollPane(studyTable), BorderLayout.CENTER);

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
