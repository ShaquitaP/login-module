package loginmodule; /**
 * This is the main class for running the login window. This class creates the database used for the login validation and
 * displays the login GUI for use.
 *
 * CEN 4078 Programming Exercise 1
 * File Name: LoginDemoMain.java
 *
 * @author Shaquita Puckett
 * @version 1.0
 * @since 2026-01-01
 */

import java.io.IOException;
import java.io.PrintWriter;

public class LoginDemoMain {

    public static void main(String[] args) {
        String[][] userDatabase = {
                {"scientist", "+1:d21jB4'v"},
                {"engineer", "G^5&hM52L94"},
                {"security", "wyD%Z$737cO"}
        };
        try {
            PrintWriter writer = new PrintWriter("database.txt");
            for (String[] userPair : userDatabase) {
                writer.println(userPair[0] + "," + userPair[1]);
            }
            writer.close();
            LoginGUI lg = new LoginGUI();
        } catch (IOException e) {
            System.out.println("Could not print credentials to User Database.");
        }
    }
}
