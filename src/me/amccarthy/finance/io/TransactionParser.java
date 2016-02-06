package me.amccarthy.finance.io;

import me.amccarthy.finance.AccountTransaction;
import me.amccarthy.finance.Transaction;
import me.amccarthy.finance.currency.CurrencyFormat;
import me.amccarthy.finance.currency.CurrencyFormatException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Package: me.amccarthy.finance.io
 * File: TransactionParser.java
 *
 * This class wraps the org.apache.commons.csv.CSVParser class to parse
 * files into Transactions. It's a thin wrapper that simply constructs
 * transactions from the output.
 *
 * <p>
 *     The CSV parser does not ignore whitespace, and as such any whitespace
 *     will be interpreted as a part of the value.
 * </p>
 *
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class TransactionParser {
    // indeces of each row in the CSV file.
    private static final int DATE = 0;
    private static final int DESCRIPTION = 1;
    private static final int AMOUNT = 2;
    private static final int NUM_FIELDS = 3;

    private final DateFormat CSV_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private CSVParser parser;

    /**
     * Construct a TransactionParser using any type of Reader.
     * @param reader
     *      A java Reader object that supplies a CSV file
     * @throws IOException
     *      If there is an IO error while constructing the CSV parser.
     */
    public TransactionParser(Reader reader) throws IOException {
        this.parser = new CSVParser(reader, CSVFormat.DEFAULT);
    }

    private Transaction parseRecord(CSVRecord record) {
        // A transaction must have a valid date, description, and amount.
        // although the description may be empty.
        if (record.size() != NUM_FIELDS) {
            return null;
        }
        String dateString;      // raw date string
        String description;     // raw description
        String value;           // raw dollar amount

        Date date;              // parsed date
        int amount;             // parsed amount

        try {
            dateString = record.get(DATE);
            description = record.get(DESCRIPTION);
            value = record.get(AMOUNT);
            date = CSV_DATE_FORMAT.parse(dateString);
            amount = CurrencyFormat.parse(value);
        } catch (ParseException|CurrencyFormatException e) {
            return null;
        }
        // construct a new transaction now that we have all the values.
        AccountTransaction newTransaction = new AccountTransaction();
        newTransaction.setDate(date);
        newTransaction.setDescription(description);
        newTransaction.setAmount(amount);

        return newTransaction;
    }

    /**
     * Parse the CSV file into a collection of Transaction objects.
     * @return
     *      A collection of Transaction objects parsed from the CSV file.
     */
    public Collection<Transaction> parse() {
        Collection<Transaction> transactions = new ArrayList<>();
        for (CSVRecord record : parser) {
            Transaction transaction = parseRecord(record);
            if (transaction == null) {
                continue; // means it did not parse correctly.
            }
            transactions.add(transaction);
        }
        return transactions;
    }

    /**
     * Close the underlying CSVParser
     */
    public void close() {
        try {
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
