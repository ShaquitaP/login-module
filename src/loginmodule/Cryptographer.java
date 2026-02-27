package loginmodule;

import java.util.ArrayList;
import java.util.List;

public class Cryptographer {
    private static final String ALPHAKEY = "ARGOSROCK";
    private static final int NUMBERKEY = 1963;
    private List<Integer> key;

    public Cryptographer() {
        this.key = new ArrayList<Integer>();
        toKeyValues();
    }

    public String encrypt(String cleartext) {
        StringBuilder ciphertext = new StringBuilder();
        String newCleartext = cleartext.toUpperCase();
        int keyElement = 0;

        for (char n : newCleartext.toCharArray()) {
            ciphertext.append((n + key.get(keyElement)) % 26);
            ++keyElement;
        }

        return ciphertext.toString();
    }

    private void toKeyValues() {
        for (char a : ALPHAKEY.toCharArray()) {
            key.add(a - 65);
        }
    }
}
