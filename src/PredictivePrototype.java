

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Roman Gaev on 15.02.2018.
 * This is an inefficient utility class for T9 prototype
 * This implementation is inefficient by two reasons:
 * 1) We need to establish file connection every time
 * 2) As long as we cant sort and reach indexes we cant use an efficient search method
 */
public class PredictivePrototype {
    public static String wordToSignature(String word) {
        //StringBuffer is more efficient in dealing with concatenation.
        //It is credited to more sensible memory usage when creating new String objects.
        StringBuffer sb = new StringBuffer();
        char[] chArr = word.toLowerCase().toCharArray();
        for (char character : chArr) {
            if (character == 'a' || character == 'b' || character == 'c')
                sb.append(2);
            else if (character == 'd' || character == 'e' || character == 'f')
                sb.append(3);
            else if (character == 'g' || character == 'h' || character == 'i')
                sb.append(4);
            else if (character == 'j' || character == 'k' || character == 'l')
                sb.append(5);
            else if (character == 'm' || character == 'n' || character == 'o')
                sb.append(6);
            else if (character == 'p' || character == 'q' || character == 'r' || character == 's')
                sb.append(7);
            else if (character == 't' || character == 'u' || character == 'v')
                sb.append(8);
            else if (character == 'w' || character == 'x' || character == 'y' || character == 'z')
                sb.append(9);
            else
                sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * signatureToWords offers a set of probable words for a given signature
     *
     * @param signature - given signature
     * @return set of probable words
     */
    public static Set<String> signatureToWords(String signature) {
        Set<String> matches = new TreeSet<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/usr/share/dict/words.txt"));
            String contentLine;
            while ((contentLine = br.readLine()) != null) {
                if (!isValidWord(contentLine)) continue;
                if (wordToSignature(contentLine).equals(signature))
                    matches.add(contentLine.toLowerCase());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }

    /**
     * isValidWord - checks if the word is valid(all characters are alphabetical)
     *
     * @param word - given signature
     * @return true if the word is valid
     */
    public static boolean isValidWord(String word) {
        if (word.matches("[a-zA-Z]*")) return true;
        else return false;
    }
}
