import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class MainPanel extends JFrame{
    private JPanel MainPanel;
    private JButton PatientButton;
    private JButton CreateButton;
    private JButton UpdateButton;
    private JButton DeleteButton;
    private JTable Study_Patient;
    private JTable Study;
    private JTable Appointments;
    private JTable Department;
    private JTable Diagnosis;
    private JTable Patients;
    private JTable Specimens;
    private JPanel TablesPanel;
    private JButton ShowTablesButton;
    private JTable Doctors;



    public MainPanel(String title) {
        super(title);

        // establish the database connection
        Connection connection;
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MainPanel.this, "Database connection error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Department.setModel(DepartmentTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Department")));
            Diagnosis.setModel(DiagnosisTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Diagnosis")));
            Diagnosis.setModel(DiagnosisTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Diagnosis")));
            Specimens.setModel(SpecimenTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Specimen")));
            Study_Patient.setModel(StudyPatientTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Study_Patient")));
            Study.setModel(StudyTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Study")));
            Appointments.setModel(AppointmentsTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Appointments")));
            Doctors.setModel(DoctorTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Doctor")));
            Patients.setModel(PatientTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Patient")));
        }catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MainPanel.this, "Database error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainPanel);
        this.pack();

        TablesPanel.setVisible(false);

// add action listener to the button
        ShowTablesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switch the visibility of the panel
                if (TablesPanel.isVisible()) {
                    TablesPanel.setVisible(false);
                } else {
                    TablesPanel.setVisible(true);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new MainPanel("Oncology Database Main Panel");
        frame.setSize(1000, 500);
        frame.setVisible(true);


    }
}
