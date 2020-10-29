//
/*
 * For future reference on how to enable JavaFX to allowed libraries
1.       Right-click on the package

2.       Select properties

3.       Click on “Java build path”

4.       Select the ‘Libraries’ tab

5.       Expand the entry

6.       Click access rules

7.       Select Edit

8.       Select Add

9.       Enter “javafx/**”
 */

package othello_UI;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.awt.Label;

public class UI_Prototype extends Application {
	private GridPane Gpane;
	private Board gameBoard;
	private Game currGame;
	private Player player1;
	private Player player2;
	private TextField p1ScoreBox,p2ScoreBox; 
	//Creates our Primary Stage
	public void start(Stage primaryStage) {
		//Everything in here is in our main stage

		//		if(coinFlip()) {
		//			// Login logic goes here GetUser then set color
		//			player1 = new Player("Player1", Player.BLACK);
		//			player2 = new Player("Player2", Player.WHITE);
		//		} else {
		//			// Login logic goes here
		//			player1 = new Player("Player1", Player.WHITE);
		//			player2 = new Player("Player2", Player.BLACK);
		//		}

		player1 = new Player("Player1", Player.WHITE);
		player2 = new Player("Player2", Player.BLACK);

		gameBoard = new Board(player1, player2);
		this.currGame = gameBoard.CurrentGame;



		//Creates our pane
		Pane pane = new Pane();
		Gpane = new GridPane();
		pane.setPadding(new Insets(0,0,0,0));

		//Creates our scene
		Scene scene = new Scene(pane,800,850);

		pane.getChildren().add(Gpane);

		//Specific code for layout goes here

		//add grid lines (horizontal and vertical) to create board square
		//creates a 8x8 green grid with black lines

		int k = 0;
		int r = 0;
		String[][] color={{"GREEN","GREEN"},{"GREEN","GREEN"}};
		//Gpane.setAlignment(Pos.CENTER);   

		for(int k1 = 0; k1 < 8; k1++) {
			if(r > 1)
				r = 0;
			for(int k2 = 0; k2 < 8; k2++) {
				if(k > 1)
					k = 0;
				Tile r1 = new Tile(75,75,k1,k2);
				r1.setOnMouseClicked(event -> drawMove(r1.row, r1.col));
				r1.setStroke(Color.BLACK);
				r1.setFill(Paint.valueOf(color[r][k]));
				Gpane.add(r1,k1,k2);
				k++;
			}
			r++;
		}

		Gpane.setLayoutX(100);
		Gpane.setLayoutY(150);
		drawBoard();


		//Needed buttons
		//add "settings" button (will be replaced with a more appropriate "settings" icon later)
		Text settings = new Text(745,30, "settings");
		settings.setFill(Color.WHITE);
		Rectangle settingsBox = new Rectangle();
		settingsBox.setFill(Color.GRAY);
		settingsBox.setWidth(50);
		settingsBox.setHeight(50);
		settingsBox.setLayoutX(740);
		settingsBox.setLayoutY(5);
		pane.getChildren().add(settingsBox);
		pane.getChildren().add(settings);


		//add "pass" button
		Text pass = new Text(387, 95, "PASS");
		Circle passButton = new Circle();
		passButton.setFill(Color.RED);
		passButton.setCenterX(402.5);
		passButton.setCenterY(90);
		passButton.setRadius(40);
		pass.setOnMouseClicked(event -> passMove());
		pane.getChildren().add(passButton);
		pane.getChildren().add(pass);

		//Text boxes needed
		//Add "Player 1" and "Player 2" text boxes
		//Player1
		Text player1 = new Text(155,90, "Player 1: ");
		TextField player1Box = new TextField();
		player1Box.setMaxWidth(100);
		player1Box.setLayoutX(130);
		player1Box.setLayoutY(100);
		pane.getChildren().add(player1);
		pane.getChildren().add(player1Box);
		//		player1Box.appendText("gi");

		//Player2
		Text player2 = new Text(600,90, "Player 2: ");
		TextField player2Box = new TextField();
		//		player2Box.setText("2");
		player2Box.setMaxWidth(100);
		player2Box.setLayoutX(575);
		player2Box.setLayoutY(100);
		pane.getChildren().add(player2);
		pane.getChildren().add(player2Box);

		//add player 1 and player 2 score boxes
		//Player1
		Text p1Score = new Text(255,90, "SCORE");
		p1ScoreBox = new TextField();
		p1ScoreBox.setText("2");
		p1ScoreBox.setMaxWidth(50);
		p1ScoreBox.setLayoutX(250);
		p1ScoreBox.setLayoutY(100);
		pane.getChildren().add(p1Score);
		pane.getChildren().add(p1ScoreBox);
		//		p1ScoreBox.setText("20");
		//Player2
		Text p2Score = new Text(510,90, "SCORE");
		p2ScoreBox = new TextField();
		p2ScoreBox.setMaxWidth(50);
		p2ScoreBox.setLayoutX(505);
		p2ScoreBox.setLayoutY(100);
		pane.getChildren().add(p2Score);
		pane.getChildren().add(p2ScoreBox);
		p2ScoreBox.setText("2");

		//add player 1 and player 2 timer boxes
		//Player1
		Text placeHolderTime = new Text(275,39, "Time1");
		placeHolderTime.setFill(Color.WHITE);
		Rectangle p1TimerBox = new Rectangle();
		p1TimerBox.setFill(Color.DARKBLUE);
		p1TimerBox.setWidth(100);
		p1TimerBox.setHeight(30);
		p1TimerBox.setLayoutX(240);
		p1TimerBox.setLayoutY(20);
		pane.getChildren().add(p1TimerBox);
		pane.getChildren().add(placeHolderTime);


		//Player2
		Text placeHolderTime2 = new Text(495,39, "Time2");
		placeHolderTime2.setFill(Color.WHITE);
		Rectangle p2TimerBox = new Rectangle();
		p2TimerBox.setFill(Color.DARKBLUE);
		p2TimerBox.setWidth(100);
		p2TimerBox.setHeight(30);
		p2TimerBox.setLayoutX(460);
		p2TimerBox.setLayoutY(20);
		pane.getChildren().add(p2TimerBox);
		pane.getChildren().add(placeHolderTime2);



		primaryStage.setTitle("Othello");
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}

