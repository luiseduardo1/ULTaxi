package ca.ulaval.glo4003.ultaxi.utils;

public final class LuhnAlgorithm {

    //  Taken from https://fr.wikipedia.org/wiki/Formule_de_Luhn (accessed on Thu Oct 19 23:31:22 EDT 2017)
    public static boolean checkLuhnAlgorithm(int[] digits) {
        int sum = 0;
        int length = digits.length;
        for (int i = 0; i < length; i++) {
            int digit = digits[length - i - 1];

            if (i % 2 == 1) {
                digit *= 2;
            }
            sum += digit > 9 ? digit - 9 : digit;
        }
        return sum % 10 == 0;
    }
}
