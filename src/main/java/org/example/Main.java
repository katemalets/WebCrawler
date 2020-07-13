package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * ToDo:
 *
 * CSV таблица из 3 столбцов: ссылка статистика тотал. Вместо toString -> свой метод toThreeStrings
 * Навести порядок в main()
 * Устранить баг
 * Убрать класс с Компаратором и заменить на лямбда выражение
 * Изменить дефолтные поля
 */
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

        Scanner scanner = new Scanner(System.in);

        System.out.print("Input terms separated by comma: ");

        /*
         * terms - set of words (or word combinations) to be searched by Web Crawler
         * defaultTerms - terms to be used in extreme cases
         */
        String[] terms;
        final String[] defaultTerms = {"and", "la", "Статья", "Julian", "no"};

        String termsLine = scanner.nextLine();
        if(termsLine.isEmpty()){
            System.out.println("Using default terms: " + Arrays.toString(defaultTerms));
            terms = defaultTerms;
        } else {
            terms = termsLine.split(",");
        }

        System.out.print("Input seed: ");

        /*
         * seed - a URL which determines Web Crawler's entry point to start surfing web
         * defaultSeed - seed to be used in extreme cases
         */
        final String defaultSeed = "https://en.wikipedia.org/wiki/Quentin_Tarantino";

        String seed = scanner.nextLine();
        if (seed.isEmpty()) {
            System.out.println("Using default seed: " + defaultSeed);
            seed = defaultSeed;
        }

        Document htmlDocument;

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

        /*
         * depthLimit - maximum depth value that the user fills in
         * defaultDepthLimit - depth to be used in extreme cases
         */
        int depthLimit;
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
        }

        System.out.print("Input visited pages limit: ");

        /*
         * visitedPagesLimit - max number of web-pages to be traversed by Web Crawler
         * defaultVisitedPagesLimit - visited pages limit to be used in extreme cases
         */
        int visitedPagesLimit;
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

        if(visitedPagesLimit >= 300) {
            System.out.println("Process may take significant time due to visited" +
                    " pages limit is set to " + visitedPagesLimit);
        }

        scanner.close();

        /*
         * webCrawler traverses websites following predefined seed, link depth and visited pages limit,
         * detects the presence of user terms on the page and collects statistic.
         */
        WebCrawler webCrawler = new WebCrawler(terms, depthLimit, visitedPagesLimit);
        webCrawler.crawl(htmlDocument, 1);


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
