package org.example;

import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UserInputTest {

    private String[] terms;
    private Document htmlDocument;
    private int depthLimit;
    private int visitedPagesLimit;

    @Test
    public void user_input_test() throws IOException {

        UserInput user = new UserInput();

        // Test for user terms
        assertNull(user.getTerms());

        // Test for user html page
        assertNull(user.getHtmlDocument());

        // Test for user depth limit
        assertEquals(0, user.getDepthLimit());

        //Test for visited pages limit
        assertEquals(0, user.getVisitedPagesLimit());

        String[] actual = {"Julian","and","to"};
        String[] expected = {"Julian","and","to"};

        //Test for identity
        assertArrayEquals("Wrong array", expected, actual);

        //Test for size
        assertEquals(actual.length, 3);
    }
}
