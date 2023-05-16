import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class MainPanel extends JFrame{
    private JPanel MainPaneZ;
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
    private JPanel DeletePanel;
    private JPanel UpdatePanel;
    private JPanel CreatePanel;
    private JTextField Patient_ID_Delete;
    private JLabel Patient_ID_Delete_Btn;
    private JButton DeleteBtnAction;
    private JLabel Patient_ID_Create;
    private JTextField Create_Patient_ID;
    private JTextField Create_Fname;
    private JTextField Create_Lname;
    private JFormattedTextField Create_DOB;
    private JTextField Create_Treated;
    private JTextField Create_Doctor_ID;
    private JButton Create_Btn;
    private JTextField New_Create_DOB;
    private JCheckBox Checkbox_Treated;
    private JTextField Search_Patient_ID;
    private JLabel SearchLabel;
    private JButton Search_Btn;
    private JCheckBox Search_Update_Treated;
    private JPanel Search_Filled;
    private JTextField Search_Fname_Fill;
    private JTextField Seach_Lname_Fill;
    private JTextField Search_DOB_Fill;
    private JLabel doctorLabel;
    private JTextField Search_Doctor_ID;
    private JLabel doesntMatter;
    private JButton SEND_UpdateBtn;
    private JTextField Update_Lname;
    private JTextField Update_DOB;
    private JTextField Update_Doctor_ID;
    private JTextField Update_Fname;
    private JPanel UpdateSecondaryPanel;
    private JCheckBox Update_Treated_Value;
    private JButton SearchBTNRightSide;
    private JTextField TextFieldPatient;
    private JButton sendButton;
    private JPanel Patient_IdSearchPanel;
    private JPanel PatientInformationPanel;
    private JTable SearchedPatientTable;
    private JTable SearchedPatientDoctor;
    private JTable SearchedPatientDiagnosis;
    private JTable SearchedPatientSpecimens;
    private JTable SearchedPatientAppt;
    private JTable SearchedPatientStudy_Patient;
    private JTable SearchedPatientStudy;
    private JScrollPane ds;


    public MainPanel(String title) {
        super(title);
        ShowTablesButton.setPreferredSize(new Dimension(200, 75));
        CreateButton.setPreferredSize(new Dimension(200, 75));
        UpdateButton.setPreferredSize(new Dimension(200, 75));
        DeleteButton.setPreferredSize(new Dimension(200, 75));
        SearchBTNRightSide.setPreferredSize(new Dimension(200, 75));

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
        this.setContentPane(MainPaneZ);
        this.pack();
        this.setLocationRelativeTo(null);
        TablesPanel.setVisible(false);

        TablesPanel.setPreferredSize(new Dimension(1100, 500));
        ShowTablesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switch the visibility of the panel
                if (TablesPanel.isVisible()) {
                    TablesPanel.setVisible(false);
                } else {
                    TablesPanel.setVisible(true);
                    UpdatePanel.setVisible(false);
                    CreatePanel.setVisible(false);
                    DeletePanel.setVisible(false);

                }
            }
        });

        DeletePanel.setVisible(false);
        DeletePanel.setPreferredSize(new Dimension(400,250));
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switch the visibility of the panel
                if (DeletePanel.isVisible()) {
                    DeletePanel.setVisible(false);
                } else {
                    TablesPanel.setVisible(false);
                    UpdatePanel.setVisible(false);
                    CreatePanel.setVisible(false);
                    DeletePanel.setVisible(true);

                }

            }
        });


        CreatePanel.setVisible(false);
        CreatePanel.setPreferredSize(new Dimension(400,250));
        CreateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switch the visibility of the panel
                if (CreatePanel.isVisible()) {
                    CreatePanel.setVisible(false);
                } else {
                    TablesPanel.setVisible(false);
                    UpdatePanel.setVisible(false);
                    CreatePanel.setVisible(true);
                    DeletePanel.setVisible(false);
                }

            }
        });
        UpdatePanel.setPreferredSize(new Dimension(400,250));
        UpdateSecondaryPanel.setPreferredSize(new Dimension(400,250));
        UpdatePanel.setVisible(false);
        UpdateSecondaryPanel.setVisible(false);
        Search_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the patient ID to search for from the text field
                String patientID = Search_Patient_ID.getText();

                try {
                    // check if the patient exists in the database
                    String sql = "SELECT * FROM mydb.Patient WHERE Patient_ID = " + patientID;
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);

                    if (resultSet.next()) {
                        // patient found
                        String message = "Found Patient: " + resultSet.getString("Patient_ID");
                        int confirm = JOptionPane.showConfirmDialog(MainPanel.this, message + "\nDo you want to update this patient?");

                        if (confirm == JOptionPane.YES_OPTION) {
                            // fill the fields with patient information
                            // fill the fields with patient information
                            togglePanelVisibility(PatientInformationPanel);

//                            String treated = Search_Update_Treated_Checkbox.isSelected() ? "Y" : "N";
                            String treated = resultSet.getString("Treated");
                            if (treated.equals("Y")) {
                                Search_Update_Treated.setSelected(true);
                            } else {
                                Search_Update_Treated.setSelected(false);
                            }
                            Search_Fname_Fill.setText(resultSet.getString("Fname"));
                            Seach_Lname_Fill.setText(resultSet.getString("Lname"));
                            Search_DOB_Fill.setText(resultSet.getString("DOB"));
                            Search_Doctor_ID.setText(resultSet.getString("Doctor_ID"));
                        }
                    } else {
                        // patient not found
                        JOptionPane.showMessageDialog(MainPanel.this, "Patient not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MainPanel.this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        SEND_UpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the patient ID to update from the search panel
                String patientID = Search_Patient_ID.getText();

                // get the updated patient information from the update panel
                String updateFname = Update_Fname.getText();
                String updateLname = Update_Lname.getText();
                String updateDOB = Update_DOB.getText();
                String updateDoctorID = Update_Doctor_ID.getText();
                String updateTreated = Search_Update_Treated.isSelected() ? "Y" : "N";

                try {
                    // update the patient information in the database
                    String sql = "UPDATE mydb.Patient SET Fname = ?, Lname = ?, DOB = ?, Doctor_ID = ?, Treated = ? WHERE Patient_ID = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, updateFname);
                    statement.setString(2, updateLname);
                    statement.setString(3, updateDOB);
                    statement.setString(4, updateDoctorID);
                    statement.setString(5, updateTreated);
                    statement.setString(6, patientID);

                    int rowsAffected = statement.executeUpdate();

                    // check if the update was successful
                    if (rowsAffected == 0) {
                        JOptionPane.showMessageDialog(MainPanel.this, "Patient not found", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(MainPanel.this, "Patient updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // hide the update panel
                        UpdateSecondaryPanel.setVisible(false);

                        // refresh the table to show the updated data
                        Patients.setModel(PatientTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Patient")));
                        Search_Patient_ID.setText("");
                        Search_Update_Treated.setSelected(false);
                        Search_Fname_Fill.setText("");
                        Seach_Lname_Fill.setText("");
                        Search_DOB_Fill.setText("");
                        Search_Doctor_ID.setText("");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MainPanel.this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        UpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switch the visibility of the panel
                if (UpdatePanel.isVisible()) {
                    UpdatePanel.setVisible(false);
                    UpdateSecondaryPanel.setVisible(false);
                } else {
                    TablesPanel.setVisible(false);
                    UpdatePanel.setVisible(true);
                    CreatePanel.setVisible(false);
                    DeletePanel.setVisible(false);
                }

            }
        });
        DeleteBtnAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the patient ID to delete from the text field
                String patientID = Patient_ID_Delete.getText();

                try {
                    // delete the specimens associated with the patient
                    int confirmSpecimens = JOptionPane.showConfirmDialog(MainPanel.this, "Are you sure you want to delete all specimens associated with this patient?");
                    if (confirmSpecimens == JOptionPane.YES_OPTION) {
                        String sqlDeleteSpecimens = "DELETE FROM mydb.Specimen WHERE Patient_ID = " + patientID;
                        Statement statementSpecimen = connection.createStatement();
                        int rowsAffectedSpecimen = statementSpecimen.executeUpdate(sqlDeleteSpecimens);

                        // check if the deletion was successful
                        if (rowsAffectedSpecimen > 0) {
                            JOptionPane.showMessageDialog(MainPanel.this, rowsAffectedSpecimen + " Specimens records deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                    // delete the diagnoses associated with the patient
                    int confirmDiagnoses = JOptionPane.showConfirmDialog(MainPanel.this, "Are you sure you want to delete all diagnoses associated with this patient?");
                    if (confirmDiagnoses == JOptionPane.YES_OPTION) {
                        String sqlDeleteDiagnoses = "DELETE FROM mydb.Diagnosis WHERE Patient_ID = " + patientID;
                        Statement statementDiagnosis = connection.createStatement();
                        int rowsAffectedDiagnosis = statementDiagnosis.executeUpdate(sqlDeleteDiagnoses);

                        // check if the deletion was successful
                        if (rowsAffectedDiagnosis > 0) {
                            JOptionPane.showMessageDialog(MainPanel.this, rowsAffectedDiagnosis + " Diagnoses records of patient deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                    // delete the study patients associated with the patient
                    int confirmStudyPatients = JOptionPane.showConfirmDialog(MainPanel.this, "Are you sure you want to delete all study records associated with this patient?");
                    if (confirmStudyPatients == JOptionPane.YES_OPTION) {
                        String sqlDeleteStudyPatients = "DELETE FROM mydb.Study_Patient WHERE Patient_ID = " + patientID;
                        Statement statementStudyPatient = connection.createStatement();
                        int rowsAffectedStudyPatient = statementStudyPatient.executeUpdate(sqlDeleteStudyPatients);

                        // check if the deletion was successful
                        if (rowsAffectedStudyPatient > 0) {
                            JOptionPane.showMessageDialog(MainPanel.this, rowsAffectedStudyPatient + " Patient Study records deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                    // delete the appointments associated with the patient
                    int confirmAppointments = JOptionPane.showConfirmDialog(MainPanel.this, "Are you sure you want to delete all appointments associated with this patient?");
                    if (confirmAppointments == JOptionPane.YES_OPTION) {
                        String sqlDeleteAppointments = "DELETE FROM mydb.Appointments WHERE Patient_ID = " + patientID;
                        Statement statementAppointments = connection.createStatement();
                        int rowsAffectedAppointments = statementAppointments.executeUpdate(sqlDeleteAppointments);

                        // check if the deletion was successful
                        if (rowsAffectedAppointments > 0) {
                            JOptionPane.showMessageDialog(MainPanel.this, rowsAffectedAppointments + " Appointments for patient deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                    // delete the patient from the database
                    int confirmPatient = JOptionPane.showConfirmDialog(MainPanel.this, "Are you sure you want to delete this patient?");
                    if (confirmPatient == JOptionPane.YES_OPTION) {
                        String sqlDeletePatient = "DELETE FROM mydb.Patient WHERE Patient_ID = " + patientID;
                        Statement statementPatient = connection.createStatement();
                        int rowsAffectedPatient = statementPatient.executeUpdate(sqlDeletePatient);

                        // check if the deletion was successful
                        if (rowsAffectedPatient == 0) {
                            JOptionPane.showMessageDialog(MainPanel.this, "Patient not found", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(MainPanel.this, "Patient deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }

                        Patients.setModel(PatientTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Patient")));
                        Diagnosis.setModel(DiagnosisTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Diagnosis")));
                        Specimens.setModel(SpecimenTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Specimen")));
                        Study_Patient.setModel(StudyPatientTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Study_Patient")));
                        Study.setModel(StudyTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Study")));
                        Appointments.setModel(AppointmentsTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Appointments")));
                        Doctors.setModel(DoctorTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Doctor")));
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MainPanel.this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        Create_Btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // get the values of the text fields for creating a new patient
                    String patientID = Create_Patient_ID.getText();
                    String firstName = Create_Fname.getText();
                    String lastName = Create_Lname.getText();
                    String dob = New_Create_DOB.getText();
                    String treated = Checkbox_Treated.isSelected() ? "Y" : "N"; // convert boolean to ENUM value
                    String doctorID = Create_Doctor_ID.getText();

                    // build the SQL query to insert a new patient into the database
                    String sqlInsertPatient = "INSERT INTO mydb.Patient (Patient_ID, Fname, Lname, DOB, Treated, Doctor_ID) VALUES ('" + patientID + "', '" + firstName + "', '" + lastName + "', '" + dob + "', '" + treated + "', '" + doctorID + "')";

                    try {
                        // execute the query
                        Statement statement = connection.createStatement();
                        int rowsAffected = statement.executeUpdate(sqlInsertPatient);

                        // check if the insertion was successful
                        if (rowsAffected == 0) {
                            JOptionPane.showMessageDialog(MainPanel.this, "Failed to create new patient", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(MainPanel.this, "New patient created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }

                        // refresh the table to show the updated data
                        Patients.setModel(PatientTable.buildTableModel(connection.createStatement().executeQuery("SELECT * FROM mydb.Patient")));

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(MainPanel.this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

        PatientInformationPanel.setVisible(false);
        Patient_IdSearchPanel.setVisible(false);
        SearchBTNRightSide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePanelVisibility(Patient_IdSearchPanel);
                if(PatientInformationPanel.isVisible()) {
                    PatientInformationPanel.setVisible(false);
                }
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patientID_SearchPanel = TextFieldPatient.getText();
                try {
                    // check if the patient exists in the database
                    String sql = "SELECT * FROM mydb.Patient WHERE Patient_ID = " + patientID_SearchPanel;
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);

                    if (resultSet.next()) {
                        // patient found
                        PatientInformationPanel.setVisible(true);
                        String message = "Found Patient: " + resultSet.getString("Patient_ID");
                        int confirm = JOptionPane.showConfirmDialog(MainPanel.this, message + "\nWould you like to view patients data?");

                        if (confirm == JOptionPane.YES_OPTION) {
                            //This is the search left side button Search area
                            String patientId = patientID_SearchPanel;
                            String PatientQuery = "SELECT * FROM mydb.Patient WHERE Patient_ID = '" + patientId + "'";
                            String PatientDiagnosisQuery = "SELECT * FROM mydb.Diagnosis WHERE Patient_ID = '" + patientId + "'";
                            String PatientAppt = "SELECT * FROM mydb.Appointments WHERE Patient_ID = '" + patientId + "'";
                            String PatientStudyPatient = "SELECT * FROM mydb.Study_Patient WHERE Patient_ID = '" + patientId + "'";
                            String PatientSpecimen = "SELECT * FROM mydb.Specimen WHERE Patient_ID = '" + patientId + "'";
                            String PatientDoctor = "SELECT d.* FROM mydb.Doctor d INNER JOIN mydb.Patient p ON d.Doctor_ID = p.Doctor_ID WHERE p.Patient_ID = '" + patientId + "'";

                            SearchedPatientDoctor.setModel(DoctorTable.buildTableModel(connection.createStatement().executeQuery(PatientDoctor)));

                            SearchedPatientTable.setModel(PatientTable.buildTableModel(connection.createStatement().executeQuery(PatientQuery)));
                            SearchedPatientDiagnosis.setModel(DiagnosisTable.buildTableModel(connection.createStatement().executeQuery(PatientDiagnosisQuery)));
                            SearchedPatientAppt.setModel(AppointmentsTable.buildTableModel(connection.createStatement().executeQuery(PatientAppt)));
                            SearchedPatientStudy_Patient.setModel(StudyPatientTable.buildTableModel(connection.createStatement().executeQuery(PatientStudyPatient)));
                            SearchedPatientSpecimens.setModel(SpecimenTable.buildTableModel(connection.createStatement().executeQuery(PatientSpecimen)));

                            String patientStudyQuery = "SELECT s.* FROM mydb.Study s INNER JOIN mydb.Study_Patient sp ON s.Study_ID = sp.Study_ID WHERE sp.Patient_ID = '" + patientId + "'";

                            SearchedPatientStudy.setModel(StudyTable.buildTableModel(connection.createStatement().executeQuery(patientStudyQuery)));                        }
                    } else {
                        // patient not found
                        JOptionPane.showMessageDialog(MainPanel.this, "Patient not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MainPanel.this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new MainPanel("Oncology Database Main Panel");
        frame.setSize(1250, 1000);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);


    }
    private void togglePanelVisibility(JPanel panel) {
        boolean isPanelVisible = panel.isVisible(); // Get the current visibility state
        panel.setVisible(!isPanelVisible); // Toggle the visibility of the panel
    }
}
