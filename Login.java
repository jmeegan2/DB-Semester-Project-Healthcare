import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel loginPanel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;

    public Login(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(loginPanel);
        this.pack();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usernameField.getText().equals("yourUsername") && new String(passwordField.getPassword()).equals("yourPassword")) {
                    // If the credentials are correct, open the next screen or new screen with new content
                    // If the credentials are correct, open the MainPanel
                    // Remove the login panel from the content pane
                    getContentPane().remove(loginPanel);
                    revalidate();
                    repaint();

                    MainPanel mainPanel = new MainPanel("Oncology Database Main Panel");
                    mainPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    mainPanel.setVisible(true);


                } else {
                    // If the credentials are incorrect, show an error message
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new Login("Oncology Database Login Panel");
        frame.setSize(500, 200);
        frame.setVisible(true);


    }
}
