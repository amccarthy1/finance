package me.amccarthy.finance.rules;

import me.amccarthy.finance.Transaction;
import me.amccarthy.finance.messages.MessageService;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
// TODO This is a last-minute, hastily thrown together module.
// I had the last minute idea to check not only overspending but also underspending.
// unfortunately, that didn't fit into the rule system.
public class GlobalRuleChecker {
    public static boolean visitedFinancialAdviser(Collection<Transaction> transactions) {
        return transactions.stream()
            .filter(
                t -> t.getGroup().equals(
                        MessageService.getInstance().getMessage("finance.groups.finance")
                )
            ).anyMatch(
                t -> {
                    long time = t.getDate().getTime();
                    // time delta < 365 days
                    return (new Date().getTime() - time) / (1000 * 60 * 60 * 24) < 365;
                }
            );
    }

    public static void check(Collection<Transaction> transactions) {
        if (!visitedFinancialAdviser(transactions)) {
            System.out.println(MessageService.getInstance().getMessage("finance.tips.finance"));
        }
    }
}
