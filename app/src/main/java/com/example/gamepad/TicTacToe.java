package com.example.gamepad;

import java.util.ArrayList;

public class TicTacToe {
    private int[][] board;
    private String player;
    private int playerVal;
    private final int DIMENSIONS = 3;

    /**
     * Default constructor
     */
    public TicTacToe() {
        this.board = new int[3][3];
        this.player = "X";
        this.playerVal = 1;
        clear();
    }

    /**
     * Clears the board and reset variables
     */
    public void clear(){
        for (int row = 0; row < DIMENSIONS; row++){
            for (int col = 0; col < DIMENSIONS; col++){
                board[row][col] = -1;
            }
        }
        player = "X";
        playerVal = 1;
    }

    /**
     * Add a move to the game board and updates latest move
     * @param row row of board cell
     * @param col column of board cell
     */
    public void addToBoard(int row, int col) {
        if (board[row][col] == -1) {
            board[row][col] = playerVal;
        }
    }

    /**
     * returns the winner if there is, -1 if none
     * @return the winner or -1 if no winner
     */
    public int winner() {
        boolean rowWin, colWin, diagWin;
        // check rows & columns
        for (int i = 0; i < DIMENSIONS; i++) {
            rowWin = board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != -1;
            colWin = board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != -1;
            if (rowWin)
                return board[i][0];
            if (colWin)
                return board[0][i];
        }
        // check diagonals
        diagWin = board[1][1] != -1 &&
                (board[0][0] == board[1][1] && board[0][0] == board[2][2] ||
                board[0][2] == board[1][1] && board[0][2] == board[2][0]);
        if (diagWin)
            return board[1][1];
        return -1;
    }

    /**
     * Returns if board is full
     * @return true if board is full
     */
    public boolean boardIsFull() {
        for (int[] r : board) {
            for (int c : r) {
                if (c == -1)
                    return false;
            }
        }
        return true;
    }

    /**
     * Switches to next player and update player value
     */
    public void nextPlayer() {
        if (playerVal == 1) {
            player = "O";
            playerVal = 0;
        }
        else {
            player = "X";
            playerVal = 1;
        }
    }

    /**
     * returns the current player
     * @return the current player
     */
    public String getPlayer() {
        return player;
    }

    private ArrayList<int[]> getPossibleMoves() {
        ArrayList<int[]> moveList = new ArrayList<>();
        for (int row = 0; row < DIMENSIONS; row++) {
            for (int col = 0; col < DIMENSIONS; col++) {
                if (board[row][col] == -1) {
                    int[] move = {row,col};
                    moveList.add(move);
                }
            }
        }
        return moveList;
    }

    /**
     * returns a score based on who won
     * @return -100 if player wins, 100 if computer wins, 0 for tie, -1 for no conclusion
     */
    private int moveHeuristic() {
        int result = winner();
        // human wins
        if (result == 1)
            return -100;
        // ai wins
        if (result == 0)
            return 100;
        // a tie
        if (result == -1 && boardIsFull())
            return 0;
        return -1;
    }

    /**
     * returns the optimized move for the opponent
     * @return the cell number for the best possible move
     */
    public int bestMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestIndex = -1;
        for (int[] move : getPossibleMoves()) {
            int r = move[0];
            int c = move[1];
            // make move
            board[r][c] = playerVal;
            int score = minimax(0, false);
            // reverse move
            board[r][c] = -1;
            if (score > bestScore) {
                bestScore = score;
                bestIndex = r * DIMENSIONS + c + 1;
            }
        }
        return bestIndex;
    }

    /**
     * The minimax algorithm
     * @param depth depth of recursion
     * @param isMax is maximizing
     * @return returns score of game state
     */
    private int minimax(int depth, boolean isMax) {
        int result = moveHeuristic();
        if (result != -1)
            return result;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int[] move : getPossibleMoves()) {
            int r = move[0];
            int c = move[1];
            // make move
            nextPlayer();
            board[r][c] = playerVal;
            if (isMax) {
                int score = minimax(depth + 1, false);
                max = Math.max(score, max);
            } else {
//                board[r][c] = 1;
                int score = minimax(depth + 1, true);
                min = Math.min(min,score);
            }
            // reverse move
            nextPlayer();
            board[r][c] = -1;
        }
        return isMax ? max : min;
    }
}
