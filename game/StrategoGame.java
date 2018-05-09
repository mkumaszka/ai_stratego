package game;

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

    Minimax minimax;

    public StrategoGame(int size, String player1, String player2, int depth) {
        this.size = size;
        this.points = new int[]{0, 0};
        this.board = new int[size][size];
        this.currentPlayer = 1;
        this.winner = 0;
        this.moves = 0;
        this.players = new String[]{player1, player2};
        this.minimax = new Minimax(depth);
    }


    public int getSize() {
        return size;
    }


    public boolean player_move(int column, int row) {
        if(isTurnValid(row,column)){
            Move move = new Move(row,column);
            makeMove(row,column);
            updatePlayersPointsOnMove(move);
            checkWinner();
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            return true;
        }
        return false;
    }

    private void updatePlayersPointsOnMove(Move move) {
        points[currentPlayer - 1] += move.getMoveScore(this.board);
    }

    public int[] computer_move(){
        Move cMove = minimax.startMinimax(this.board);
        int row = cMove.getRow();
        int col = cMove.getCol();
        makeMove(row,col);
        updatePlayersPointsOnMove(cMove);
        checkWinner();
        currentPlayer = currentPlayer == 1 ? 2 : 1;
        return new int[]{cMove.getRow(),cMove.getCol()};
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
