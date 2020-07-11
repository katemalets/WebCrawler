package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * ToDo:
 * 6. GitHub
 * 7. Tests (coverage >40%)
 * 8. JavaDoc/comments
 * 9. Сделать класс Собиратор-статистик (в него кинуть глобальные переменные)
 * 10. CSV таблица из 3 столбцов: ссылка статистика тотал. Вместо toString -> свой метод toThreeStrings
 * 11. Почему первая строка в кавычках?
 */
public class App 
{
    static final int depthLimit = 8;
    static final int visitedPagesLimit = 20;
    static int visitedPages = 1;
    static int deadLinks = 0;
    static Statistics statistics = new Statistics();

    public static void main( String[] args ) throws IOException {

        String seed = "https://ru.wikipedia.org/wiki/Ассанж,_Джулиан";
        Document htmlDocument   = Jsoup.connect(seed).get();
        String[] terms = getTerms();

        crawl(htmlDocument, terms, 1);

        statistics.saveToCSVFile("all");
        statistics.printTopStatistics();

    }

    public static void crawl(Document doc, String[] userTerms, int depth){

        Statistic statistic = new Statistic(userTerms, doc);
        statistic.getValues();
        //System.out.println("Depth: " + depth + ". Visited pages: " + visitedPages);

        statistics.addStatistic(statistic);

        Elements links = doc.select("a[href]");
        for(Element element: links){
            if(depth >= depthLimit || visitedPages >= visitedPagesLimit) {
                return;
            }

            String link = element.attr("href");
            if(link.startsWith("http")) {
                try {
                    Document nextDoc = Jsoup.connect(link).get();
                    visitedPages++;
                    crawl(nextDoc, userTerms, depth + 1);
                } catch (Exception exc) {
                    deadLinks++;
                }
            }
        }
    }

    public static String[] getTerms(){
        String[] terms;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input terms: ");
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
