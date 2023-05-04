
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Entities_Data.Relationship;
import Entities_Data.Patient;
import Entities_Data.Doctor;
public class App extends JPanel {
    private String[] PatientColumns
            = {"ID", "Fname", "Lname", "DOB", "Treated Y/N", "StudyID", "Next Appointment"};


    private Object[][] Patient = {
            {"01", "Justin", "Bieber", "05/07/1994", "Yes", "12", "05/07/2023"},
            {"02", "Janelle", "Monae", "12/01/1985", "Yes", "32", "05/04/2023"},
            {"03", "Min", "Yoongi", "03/09/1993", "No", "32", null},
            {"04", "Samuel", "Jackson", "12/21/1948", "No", "06", null},
            {"05", "Makayla", "St. Cyr", "06/02/2001", "No", "12", null},
            {"06", "Peter", "Pan", "07/20/1904", "Yes", "12", "05/24/2023"},
            {"07", "Holden", "Door", "07/16/1949", "Yes", "12", "05/04/2023"}
    };

    private DefaultTableModel patientModel = new DefaultTableModel(Patient, PatientColumns);
    // Define columns for the Doctor table
    private String[] doctorColumns = {"Doctor_ID", "Fname", "Lname"};

    // Define rows for the Doctor table
    private Object[][] doctorData = {
            {"101", "John", "Doe"},
            {"102", "Jane", "Smith"},
            {"103", "Alex", "Johnson"}
    };
    private DefaultTableModel doctorModel = new DefaultTableModel(doctorData, doctorColumns);

    // Define the relationship between Patient and Doctor
    private String relationshipName = "assigned";
    private String[] relationshipColumns = {"Patient ID", "Doctor ID"};
    private Object[][] relationshipData = {
            {"01", "D01"},
            {"02", "D02"},
            {"03", "D01"},
            {"04", "D03"},
            {"05", "D01"},
            {"06", "D02"},
            {"07", "D01"}
    };
    private DefaultTableModel relationshipModel = new DefaultTableModel(relationshipData, relationshipColumns);

    private JTable patientTable = new JTable(patientModel) {
        public boolean editCellAt(int row, int column, java.util.EventObject e) {
            return false;
        }
    };

    private JTable doctorTable = new JTable(doctorModel) {
        public boolean editCellAt(int row, int column, java.util.EventObject e) {
            return false;
        }
    };

    private TableRowSorter<TableModel> patientRowSorter = new TableRowSorter<>(patientTable.getModel());
    private TableRowSorter<TableModel> doctorRowSorter = new TableRowSorter<>(doctorTable.getModel());

    private JTextField patientFilterField = new JTextField();
    private JButton patientFilterButton = new JButton("Filter");
    private JTextField doctorFilterField = new JTextField();
    private JButton doctorFilterButton = new JButton("Filter");

    public App() {
        JTable patientTable = new JTable(patientModel) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        patientTable.setRowSorter(patientRowSorter);
        patientTable.setFocusable(false);
        patientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {     // to detect double click events
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow(); // select a row
                    JOptionPane.showMessageDialog(null, patientTable.getValueAt(row, 1)); // get the value of a row and column.
                }
            }
        });

        JTable doctorTable = new JTable(doctorModel) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        doctorTable.setRowSorter(doctorRowSorter);
        doctorTable.setFocusable(false);
        doctorTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {     // to detect double click events
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow(); // select a row
                    JOptionPane.showMessageDialog(null, doctorTable.getValueAt(row, 1)); // get the value of a row and column.
                }
            }
        });

        JPanel patientPanel = new JPanel(new BorderLayout());
        patientPanel.add(new JLabel("Search for a patient:"), BorderLayout.WEST);
        patientPanel.add(patientFilterField, BorderLayout.CENTER);

        JPanel doctorPanel = new JPanel(new BorderLayout());
        doctorPanel.add(new JLabel("Search for a doctor:"), BorderLayout.WEST);
        doctorPanel.add(doctorFilterField, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        JPanel tablePanel = new JPanel(new GridLayout(2, 1));
        tablePanel.add(new JScrollPane(patientTable));
        tablePanel.add(new JScrollPane(doctorTable));
        add(tablePanel, BorderLayout.CENTER);
        add(patientPanel, BorderLayout.SOUTH);

        patientFilterField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = patientFilterField.getText();

                if (text.trim().length() == 0) {
                    patientRowSorter.setRowFilter(null);
                } else {
                    patientRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = patientFilterField.getText();

                if (text.trim().length() == 0) {
                    patientRowSorter.setRowFilter(null);
                } else {
                    patientRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        doctorFilterField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = doctorFilterField.getText();

                if (text.trim().length() == 0) {
                    doctorRowSorter.setRowFilter(null);
                } else {
                    doctorRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = doctorFilterField.getText();
                if (text.trim().length() == 0) {
                    doctorRowSorter.setRowFilter(null);
                } else {
                    doctorRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                JFrame frame = new JFrame("Row Filter");
                frame.add(new App());
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        });
    }
}
