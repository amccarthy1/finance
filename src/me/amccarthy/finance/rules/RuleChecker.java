package me.amccarthy.finance.rules;

import me.amccarthy.finance.Transaction;
import me.amccarthy.finance.TransactionSet;
import me.amccarthy.finance.currency.CurrencyFormat;
import me.amccarthy.finance.messages.MessageService;
import sun.plugin2.message.Message;

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
            new MonthlyRule(getMsg("finance.groups.transportation"), 3000, "finance.tips.transportation"),
            new MonthlyRule(getMsg("finance.groups.gas"), 3000, "finance.tips.gas"),
            new MonthlyRule(getMsg("finance.groups.restaurant"), 8000, "finance.tips.restaurants"),
            new MonthlyRule(getMsg("finance.groups.restaurant"), 1000, "finance.tips.retailMeNot"),
            new Rule((s, ts) -> equalsCode(s, "finance.groups.locksmith") && ts.size() > 0, "finance.tips.locksmith"),
            new MonthlyRule(getMsg("finance.groups.subscription"), 400, "finance.tips.subscription"),
            new MonthlyRule(getMsg("finance.groups.departmentStore"), 3000, "finance.tips.departmentStores"),
            new TotalRule(getMsg("finance.groups.gambling"), 0, "finance.tips.gambling"),
            new Rule(
                (groupName, transactionSet) -> transactionSet.stream()
                        .anyMatch((t -> t.getDescription().toLowerCase().contains("walmart"))),
                "finance.tips.walmart"
            )
    };

    private static String getMsg(String code) {
        return MessageService.getInstance().getMessage(code);
    }

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
