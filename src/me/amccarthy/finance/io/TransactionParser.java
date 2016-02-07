package me.amccarthy.finance.io;

import me.amccarthy.finance.*;
import me.amccarthy.finance.currency.CurrencyFormat;
import me.amccarthy.finance.currency.CurrencyFormatException;
import me.amccarthy.finance.messages.MessageService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import sun.plugin2.message.Message;

import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// TODO make this an iterator to save performance hardcore
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
        Date date;              // parsed date
        int amount;             // parsed amount

        String dateString = record.get(DATE);
        String description = record.get(DESCRIPTION);
        String value = record.get(AMOUNT);
        try {
            date = CSV_DATE_FORMAT.parse(dateString);
            amount = CurrencyFormat.parse(value);
        } catch (ParseException|CurrencyFormatException e) {
            System.err.printf(
                    MessageService.getInstance().getMessage("finance.errors.failedToParseTransaction"),
                    dateString + "," + description + "," + value
            );
            System.err.println();
            System.err.println("Ignoring Record.");
            return null;
        }
        // construct a new transaction now that we have all the values.
        AccountTransaction newTransaction = new AccountTransaction();
        newTransaction.setDate(date);
        newTransaction.setDescription(description);
        newTransaction.setGroup(GroupFinder.getGroup(description));
        newTransaction.setAmount(amount);

        return newTransaction;
    }

    /**
     * Parse the CSV file into a collection of Transaction objects.
     * @return
     *      A collection of Transaction objects parsed from the CSV file.
     */
    public Map<String, TransactionSet> parse() {
        System.out.println("Parsing CSV File...");
        Map<String, TransactionSet> map = new TreeMap<>(GroupSorter::compare);
        for (CSVRecord record : parser) {
            Transaction transaction = parseRecord(record);
            if (transaction == null) {
                continue; // means it did not parse correctly.
            }
            TransactionSet transactions = map.get(transaction.getGroup());
            if (transactions == null) {
                transactions = new TransactionSet();
                map.put(transaction.getGroup(), transactions);
            }
            transactions.add(transaction);
        }

        return map;
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
