import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MainGUI extends JFrame {

    private final JPanel contentPanel;

    public MainGUI() {
        setTitle("Main GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prompt the user to log in
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);

        // Wait for user to log in
        while (!loginGUI.isLoggedIn()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // User has logged in, show the tables
        contentPanel = new JPanel(new GridLayout(4, 4)); // 2 rows, 2 columns
        contentPanel.setPreferredSize(new Dimension(800, 600));

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            addPatientTable(statement);
            addDoctorTable(statement);
            addDiagnosisTable(statement);
            addDepartmentTable(statement);
            addAppointmentsTable(statement);
            addSpecimenTable(statement);
            addStudyPatientTable(statement);
            addStudyTable(statement);

            statement.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(MainGUI.this, "Database connection error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        add(contentPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addPatientTable(Statement statement) throws SQLException {
        JTable table = new JTable(PatientGUI.buildTableModel(statement.executeQuery("SELECT * FROM mydb.Patient")));
        contentPanel.add(new JScrollPane(table));
    }

    private void addDoctorTable(Statement statement) throws SQLException {
        JTable table = new JTable(DoctorGUI.buildTableModel(statement.executeQuery("SELECT * FROM mydb.Doctor")));
        contentPanel.add(new JScrollPane(table));
    }

    private void addDiagnosisTable(Statement statement) throws SQLException {
        JTable table = new JTable(DiagnosisGUI.buildTableModel(statement.executeQuery("SELECT * FROM mydb.Diagnosis")));
        contentPanel.add(new JScrollPane(table));
    }

    private void addDepartmentTable(Statement statement) throws SQLException {
        JTable table = new JTable(DepartmentGUI.buildTableModel(statement.executeQuery("SELECT * FROM mydb.Department")));
        contentPanel.add(new JScrollPane(table));
    }

    private void addAppointmentsTable(Statement statement) throws SQLException {
        JTable table = new JTable(AppointmentsGUI.buildTableModel(statement.executeQuery("SELECT * FROM mydb.Appointments")));
        contentPanel.add(new JScrollPane(table));
    }

    private void addSpecimenTable(Statement statement) throws SQLException {
        JTable table = new JTable(SpecimenGUI.buildTableModel(statement.executeQuery("SELECT * FROM mydb.Specimen")));
        contentPanel.add(new JScrollPane(table));
    }

    private void addStudyPatientTable(Statement statement) throws SQLException {
        JTable table = new JTable(StudyPatientGUI.buildTableModel(statement.executeQuery("SELECT * FROM mydb.Study_Patient")));
        contentPanel.add(new JScrollPane(table));
    }

    private void addStudyTable(Statement statement) throws SQLException {
        JTable table = new JTable(StudyGUI.buildTableModel(statement.executeQuery("SELECT * FROM mydb.Study")));
        contentPanel.add(new JScrollPane(table));
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}

