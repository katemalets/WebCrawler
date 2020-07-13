package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Class WebCrawler with properties: depthLimit, visitedPagesLimit,
 * visitedPages, deadLinks, userTerms, statistics.
 * Follows predefined seed, link depth and visited pages limit,
 * detects the presence of user terms on the page and collects statistic
 */
public class WebCrawler {

    /*
     *  visitedPages - number of visited pages (starts with 1 - seed)
     *  statistics - collection of statistics that is created and filled in in the future
     */
    private int depthLimit;
    private int visitedPagesLimit;
    private int visitedPages = 1;

    private String[] terms;
    private Statistics statistics = new Statistics();

    /**
     * Constructor - creating a new object with certain values
     * @param terms - set of words (or word combinations) to be searched by Web Crawler
     * @param depthLimit - maximum depth value that the user fills in
     * @param visitedPagesLimit - max number of web-pages to be traversed by Web Crawler
     */
    public WebCrawler(String[] terms, int depthLimit, int visitedPagesLimit){
        this.terms = terms;
        this.depthLimit = depthLimit;
        this.visitedPagesLimit = visitedPagesLimit;
    }

    /**
     * Crawl starts from seed, collects all user terms,
     * chooses attribute <a href>, parses it and if meets depth limit - breaks,
     * else visits next link and collects all user terms there
     * @param doc - html page where user terms are collected
     * @param depth - min number of transitions from seed to any traversed link (in the beginning - 1),
     * user puts his limit and this function will increase to the user's limit (or default limit)
     */
    public void crawl(Document doc, int depth){

        Statistic statistic = new Statistic(terms, doc);
        statistic.getValues();
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
                    System.out.println("This page no longer exists");
                }
            }
        }
    }

    /**
     * Saves statistics to csv file
     * @throws IOException if a non-existent prefix sets
     */
    public void saveStatisticsToCSVFile() throws IOException {
        statistics.saveToCSVFile("all");
    }

    /**
     *  Prints top statistics and saves it
     *  @throws IOException if a file with forbidden characters created
     */
    public void printTopStatistics() throws IOException {
        statistics.printTopStatistics();
    }

}
