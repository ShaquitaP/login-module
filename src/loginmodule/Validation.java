package loginmodule; /**
 * This class handles all input validation for the login credentials including a SQL Injection check, password policy enforcement,
 * and integer overflow detection for MFA codes.
 *
 * CEN4078 Programming Exercise 1
 * File Name: Validation.java
 *
 * @author Shaquita Puckett
 * @version 1.0
 * @since 2026-01-02
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Validation implements ValidatorInterface{
    private String username = "";
    private char[] password;
    private String mfa = "";
    private SecurityLogger logger;

    /**
     * Overloaded constructor to initialize the username, password and mfa variables
     * @param username String representing user's username
     * @param password char array representing user's password
     * @param mfa String representing multi-factor authentication
     * @param logger ILogger object representing a logger for logging validation
     */
    public Validation(String username, char[] password, String mfa, SecurityLogger logger) {
        setUsername(username);
        setPassword(password);
        setMFA(mfa);
        setLogger(logger);
    }

    public void setUsername(String username) {this.username = username;}
    public void setPassword(char[] password) {this.password = password;}
    public void setMFA(String mfa) {this.mfa = mfa;}
    public void setLogger(SecurityLogger logger) {this.logger = logger;}

    /**
     * Method used for SQL Injection prevention. It uses hasForbiddenCharacters() to check whether
     * the username and password have forbidden characters.
     * @return false if either the username or password contain forbidden characters, and true if neither has them
     */
    public boolean passesSQLInjection() {
        if (hasForbiddenCharacters(username.toCharArray()) || hasForbiddenCharacters(password)){
            logger.logEvent("LOGIN_ATTEMPT", username, false, "SQLINJECTIONDETECTED");
        }
        logger.logEvent("LOGIN_ATTEMPT", username, true);
        return !hasForbiddenCharacters(username.toCharArray()) && !hasForbiddenCharacters(password);
    }

    /**
     * Private method to check whether a char array contains forbidden characters.
     * The forbidden characters are as followed:
     * '/'
     * '-'
     * ';'
     * '"'
     * @param input, a char array
     * @return true if the array contains the forbidden characters, and false otherwise
     */
    private boolean hasForbiddenCharacters(char [] input) {
        for (char c : input) {
            if ( (c == '/') || (c == '-') || (c == ';') || (c == '"') ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check whether the password abides by the rules in the password policy. The method uses private helper methods:
     * hasDigitChar(), hasCorrectPasswordLength(), hasUpperCaseChar() and hasLowerCaseChar().
     * @return false if any of the rules fail, and true only if policy checks pass
     */
    public boolean passesPWPolicy() {
        if (!hasDigitChar() || !hasCorrectPasswordLength() || !hasLowerCaseChar() || !hasUpperCaseChar()) {
            logger.logEvent("LOGIN_ATTEMPT", username, false, "PASSWORDPOLICYVIOLATION");
        }
        logger.logEvent("LOGIN_ATTEMPT", username, true);
        return hasDigitChar() && hasCorrectPasswordLength() && hasLowerCaseChar() && hasUpperCaseChar();
    }

    /**
     * Private method to check if the password is between 8 & 12 characters
     * @return false if the password is less than 8 or greater than 12 characters, and true if it is between 8 & 12
     */
    private boolean hasCorrectPasswordLength() {
        return (password.length >= 8) && (password.length <= 12);
    }

    /**
     * Private method to check the password has at least one upper case character.
     * @return false if the password does not have an uppercase character, and true if it has at least one
     */
    private boolean hasUpperCaseChar() {
        for (char c : password) {
            if (Character.isUpperCase(c)) {return true;}
        }
        return false;
    }

    /**
     * Private method to check the password has at least one lower case character.
     * @return false if the password does not have a lowercase character, and true if it has at least one
     */
    private boolean hasLowerCaseChar() {
        for (char c : password) {
            if (Character.isLowerCase(c)) {return true;}
        }
        return false;
    }

    /**
     * Private method to check if the password has at least one digit
     * @return false if the password does not have a digit, and true if it has at least one
     */
    private boolean hasDigitChar() {
        for (char c : password) {
            if (Character.isDigit(c)) {return true;}
        }
        return false;
    }

    /**
     * Method to check the MFA has the correct length and detects for integer overflow. It first tries to parse the MFA into
     * a Long variable to send to the method for checking int overflow. If the MFA cannot be parsed to Long, the method
     * throws a NumberFormatException and immediately returns false.
     * @return false if the MFA cannot be parsed to Long or the length is wrong or integer overflow is detected,
     * and true if length == 10 and no integer overflow detected
     */
    public boolean passesMultiFactorAuthentication() {
        long mfaConvert;

        try {
            mfaConvert = Long.parseLong(mfa);
        } catch (NumberFormatException e) {
            return false;
        }
        if (!hasCorrectMFALength() || !hasNoIntOverflow(mfaConvert)) {
            logger.logEvent("LOGIN_ATTEMPT", username, false, "MFAVIOLATION");
        }
        logger.logEvent("LOGIN_ATTEMPT", username, true);
        return hasCorrectMFALength() && hasNoIntOverflow(mfaConvert);
    }

    /**
     * Private method to check the length of the MFA
     * length must be 10 characters
     * @return false if the MFA is not 10 characters, and true if the MFA is 10 characters
     */
    private boolean hasCorrectMFALength() {
        return mfa.length() == 10;
    }

    /**
     * Private method to check for integer overflow. This checks that the MFA is between the min and max values of what an
     * integer can be.
     * @param input a variable of type long
     * @return false if the input is not within the min and max values of what an integer can be, and true if it is
     */
    private boolean hasNoIntOverflow(long input) {
        return input >= Integer.MIN_VALUE && input <= Integer.MAX_VALUE;
    }

    /**
     * Method for checking the username and password against the username and passwords in the datsbase.A FileNotFoundException
     * is thrown if the database cannot be found
     * @return false if the database cannot be access or the user credentials cannot be authenticated against the database,
     * and true if the user credentials are found within the database
     */
    public boolean authenticatesUser() {
        try {
            File file = new File("database.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] creds = line.split(",");

                String storedUsername = creds[0];
                String storedPassword = creds[1];

                if ( (storedUsername.equals(username)) && (Arrays.equals(password, storedPassword.toCharArray())) ) {
                    scanner.close();
                    logger.logEvent("LOGIN_ATTEMPT", username, true);
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not access User Database");
            logger.logEvent("LOGIN_ATTEMPT", username, false, "COULDNOTACCESSDATABASEFORAUTHENTICATION");
            return false;
        }
        logger.logEvent("LOGIN_ATTEMPT", username, false, "COULDNOTAUTHENTICATEUSER");
        return false;
    }

}
