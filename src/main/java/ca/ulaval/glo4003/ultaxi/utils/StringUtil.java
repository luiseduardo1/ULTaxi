package ca.ulaval.glo4003.ultaxi.utils;

public final class StringUtil {

    private static final String NON_DIGITS_REGEX = "\\D";

    public static int[] convertStringToIntegerArray(String stringElement) {
        String[] strings = stringElement.split("");
        int[] result = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = Integer.parseInt(strings[i]);
        }
        return result;
    }

    public static String replaceNonDigitWithEmptySpace(String nonDigitNumber) {
        return nonDigitNumber.replaceAll(NON_DIGITS_REGEX, "");
    }
}
