package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class gives an opportunity for user to give default or user's values
 */
public class User implements IUser {

    /**
     * terms - set of words (or word combinations) to be searched by Web Crawler
     * seed - a URL which determines Web Crawler's entry point to start surfing web
     * depthLimit - maximum depth value that the user fills in
     * visitedPagesLimit - max number of web-pages to be traversed by Web Crawler
     */
    private String[] terms;
    private Document htmlDocument;
    private int depthLimit;
    private int visitedPagesLimit;

    private Scanner scanner;
    private IWebCrawler webCrawler;

    public User(){
        this.scanner = new Scanner(System.in);
    }

    public void userInput() throws IOException {

        inputTerms();

        inputSeed();

        inputDepthLimit();

        inputVisitedPagesLimit();

        scanner.close();
    }

    private void inputTerms(){
        System.out.print("Input terms separated by comma: ");

        // defaultTerms - terms to be used in extreme cases
        final String[] defaultTerms = {"and", "la", "Статья", "Julian", "no"};

        String termsLine = scanner.nextLine();
        if(termsLine.isEmpty()){
            System.out.println("Using default terms: " + Arrays.toString(defaultTerms));
            this.terms = defaultTerms;
        } else {
            this.terms = termsLine.split(",");
        }
    }

    private void inputSeed() throws IOException {

        System.out.print("Input seed: ");

        // defaultSeed - seed to be used in extreme cases
        final String defaultSeed = "https://en.wikipedia.org/wiki/Quentin_Tarantino";

        String seed = scanner.nextLine();
        if (seed.isEmpty()) {
            System.out.println("Using default seed: " + defaultSeed);
            seed = defaultSeed;
        }
        /*
         * IllegalArgumentException may be caught if users seed (link) does not exist.
         * In this case app will use default seed
         * UnknownHostException may be caught if user has problems with the Internet
         * System.exit method terminates the currently running JVM and exits the program
         */
        try {
            htmlDocument = Jsoup.connect(seed).get();
        } catch (IllegalArgumentException exception) {
            System.out.println("Using default seed: " + defaultSeed);
            try {
                htmlDocument = Jsoup.connect(defaultSeed).get();
            } catch (UnknownHostException exc){
                System.out.println("Check internet connection.");
                System.exit(1);
            }
        } catch (UnknownHostException exception){
            System.out.println("Check internet connection.");
            System.exit(1);
        }
    }

    private void inputDepthLimit() {
        // defaultDepthLimit - depth to be used in extreme cases
        final int defaultDepthLimit = 8;

        /*
         * InputMismatchException may be caught if user inputs letters instead
         * of numbers or may be thrown if user inputs negative number
         */

        System.out.print("Input depth limit: ");

        try {
            depthLimit = scanner.nextInt();
            if(depthLimit <= 0){
                throw new InputMismatchException();
            }
        } catch (InputMismatchException exc){
            System.out.println("Using default depth limit: " + defaultDepthLimit);
            depthLimit = defaultDepthLimit;
            scanner.nextLine();
        }
    }

    private void inputVisitedPagesLimit(){
        // defaultVisitedPagesLimit - visited pages limit to be used in extreme cases
        int defaultVisitedPagesLimit = 15;

        System.out.print("Input visited pages limit: ");

        /*
         * InputMismatchException may be caught if user inputs letters instead
         * of numbers or may be thrown if user inputs negative number
         */

        try {
            visitedPagesLimit = scanner.nextInt();
            if(visitedPagesLimit <= 0) {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException exc) {
            System.out.println("Using default visited pages limit: " + defaultVisitedPagesLimit);
            visitedPagesLimit = defaultVisitedPagesLimit;
        }

        if(visitedPagesLimit >= 100) {
            System.out.println("Process may take significant time due to visited" +
                    " pages limit is set to " + visitedPagesLimit);
        }
    }

    public static User createUser(){
        return new User();
    }

    private String[] getTerms() {
        return terms;
    }

    private Document getHtmlDocument() {
        return htmlDocument;
    }

    private int getDepthLimit() {
        return depthLimit;
    }

    private int getVisitedPagesLimit() {
        return visitedPagesLimit;
    }

    public void launchCrawling(){

        webCrawler = new WebCrawler(getTerms(), getDepthLimit(), getVisitedPagesLimit());
        webCrawler.crawl(getHtmlDocument(), 1);
    }

    public void getStatistics() throws IOException {
        /*
         * All statistics of visited pages are saved in the file all Statistics-<hh_mm_ss>.csv,
         * top 10 statistics on the total number of terms are printed to the console
         * and saved in a separate file topStatistics-<hh_mm_ss>.csv
         */
        webCrawler.printTopStatistics();
        webCrawler.saveStatisticsToCSVFile();
    }
}
