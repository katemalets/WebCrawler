package org.example;

        import java.io.FileWriter;
        import java.io.IOException;
        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Date;
        import java.util.Iterator;
        import org.apache.commons.csv.CSVFormat;
        import org.apache.commons.csv.CSVPrinter;

public class Statistics {
    private ArrayList<Statistic> statistics = new ArrayList();

    public Statistics() {
    }

    public void addStatistic(Statistic statistic) {
        this.statistics.add(statistic);
    }

    public void saveToCSVFile(String prefix) throws IOException {
        String[] HEADERS = new String[]{"Statistics"};
        DateFormat dateFormat = new SimpleDateFormat("HH_mm_ss");
        FileWriter out = new FileWriter(prefix + "Statistics-" + dateFormat.format(new Date()) + ".csv");
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS));

        try {
            Iterator var6 = this.statistics.iterator();

            while(var6.hasNext()) {
                Statistic currentStatistic = (Statistic)var6.next();
                printer.printRecord(new Object[]{currentStatistic.toString()});
            }
        } catch (Throwable var9) {
            try {
                printer.close();
            } catch (Throwable var8) {
                var9.addSuppressed(var8);
            }

            throw var9;
        }

        printer.close();
    }

    public void printTopStatistics() throws IOException {
        Collections.sort(this.statistics, new StatisticComparatorByTotal());
        Statistics topStatistics = new Statistics();
        int limit = 10;

        for(int i = 0; i < limit; ++i) {
            Statistic currentStatistic = (Statistic)this.statistics.get(i);
            System.out.println(currentStatistic.toString());
            topStatistics.addStatistic(currentStatistic);
        }

        topStatistics.saveToCSVFile("top");
    }
}
