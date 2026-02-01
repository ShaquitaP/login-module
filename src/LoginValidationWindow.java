import javax.swing.*;

public class LoginValidationWindow extends JFrame{
    private JPanel mainPanel;
    private JButton okayButton;
    private JLabel messageLabel;

    public LoginValidationWindow(JFrame parent) {
        final int WIDTH = 300;
        final int HEIGHT = 200;
        setContentPane(mainPanel);
        setTitle("Login Validation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(WIDTH, HEIGHT);

        okayButton.addActionListener(e -> System.exit(0));
    }

    public void displaySuccessMessage(String username) {
        String successMes = "Welcome ";
        messageLabel.setText(successMes + username + "!");
    }
    public void displayFailureMessage() {
        String failureMes = "Login Failed";
        messageLabel.setText(failureMes);
    }


}
