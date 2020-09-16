package com.example.gamepad;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;


public class Hangman {
    private WordBankProcessor processor;
    private String word;
    private int tries;
    private char[] wordGuess;
    private HashSet<Character> usedLetters;

    /**
     * Constructor
     * @param inputStream input stream
     */
    public Hangman(InputStream inputStream) {
        this.processor = new WordBankProcessor(inputStream);
        this.usedLetters = new HashSet<>();
        reset();
    }

    /**
     * Returns if a guess is successful or not
     * @param guess a character guess
     * @return true if a guess is sucessful
     */
    public boolean guess(char guess) {
        int letterPlaced = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                wordGuess[i] = guess;
                letterPlaced++;
            }
        }
        if (letterPlaced == 0) {
            tries--;
            return false;
        }
        return true;
    }

    /**
     * Returns if guessed word is equal to actual word
     * @return true if guessed word is equal to actual word
     */
    public boolean isWinner() {
        for (int i = 0; i < word.length(); i++){
            if (word.charAt(i) != wordGuess[i]) return false;
        }
        return true;
    }

    /**
     * Returns array of word guess to a string
     * @return Returns array of word guess to a string
     */
    public String guessToString() {
        StringBuilder str = new StringBuilder();
        for (char c : wordGuess) {
            str.append(c).append(" ");
        }
        return str.toString();
    }

    /**
     * Adds a letter to HashSet of used letters
     * @param c a letter
     * @return true if adding to HashSet is successful
     */
    public boolean addLetter(char c) { return usedLetters.add(c); }

    /**
     * Returns the number of tries
     * @return the number of tries
     */
    public int getTries() { return tries; }

    /**
     * Resets Hangman like it has been just initialized
     */
    public void reset() {
        word = processor.getRandomWord();
        wordGuess = new char[word.length()];
        Arrays.fill(wordGuess, '_');
        tries = 6;
        usedLetters.clear();
    }

}
