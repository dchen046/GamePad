package com.example.gamepad;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class TicActivityPvp extends AppCompatActivity {
    private TicTacToe game;
    private TextView playerInfo;
    private Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_pvp);
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
        }
        game = new TicTacToe();
        playerInfo = findViewById(R.id.playerInfo);
        playerInfo.setText("X");
    }

    /**
     * Resets the state of the game
     * @param view screen
     */
    public void resetGame(View view) {
        game.clear();
        playerInfo.setText(game.getPlayer());
        for (Button b : buttons) {
            b.setText("");
            b.setEnabled(true);
        }
    }

    /**
     * Process the click of a cell
     * @param view screen
     */
    public void cellClick(View view) {
        placeChoice(view.getId());
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
        checkGameStatus(currPlayer, game.winner());
        game.nextPlayer();
        playerInfo.setText(game.getPlayer());
    }

    /**
     * Checks the current game status and gives appropriate message
     * @param currPlayer current player
     * @param winner winner X is 1, O is 0, Tie is -1
     */
    private void checkGameStatus(String currPlayer, int winner) {
        String message;
        boolean result;
        result  = winner != -1;
        boolean endOfGame = result || game.boardIsFull();
        if (endOfGame) {
            if (result) {
                message = currPlayer + " has won!";
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

}