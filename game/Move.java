package game;

public class Move implements Comparable<Move>{

    int col;
    int row;
    int value = 0;

    public Move(int row, int col){
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMoveScore(int [][] board){
        int rowP = checkRow(board, this.row);
        int colP = checkCol(board, this.col);
        int biasLP = checkBiasFromLeft(board, this.row, this.col);
        int biasRP = checkBiasFromRight(board, this.row, this.col);
        return rowP + colP + biasLP + biasRP;
    }

    private int checkBiasFromRight(int [][] board, int row, int col) {
        int i = row - 1;
        int j = col + 1;
        boolean connected = true;
        int sum = 0;
        while( i >= 0 && j >= 0 && i < board.length && j < board.length && connected){
            connected = (board[i][j] != 0);
            sum++;
            i--;
            j++;
        }
        if (!connected) return 0;
        i = row + 1;
        j = col - 1;
        while( i >= 0 && j >= 0 && i < board.length && j < board.length && connected){
            connected = (board[i][j] != 0);
            sum++;
            i++;
            j--;
        }
        if (!connected) return 0;
        return ++sum > 1 ? sum : 0;
    }

    private int checkBiasFromLeft(int [][] board, int row, int column) {
        int i = row - 1;
        int j = column - 1;
        boolean connected = true;
        int sum = 0;
        while( i >= 0 && j >= 0 && i < board.length && j < board.length && connected){
            connected = (board[i][j] != 0);
            sum++;
            i--;
            j--;
        }
        if (!connected) return 0;
        i = row + 1;
        j = column + 1;
        while( i >= 0 && j >= 0 && i < board.length && j < board.length && connected){
            connected = (board[i][j] != 0);
            sum++;
            i++;
            j++;
        }
        if (!connected) return 0;
        return ++sum > 1 ? sum : 0;
    }

    private int checkCol(int [][] board, int column) {
        boolean connected = true;
        int sum = 0;
        for(int i=0; i<board.length && connected; i++){
            connected = (board[i][column] != 0);
            sum++;
        }
        if (!connected) return 0;
        return sum > 1 ? sum : 0;
    }

    private int checkRow(int [][] board, int row) {
        boolean connected = true;
        int sum = 0;
        for(int i=0; i<board.length && connected; i++){
            connected = (board[row][i] != 0);
            sum++;
        }
        if (!connected) return 0;
        return sum > 1 ? sum : 0;
    }

    @Override
    public int compareTo(Move o) {
        if(o.value > value){
            return -1;
        }
        else if(o.value < value){
            return 1;
        }
        return 0;
    }
}
