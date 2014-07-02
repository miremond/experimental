package org.miremond.fnag;

/**
 * Utility methods to convert string decimals to long integer.
 * 
 * @author mremond
 *
 */
public class DecimalUtils {

    private static final String DECIMAL_SEPARATOR_REGEX = "\\.";

    private static final String DECIMAL_SEPARATOR = ".";

    private static final int PRECISION = 2;

    /**
     * Convert string to long with precision of two.
     * 
     * @param number
     * @return
     */
    public static long toCents(String number) {
        String[] tab = number.split(DECIMAL_SEPARATOR_REGEX);
        switch (tab.length) {
        case 1:
            return Long.valueOf(tab[0] + getPadding(0));
        case 2:
            return Long.valueOf(tab[0] + tab[1] + getPadding(tab[1].length()));
        default:
            throw new IllegalArgumentException("Malformed decimal number " + number);
        }
    }

    /**
     * Convert long with precision of two to string.
     * 
     * @param number
     * @return
     */
    public static String toString(long number) {
        String string = String.valueOf(number);
        string = getPrefixPadding(string.length()) + string;
        return string.substring(0, string.length() - PRECISION) + DECIMAL_SEPARATOR
                + string.substring(string.length() - PRECISION);
    }

    private static String getPrefixPadding(int length) {
        String padding = "";
        for (int i = 0; i < PRECISION - length + 1; i++) {
            padding += "0";
        }
        return padding;
    }

    private static String getPadding(int length) {
        if (length > PRECISION) {
            throw new IllegalArgumentException("Not allowed decimal precision, valid is " + PRECISION + ", actual is "
                    + length);
        }
        String padding = "";
        for (int i = 0; i < PRECISION - length; i++) {
            padding += "0";
        }
        return padding;
    }

}
