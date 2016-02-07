package me.amccarthy.finance.rules;

import me.amccarthy.finance.Transaction;
import me.amccarthy.finance.TransactionSet;
import me.amccarthy.finance.currency.CurrencyFormat;

import java.util.Date;

/**
 * Package: me.amccarthy.finance.rules
 * File: RuleChecker.java
 *
 * Holds and checks rules, giving tips where applicable.
 *
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class RuleChecker {
    public static Rule[] rules = {
            new Rule((s, ts) -> s.equals("Fees") && ts.size() > 0,
                    "Avoiding fees can be an easy way to save a little money." +
                    " If you have a lot of late fees, try setting up" +
                    " reminders to notify you of due dates."
            ), new Rule((s, ts) -> s.equals("Transportation") && ts.perMonth() > 3000,
                    "Instead of taking personal transportation services, try " +
                    "to organize carpools, take public transportation, or " +
                    "take fewer trips. In addition, be sure to avoid using " +
                    "these services at busy times, as they often increase " +
                    "prices during traffic surges."
            )
    };

    public static void check(String group, TransactionSet ts) {
        for (Rule r : rules) {
            if (r.test(group, ts)) {
                System.out.printf("You spent %s on %s. ", CurrencyFormat.format(ts.total()), group);
                System.out.println(r.getMessage());
            }
        }
    }
}
