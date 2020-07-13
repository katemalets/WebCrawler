package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class gives an opportunity for user to give default or user's values
 */
public class UserInput {

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

    public void userInput() throws IOException {

        Scanner scanner = new Scanner(System.in);

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
         */
        try {
            htmlDocument = Jsoup.connect(seed).get();
        } catch (IllegalArgumentException exception) {
            System.out.println("Using default seed: " + defaultSeed);
            htmlDocument = Jsoup.connect(defaultSeed).get();
        }

        System.out.print("Input depth limit: ");

        // defaultDepthLimit - depth to be used in extreme cases
        final int defaultDepthLimit = 8;

        /*
         * InputMismatchException may be caught if user inputs letters instead
         * of numbers or may be thrown if user inputs negative number
         */
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

        System.out.print("Input visited pages limit: ");

        // defaultVisitedPagesLimit - visited pages limit to be used in extreme cases
        int defaultVisitedPagesLimit = 15;

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

        scanner.close();
    }

    public String[] getTerms() {
        return terms;
    }

    public Document getHtmlDocument() {
        return htmlDocument;
    }

    public int getDepthLimit() {
        return depthLimit;
    }

    public int getVisitedPagesLimit() {
        return visitedPagesLimit;
    }
}
