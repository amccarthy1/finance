package me.amccarthy.finance;

import java.util.Date;

/**
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public interface Transaction {

    /**
     * Get the net change in money resulting from this transaction.
     * (an integer value of 1 represents the smallest common unit of currency;
     * for USD this is 1 cent)
     * @return The net change in money resulting from this transaction.
     */
    int getAmount();

    /**
     * Retrieves the description string for this transaction.
     * @return The description string for this transaction.
     */
    String getDescription();

    /**
     * Retrieves the date on which this transaction occurred.
     * @return the date on which this transaction occurred.
     */
    Date getDate();

}
