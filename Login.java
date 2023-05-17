import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JPanel loginPanel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;

    private static Boolean login;

    public Login(String title) {
        super(title);

        // set up the login panel components and layout
        // ...

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(loginPanel);

        // set the preferred size of the JFrame to make it scale nicely
        this.setPreferredSize(new Dimension(500, 200));

        // center the JFrame on the screen
        this.pack();
        this.setLocationRelativeTo(null);

        // add action listener to the login button
        loginButton.addActionListener(e -> {
            if (true) {
                // If any value is considered valid, open the MainPanel
                // Remove the login panel from the content pane
                Login.this.dispose();


                MainPanel mainPanel = new MainPanel("Oncology Database Main Panel");
                mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainPanel.setVisible(true);

            } else {
                // If the credentials are incorrect, show an error message
                JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new Login("Oncology Database Login Panel");
        frame.setVisible(true);
    }
}
