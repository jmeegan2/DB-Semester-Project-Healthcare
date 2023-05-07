import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainGUI extends JFrame {

    private JTable patientTable;
    private JTable doctorTable;
    private JTable departmentTable;

    private JTable studyTable;
    private boolean patientTableVisible;
    private boolean doctorTableVisible;

    private boolean departmentTableVisible;

    private boolean studyTableVisible;

    public MainGUI() {
        setTitle("Main GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the patient table with an empty model
        patientTable = new JTable(new DefaultTableModel());

        // Create the doctor table with an empty model
        doctorTable = new JTable(new DefaultTableModel());

        // Create the department table with an empty model
        departmentTable = new JTable(new DefaultTableModel());

        // Create the department table with an empty model
        studyTable = new JTable(new DefaultTableModel());

        // Initialize the table visibility flags
        patientTableVisible = false;
        doctorTableVisible = false;
        departmentTableVisible = false;
        studyTableVisible = false;
        // Create the panel to hold the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 100, 10)); // 1 row, 4 columns, 10 pixels of horizontal and vertical spacing

// Create the "Patients" button
        JButton showPatientsButton = new JButton("Patients");
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
                        add(new JScrollPane(patientTable), BorderLayout.SOUTH);
                        patientTableVisible = true;
                    }
                    pack();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Database connection error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(showPatientsButton);

// Create the "Doctors" button
        JButton showDoctorsButton = new JButton("Doctors");
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
                        add(new JScrollPane(doctorTable), BorderLayout.CENTER);
                        doctorTableVisible = true;
                    }
                    pack();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Database connection error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(showDoctorsButton);

// Create the "Departments" button
        JButton showDepartmentsButton = new JButton("Departments");
        showDepartmentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the database connection
                    Connection connection = DatabaseConnection.getConnection();

                    // Create a statement object
                    Statement statement = connection.createStatement();

                    // Execute the query and get the result set
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM mydb.Department");

                    // Create a table model with the result set data
                    DefaultTableModel tableModel = DepartmentGUI.buildTableModel(resultSet);

                    // Set the department table model to the table model with the result set data
                    departmentTable.setModel(tableModel);

                    // Close the resources
                    resultSet.close();
                    statement.close();

                    // Toggle the department table visibility flag
                    if (departmentTableVisible) {
                        remove(new JScrollPane(departmentTable));
                        departmentTableVisible = false;
                    } else {
                        add(new JScrollPane(departmentTable), BorderLayout.WEST);
                        departmentTableVisible = true;
                    }
                    pack();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Database connection error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(showDepartmentsButton);

        //Create the "Study Button"
// Create the "Study" button
        JButton showStudyButton = new JButton("Study");
        showStudyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the database connection
                    Connection connection = DatabaseConnection.getConnection();

                    // Create a statement object
                    Statement statement = connection.createStatement();

                    // Execute the query and get the result set
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM mydb.Study");

                    // Create a table model with the result set data
                    DefaultTableModel tableModel = StudyGUI.buildTableModel(resultSet);

                    // Set the study table model to the table model with the result set data
                    studyTable.setModel(tableModel);

                    // Close the resources
                    resultSet.close();
                    statement.close();

                    // Toggle the study table visibility flag
                    if (studyTableVisible) {
                        remove(new JScrollPane(studyTable));
                        studyTableVisible = false;
                    } else {
                        add(new JScrollPane(studyTable), BorderLayout.EAST);
                        studyTableVisible = true;
                    }
                    pack();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Database connection error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(showStudyButton);


// Add the button panel to the frame
        add(buttonPanel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        new MainGUI();
    }
}
