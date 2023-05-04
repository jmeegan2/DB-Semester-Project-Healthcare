
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class App extends JPanel{
    private String[] columnNames
            = {"ID", "Name", "StudyID", "Next Appointment"};

    private Object[][] attributes = {
            {"Name", null},
            {"Address", null},
            {"Doctor", null},
            {"Study", null},
            {"Participation", null}
    };
    private Object[][] data = {
            {"01", "Justin Bieber", "12", "May 07"},
            {"02", "Janelle Monae", "32", "May 04"},
            {"03", "Min Yoongi", "32", null},
            {"04", "Samuel Jackson", "06", null},
            {"05", "Makayla St. Cyr", "12", null},
            {"06", "Peter Pan", "12", "May 24"},
            {"07", "Holden Door", "12", "May 04"}
    };

    private DefaultTableModel model = new DefaultTableModel(data, columnNames);
    private JTable jTable = new JTable(model) {
        public boolean editCellAt(int row, int column, java.util.EventObject e) {
            return false;
        }
    };

    private TableRowSorter<TableModel> rowSorter
            = new TableRowSorter<>(jTable.getModel());

    private JTextField jtfFilter = new JTextField();
    private JButton jbtFilter = new JButton("Filter");

    public App() {
        jTable.setRowSorter(rowSorter);
        jTable.setFocusable(false);
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {     // to detect double click events
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow(); // select a row
                    JFrame patientFrame = new JFrame((String) jTable.getValueAt(row, 1));
                    JTable patientInfo = new JTable(new DefaultTableModel(attributes, new String[]{"Attributes", "Info"}));
                    patientFrame.add(patientInfo);
                    patientFrame.pack();
                    patientFrame.setLocationRelativeTo(null);
                    patientFrame.setVisible(true);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Search for a patient:"),
                BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        add(new JScrollPane(jTable), BorderLayout.CENTER);

        jtfFilter.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
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
                JFrame frame = new JFrame("Patients");
                frame.add(new App());
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        });
    }
}
