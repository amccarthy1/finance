package me.amccarthy.finance;

import me.amccarthy.finance.io.TransactionParser;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Error: Please specify a file path");
            System.exit(1);
        }
        TransactionParser tp = new TransactionParser(new BufferedReader(new FileReader(args[0])));
        tp.parse().stream()
                .forEach(System.out::println);
    }
}
