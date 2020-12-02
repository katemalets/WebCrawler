package org.example;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface IWebCrawler {

    void crawl(Document doc, int depth);

    void saveStatisticsToCSVFile() throws IOException;

    void printTopStatistics() throws IOException;

}
