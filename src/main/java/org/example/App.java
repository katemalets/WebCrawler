package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * ToDo:
 * 7. Tests (coverage >40%)
 * 8. JavaDoc/comments
 * 9. Read me gitHub
 * 10. CSV таблица из 3 столбцов: ссылка статистика тотал. Вместо toString -> свой метод toThreeStrings
 * 11. Почему первая строка в кавычках?
 * 12. Спрашивать у пользователя про depth limit и visited pages limit,
 *      но оставить возхможность использовать default, depthLimit = 8 и
 *      visitedPagesLimit = 10_000. По аналогии с terms
 * 13. спрашивать seed у пользователя
 */
public class App 
{

    public static void main( String[] args ) throws IOException {

        String seed = "https://ru.wikipedia.org/wiki/Ассанж,_Джулиан";
        Document htmlDocument = Jsoup.connect(seed).get();
        String[] terms = getTerms();

        WebCrawler webCrawler = new WebCrawler(terms);
        webCrawler.crawl(htmlDocument, 1);

        webCrawler.printTopStatistics();
        webCrawler.saveStatisticsToCSVFile();
    }

    public static String[] getTerms(){
        String[] terms;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input sample terms: ");
        String line = scanner.nextLine();

        if(!line.isEmpty()){
            terms = line.split(",");
        } else {
            terms = new String[] {"and", "to", "Статья", "Julian", "год"};
            System.out.println("Using default terms: " + Arrays.toString(terms));
        }
        scanner.close();
        return terms;
    }
}
