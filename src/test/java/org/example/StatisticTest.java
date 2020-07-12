package org.example;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class StatisticTest {

    @Test
    public void assert_hashMap() {

        HashMap<String, Integer> map = new HashMap<>();
        map.put("Julian", 10);
        map.put("and", 15);
        map.put("tuturu", 0);
        map.put("Julian Assange", 3);

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("Julian Assange", 3);
        expected.put("and", 15);
        expected.put("Julian", 10);
        expected.put("tuturu", 0);

        //Test equality
        assertThat(map, is(expected));

        //Test size
        assertThat(map.size(), is(4));

        //Test map entry
        assertThat(map, IsMapContaining.hasEntry("and", 15));

        assertThat(map, not(IsMapContaining.hasEntry("newEntry", 6)));

        //Test map key
        assertThat(map, IsMapContaining.hasKey("tuturu"));

        assertThat(map, not(IsMapContaining.hasKey("notExistedKey")));

        //Test map value
        assertThat(map, IsMapContaining.hasValue(0));

        assertThat(map, not(IsMapContaining.hasValue(1)));
    }
}
