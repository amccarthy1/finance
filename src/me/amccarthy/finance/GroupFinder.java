package me.amccarthy.finance;

import java.util.Map;

/**
 * Package: me.amccarthy.finance
 * File: GroupFinder.java
 *
 * Finds a group for a transaction.
 *
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class GroupFinder {
    // maps shortened descriptions to their 'canonical' groups
    private Map<String, String> descriptionGroupMap;

    public static String getGroup(String description) {
        DescriptionTrimmer trimmer = new DescriptionTrimmer(description);
        String group = trimmer.getGroupCriteria();
        if (trimmer.isCanonical()) {
            return group;
        }
        else {
            // TODO do a google places API query
            return group;
        }
    }
}
