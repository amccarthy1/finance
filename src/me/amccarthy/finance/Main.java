package me.amccarthy.finance;

import me.amccarthy.finance.currency.CurrencyFormat;
import me.amccarthy.finance.io.TransactionParser;
import me.amccarthy.finance.rules.RuleChecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Error: Please specify a file path");
            System.exit(1);
        }
        TransactionParser tp = new TransactionParser(new BufferedReader(new FileReader(args[0])));
        Map<String, TransactionSet> transactionMap = tp.parse();

        transactionMap.forEach((group, transactions) -> {
            System.out.print(group);
            int sum = transactions.stream()
                    .mapToInt(Transaction::getAmount)
                    .sum();
            System.out.printf(" (%s):\n", CurrencyFormat.format(sum));
//            transactions.stream()
//                    .map(transaction -> "    " + transaction.toString())
//                    .forEach(System.out::println);
        });
        // now run our suite of rule checks against the transactions provided.
        System.out.println("Checking for savings tips");
        transactionMap.forEach(RuleChecker::check);

    }
}
