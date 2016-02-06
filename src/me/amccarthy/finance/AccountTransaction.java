package me.amccarthy.finance;

import me.amccarthy.finance.currency.CurrencyFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class AccountTransaction implements Transaction {
    private static final String STRING_FORMAT = "[%s] (%s): %s";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private int amount;
    private String description;
    private Date date;


    /**
     * Construct a card-based transaction
     */
    public AccountTransaction() {
        this.amount = 0;
        this.description = null;
        this.date = null;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return String.format(
                STRING_FORMAT,
                DATE_FORMAT.format(date),
                CurrencyFormat.format(amount),
                description
        );
    }
}
