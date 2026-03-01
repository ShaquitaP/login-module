package loginmodule;
/**
 * This class handles all encryption and decryption using the alpha and number keys.
 *
 * CEN4078 Programming Exercise 2
 * File Name: Cryptographer.java
 *
 * @author Shaquita Puckett
 * @version 1.0
 * @since 2026-03-01
 */

import java.util.ArrayList;
import java.util.List;

public class Cryptographer {

    private static final String ALPHAKEY = "ARGOSROCK";
    private static final String NUMBERKEY = "1963";
    private List<Integer> aKey;
    private List<Integer> nKey;

    /**
     * Overload constructor initializes the aKey with the alphakey values and the nKey with the numberkey values
     */
    public Cryptographer() {
        this.aKey = new ArrayList<>();
        this.nKey = new ArrayList<>();
        toKeyValues();
    }

    /**
     * Encrypts cleartext by converting to uppercase and then looping through each character. It uses private methods
     * encryptVigenere and encryptNumber to handle the different characters. Returns the toString of the ed result.
     * @param cleartext - the text to be encrypted
     * @return a string representing the encrypted text
     */
    public String encrypt(String cleartext) {
        List<Integer> encryptList = new ArrayList<>();
        List<Character> typeList = new ArrayList<>();
        String newCleartext = cleartext.toUpperCase();
        int numTracker = 0;
        int letTracker = 0;

        for (char c : newCleartext.toCharArray()) {
            if (Character.isDigit(c)) {
                encryptList.add(encryptNumber(c, (numTracker % nKey.size()) ));
                typeList.add('D');
                ++numTracker;
            }
            else if (Character.isLetter(c)) {
                encryptList.add(encryptVigenere(c, (letTracker % aKey.size())));
                typeList.add('L');
                ++letTracker;
            }
        }

        return toString(encryptList, typeList);
    }

    /**
     * Decrypts ciphertext by converting to uppercase and then looping through each character. It uses private methods
     * decryptVigenere and decryptNumber to handle the different characters. Returns the toString of the end result
     * @param ciphertext - the text to be decrypted
     * @return a string representing the decrypted text
     */
    public String decrypt(String ciphertext) {
        List<Integer> decryptList = new ArrayList<>();
        List<Character> typeList = new ArrayList<>();
        String newCiphertext = ciphertext.toUpperCase();
        int numTracker = 0;
        int letTracker = 0;

        for (char c : newCiphertext.toCharArray()) {
            if (Character.isDigit(c)) {
                decryptList.add(decryptNumber(c, (numTracker % nKey.size()) ));
                typeList.add('D');
                ++numTracker;
            }
            else if (Character.isLetter(c)) {
                decryptList.add(decryptVigenere(c, (letTracker % aKey.size()) ));
                typeList.add('L');
                ++letTracker;
            }
        }

        return toString(decryptList, typeList);
    }

    /**
     * Formula: (C + K) mod 26
     * @param c - the character that will be encrypted
     * @param element - the element of the alphakey that 'c' is encrypted against
     * @return (C + k) mod 26
     */
    private int encryptVigenere(char c, int element) {
        return (toASCIIFromLetter(c) + aKey.get(element)) % 26;
    }

    /**
     * Formula: (K - C + 10) mod 10
     * @param c - the character that will be encrypted
     * @param element - the element of the alphakey that 'c' is encrypted against
     * @return (K - C + 10) mod 10
     */
    private int encryptNumber(char c, int element) {
        return (nKey.get(element) - toASCIIFromNum(c) + 10) % 10;
    }

    /**
     * Formula: (C - K + 26) mod 26
     * @param c - the character that will be decrypted
     * @param element - the element of the alphakey that 'c' is decrypted against
     * @return (C - K + 26) mod 26
     */
    private int decryptVigenere(char c, int element) {
        return (toASCIIFromLetter(c) - aKey.get(element) + 26) % 26;
    }

    /**
     * Formula: (K - C + 10) mod 10
     * @param c - the character that will be decrypted
     * @param element - the element of the alphakey that 'c' is decrypted against
     * @return (K - C + 10) mod 10
     */
    private int decryptNumber(char c, int element) {
        return (nKey.get(element) - toASCIIFromNum(c) + 10) % 10;
    }

    /**
     * Private methods used to convert the alpha and number keys to proper number values. It converts each to a
     * char array and then subtracts 65 for alpha and subtracts 48 for number to store into the the aKey and nKey
     * lists. This makes it easier to access each element for encryption/decryption.
     */
    private void toKeyValues() {

        for (char a : ALPHAKEY.toCharArray()) {
            aKey.add(a - 65);
        }
        for (char a : NUMBERKEY.toCharArray()) {
            nKey.add(a - 48);
        }
    }

    /**
     *
     * @param c
     * @return
     */
    private int toASCIIFromLetter(char c) {
        return c - 64;
    }

    private int toASCIIFromNum(char c) {
        return c - 48;
    }


    public String toString(List<Integer> nums, List<Character> types) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < nums.size(); ++i) {
            if (types.get(i) == 'L') {
                result.append((char)(nums.get(i) + 64));
            }
            else if (types.get(i) == 'D') {
                result.append((char)(nums.get(i) + 48));
            }
        }
        return result.toString();
    }

}