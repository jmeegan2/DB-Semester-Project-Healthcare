import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        // Create the password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        // Create the login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Perform login validation
                JOptionPane.showMessageDialog(LoginGUI.this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Open the main GUI
                new MainGUI();
                dispose();
            }
        });

        // Create the panel to hold the components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        panel.add(usernameLabel, constraints);
        constraints.gridx = 1;
        panel.add(usernameField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);
        constraints.gridx = 1;
        panel.add(passwordField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(loginButton, constraints);

        // Add the panel to the frame
        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}