/**
 * CEN 4078 Programming Exercise 1
 * File Name: LoginGUI.java
 *
 * This file is the main file associated with a Login GUI. It contains a field for the login and password.
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

        LOGINButton.addActionListener(new LoginButtonListener());
    }

    public void buildNGENImgPanel() {
        ImageIcon ngenIcon = new ImageIcon("src/resources/ngen.png");
        Image ngenImg = ngenIcon.getImage().getScaledInstance(325, 250, Image.SCALE_SMOOTH);
        ImageIcon scaledNGENIcon = new ImageIcon(ngenImg);
        imageLabel.setIcon(scaledNGENIcon);
    }
    public void buildUserImg() {
        ImageIcon userIcon = new ImageIcon("src/resources/userImgIcon2.png");
        Image userImg = userIcon.getImage().getScaledInstance(115, 115, Image.SCALE_SMOOTH);
        ImageIcon scaledUserIcon = new ImageIcon(userImg);
        userImgLabel.setIcon(scaledUserIcon);
    }


    private class PasswordActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LOGINButton.doClick();
        }
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Validation val = new Validation(userNameField.getText(), userPasswordField.getPassword(), mfaTextField.getText());

            if (val.passesSQLInjection() && val.passesPWPolicy() && val.passesMultiFactorAuthentication() ) {
                LoginValidationWindow valWindow = new LoginValidationWindow(LoginGUI.this);
                valWindow.displaySuccessMessage(userNameField.getText());
                valWindow.setVisible(true);
            }
            else if (!val.passesSQLInjection() || !val.passesPWPolicy() || !val.passesMultiFactorAuthentication()) {
                LoginValidationWindow valWindow = new LoginValidationWindow(LoginGUI.this);
                valWindow.displayFailureMessage();
                valWindow.setVisible(true);
            }
        }
    }

}
