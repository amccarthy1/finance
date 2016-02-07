package me.amccarthy.finance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class DescriptionTrimmer {
    // 1xxx and 2xxx could be years, common in business names.
    // 3xxx+ is not very common, so we'll treat numbers greater than 3k as ID numbers.
    private static final String PHONE_AREA_CODE = "\\(\\d{3}\\)";
    private static final String PHONE_NUMBER = "(\\(\\d{3}\\)|\\d{3})?-?(\\d{3}-?\\d{4}|\\d{7})";
    private static final String ID_NUM_PATTERN = "#?(([03-9]\\d{3})|(\\d{5}\\d*))";
    private static final String ID_NUM_REDACT_PATTERN = "#?(x+|\\*+)\\d{4}";
    private static final String REDUNDANT_WORDS = "purchase";
    private static final String KEY_VALUE_PAIR = "[a-z]+:.+"; // very frequently redundant
    private String description;
    private String group;


    public DescriptionTrimmer(String description) {
        this.description = description;
        if (!firstPass()) {
            secondPass();
        }

    }

    private boolean firstPass() {
        // check for common groups, like rent, gas, ATMs, etc.
        // All of these will be grouped together, and doing this will save some execution time
        // TODO specify using a configuration file for cleaner code.
        String[] parts = description.split("\\s+|:");
        for (String word : parts) {
            String lower = word.toLowerCase().replaceAll("[^a-z]", " ").trim();
            switch (lower) {
                case "atm":
                    group = "ATM";
                    return true;
                case "fee":
                    group = "Fees";
                    return true;
                case "gas":
                case "exxon":
                case "mobile":
                case "shell":
                case "sunoco":
                case "gasoline":
                case "gulf":
                case "citgo":
                case "valero":
                case "conoco":
                case "chevron":
                    group = "Gasoline";
                    return true;
                case "uber":
                case "lyft":
                case "taxi":
                case "cab":
                    group = "Transportation";
                    return true;
                case "rent":
                case "resident":
                    group = "Rent";
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    private void secondPass() {
        List<String> usefulBits = new ArrayList<>();

        boolean usefulEnded = false;
        for (String s : description.split("\\s")) {
            String lower = s.toLowerCase();
            // first word must be a part of the description
            if (usefulBits.isEmpty()) {
                usefulBits.add(s);
                continue;
            }
            if (
                lower.matches(ID_NUM_PATTERN) ||
                lower.matches(ID_NUM_REDACT_PATTERN) ||
                lower.matches(REDUNDANT_WORDS) ||
                lower.matches(PHONE_NUMBER) ||
                lower.matches(PHONE_AREA_CODE) ||
                lower.matches(KEY_VALUE_PAIR)
            ) {
                break;
            }
            usefulBits.add(s);
        }
        group = String.join(" ", usefulBits);
    }

    public String getGroupCriteria() {
        return group;
    }

    public String getDescription() {
        return description;
    }
}