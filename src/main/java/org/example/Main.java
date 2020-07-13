package org.example;

import java.io.IOException;

/**
 * @author Katya Malets
 * @version 1.0-beta
 */
public class Main {

    /**
     * Entry point of the program is here
     * @param args command line values
     * @throws IOException may be thrown if file name has forbidden characters
     */
    public static void main(String[] args) throws IOException {

        UserInput user = new UserInput();
        user.userInput();

        /*
         * webCrawler traverses websites following predefined seed, link depth and visited pages limit,
         * detects the presence of user terms on the page and collects statistic.
         */
        WebCrawler webCrawler = new WebCrawler(user.getTerms(), user.getDepthLimit(), user.getVisitedPagesLimit());
        webCrawler.crawl(user.getHtmlDocument(), 1);

        /*
         * All statistics of visited pages are saved in the file all Statistics-<hh_mm_ss>.csv,
         * top 10 statistics on the total number of terms are printed to the console
         * and saved in a separate file topStatistics-<hh_mm_ss>.csv
         */
        webCrawler.printTopStatistics();
        webCrawler.saveStatisticsToCSVFile();
        System.out.println("Statistics successfully collected and saved to csv files.");
    }
}
