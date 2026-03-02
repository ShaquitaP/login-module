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
                {"scientist", "T34KH8s8Tl"},
                {"engineer", "10bYxVXtH7"},
                {"security", "i32lS4z3N7"}
        };

        try {
            PrintWriter writer = new PrintWriter("database.txt");
            Cryptographer crypt = new Cryptographer();

            for (String[] userPair : userDatabase) {
                // uses Cryptographer class to encrypt before writing to database
                writer.println(crypt.encrypt(userPair[0]) + "," + crypt.encrypt(userPair[1]) );
            }
            writer.close();
            LoginGUI lg = new LoginGUI();
        } catch (IOException e) {
            System.out.println("Could not print credentials to User Database.");
        }
    }
}