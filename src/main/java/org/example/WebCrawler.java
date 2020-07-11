package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * limit
 * pagesLimit
 * visitedPages
 * deadLinks
 *
 * metod
 * собрать всю статистику!
 */

public class WebCrawler {

    private final int depthLimit = 8;
    private final int visitedPagesLimit = 20;
    private int visitedPages = 1;
    private int deadLinks = 0;

    private String[] userTerms;
    private Statistics statistics;

    public WebCrawler(Statistics statistics, String[] userTerms){
        this.statistics = statistics;
        this.userTerms = userTerms;
    }

    public void crawl(Document doc, int depth){

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
                    crawl(nextDoc,depth + 1);
                } catch (Exception exc) {
                    deadLinks++;
                }
            }
        }
    }
}
