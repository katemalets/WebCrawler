package org.example;

import org.hamcrest.collection.IsMapContaining;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StatisticTest {

    @Test
    public void testGetTotalTermsOccurred() throws IOException {
        String[] userTerms = {"1","2","3","4","5"};
        String seed = "https://en.wikipedia.org/";
        Document document = Jsoup.connect(seed).get();
        Statistic statistic = new Statistic(userTerms,document);

        //Subtest #1
        assertEquals(0, statistic.getTotalTermsOccurred()); //zero sum

        //Subtest #2
        statistic.getValues();
        assertEquals(83, statistic.getTotalTermsOccurred()); //manually counted
    }

    @Test
    public void testGetValues() throws IOException{
        String[] userTerms = {"one","Belgium","and","is","page"};
        String seed = "https://www.youtube.com/about/";
        Document document = Jsoup.connect(seed).get();
        Statistic statistic = new Statistic(userTerms,document);

        statistic.getValues();
        HashMap<String, Integer> actualTermsStatistic = statistic.getTermsStatistic();

        // Test of a known response
        Arrays.equals(actualTermsStatistic.values().toArray(), new Integer[]{0, 20, 6, 16, 0});
    }

    @Test
    public void testUpdateEntryValue() throws IOException {
        String[] userTerms = {"lib"};
        String seed = "https://en.wikipedia.org/";  // does not matter
        Document document = Jsoup.connect(seed).get();
        Statistic statistic = new Statistic(userTerms,document);

        HashMap<String, Integer> actualTermsStatistic = statistic.getTermsStatistic();
        Set<HashMap.Entry<String, Integer>> actualEntrySet = actualTermsStatistic.entrySet();

        statistic.updateEntryValue(actualEntrySet.iterator().next(), "Librarian stuck in library looking for .lib");
        assertEquals(2, (long) actualTermsStatistic.get("lib"));
    }

    @Test
    public void testToString() throws IOException {
        String[] userTerms = {"1","2","3","4","5"};
        String seed = "https://en.wikipedia.org/";
        Document document = Jsoup.connect(seed).get();
        Statistic statistic = new Statistic(userTerms,document);
        statistic.getValues();
        String actualString = statistic.toString();

        assertEquals("https://en.wikipedia.org/wiki/Main_Page 32 23 13 9 6 83", actualString);
    }
}
