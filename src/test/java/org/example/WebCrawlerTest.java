package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WebCrawlerTest {

    String[] terms = {"a", "а", "Статья", "Julian", "год"};
    String actualLink = "https://ru.wikipedia.org/wiki/Ассанж,_Джулиан";
    Document actualDoc = Jsoup.connect(actualLink).get();
    int depthLimit = 8;
    int visitedPagesLimit = 20;


    public WebCrawlerTest() throws IOException {
    }

    @Test
    public void crawl_link() {

        WebCrawler webCrawler = new WebCrawler(terms,depthLimit,visitedPagesLimit);
        webCrawler.crawl(actualDoc,1);

        String actualLink = "https://ru.wikipedia.org/wiki/Ассанж,_Джулиан";
        String expected = "https://ru.wikipedia.org/wiki/Ассанж,_Джулиан";

        //Test equality
        assertThat(actualLink, is(expected));

        //Test size
        assertThat(actualLink.length(),is(45));

        //Test for a link
        assertTrue(actualLink, actualLink.startsWith("http"));

        assertFalse(actualLink, actualLink.matches("notExisted"));
    }
}
