package tests.dublicatestask;

import java.util.*;
import java.util.regex.Pattern;

public class FindingDoubles {
    public static String word;

    public static int counter(String word) {
        String newWord = word.toLowerCase();
        Map<Character, Integer> resultMap = new HashMap<>();
        for (int i = 0; i < newWord.length(); i++) {
            if (resultMap.isEmpty() || !resultMap.containsKey(newWord.charAt(i))) {
                resultMap.put(newWord.charAt(i), 1);
            } else resultMap.replace(newWord.charAt(i), resultMap.get(newWord.charAt(i)) + 1);
        }

        int count = 0;
        if (!resultMap.isEmpty()) {
            for (Character character : resultMap.keySet()) {
                if (resultMap.get(character) != 1) {
                    count++;
                }
            }
            return count;
        } else return 0;
    }
}
