package ca.ulaval.glo4003.ws.utils;

public final class StringUtil {

    private static final String NON_DIGITS_REGEX = "\\D";

    public static int[] stringToIntArr(String stringElement) {
        String[] s = stringElement.split("");
        int[] result = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            result[i] = Integer.parseInt(s[i]);
        }
        return result;
    }

    public static String replaceNonDigitWithEmptySpace(String nonDigitNumber) {
        return nonDigitNumber.replaceAll(NON_DIGITS_REGEX, "");
    }
}