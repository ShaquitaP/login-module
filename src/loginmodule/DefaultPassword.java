package loginmodule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DefaultPassword {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private Random random;

    public DefaultPassword() {
        random = new Random();
    }

     /**
     * Generates a random password that meets the NGENius password policy.
     * The password characters are shuffled after generating to ensure
     * the required characters are not always in the same position,
     * making the password more secure.
     * @return String representing the randomly generated default password
     *  */
    public String generatePassword() {
        List<Character> passwordChars = new ArrayList<>();

        passwordChars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        passwordChars.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));

        String allChars = UPPERCASE + LOWERCASE + DIGITS;
        for (int i = 3; i < 9; i++) {
            passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
        }

        Collections.shuffle(passwordChars);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }
        return password.toString();
    }

    public String notifyUserMessage() {
        return "<html>Your password did not meet our requirements.<br>" +
                "A default password has been created for you.<br>" +
                "You will receive a secure email with your password.<br>" +
                "Please change your password upon next login.</html>";
    }
}
