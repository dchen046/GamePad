package com.example.gamepad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class WordBankProcessor {
    private ArrayList<String> wordBank;
    private InputStream input;

    /**
     * Constructor
     * @param input input stream
     */
    public WordBankProcessor(InputStream input) {
        this.input = input;
        wordBank = new ArrayList<>();
        populateBank();
    }

    /**
     * Populates the list of words by parsing through the file
     */
    private void populateBank() {
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\\s+","");
                wordBank.add(line.toLowerCase());
            }
        } catch (IOException io) {
            System.out.println("Something went wrong while processing the text file");
        } finally {
            try {
                br.close();
                if (input != null) {
                    input.close();
                }
            } catch (IOException io) {
                System.out.println("Error when closing");
            }

        }
    }

    /**
     * Returns a random word from processed data
     * @return a random word from processed data
     */
    public String getRandomWord() {
        int size = wordBank.size();
        int randNum = new Random().nextInt(size);
        return wordBank.get(randNum);
    }
}
