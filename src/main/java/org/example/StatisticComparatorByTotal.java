package org.example;

import java.util.Comparator;

public class StatisticComparatorByTotal implements Comparator<Statistic> {

    @Override
    public int compare(Statistic s1, Statistic s2) {
        return s2.getTotal() - s1.getTotal();
    }
}
