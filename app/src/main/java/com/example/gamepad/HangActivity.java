package com.example.gamepad;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;


public class HangActivity extends AppCompatActivity {
    private Hangman hangman;
    private TextView wordTV, triesTV, lettersTV;
    private EditText input;
    private ImageView hangImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeGame();
    }

    /**
     * Initialize the game with default values
     */
    private void initializeGame() {
        try {
            InputStream inputStream = getAssets().open("words.txt");
            hangman = new Hangman(inputStream);
        } catch (Exception io) {
            System.out.println("Failed to open file");
        }
        wordTV = findViewById(R.id.word);
        wordTV.setText(hangman.guessToString());
        triesTV = findViewById(R.id.tries);
        triesTV.setTextColor(Color.RED);
        lettersTV = findViewById(R.id.lettersUsed);
        input = findViewById(R.id.input);
        hangImage = findViewById(R.id.hangImage);
        String tries = "XXXXXX";
        triesTV.setText(tries);
        updateImageDisplay(6);
        setupInputListener();
    }

    /**
     * Updates the Used Letter Display
     * @param c a letter
     */
    private void updateUsedLetterDisplay(char c) {
        String str = lettersTV.getText().toString();
        str += c + " ";
        lettersTV.setText(str);
    }

    /**
     * Updates the number of tries left on screen
     * @param isSucc success of a guess
     */
    private void updateTryDisplay(boolean isSucc) {
        if (!isSucc) {
            int tries = hangman.getTries();
            StringBuilder sb = new StringBuilder(triesTV.getText());
            sb.deleteCharAt(tries);
            triesTV.setText(sb.toString());
            updateImageDisplay(tries);
        }
    }

    /**
     * Updates the Image Display
     * @param num current number of tries
     */
    private void updateImageDisplay(int num) {
        if (num >= 0 && num <= 6) {
            String route = "@drawable/hang" + num;
            int imgResource = getResources().getIdentifier(route, null, this.getPackageName());
            hangImage.setImageResource(imgResource);
        }
    }

    /**
     * Updates the word your are trying to guess
     * @param c a letter
     */
    private void updateGuessedLettersDisplay(char c) {
        updateTryDisplay(hangman.guess(c));
        wordTV.setText(hangman.guessToString());
        String message;
        boolean endOfGame = hangman.getTries() <= 0 || hangman.isWinner();
        if (endOfGame){
            if (hangman.getTries() <= 0) {
                message = "You Lose! No more tries!";
            } else {
                message = "You Win! Nice Guessing!";
            }
            Toast toast = Toast.makeText(HangActivity.this, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            input.setEnabled(false);
        }
    }

    /**
     * Resets the status of the game
     * @param view screen
     */
    public void resetGame(View view) {
        hangman.reset();
        // reset displays
        wordTV.setText(hangman.guessToString());
        String tries = "XXXXXX";
        triesTV.setText(tries);
        lettersTV.setText("");
        lettersTV.setHint("Letters Used");
        input.setText("");
        input.setEnabled(true);
        updateImageDisplay(6);
    }

    private void setupInputListener() {
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    char guess = charSequence.toString().toLowerCase().charAt(0);
                    // if guess is a letter not used yet
                    if (hangman.addLetter(guess)) {
                        updateUsedLetterDisplay(guess);
                        updateGuessedLettersDisplay(guess);
                    }
                    input.setText("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

}
