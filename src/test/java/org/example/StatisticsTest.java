package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class StatisticsTest {

    @Test
    public void check_adding_statistic() throws IOException {

        //basic info
        String[] terms = {"a", "аnd", "Статья", "Julian", "год"};

        String actualLink = "https://ru.wikipedia.org/wiki/Ассанж,_Джулиан";
        Document actualDoc = Jsoup.connect(actualLink).get();

        Statistic actualStatistic = new Statistic(terms,actualDoc);
        Statistics actualStatistics = new Statistics();

        actualStatistics.addStatistic(actualStatistic);
        ArrayList<Statistic> statistics = actualStatistics.getStatistics();

        //Test size
        assertEquals(1, statistics.size());

        //Test availability
        assertTrue(statistics.contains(actualStatistic));
    }
}
