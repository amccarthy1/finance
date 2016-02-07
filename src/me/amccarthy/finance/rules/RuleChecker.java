package me.amccarthy.finance.rules;

import me.amccarthy.finance.TransactionSet;
import me.amccarthy.finance.currency.CurrencyFormat;
import me.amccarthy.finance.messages.MessageService;

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
            new Rule((s, ts) -> equalsCode(s, "finance.groups.fees") && ts.size() > 0, "finance.tips.fees"),
            new Rule((s, ts) -> equalsCode(s, "finance.groups.transportation") && ts.perMonth() > 3000, "finance.tips.transportation"),
            new Rule((s, ts) -> equalsCode(s, "finance.groups.gas") && ts.perMonth() > 3000, "finance.tips.gas"),
            new Rule((s, ts) -> equalsCode(s, "finance.groups.restaurants") && ts.perMonth() > 8000, "finance.tips.restaurants")
    };

    private static boolean equalsCode(String s, String code) {
        return s.equals(MessageService.getInstance().getMessage(code));
    }

    public static void check(String group, TransactionSet ts) {
        for (Rule r : rules) {
            if (r.test(group, ts)) {
                System.out.println(
                    MessageService.getInstance().getMessage("finance.tip.prefix",
                        CurrencyFormat.format(ts.total()),
                        group,
                        r.getMessage()
                    )
                );
            }
        }
    }
}
