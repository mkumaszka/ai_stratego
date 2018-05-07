package game;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class Controller {

    @FXML
    GridPane gp;

    @FXML
    Button newGameB, exitB;

    @FXML
    ChoiceBox<String> player1;
    @FXML
    ChoiceBox<String> player2;

    @FXML
    Text p1points, p2points, turn, infoText;

    @FXML
    javafx.scene.control.TextField boardSize;

    StrategoGame game;
    ArrayList<Rectangle> fields;

    String COMPUTER = "Computer";
    String HUMAN = "Human";

    int currentPlayer;
    String player1Color = "#1e90ff";
    String player2Color = "#6fff1f";

    String turn1 = "PLAYER 1";
    String turn2 = "PLAYER 2";

    public void initialiseBoard() {
        fillChoiceBox(player1);
        fillChoiceBox(player2);
    }

    @FXML
    private void exit(){
        System.exit(1);
    }

    public void startNewGame(){
        infoText.setText("TURN ");
        turn.setText("PLAYER 1");
        p1points.setText("0");
        p2points.setText("0");
        game = new StrategoGame(Integer.parseInt(boardSize.getText()), player1.getValue(), player2.getValue());

        gp.setGridLinesVisible(true);
        while(gp.getRowConstraints().size() > 0){
            gp.getRowConstraints().remove(0);
        }

        while(gp.getColumnConstraints().size() > 0){
            gp.getColumnConstraints().remove(0);
        }
        fields = new ArrayList<>();
        int factor = 700/game.getSize();

        for(int i =0; i < game.getSize(); i++){
            gp.addRow(i);
            gp.addColumn(i);
            gp.getRowConstraints().add(new RowConstraints(factor));
            gp.getColumnConstraints().add(new ColumnConstraints(factor));
            for(int j=0; j < game.getSize(); j++){
                final int row = i;
                final int column = j;

                Rectangle r = new Rectangle();
                r.setWidth(factor);
                r.setHeight(factor);
                if((i+j)%2==0) r.setFill(Color.web("#f2e6ff",1.0));
                else r.setFill(Color.web("#f0deff",1.0));
                r.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        onClick(row, column);
                    }
                });
                fields.add(r);
                gp.add(r,i,j);
            }
        }
        currentPlayer = game.getCurrentPlayer();
        if(game.isCurrentPlayerComputer()){
            if(game.isNextPlayerComputer()){
                //rozgrywka dwóch komputerów
            }
            else {
                int[] move = game.computer_move();
                makeMove(move[0],move[1]);
            }
        }
    }

    private void onClick(int row, int column) {
        if(game.player_move(row, column)){
            makeMove(row, column);
        }
        if (game.isCurrentPlayerComputer()){
            int[] move = game.computer_move();
            makeMove(move[0],move[1]);
        }
    }

    private void makeMove(int row, int column){
        String color = currentPlayer == 1 ? player1Color : player2Color;
        getField(row,column).setFill(Color.web(color));
        if(game.getWinner() > 0){
            infoText.setText("WINS: ");
            String winner = game.getWinner() == 1 ? turn1 : turn2;
            turn.setText(winner);
        }
        else if(game.getWinner() == -1){
            turn.setText(" ");
            infoText.setText("DRAW");
        }
        else {
            currentPlayer = game.getCurrentPlayer();
            String turnS = currentPlayer == 1 ? turn1 : turn2;
            this.turn.setText(turnS);
        }
        p1points.setText(String.valueOf(game.getPlayerPoints(1)));
        p2points.setText(String.valueOf(game.getPlayerPoints(2)));
    }

    public Rectangle getField(int y, int x){
        int index = y*game.getSize()+x;
        return fields.get(index);
    }

    private void fillChoiceBox(ChoiceBox<String> choiceBox) {
        choiceBox.getItems().add(COMPUTER);
        choiceBox.getItems().add(HUMAN);
    }

}
