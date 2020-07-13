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
     *  depthLimit - link depth that user sets (passed through constructor)
     *  visitedPagesLimit - max visited pages limit that user sets (passed through constructor)
     *  visitedPages - number of visited pages (starts with 1 - seed)
     *  terms - words that user passed (passed through constructor)
     *  statistics - collection of statistics that is created and filled in in the future
     */
    private int depthLimit;
    private int visitedPagesLimit;
    private int visitedPages = 1;

    private String[] terms;
    private Statistics statistics = new Statistics();

    /**
     * Constructor - creating a new object with certain values
     * @param terms - words that user passed
     * @param depthLimit - link depth that user sets
     * @param visitedPagesLimit - max visited pages limit that user sets
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
     * @param depth - the number of possible clicks from one link to another (in the beginning - 1),
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
                    exc.getMessage();
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
