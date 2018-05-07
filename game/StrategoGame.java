package game;

import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.BitSet;

public class StrategoGame {

    String COMPUTER = "Computer";
    String HUMAN = "Human";

    int size;
    String[] players;
    int[] points;
    int[][] board;
    int currentPlayer;
    int winner;
    int moves;

//    int rows[];
//    int cols[];
//    int rbias[];
//    int lbias[];

    public StrategoGame(int size, String player1, String player2) {
        this.size = size;
        this.points = new int[]{0, 0};
        this.board = new int[size][size];
        this.currentPlayer = 1;
        this.winner = 0;
        this.moves = 0;
        this.players = new String[]{player1, player2};
//        this.rows = new int[size];
//        this.cols = new int[size];
//        this.rbias = new int[2*size-1];
//        this.lbias = new int[2*size-1];
    }

//    private void fillArrays(){
//        Arrays.fill(this.rows,size);
//        Arrays.fill(this.cols,size);
//        for(int i=0; i<this.size;i++)
//            this.rbias[i] = get_rbias_pts_on_index(i);
//    }
//
//    private int get_rbias_pts_on_index(int i) {
//        return
//    }

    public int getSize() {
        return size;
    }



    public boolean player_move(int column, int row) {
        if(isTurnValid(row,column)){
            makeMove(row,column);
            checkLines(row,column);
            checkWinner();
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            return true;
        }
        return false;
    }

    private void checkLines(int row, int column) {
        int rowP = checkRow(row);
        int colP = checkCol(column);
        int biasLP = checkBiasFromLeft(row,column);
        int biasRP = checkBiasFromRight(row,column);
        points[currentPlayer - 1] += rowP + colP + biasLP + biasRP;
    }

    private int checkBiasFromRight(int row, int column) {
        int i = row - 1;
        int j = column + 1;
        boolean connected = true;
        int sum = 0;
        while( i >= 0 && j >= 0 && i < size && j < size && connected){
            connected = (board[i][j] != 0);
            sum++;
            i--;
            j++;
        }
        if (!connected) return 0;
        i = row + 1;
        j = column - 1;
        while( i >= 0 && j >= 0 && i < size && j < size && connected){
            connected = (board[i][j] != 0);
            sum++;
            i++;
            j--;
        }
        if (!connected) return 0;
        return ++sum > 1 ? sum : 0;
    }

    private int checkBiasFromLeft(int row, int column) {
        int i = row - 1;
        int j = column - 1;
        boolean connected = true;
        int sum = 0;
        while( i >= 0 && j >= 0 && i < size && j < size && connected){
            connected = (board[i][j] != 0);
            sum++;
            i--;
            j--;
        }
        if (!connected) return 0;
        i = row + 1;
        j = column + 1;
        while( i >= 0 && j >= 0 && i < size && j < size && connected){
            connected = (board[i][j] != 0);
            sum++;
            i--;
            j--;
        }
        if (!connected) return 0;
        return ++sum > 1 ? sum : 0;
    }

    private int checkCol(int column) {
        boolean connected = true;
        int sum = 0;
        for(int i=0; i<size && connected; i++){
            connected = (board[i][column] != 0);
            sum++;
        }
        if (!connected) return 0;
        return sum > 1 ? sum : 0;
    }

    private int checkRow(int row) {
        boolean connected = true;
        int sum = 0;
        for(int i=0; i<size && connected; i++){
            connected = (board[row][i] != 0);
            sum++;
        }
        if (!connected) return 0;
        return sum > 1 ? sum : 1;
    }

    public int[] computer_move(){
        //Tu bedzie musial byc pobierany ruch komputera
        int[] move = new int[]{0,0};
        return move;
    }

    private void makeMove(int row, int column) {
        moves++;
        board[row][column] = 1;
    }

    private void checkWinner() {
        if(isEnd()){
            if(points[0] == points[1]) winner = -1;
            else if(points[0] > points[1]) winner = 1;
            else if(points[1] > points[0]) winner = 2;
        }
    }

    private boolean isTurnValid(int row, int column) {
        return board[row][column] == 0;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getWinner() {
        return winner;
    }

    public boolean isEnd() {
        return moves == size*size;
    }

    public boolean isCurrentPlayerComputer(){
        return players[currentPlayer - 1].equals(COMPUTER);
    }

    public boolean isNextPlayerComputer(){
        if (currentPlayer == 1){
            return players[1].equals(COMPUTER);
        }
        else {
            return players[0].equals(COMPUTER);
        }

    }

    public int getPlayerPoints(int player){
        return points[player - 1];
    }
}
