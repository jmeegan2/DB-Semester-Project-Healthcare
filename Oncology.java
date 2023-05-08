import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Oncology extends JFrame {
    private JPanel mainPanel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;

    public Oncology(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usernameField.getText().equals("yourUsername") && new String(passwordField.getPassword()).equals("yourPassword")) {
                    // If the credentials are correct, open the next screen or new screen with new content
                    JOptionPane.showConfirmDialog(null, "Congrats idiot");
                } else {
                    // If the credentials are incorrect, show an error message
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new Oncology("Oncology Database");
        frame.setVisible(true);


    }
}
