package svetonosets.test.test.service;

import org.springframework.stereotype.Service;

@Service
public class BaseConversion {

    private static final String allowedString = "abcdefghijklmnopqrstuvwxyz0123456789";
    private char[] allowedCharacters = allowedString.toCharArray();
    private int base = allowedCharacters.length;

    public String encode(long input) {
        StringBuilder encodedString = new StringBuilder();

        if (input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (input > 0) {
            encodedString.append(allowedCharacters[(int)(input % base)]);
            input /= base;
        }

        return encodedString.reverse().toString();
    }

    public long decode(String input) {
        char[] characters = input.toCharArray();
        long length = characters.length;
        long decoded = 0;
        long counter = 1;

        for (int i = 0; i < length; i++){
            decoded += allowedString.indexOf(characters[i]) * Math.pow(base, length - counter);
            counter++;
        }
        return decoded;
    }
}
