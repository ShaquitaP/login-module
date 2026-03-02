package loginmodule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class RegisterGUI extends JFrame {

    private JPanel mainPanel;
    private JPanel ngenImgPanel;
    private JLabel imageLabel;
    private JLabel imgMessageLabel;
    private JPanel loginPanel;
    private JPasswordField userPasswordField;
    private JLabel passwordLabel;
    private JLabel usernameLabel;
    private JTextField userNameField;
    private JLabel userImgLabel;
    private JLabel userImgMessagesLabel;
    private JLabel usernameIconLabel;
    private JLabel passwordIconLabel;
    private JLabel passwordPolicyLabel;
    private JLabel pwPolicy1;
    private JLabel pwPolicy2;
    private JLabel pwPolicy3;
    private JLabel pwPolicy4;
    private JButton registerButton;
    private JLabel pwAttemptsLabel;


    public RegisterGUI() {
        final int WIDTH = 850;
        final int HEIGHT = 540;
        setContentPane(mainPanel);
        setTitle("Register");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(WIDTH, HEIGHT);

        buildNGENImgPanel();
        buildUserImg();

        registerButton.addActionListener(new RegisterButtonListener());

    }

    /**
     * Method for handling the logic for the image on the left side.
     * Width: 325, Length: 250
     * image location: src/resources/ngen.png
     */
    public void buildNGENImgPanel() {
        ImageIcon ngenIcon = new ImageIcon("src/resources/ngen.png");
        Image ngenImg = ngenIcon.getImage().getScaledInstance(325, 300, Image.SCALE_SMOOTH);
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

    private class RegisterButtonListener implements ActionListener{
        int attemptsRemaining = 2;

        @Override
        public void actionPerformed(ActionEvent e) {
            ValidatorInterface val = new Validation(userNameField.getText(), userPasswordField.getPassword(), new SecurityLogger());

            if (val.passesPWPolicy() && val.passesSQLInjection()) {
                LoginValidationWindow valWindow = new LoginValidationWindow(RegisterGUI.this);

                try {
                    PrintWriter writer = new PrintWriter("database.txt");
                    Cryptographer crypt = new Cryptographer();
                    writer.println(crypt.encrypt(userNameField.getText()) + "," + crypt.encrypt(Arrays.toString(userPasswordField.getPassword())));
                    writer.close();
                    valWindow.displaySuccessMessage(userNameField.getText());
                    valWindow.setVisible(true);
                }
                catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if (!val.passesPWPolicy() || !val.passesSQLInjection()) {
                LoginValidationWindow valWindow = new LoginValidationWindow(RegisterGUI.this);
                if (attemptsRemaining > 0) {
                    --attemptsRemaining;
                    valWindow.displayPasswordFailureMessage(attemptsRemaining);
                    valWindow.setVisible(true);
                }

            }
        }
    }

    public static void main(String[] args) {
        RegisterGUI rg = new RegisterGUI();
    }
}
