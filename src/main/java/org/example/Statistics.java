package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * Class with property : statistics
 * collects whole statistics together
 * saves all statistics of visited pages in the file all Statistics-<time>.csv,
 * prints to the console top 10 statistics on the total number of terms
 * and saves in a separate file topStatistics-<time>.csv
 */
public class Statistics{

    // statistics - a collection in which statistic will be added
    private ArrayList<Statistic> statistics = new ArrayList();

    /**
     * @param statistic - is passed when adding new statistic to statistics
     */
    public void addStatistic(Statistic statistic) {
        this.statistics.add(statistic);
    }

    /**
     * saves results to scv files using commons-csv library
     * @param prefix - creates file with prefix all or top
     * @throws IOException - if a non-existent prefix sets.
     * Before creating new file make sure that all
     * syntax is acceptable for creating a new file
     */
    public void saveToCSVFile(String prefix) throws IOException {
        String[] HEADERS = new String[]{"Statistics"};
        DateFormat dateFormat = new SimpleDateFormat("HH_mm_ss");
        FileWriter out = new FileWriter( prefix + "Statistics-" + dateFormat.format(new Date()) + ".csv");
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                .withHeader(HEADERS))) {
           for (Statistic currentStatistic : statistics){
               printer.printRecord(currentStatistic.toString());
            }
        }
        out.close();
    }

    /**
     * creates a copy of existed statistics, sorts it by total (using comparator), chooses
     * how many statistic will be printed(default - 10, but user may put less limitedPages),
     * prints top 10 statistics to the console and save top 10 to csv file (using saveToCSVFile())
     * @throws IOException if a file with forbidden characters created
     */
    public void printTopStatistics() throws IOException {
        ArrayList<Statistic> statisticsCopy = new ArrayList<>(statistics);
        Collections.sort(statisticsCopy, new StatisticComparatorByTotal());
        Statistics topStatistics = new Statistics();
        int limit = Math.min(10, statisticsCopy.size());

        for(int i = 0; i < limit; ++i) {
            Statistic currentStatistic = statisticsCopy.get(i);
            System.out.println((i + 1) + ") " + currentStatistic.toString());
            topStatistics.addStatistic(currentStatistic);
        }

        topStatistics.saveToCSVFile("top");
    }

    /**
     * gives an opportunity to get statistics
     * @return private statistics collection
     */
    public ArrayList<Statistic> getStatistics() {
        return statistics;
    }
}
