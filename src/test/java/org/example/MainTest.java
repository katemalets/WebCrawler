package org.example;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainTest
{
    @Test
    public void get_user_terms() {

        String[] actual = {"Julian","and","to"};
        String[] expected = {"Julian","and","to"};

        //Test for identity
        assertArrayEquals("Wrong array", expected, actual);

        //Test for size
        assertEquals(actual.length, 3);
    }
}
