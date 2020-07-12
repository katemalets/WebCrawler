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

    //basic info
    String[] terms = {"a", "а", "Статья", "Julian", "год"};

    String actualLink = "https://ru.wikipedia.org/wiki/Ассанж,_Джулиан";
    Document actualDoc = Jsoup.connect(actualLink).get();

    String anotherLink = "https://www.culture.ru/persons/8127/nikolai-gogol";
    Document anotherDoc = Jsoup.connect(anotherLink).get();

    public StatisticsTest() throws IOException {
    }

    @Test
    public void check_adding_statistic(){

        Statistic actualStatistic = new Statistic(terms,actualDoc);

        Statistics actualStatistics = new Statistics();

        actualStatistics.addStatistic(actualStatistic);

        ArrayList<Statistic> statistics = actualStatistics.getStatistics();

        //Test size
        assertEquals(1, statistics.size());

        //Test availability
        assertTrue(statistics.contains(actualStatistic));
    }

    @Test
    public void check_CSVFile_saving() throws IOException {

        Statistics actualStatistics = new Statistics();
        Statistics expectedStatistics = new Statistics();

        actualStatistics.saveToCSVFile("test");
        expectedStatistics.saveToCSVFile("expect");

        //Test created files with different names
        assertFalse(expectedStatistics.equals(actualStatistics));

        //Test bad file names
        try {
            actualStatistics.saveToCSVFile("^жж::!?");
            fail();
        } catch (FileNotFoundException exc) {
            assertTrue(exc.getMessage().endsWith("(Синтаксическая ошибка в имени файла, имени папки или метке тома)"));
        }
    }

    @Test
    public void print_top_statistics() throws IOException {

        Statistics actualStatistics = new Statistics();

        ArrayList<Statistic> baseStatistics = actualStatistics.getStatistics();
        ArrayList<Statistic> copyStatistics = new ArrayList<>(baseStatistics);

        //Test for copy array
        Assert.assertArrayEquals(baseStatistics.toArray(), copyStatistics.toArray());

        Statistic statisticOne = new Statistic(terms, actualDoc);
        Statistic statisticTwo = new Statistic(terms, anotherDoc);

        copyStatistics.add(statisticOne);
        copyStatistics.add(statisticTwo);

        //Test size
        assertEquals(2, copyStatistics.size());


        for (int i = 0; i < 30; i++) {
            actualStatistics.addStatistic(statisticOne);
        }

        int limit = 10;
        actualStatistics.printTopStatistics();

        //Test working limit
        assertTrue(actualStatistics.getStatistics().size() > limit);
    }

    @Test
    public void check_getter_statistics(){

        Statistics statistics = new Statistics();
        assertTrue(statistics.getStatistics().size() == 0);

    }
}
