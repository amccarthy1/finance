package me.amccarthy.finance.rules;

/**
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class TotalRule extends Rule{

    public TotalRule(String group, int amount, String messageCode) {
        super(
            (groupName, transactions) -> groupName.equals(group) && transactions.total() > amount,
            messageCode
        );
    }

    public TotalRule(int amount, String messageCode) {
        super(
            (groupName, transactions) -> transactions.total() > amount,
            messageCode
        );
    }
}
