package loginmodule;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidationTest {

    private Validation val;
    private SecurityLogger logger;

    @Before
    public void setUp() {
        logger = new SecurityLogger();
        val = new Validation();
        val.setLogger(logger);
    }

    // ==================== Password Policy Tests ====================

    @Test
    public void testPasswordPolicyContainsRequirements() {
        String policy = val.passwordPolicy();
        assertTrue(policy.contains("uppercase") || policy.contains("Uppercase") || policy.contains("A-Z"));
        assertTrue(policy.contains("lowercase") || policy.contains("Lowercase") || policy.contains("a-z"));
        assertTrue(policy.contains("digit") || policy.contains("Digit") || policy.contains("0-9"));
    }

    // ==================== Password Validation Tests ====================

    @Test
    public void testValidPassword() {
        val.setPassword("Argos99x".toCharArray());
        assertTrue(val.passesPWPolicy());
    }

    @Test
    public void testNoUppercaseFails() {
        val.setPassword("argos99x".toCharArray());
        assertFalse(val.passesPWPolicy());
    }

    @Test
    public void testNoLowercaseFails() {
        val.setPassword("ARGOS99X".toCharArray());
        assertFalse(val.passesPWPolicy());
    }

    @Test
    public void testNoDigitFails() {
        val.setPassword("ArgosRock".toCharArray());
        assertFalse(val.passesPWPolicy());
    }

    @Test
    public void testTooShortFails() {
        val.setPassword("Ar9x".toCharArray());
        assertFalse(val.passesPWPolicy());
    }

    @Test
    public void testTooLongFails() {
        val.setPassword("Argos99xyzabc".toCharArray());
        assertFalse(val.passesPWPolicy());
    }

    @Test
    public void testSpecialCharsFails() {
        val.setPassword("Argos99!".toCharArray());
        assertFalse(val.passesPWPolicy());
    }

    // ==================== Default Password Tests ====================

    @Test
    public void testDefaultPasswordNotNull() {
        DefaultPassword dp = new DefaultPassword();
        String password = dp.generatePassword();
        assertNotNull(password);
        assertFalse(password.isEmpty());
    }

    @Test
    public void testDefaultPasswordMeetsPolicy() {
        DefaultPassword dp = new DefaultPassword();
        String password = dp.generatePassword();
        val.setPassword(password.toCharArray());
        assertTrue(val.passesPWPolicy());
    }

    @Test
    public void testDefaultPasswordLength() {
        DefaultPassword dp = new DefaultPassword();
        String password = dp.generatePassword();
        assertTrue(password.length() >= 8 && password.length() <= 12);
    }

    @Test
    public void testDefaultPasswordHasUppercase() {
        DefaultPassword dp = new DefaultPassword();
        String password = dp.generatePassword();
        assertTrue(password.chars().anyMatch(Character::isUpperCase));
    }

    @Test
    public void testDefaultPasswordHasLowercase() {
        DefaultPassword dp = new DefaultPassword();
        String password = dp.generatePassword();
        assertTrue(password.chars().anyMatch(Character::isLowerCase));
    }

    @Test
    public void testDefaultPasswordHasDigit() {
        DefaultPassword dp = new DefaultPassword();
        String password = dp.generatePassword();
        assertTrue(password.chars().anyMatch(Character::isDigit));
    }

    // ==================== Default Password Notification Tests ====================

    @Test
    public void testNotifyUserDoesNotLeakPassword() {
        DefaultPassword dp = new DefaultPassword();
        String password = dp.generatePassword();
        String message = dp.notifyUserMessage();
        assertFalse(message.contains(password));
    }

    @Test
    public void testNotifyUserMentionsEmail() {
        DefaultPassword dp = new DefaultPassword();
        String message = dp.notifyUserMessage();
        assertTrue(message.contains("email") || message.contains("Email"));
    }
}