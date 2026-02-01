package loginmodule; /**
 * This class handles all logic for creating a login window used to input user credentials. This class allows the user to
 * input their user credentials and then sends the information for validation.
 *
 * CEN 4078 Programming Exercise 1
 * File Name: LoginGUI.java
 *
 * @author Shaquita Puckett
 * @version 1.0
 * @since 2026-01-02
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {

    private JLabel welcomeLabel;
    private JPanel mainPanel;
    private JPanel welcomePanel;
    private JPanel loginPanel;
    private JTextField userNameField;
    private JPasswordField userPasswordField;
    private JPanel buttonPanel;
    private JButton LOGINButton;
    private JButton EXITButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel mfaLabl;
    private JTextField mfaTextField;
    private JPanel ngenImgPanel;
    private JLabel imageLabel;
    private JLabel imgMessageLabel;
    private JLabel userImgMessagesLabel;
    private JLabel userImgLabel;
    private JLabel usernameIconLabel;
    private JLabel passwordIconLabel;
    private JLabel mfaIconLabel;

    /**
     * Constructor for setting up the window. Contains the methods for building the displayed images and creating the login button listener.
     * Width: 850, Length: 500
     * Title: Login GUI
     * Exit on Close
     */
    public LoginGUI() {
        final int WIDTH = 850;
        final int HEIGHT = 500;
        setContentPane(mainPanel);
        setTitle("Login GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(WIDTH, HEIGHT);

        buildNGENImgPanel();
        buildUserImg();
        buildLoginIcons();

        LOGINButton.addActionListener(new LoginButtonListener());
    }

    /**
     * Method for handling the logic for the image on the left side.
     * Width: 325, Length: 250
     * image location: src/resources/ngen.png
     */
    public void buildNGENImgPanel() {
        ImageIcon ngenIcon = new ImageIcon("src/resources/ngen.png");
        Image ngenImg = ngenIcon.getImage().getScaledInstance(325, 250, Image.SCALE_SMOOTH);
        ImageIcon scaledNGENIcon = new ImageIcon(ngenImg);
        imageLabel.setIcon(scaledNGENIcon);
    }

    /**
     * Method for handling the logic for the user image on the top right side
     * width: 115, length: 115
     * image location: src/resources/userImgIcon2.png
     */
    public void buildUserImg() {
        ImageIcon userIcon = new ImageIcon("src/resources/userImgIcon2.png");
        Image userImg = userIcon.getImage().getScaledInstance(115, 115, Image.SCALE_SMOOTH);
        ImageIcon scaledUserIcon = new ImageIcon(userImg);
        userImgLabel.setIcon(scaledUserIcon);
    }

    /**
     * Method for handling the logic for the icons immediately to the left of the username, password and mfa text fields
     * width: 20, length: 20
     * image location: src/resources/personalization.png
     * image location: src/resources/password.png
     * image location: src/resources/multi-factor.png
     */
    public void buildLoginIcons() {
        final int WIDTH = 20;
        final int LENGTH = 20;

        ImageIcon usernameIcon = new ImageIcon("src/resources/personalization.png");
        Image usernameIconImg = usernameIcon.getImage().getScaledInstance(WIDTH, LENGTH, Image.SCALE_SMOOTH);
        ImageIcon scaledUsernameIcon = new ImageIcon(usernameIconImg);
        usernameIconLabel.setIcon(scaledUsernameIcon);

        ImageIcon passwordIcon = new ImageIcon("src/resources/password.png");
        Image passwordIconImg = passwordIcon.getImage().getScaledInstance(WIDTH, LENGTH, Image.SCALE_SMOOTH);
        ImageIcon scaledPasswordIcon = new ImageIcon(passwordIconImg);
        passwordIconLabel.setIcon(scaledPasswordIcon);

        ImageIcon mfaIcon = new ImageIcon("src/resources/multi-factor.png");
        Image mfaIconImg = mfaIcon.getImage().getScaledInstance(WIDTH, LENGTH, Image.SCALE_SMOOTH);
        ImageIcon scaledMFAIcon = new ImageIcon(mfaIconImg);
        mfaIconLabel.setIcon(scaledMFAIcon);
    }

    /**
     * Private class used to handle the logic for the login button action listener. Once the login button is clicked,
     * all input login information will be sent to the Validation class for authentication, once authenticated this also
     * handles which of the LoginValidationWindow class's messages will be displayed on its window.
     */
    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Validation val = new Validation(userNameField.getText(), userPasswordField.getPassword(), mfaTextField.getText());

            if (val.passesSQLInjection() && val.passesPWPolicy() && val.passesMultiFactorAuthentication() ) {

                if (val.authenticatesUser()) {
                    LoginValidationWindow valWindow = new LoginValidationWindow(LoginGUI.this);
                    valWindow.displaySuccessMessage(userNameField.getText());
                    valWindow.setVisible(true);
                }
                else if (!val.authenticatesUser()) {
                    LoginValidationWindow valWindow = new LoginValidationWindow(LoginGUI.this);
                    valWindow.displayFailureMessage();
                    valWindow.setVisible(true);
                }
            }
            else if (!val.passesSQLInjection() || !val.passesPWPolicy() || !val.passesMultiFactorAuthentication()) {
                LoginValidationWindow valWindow = new LoginValidationWindow(LoginGUI.this);
                valWindow.displayFailureMessage();
                valWindow.setVisible(true);
            }
        }
    }

}
