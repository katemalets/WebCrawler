package org.example;

import java.util.Comparator;

/**
 * This class implements Comparator<Statistic>
 * compares two statistics objects by parameter total
 */
public class StatisticComparatorByTotal implements Comparator<Statistic> {

    /**
     * compares two statistics objects by parameter total indicating which is larger
     * @param s1 - one statistic
     * @param s2 - another statistic that compares to s1
     * @return a negative integer, zero, or a positive integer
     * as the first argument is less than, equal to, or greater than the second.
     */
    @Override
    public int compare(Statistic s1, Statistic s2) {
        return s2.getTotalTermsOccurred() - s1.getTotalTermsOccurred();
    }
}
