package me.amccarthy.finance.rules;

import me.amccarthy.finance.TransactionSet;
import me.amccarthy.finance.messages.MessageService;

import java.util.function.BiPredicate;

/**
 * Package: me.amccarthy.finance.rules
 * File: Rule.java
 *
 * A rule and associated messageCode for giving financial advice.
 * <p>
 *     The general flow is as follows. For each transaction group, check all
 *     relevant rules to see if they apply. If any rules apply, display the
 *     corresponding messageCode to the user.
 * </p>
 *
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class Rule {
    private BiPredicate<String, TransactionSet> criteria;
    private String messageCode;

    public Rule(BiPredicate<String, TransactionSet> criteria, String messageCode) {
        this.criteria = criteria;
        this.messageCode = messageCode;
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
     * Get the messageCode associated with this rule
     * @return
     *      A string messageCode to the user.
     */
    public String getMessage() {
        return MessageService.getInstance().getMessage(messageCode);
    }
}
