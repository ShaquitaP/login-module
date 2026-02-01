import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Validation {
    private String username = "";
    private char[] password;
    private String mfa = "";

    public Validation(String username, char[] password, String mfa) {
        setUsername(username);
        setPassword(password);
        setMFA(mfa);
    }

    public void setUsername(String username) {this.username = username;}
    public void setPassword(char[] password) {this.password = password;}
    public void setMFA(String mfa) {this.mfa = mfa;}

    public boolean passesSQLInjection() {
        return !hasForbiddenCharacters(username.toCharArray()) && !hasForbiddenCharacters(password);
    }

    private boolean hasForbiddenCharacters(char [] input) {
        for (char c : input) {
            if ( (c == '/') || (c == '-') || (c == ';') || (c == '"') ) {
                return true;
            }
        }
        return false;
    }

    public boolean passesPWPolicy() {
        return hasDigitChar(password) && hasCorrectPasswordLength(password) && hasLowerCaseChar(password) && hasUpperCaseChar(password);
    }

    private boolean hasCorrectPasswordLength(char[] input) {
        return (input.length >= 8) && (input.length <= 12);
    }

    private boolean hasUpperCaseChar(char [] input) {
        for (char c : input) {
            if (Character.isUpperCase(c)) {return true;}
        }
        return false;
    }

    private boolean hasLowerCaseChar(char [] input) {
        for (char c : input) {
            if (Character.isLowerCase(c)) {return true;}
        }
        return false;
    }

    private boolean hasDigitChar(char [] input) {
        for (char c : input) {
            if (Character.isDigit(c)) {return true;}
        }
        return false;
    }

    public boolean passesMultiFactorAuthentication() {
        long mfaConvert;
        System.out.println("mfa input:" + mfa);
        try {
            mfaConvert = Long.parseLong(mfa);
        } catch (NumberFormatException e) {
            return false;
        }
        return hasCorrectMFALength(mfa) && hasNoIntOverflow(mfaConvert);
    }

    private boolean hasCorrectMFALength(String mfa) {
        System.out.println("mfa length: " + mfa.length());
        return mfa.length() == 10;
    }

    private boolean hasNoIntOverflow(long input) {
        System.out.println("parsed val: " + input);
        return input >= Integer.MIN_VALUE && input <= Integer.MAX_VALUE;
    }

    public boolean authenticatesUser(String username, char[] password) {
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
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not access User Database");
            return false;
        }
        return false;
    }

}
