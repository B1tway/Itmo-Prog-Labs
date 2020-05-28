package utils;

import java.util.Random;

public class RandomStringGenerator {
    private static String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String NUMBERS = "0123456789";
    private static String SPECIAL_CHARS = "!@#$%&*()_+-=[]|,./?><";

    private String charsForGenerate = "";

    public RandomStringGenerator() {
       charsForGenerate = LOWER + UPPER + NUMBERS + SPECIAL_CHARS;
    }

    public String generate(int minLen, int maxLen) {
        Random random = new Random();
        int len = random.nextInt(maxLen - minLen + 1) + minLen;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < len; i++)
            result.append(charsForGenerate.charAt(random.nextInt(charsForGenerate.length())));
        return result.toString();
    }
}
