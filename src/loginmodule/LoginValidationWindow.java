package loginmodule; /**
 * This class handles all logic for creating a login validation window that tells a user whether their login credentials
 * were authenticated or not. This displays "Welcome + username" or "Login Failed" dependent on whether the login credentials
 * are validated.
 *
 * CEN 4078 Programming Exercise 1
 * File Name: LoginValidationWindow.java
 *
 * @author Shaquita Puckett
 * @version 1.0
 * @since 2026-01-02
 */

import javax.swing.*;
import java.awt.*;

public class LoginValidationWindow extends JFrame{
    private JPanel mainPanel;
    private JButton okayButton;
    private JLabel messageLabel;
    private JLabel messageIconLabel;

    /**
     * Overloaded constructor takes in a parent class and sets the login window JFrame information:
     * Width: 400, Length: 300
     * Title: Login Validation
     * Exit On Close
     * Okay Button: Exits both windows
     * @param parent The parent class that handles the input and validation
     */
    public LoginValidationWindow(JFrame parent) {
        final int WIDTH = 400;
        final int HEIGHT = 300;
        setContentPane(mainPanel);
        setTitle("Login Validation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(WIDTH, HEIGHT);

        okayButton.addActionListener(e -> System.exit(0));
    }

    /**
     * Method used to change the message label to display a "Welcome + username!" message along with the authenticate icon.
     * @param username a username from the parent class to name the user
     */
    public void displaySuccessMessage(String username) {
        final int WIDTH = 25;
        final int LENGTH = 25;

        ImageIcon messageIcon = new ImageIcon("src/resources/authentic.png");
        Image messageImg = messageIcon.getImage().getScaledInstance(115, 115, Image.SCALE_SMOOTH);
        ImageIcon scaledMessageIcon = new ImageIcon(messageImg);

        String successMes = "Welcome ";
        messageLabel.setText(successMes + username + "!");
        messageIconLabel.setIcon(scaledMessageIcon);
    }

    /**
     * Method to change the message label to display a "Login Failed" message along with the emergency icon.
     */
    public void displayFailureMessage() {
        final int WIDTH = 25;
        final int LENGTH = 25;

        ImageIcon messageIcon = new ImageIcon("src/resources/emergency.png");
        Image messageImg = messageIcon.getImage().getScaledInstance(115, 115, Image.SCALE_SMOOTH);
        ImageIcon scaledMessageIcon = new ImageIcon(messageImg);

        String failureMes = "Login Failed";
        messageLabel.setText(failureMes);
        messageIconLabel.setIcon(scaledMessageIcon);
    }


}
