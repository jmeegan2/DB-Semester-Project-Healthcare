import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private boolean loggedIn = false;

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
                // Check if the username and password are correct
                if (checkLogin(usernameField.getText(), new String(passwordField.getPassword()))) {
                    // Log in the user
                    loggedIn = true;

                    // Close the login dialog
                    dispose();
                } else {
                    // Display an error message
                    JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create the panel to hold the components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Add the username label and field
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(usernameLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        panel.add(usernameField, constraints);

        // Add the password label and field
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0;
        panel.add(passwordLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        panel.add(passwordField, constraints);

        // Add the login button
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.weightx = 0;
        panel.add(loginButton, constraints);

        // Set the panel size
        panel.setPreferredSize(new Dimension(300, 100));

        // Add the panel to the frame
        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean checkLogin(String username, String password) {
        // TODO: Implement actual login validation here
        return true;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