	private void drawMove(int row, int col) {

		Player p = currGame.getPlayerwithTurn();
		try {
			Color color = Color.WHITE;
			if(Player.BLACK == p.Color) {
				color = Color.BLACK;
			}
			gameBoard.PlaceDisc(p, row, col);
			updateBoard();
			Circle c = new Circle(75/2, 75/2, 37, color );
			Gpane.add(c, row, col);
			currGame.SwitchTurn();

		} catch (IllegalArgumentException ex) {
			System.out.println("Illegal Move");
			return;
		}
		System.out.println(row + " " + col);
		return;
	}

	private void passMove() {

		System.out.println("pass pressed");
		if (gameBoard.Pass(currGame.getPlayerwithTurn())) {
			currGame.SwitchTurn();
		}

		return;

	}


	private void drawBoard() {
		Circle c1 = new Circle(75/2, 75/2, 37, Color.WHITE);
		Circle c2 = new Circle(75/2, 75/2, 37, Color.BLACK);
		Circle c3 = new Circle(75/2, 75/2, 37, Color.BLACK);
		Circle c4 = new Circle(75/2, 75/2, 37, Color.WHITE);
		Gpane.add(c1, 3, 3);
		Gpane.add(c2, 3, 4);
		Gpane.add(c3, 4, 3);
		Gpane.add(c4, 4, 4);
	}

	/**Updates board after disc is placed
	 * 
	 */
	private void updateBoard(){

		Gpane.getChildren().clear();

		int k = 0;
		int r = 0;
		String[][] color={{"GREEN","GREEN"},{"GREEN","GREEN"}};


		for(int k1 = 0; k1 < 8; k1++) {
			if(r > 1)
				r = 0;
			for(int k2 = 0; k2 < 8; k2++) {
				if(k > 1)
					k = 0;
				Tile r1 = new Tile(75,75,k1,k2);
				r1.setOnMouseClicked(event -> drawMove(r1.row, r1.col));
				r1.setStroke(Color.BLACK);
				r1.setFill(Paint.valueOf(color[r][k]));
				Gpane.add(r1,k1,k2);
				k++;
			}
			r++;
		}

		Gpane.setLayoutX(100);
		Gpane.setLayoutY(150);
		drawBoard();
		int tempBlackScore=0;
		int tempWhiteScore=0;
		for (int i=0; i<gameBoard.CurrentBoard.length; i++)
		{
			for (int j=0; j<gameBoard.CurrentBoard[i].length; j++)
			{
				if(gameBoard.CurrentBoard[j][i] == Player.BLACK) {
					tempBlackScore++;
					Circle c = new Circle(75/2, 75/2, 37, Color.BLACK);
					Gpane.add(c, i, j);

				} else if(gameBoard.CurrentBoard[j][i] == Player.WHITE) {
					tempWhiteScore++;
					Circle c = new Circle(75/2, 75/2, 37, Color.WHITE);
					Gpane.add(c, i, j);

				}

			}

		}
		updateScores(String.valueOf(tempBlackScore),
				String.valueOf(tempWhiteScore));

	}

	private Node getCircleFromGridPane( int col, int row) {
		for (Node node : Gpane.getChildren()) {
			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
				return (Circle)node;
			}
		}
		return null;

	}

	/**
	 * Decides who goes first
	 * @param p1
	 * @param p2
	 * @return returns true for black false for white
	 */
	public boolean coinFlip() {

		if (Math.random() > 0.5 ) {

			return true;

		} else {
			return false;
		}
	}

	//Idea: Set boolean flag for for player turns (if checked True, then player1 turn, if false, player2 turn)

	public static void main(String[] args) {
		launch(args);
	}
	private void updateScores(String black,String white) {
		this.p1ScoreBox.setText(black);
		this.p2ScoreBox.setText(white);
	}

	//for future update players name
	//	private void updatePlayersNameinUI(String black,String white) {
	////		this.p1ScoreBox.setText(black);
	////		this.p2ScoreBox.setText(white);
	//	}

}
