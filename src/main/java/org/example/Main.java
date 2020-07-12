package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * ToDo:
 * 8. JavaDoc/comments
 * 9. Read me gitHub
 * 10. CSV таблица из 3 столбцов: ссылка статистика тотал. Вместо toString -> свой метод toThreeStrings
 * 11. Почему первая строка в кавычках?
 * 12. Спрашивать у пользователя про depth limit и visited pages limit,
 *      но оставить возхможность использовать default, depthLimit = 8 и
 *      visitedPagesLimit = 10_000. По аналогии с terms
 * 13. спрашивать seed у пользователя
 */

/**
 * @author Katya Malets
 */
public class Main {
    public static void main(String[] args) throws IOException {

        String seed;
        String[] terms;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Input terms: ");
        String termsLine = scanner.nextLine();

        if (!termsLine.isEmpty()) {
            terms = termsLine.split(",");
        } else {
            terms = new String[]{"and", "la", "Статья", "Julian", "no"};
            System.out.println("Using default terms: " + Arrays.toString(terms));
        }

        System.out.print("Input seed: ");
        String seedLine = scanner.nextLine();

        if (!seedLine.isEmpty()) {
            seed = seedLine;
        } else {
            System.out.println("Using default seed: " + "https://en.wikipedia.org/wiki/Quentin_Tarantino");
            seed = "https://en.wikipedia.org/wiki/Quentin_Tarantino";
        }
        scanner.close();

        Document htmlDocument = Jsoup.connect(seed).get();

        WebCrawler webCrawler = new WebCrawler(terms);
        webCrawler.crawl(htmlDocument, 1);

        webCrawler.printTopStatistics();
        webCrawler.saveStatisticsToCSVFile();
    }
}
