import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainGUI extends JFrame {

    private JTable patientTable;
    private JTable doctorTable;
    private boolean patientTableVisible;
    private boolean doctorTableVisible;

    public MainGUI() {
        setTitle("Main GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the patient table with an empty model
        patientTable = new JTable(new DefaultTableModel());

        // Create the doctor table with an empty model
        doctorTable = new JTable(new DefaultTableModel());

        // Initialize the table visibility flags
        patientTableVisible = false;
        doctorTableVisible = false;

        // Create the "Show Patients" button
        JButton showPatientsButton = new JButton("Show Patients");
        showPatientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the database connection
                    Connection connection = DatabaseConnection.getConnection();

                    // Create a statement object
                    Statement statement = connection.createStatement();

                    // Execute the query and get the result set
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM mydb.Patient");

                    // Create a table model with the result set data
                    DefaultTableModel tableModel = PatientGUI.buildTableModel(resultSet);

                    // Set the patient table model to the table model with the result set data
                    patientTable.setModel(tableModel);

                    // Close the resources
                    resultSet.close();
                    statement.close();

                    // Toggle the patient table visibility flag
                    if (patientTableVisible) {
                        remove(new JScrollPane(patientTable));
                        patientTableVisible = false;
                    } else {
                        add(new JScrollPane(patientTable), BorderLayout.WEST);
                        patientTableVisible = true;
                    }
                    pack();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Database connection error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create the "Show Doctors" button
        JButton showDoctorsButton = new JButton("Show Doctors");
        showDoctorsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the database connection
                    Connection connection = DatabaseConnection.getConnection();

                    // Create a statement object
                    Statement statement = connection.createStatement();

                    // Execute the query and get the result set
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM mydb.Doctor");

                    // Create a table model with the result set data
                    DefaultTableModel tableModel = DoctorGUI.buildTableModel(resultSet);

                    // Set the doctor table model to the table model with the result set data
                    doctorTable.setModel(tableModel);

                    // Close the resources
                    resultSet.close();
                    statement.close();

                    // Toggle the doctor table visibility flag
                    if (doctorTableVisible) {
                        remove(new JScrollPane(doctorTable));
                        doctorTableVisible = false;
                    } else {
                        add(new JScrollPane(doctorTable), BorderLayout.EAST);
                        doctorTableVisible = true;
                    }
                    pack();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Database connection error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the components to the frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(showPatientsButton);
        buttonPanel.add(showDoctorsButton);
        add(buttonPanel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        new MainGUI();
    }
}
