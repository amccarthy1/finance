package me.amccarthy.finance;

/**
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class GroupSorter {
    private static final String[] TOP_PRIORITY = {"gas", "restaurants", "transportation", "fees"};
    private static final String[] BOTTOM_PRIORITY = {"atm", "rent"};

    /**
     * Finds the priority of the given group name. Predefined group names have
     * special priorities. If the group name is not one of the predefined groups
     * then it will be given a priority of 0.
     * @param group
     *      The name of the group to look up.
     * @return
     *      The priority of the group.
     */
    private static int getPriority(final String group) {
        for (int i = 0; i < TOP_PRIORITY.length; i++) {
            if (TOP_PRIORITY[i].equals(group)) {
                return -i-1;
            }
        }
        for (int i = 0; i < BOTTOM_PRIORITY.length; i++) {
            if (BOTTOM_PRIORITY[i].equals(group)) {
                return i+1;
            }
        }
        return 0;
    }

    /**
     * Compare two groups, returning a negative integer if the former precedes
     * the latter, a positive number if the latter precedes the former, and 0 if
     * they are equal.
     * @param a
     *      The left string to compare
     * @param b
     *      The right string to compare
     * @return
     *      a number indicating the relative precedence of a to b
     */
    public static int compare(String a, String b) {
        if (a == null) {
            if (b == null) {
                return 0;
            }
            return 1;
        }
        if (b == null) {
            return -1;
        }
        a = a.toLowerCase();
        b = b.toLowerCase();
        int diff = getPriority(a) - getPriority(b);
        if (diff != 0) {
            return diff;
        }
        return a.compareTo(b);
    }
}
