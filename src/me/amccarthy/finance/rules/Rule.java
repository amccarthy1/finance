package me.amccarthy.finance.rules;

import me.amccarthy.finance.Transaction;
import me.amccarthy.finance.TransactionSet;

import java.util.Collection;
import java.util.function.BiPredicate;

/**
 * Package: me.amccarthy.finance.rules
 * File: Rule.java
 *
 * A rule and associated message for giving financial advice.
 * <p>
 *     The general flow is as follows. For each transaction group, check all
 *     relevant rules to see if they apply. If any rules apply, display the
 *     corresponding message to the user.
 * </p>
 *
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class Rule {
    private BiPredicate<String, TransactionSet> criteria;
    private String message;

    public Rule(BiPredicate<String, TransactionSet> criteria, String message) {
        this.criteria = criteria;
        this.message = message;
    }

    /**
     * Tests this rule against the given group of transactions.
     * @param group
     *      The string representing the name of the group to test.
     * @param transactions
     *      A collection of transactions forming the group to test.
     * @return
     *      true if the rule applies to this group, false otherwise.
     */
    public boolean test(String group, TransactionSet transactions) {
        return this.criteria.test(group, transactions);
    }

    /**
     * Get the message associated with this rule
     * @return
     *      A string message to the user.
     */
    public String getMessage() {
        return this.message;
    }
}
