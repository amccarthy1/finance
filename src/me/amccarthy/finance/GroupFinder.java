package me.amccarthy.finance;

import me.amccarthy.finance.places.Places;

import java.util.HashMap;
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

    public static String getGroup(String description) {
        DescriptionTrimmer trimmer = new DescriptionTrimmer(description);
        String group = trimmer.getGroupCriteria();
        if (trimmer.isCanonical()) {
            return group;
        }
        return Places.getTypeOf(group);
    }
}
