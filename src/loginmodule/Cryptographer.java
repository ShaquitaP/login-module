package loginmodule;

import java.util.ArrayList;
import java.util.List;

public class Cryptographer {
    private static final String ALPHAKEY = "ARGOSROCK";
    private static final String NUMBERKEY = "1963";
    private List<Integer> aKey;
    private List<Integer> nKey;

    public Cryptographer() {
        this.aKey = new ArrayList<>();
        this.nKey = new ArrayList<>();
        toKeyValues();
    }

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

    private int encryptVigenere(char c, int element) {
        return (toASCIIFromLetter(c) + aKey.get(element)) % 26;
    }

    private int encryptNumber(char c, int element) {
        return (nKey.get(element) - toASCIIFromNum(c) + 10) % 10;
    }

    private int decryptVigenere(char c, int element) {
        return (toASCIIFromLetter(c) - aKey.get(element) + 26) % 26;
    }

    private int decryptNumber(char c, int element) {
        return (nKey.get(element) - toASCIIFromNum(c) + 10) % 10;
    }

    private void toKeyValues() {

        for (char a : ALPHAKEY.toCharArray()) {
            aKey.add(a - 65);
        }
        for (char a : NUMBERKEY.toCharArray()) {
            nKey.add(a - 48);
        }
        System.out.println(aKey);
        System.out.println(nKey);

    }

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