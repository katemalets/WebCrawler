package org.example;

import java.io.IOException;

import static org.example.User.createUser;

/**
 * @author Kate Malets
 * @version 2.0-beta
 */
public class Main {

    /**
     * Entry point of the program is here
     * @param args command line values
     * @throws IOException may be thrown if file name has forbidden characters
     */
    public static void main(String[] args) throws IOException {

        IUser user = createUser();

        user.userInput();
        user.launchCrawling();
        user.getStatistics();

        System.out.println("Statistics successfully collected and saved to csv files.");
    }
}
