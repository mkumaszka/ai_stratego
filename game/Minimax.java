package game;

import java.util.ArrayList;
import java.util.Collections;

public class Minimax {

    int initialDepth;

    public Minimax(int depth) {
        this.initialDepth = depth;
    }

    private int minimax(int[][] board, int depth, boolean isMaxPlayer, int score){
        int[][] possibleBoard;
        if(depth == 0 || isTerminal(board)){
            return score;
        }
        ArrayList<Move> possibleMoves = getPossibleMoves(board);
        if(isMaxPlayer){
            int bestValue = Integer.MIN_VALUE;
            for (Move move: possibleMoves){
                possibleBoard = makeMove(board,move);
                int moveScore = score + move.getMoveScore(possibleBoard);
                int value = minimax(possibleBoard, depth - 1, false, moveScore);
                if(value > bestValue){
                    bestValue = value;
                }
            }
            return bestValue;
        }
        else {
            int bestValue = Integer.MAX_VALUE;
            for (Move move: possibleMoves){
                possibleBoard = makeMove(board,move);
                int moveScore = score - move.getMoveScore(possibleBoard);
                int value = minimax(possibleBoard, depth - 1, true, moveScore);
                if(value < bestValue){
                    bestValue = value;
                }
            }
            return bestValue;
        }
    }

    public Move startMinimax(int[][] board){
        int score;
        int[][] possibleBoard;
        ArrayList<Move> possibleMoves = getPossibleMoves(board);
        for (Move move: possibleMoves) {
            possibleBoard = makeMove(board,move);
            score = move.getMoveScore(possibleBoard);
            move.setValue(minimax(possibleBoard, this.initialDepth - 1, false, score));
        }
        return Collections.max(possibleMoves);
    }

    private ArrayList<Move> getPossibleMoves(int[][] board) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int row = 0; row<board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if(board[row][col] == 0){
                    moves.add(new Move(row,col));
                }
            }
        }
        return moves;
    }

    private boolean isTerminal(int[][] board) {
        for (int row = 0; row<board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if(board[row][col] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] makeMove(int[][] board, Move move){
        int[][] new_board = Utils.cloneArray(board);
        new_board[move.getRow()][move.getCol()] = 1;
        return new_board;
    }
}
