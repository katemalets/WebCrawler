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

public class Statistics{

    private ArrayList<Statistic> statistics = new ArrayList();

    public void addStatistic(Statistic statistic) {
        this.statistics.add(statistic);
    }

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

    public void printTopStatistics() throws IOException {
        ArrayList<Statistic> statisticsCopy = new ArrayList<>(statistics);
        Collections.sort(statisticsCopy, new StatisticComparatorByTotal());
        Statistics topStatistics = new Statistics();
        int limit = 10;

        for(int i = 0; i < limit; ++i) {
            Statistic currentStatistic = (Statistic)statisticsCopy.get(i);
            System.out.println(currentStatistic.toString());
            topStatistics.addStatistic(currentStatistic);
        }

        topStatistics.saveToCSVFile("top");
    }
}
