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
 * JavaDoc/comments
 * Read me gitHub
 * CSV таблица из 3 столбцов: ссылка статистика тотал. Вместо toString -> свой метод toThreeStrings
 * Навести порядок в main()
 * Устранить баг
 *
 */
/**
 * @author Katya Malets
 */
public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Input terms separated by comma: ");
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
        final String defaultSeed = "https://en.wikipedia.org/wiki/Quentin_Tarantino";

        String seed = scanner.nextLine();
        if (seed.isEmpty()) {
            System.out.println("Using default seed: " + defaultSeed);
            seed = defaultSeed;
        }

        Document htmlDocument;

        try {
            htmlDocument = Jsoup.connect(seed).get();
        } catch (IllegalArgumentException exception) {
            System.out.println("Using default seed: " + defaultSeed);
            htmlDocument = Jsoup.connect(defaultSeed).get();
        }

        System.out.print("Input depth limit: ");
        int depthLimit;
        final int defaultDepthLimit = 8;

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
        int visitedPagesLimit;
        int defaultVisitedPagesLimit = 15;

        try {
            visitedPagesLimit = scanner.nextInt();
            if(visitedPagesLimit <= 0) {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException exc) {
            System.out.println("Using default visited pages limit: " + defaultVisitedPagesLimit);
            visitedPagesLimit = defaultVisitedPagesLimit;
        }

        if(visitedPagesLimit >= 500) {
            System.out.println("Process may take significant time due to visited" +
                    " pages limit is set to " + visitedPagesLimit);
        }

        scanner.close();

        WebCrawler webCrawler = new WebCrawler(terms, depthLimit, visitedPagesLimit);
        webCrawler.crawl(htmlDocument, 1);

        webCrawler.printTopStatistics();
        webCrawler.saveStatisticsToCSVFile();

    }
}
