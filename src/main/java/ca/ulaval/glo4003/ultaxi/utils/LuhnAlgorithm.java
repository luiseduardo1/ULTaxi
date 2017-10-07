package ca.ulaval.glo4003.ultaxi.utils;

public final class LuhnAlgorithm {

    //Luhn algorithm code come from Wikipedia , url : https://fr.wikipedia.org/wiki/Formule_de_Luhn
    public static boolean checkLuhnAlgorithm(int[] digits) {
        int sum = 0;
        int length = digits.length;
        for (int i = 0; i < length; i++) {
            // get digits in reverse order
            int digit = digits[length - i - 1];

            // every 2nd number multiply with 2
            if (i % 2 == 1) {
                digit *= 2;
            }
            sum += digit > 9 ? digit - 9 : digit;
        }
        return sum % 10 == 0;
    }
}
