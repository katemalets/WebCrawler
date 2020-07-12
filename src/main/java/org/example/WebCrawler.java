package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebCrawler {

    private int depthLimit = 8;
    private int visitedPagesLimit = 20;
    private int visitedPages = 1;
    private int deadLinks = 0;

    private String[] userTerms;
    private Statistics statistics = new Statistics();

    public WebCrawler(String[] userTerms, int depthLimit, int visitedPagesLimit){
        this.userTerms = userTerms;
        this.depthLimit = depthLimit;
        this.visitedPagesLimit = visitedPagesLimit;
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

    public void saveStatisticsToCSVFile() throws IOException {
        statistics.saveToCSVFile("all");
    }

    public void printTopStatistics() throws IOException {
        statistics.printTopStatistics();
    }

}
