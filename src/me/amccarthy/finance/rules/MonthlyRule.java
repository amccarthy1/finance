package me.amccarthy.finance.rules;

import me.amccarthy.finance.TransactionSet;

import java.util.function.BiPredicate;

/**
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class MonthlyRule extends Rule {

    public MonthlyRule(String group, int amount, String messageCode) {
        super(
            // criteria is that the group name matches and the threshold is exceeded.
            (groupName, transactions) -> groupName.equals(group) && transactions.perMonth() > amount,
            messageCode
        );
    }

    public MonthlyRule(int amount, String messageCode) {
        super(
            // criteria is that the threshold is exceeded (matches any group name)
            (groupName, transactions) -> transactions.perMonth() > amount,
            messageCode
        );
    }
}
