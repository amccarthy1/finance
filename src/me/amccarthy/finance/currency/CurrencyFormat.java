package me.amccarthy.finance.currency;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File: CurrencyFormat.java
 * Package: me.amccarthy.finance.currency
 *
 * The purpose of this interface is to decouple the values used internally
 * for computation from their external display, allowing support for multiple
 * units of currency.
 *
 * Although only USD is supported at the moment, this class prevents that from
 * being a hard requirement.
 *
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class CurrencyFormat {
    private static final Pattern USD_PATTERN = Pattern.compile(
            "\\$?(-?\\d*)\\.?(\\d?\\d?)"
    );

    /**
     * Convert an integer into a correctly-formatted string representation of
     * currency.
     * @param value
     *      An integer value usable for computation
     * @return
     *      A human-readable string representing the currency in proper units
     */
    public static String format(int value) {
        String prefix = (value < 0 ? "$-" : "$");
        String dollars = String.format("%01d", Math.abs(value) / 100);
        String cents = String.format("%02d", Math.abs(value) % 100);
        return prefix + dollars + '.' + cents;
    }

    /**
     * Interpret a human-readable string as an integer that can be used within
     * the application for computation.
     * @param data
     *      A human-readable string representing the currency in proper units
     * @return
     *      An integer value usable for computation
     */
    public static int parse(String data) throws CurrencyFormatException {
        Matcher m = USD_PATTERN.matcher(data);
        if (!m.matches()) {
            throw new CurrencyFormatException(
                    "Tried to parse currency that was of invalid format"
            );
        }
        String dollarStr = m.group(1);
        // pad cents with two zeros to the right.
        String centStr = String.format("%2s", m.group(2)).replace(' ', '0');
        int dollars = dollarStr.isEmpty() ? 0 : Integer.parseInt(dollarStr, 10);
        int cents = Integer.parseInt(centStr, 10);
        return dollars * 100 + cents;
    }
}
