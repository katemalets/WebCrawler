package org.example;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.HashMap;

/**
 * Class with properties : termsStatistic, doc
 * contains information about single statistic and puts user terms in HashMap
 * counts and updates values, gets total and override toString method
 */
public class Statistic {

    /*
     * termsStatistic - a map that contains userTerm as the key and number of repetition as value
     * doc - html page where user terms are collected
     */
    private HashMap<String, Integer> termsStatistic;
    private Document doc;

    /**
     * Constructor - creating a new object with certain values
     * @param userTerms - words that user passed
     * @param doc - html page where user terms are collected
     */
    public Statistic(String[] userTerms, Document doc){

        termsStatistic = new HashMap<>();
        this.doc = doc;

        for(String userTerm: userTerms) {
            termsStatistic.put(userTerm, 0);
        }
    }

    /**
     * gets all the elements in the page and iterate them
     * term is counted and updated the number of occurrences
     * updates terms statistic values according to document
     */
    public void getValues(){

        Elements elements = doc.body().select("*");

        for (Element element : elements) {
            String currentLine = element.ownText();

            for(HashMap.Entry<String, Integer> entry: termsStatistic.entrySet()) {
                updateEntryValue(entry, currentLine);
            }
        }
    }

    /**
     * updates entry values, finds the same word as key - remember word's index,
     * changes this index, continues until the words are found
     * @param entry - one term from map (use it to find the same term in html doc)
     * @param line - raw html text without tags
     */
    public void updateEntryValue(HashMap.Entry<String, Integer> entry, String line){

        int lastPos = -1;
        while (true) {
            lastPos = line.indexOf(entry.getKey(), lastPos + 1);
            if (lastPos >= 0) {
                Integer oldValue = entry.getValue();
                entry.setValue(oldValue + 1);
            } else {
                break;
            }
        }
    }

    /**
     * counts all term values
     * @return a summary number of terms occurred on one page
     */
    public int getTotalTermsOccurred(){

        int totalTermsOccurred = 0;
        for(HashMap.Entry<String, Integer> entry: termsStatistic.entrySet()){
            totalTermsOccurred += entry.getValue();
        }
        return totalTermsOccurred;
    }

    /**
     * prints top statistics to console and to csv file
     * @return result as a string consisting of 3 parts: link, values and total
     */
    @Override
    public String toString() {

        String result = new String();

        result += doc.baseUri();
        for (HashMap.Entry<String, Integer> entry: termsStatistic.entrySet()){
            result += " " + entry.getValue();
        }

        result += " " + getTotalTermsOccurred();
        return result;
}

    public HashMap<String, Integer> getTermsStatistic() {
        return termsStatistic;
    }
}

