package org.example;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.HashMap;

public class Statistic {

    private HashMap<String, Integer> termsStatistic;
    private Document doc;

    public Statistic(String[] userTerms, Document doc){
        termsStatistic = new HashMap<>();
        this.doc = doc;

        for(String userTerm: userTerms) {
            termsStatistic.put(userTerm, 0);
        }
    }

    //updating termsStatistic values according to Document
    //Перебор всех строк в документе.Для каждого термина считаем и обновляем число вхождений в текущую строку.
    public void getValues(){

        Elements elements = doc.body().select("*");

        for (Element element : elements) {
            String currentLine = element.ownText();

            for(HashMap.Entry<String, Integer> entry: termsStatistic.entrySet()) {
                updateEntryValue(entry, currentLine);
            }
        }
    }

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

    public int getTotal(){
        int total = 0;
        for(HashMap.Entry<String, Integer> entry: termsStatistic.entrySet()){
            total += entry.getValue();
        }
        return total;
    }

    @Override
    public String toString() {
        String result = new String();

        result += doc.baseUri();
        for (HashMap.Entry<String, Integer> entry: termsStatistic.entrySet()){
            result += " " + entry.getValue();
        }

        result += " " + getTotal();
        return result;
    }
}

