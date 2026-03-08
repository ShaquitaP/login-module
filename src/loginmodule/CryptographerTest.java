package loginmodule;

import org.junit.Test;

import static org.junit.Assert.*;

public class CryptographerTest {

    @Test
    public void encrypt() {
        Cryptographer crypt = new Cryptographer();
        String result = crypt.encrypt("Argos45");
        assertEquals(crypt.decrypt(result), "ARGOS45");
    }

    @Test
    public void decrypt() {
        Cryptographer crypt = new Cryptographer();
        String encrypted = crypt.encrypt("Argos45");

        String result = crypt.decrypt(encrypted);
        assertEquals("ARGOS45", result);
    }
}