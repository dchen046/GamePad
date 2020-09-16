package com.example.gamepad;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


public class TicActivityPvc extends AppCompatActivity {
    private TicTacToe game;
    private TextView playerInfo;
    private Button[] buttons;
    private Button easyBtn, hardBtn;
    private boolean hasWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_pvc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeGame();
    }

    /**
     * Initializes the game with default values
     */
    private void initializeGame() {
        buttons = new Button[9];
        for (int i = 1; i <= 9; i++) {
            int id = getResources().getIdentifier("button"+i, "id", getPackageName());
            buttons[i-1] = findViewById(id);
            buttons[i-1].setEnabled(false);
        }
        game = new TicTacToe();
        playerInfo = findViewById(R.id.playerInfo);
        easyBtn = findViewById(R.id.button_easy);
        hardBtn = findViewById(R.id.button_hard);
        hasWinner = false;
        playerInfo.setText(game.getPlayer());
    }

    /**
     * Process the click of the easy button
     * @param view screen
     */
    public void easyClick(View view) {
        resetGame(view);
        easyBtn.setEnabled(false);
        hardBtn.setEnabled(true);
        enableBoard();
    }

    /**
     * Process the click of the hard button
     * @param view screen
     */
    public void hardClick(View view) {
        resetGame(view);
        hardBtn.setEnabled(false);
        easyBtn.setEnabled(true);
        enableBoard();
    }

    /**
     * Process the click of a cell
     * @param view screen
     */
    public void cellClick(View view) {
        placeChoice(view.getId());
        if (!hasWinner) {
            if (!easyBtn.isEnabled())
                easyAiTurn();
            else if (!hardBtn.isEnabled())
                hardAiTurn();
        }
    }

    /**
     * Resets the state of the game
     * @param view screen
     */
    public void resetGame(View view) {
        game.clear();
        hasWinner = false;
        playerInfo.setText(game.getPlayer());
        for (Button b : buttons) {
            b.setText("");
            b.setEnabled(true);
        }
    }

    /**
     * Enables all the cells of the board
     */
    private void enableBoard() {
        for (Button b : buttons) {
            b.setEnabled(true);
        }
    }

    /**
     * Updates display with moves made
     * @param id id of the button pressed
     */
    private void placeChoice(int id) {
        String currPlayer = game.getPlayer();
        Button cell = findViewById(id);
        cell.setText(currPlayer);
        cell.setEnabled(false);
        switch (id) {
            case R.id.button1:
                game.addToBoard(0, 0);
                break;
            case R.id.button2:
                game.addToBoard(0, 1);
                break;
            case R.id.button3:
                game.addToBoard(0, 2);
                break;
            case R.id.button4:
                game.addToBoard(1, 0);
                break;
            case R.id.button5:
                game.addToBoard(1, 1);
                break;
            case R.id.button6:
                game.addToBoard(1, 2);
                break;
            case R.id.button7:
                game.addToBoard(2, 0);
                break;
            case R.id.button8:
                game.addToBoard(2, 1);
                break;
            case R.id.button9:
                game.addToBoard(2, 2);
                break;
        }
        checkGameStatus(currPlayer,game.winner());
        game.nextPlayer();
        playerInfo.setText(game.getPlayer());
    }

    /**
     * Checks the current game status and gives appropriate message
     * @param currPlayer current player
     */
    private void checkGameStatus(String currPlayer, int winner) {
        String message;
        hasWinner  = winner != -1;
        boolean endOfGame =  hasWinner || game.boardIsFull();
        if (endOfGame) {
            if (hasWinner) {
                if (currPlayer.equals(("X"))) {
                    message = "You won against the computer!";
                } else {
                    message = "The computer has won! Better luck next time.";
                }
                for (Button b : buttons)
                    b.setEnabled(false);
            } else {
                message = "No Winner. It is a tie!";
            }
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * Process a random move on the tic-tac-toe board
     */
    private void easyAiTurn() {
        Button aiChoice;
        // stops when empty cell is found
        do {
            int randNum = new Random().nextInt(buttons.length);
            aiChoice = buttons[randNum];
            System.out.println(aiChoice.isEnabled());
        } while (!aiChoice.isEnabled());
        placeChoice(aiChoice.getId());
    }

    /**
     * Process an optimized move on the tic-tac-toe board
     */
    private void hardAiTurn() {
        int bestMove = game.bestMove();
        switch(bestMove) {
            case 1:
                placeChoice(R.id.button1);
                break;
            case 2:
                placeChoice(R.id.button2);
                break;
            case 3:
                placeChoice(R.id.button3);
                break;
            case 4:
                placeChoice(R.id.button4);
                break;
            case 5:
                placeChoice(R.id.button5);
                break;
            case 6:
                placeChoice(R.id.button6);
                break;
            case 7:
                placeChoice(R.id.button7);
                break;
            case 8:
                placeChoice(R.id.button8);
                break;
            case 9:
                placeChoice(R.id.button9);
                break;
        }
    }
}