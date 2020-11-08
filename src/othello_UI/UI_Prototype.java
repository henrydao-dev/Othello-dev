//
/*
 * For future reference on how to enable JavaFX to allowed libraries
1.       Right-click on the package

2.       Select properties

3.       Click on �Java build path�

4.       Select the �Libraries� tab

5.       Expand the entry

6.       Click access rules

7.       Select Edit

8.       Select Add

9.       Enter �javafx/**�
 */

package othello_UI;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.TODO; 


public class UI_Prototype extends Application {
	private GridPane Gpane;
	private Pane pane;
	private Board gameBoard;
	private Game currGame;
	private Player player1;
	private Player player2;
	private TextField p1ScoreBox,p2ScoreBox;
	private Integer tempTimerDuration ;
	private Text placeHolderTime2, placeHolderTime;
	private Timeline timer ;
	Scanner input = new Scanner(System.in);

	//Creates our Primary Stage
	public void start(Stage primaryStage) {
		//Everything in here is in our main stage

		//Creates our pane
		pane = new Pane();
		Gpane = new GridPane();
		pane.setPadding(new Insets(0,0,0,0));

		//Creates our scene
		Scene scene = new Scene(pane,800,850);

		pane.getChildren().add(Gpane);

		//add grid lines (horizontal and vertical) to create board square
		//creates a 8x8 green grid with black lines

		int k = 0;
		int r = 0;
		String[][] color={{"GREEN","GREEN"},{"GREEN","GREEN"}};
		
//		Player tempPlayer = resolvePlayerToName(currGame.playerUpNext());
		for(int k1 = 0; k1 < 8; k1++) {
			if(r > 1)
				r = 0;
			for(int k2 = 0; k2 < 8; k2++) {
				if(k > 1)
					k = 0;
				
				Tile r1 = new Tile(75,75,k1,k2);
				r1.setOnMouseClicked(event -> drawMove(r1.row, r1.col));
				r1.setStroke(Color.BLACK);
				if((k1==3& k2==2) | (k1==2& k2==3) |
				   (k1==4& k2==5) | (k1==5& k2==4) )
					r1.setFill(Color.MEDIUMSEAGREEN);
				else r1.setFill(Paint.valueOf(color[r][k]));
				Gpane.add(r1,k1,k2);
				
				k++;
			}
			r++;
		}

		Gpane.setLayoutX(100);
		Gpane.setLayoutY(150);

		drawStartingDiscs();

		//Text Input for Player 1's Name:
		TextInputDialog dialog = new TextInputDialog("Player1");
		dialog.setTitle("Name Entry");
		dialog.setHeaderText("Player 1 Name Entry");
		dialog.setContentText("Please enter your name:");
		Optional<String> result = dialog.showAndWait();
		String p1Name = "none";
		if (result.isPresent()){
			p1Name = result.get();
		}

		//Text Input for Player 2's Name:
		TextInputDialog dialog2 = new TextInputDialog("Player2");
		dialog2.setTitle("Name Entry");
		dialog2.setHeaderText("Player 2 Name Entry");
		dialog2.setContentText("Please enter your name:");
		Optional<String> result2 = dialog2.showAndWait();
		String p2Name = "none";
		if (result2.isPresent()){
			p2Name = result2.get();
		}
		// Create Game
		player1 = new Player(p1Name, Player.BLACK);
		player2 = new Player(p2Name, Player.WHITE);

		gameBoard = new Board(player1, player2);
		this.currGame = gameBoard.CurrentGame;
		tempTimerDuration = currGame.PlayerOneTime;
		this.SetTimer();
		drawButtonsAndLabels(p1Name, p2Name);
		primaryStage.setTitle("Othello");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void drawMove(int row, int col) {

		Player nextPlayer = resolvePlayerToName(currGame.playerUpNext());
		try {
			Color color = Color.WHITE;
			if(Player.BLACK == nextPlayer.Color) {
				color = Color.BLACK;
			}
			gameBoard.PlaceDisc(nextPlayer, row, col);
			updateBoard();
			Circle c = new Circle(75/2, 75/2, 37, color );
			Gpane.add(c, row, col);

			//This section of code will run on every valid disc placement to check for end of game
			if (gameBoard.isGameOver()) {
				/*
				 * calls delcareWinner() which will call upon another method countDiscs() to calculate
				 * which player has won the game and output all of : Player1's score, Player2's score, 
				 * and winner declaration
				 */
				declareWinner();
				//calls endGame() only after isGameOver() is true and the winner is declared.	 
				endGame();	
			}
			currGame.SwitchTurn();
			this.SetTimer();

			//Sets the color for turnIndicator
			if(Player.WHITE == nextPlayer.Color) {
				Circle turnIndicator = new Circle(75/2, 75/2, 28);
				turnIndicator.setFill(Color.BLACK);
				turnIndicator.setLayoutX(718);
				turnIndicator.setLayoutY(775);
				pane.getChildren().add(turnIndicator);
			} else {
				Circle turnIndicator = new Circle(75/2, 75/2, 28);
				turnIndicator.setFill(Color.WHITE);
				turnIndicator.setLayoutX(718);
				turnIndicator.setLayoutY(775);
				pane.getChildren().add(turnIndicator);
			}


		} catch (IllegalArgumentException ex) {
			Alert warning= new Alert(AlertType.WARNING);
			warning.setTitle("Invalid Move");
			warning.setContentText("Invalid move played. Please play a valid move or pass.");
			warning.show();
			return;
		}
		System.out.println(row + " " + col);
		return;
	}
	private Player resolvePlayerToName(String name) {
		if(name == player1.Name) {
			return player1;
		} else {
			return player2;
		}
	}
	private void passMove() {
		Player nextPlayer = resolvePlayerToName(currGame.playerUpNext());
		System.out.println("pass pressed");
		try {
			if (gameBoard.Pass(nextPlayer)) {
				if(nextPlayer == player1) {
					Circle turnIndicator = new Circle(75/2, 75/2, 28);
					turnIndicator.setFill(Color.WHITE);
					turnIndicator.setLayoutX(718);
					turnIndicator.setLayoutY(775);
					pane.getChildren().add(turnIndicator);
				} else {
					Circle turnIndicator = new Circle(75/2, 75/2, 28);
					turnIndicator.setFill(Color.BLACK);
					turnIndicator.setLayoutX(718);
					turnIndicator.setLayoutY(775);
					pane.getChildren().add(turnIndicator);
				}
				currGame.SwitchTurn();
				this.SetTimer();
			}
		} catch(IllegalArgumentException ex) {
			System.out.println("A valid move exists. You must make a valid move");
		}

		return;

	}


	private void drawStartingDiscs() {
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
		Player nextPlayer = resolvePlayerToName(currGame.nextPlayer());

		int k = 0;
		int r = 0;

		for(int k1 = 0; k1 < 8; k1++) {
			if(r > 1)
				r = 0;
			for(int k2 = 0; k2 < 8; k2++) {
				if(k > 1)
					k = 0;
				Tile r1 = new Tile(75,75,k1,k2);
				r1.setOnMouseClicked(event -> drawMove(r1.row, r1.col));
				r1.setStroke(Color.BLACK);
//				Color tempColor = nextPlayer.Color==Player.BLACK ? Color.valueOf("#d5eddd") : Color.valueOf("#3d4a41");
				if(gameBoard.isMoveValid(nextPlayer,k2,k1)) r1.setFill(Color.MEDIUMSEAGREEN) ;
				else r1.setFill(Color.GREEN);
				Gpane.add(r1,k1,k2);
				k++;
			}
			r++;
		}

		Gpane.setLayoutX(100);
		Gpane.setLayoutY(150);
		drawStartingDiscs();
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
		// Output board to console
		System.out.println(gameBoard.toString());
		updateScores(String.valueOf(tempBlackScore),
				String.valueOf(tempWhiteScore));


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

	//Method for being the final aspect of the game | Asks user (up to 3 times) to play again or quit
	private void endGame() {

		//TODO: *could also display the game count b/w players at this point, before moving to "play again?"*

		gameBoard.CurrentGame.EndGame();

		//Pop up box alerting the players that the game is over
		Alert gameOverBox = new Alert(AlertType.CONFIRMATION);
		ButtonType yesButton = new ButtonType("Yes");
		ButtonType noButton = new ButtonType("No");
		gameOverBox.getButtonTypes().setAll(yesButton, noButton);
		gameOverBox.setTitle("Game Over");
		gameOverBox.setHeaderText(declareWinner());
		gameOverBox.setContentText("Would you like play again?");
		gameOverBox.show();

		//Results of player clicking buttons
		Optional<ButtonType> result = gameOverBox.showAndWait();
		if (result.get() == yesButton) {
			//Restart Game and Board
			gameBoard = new Board(player1, player2);
			this.currGame = gameBoard.CurrentGame;

		} else if (result.get() == noButton) {
			System.exit(0);
		}

	}

	public String declareWinner () {

		String blackCountString;
		String whiteCountString;
		String winner;
		String returnMessage;

		/* whiteCount = list(0)
		 * blackCount = list(1)
		 */
		List <Integer> discCount = gameBoard.countDiscs();
		int whiteCount = discCount.get(0);
		int blackCount = discCount.get(1);

		//Determines who the winner of the game is and returns it as a String
		if (blackCount > whiteCount) {
			winner = "Player 1";
		} else if (blackCount < whiteCount) {
			winner = "Player 2";
		} else {
			winner = "The game is a tie!";
		}

		returnMessage = "Player 1 had a score of: " + blackCount + "." + " Player 2 had a score of: " +
				whiteCount + "." + "\nThe WINNER IS..." + winner +"!";

		return returnMessage;

	}


	private void updateScores(String black,String white) {
		this.p1ScoreBox.setText(black);
		this.p2ScoreBox.setText(white);
	}
	private void drawButtonsAndLabels(String p1Name, String p2Name) {
		//Needed buttons
		//TODO "settings" button (will be replaced with a more appropriate "settings" icon later)


		Image image = new Image("https://cdn2.iconfinder.com/data/icons/web-application-icons-part-i/100/Artboard_50-512.png");
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(40);
		imageView.setFitWidth(40);
		Button settingsButton = new Button();
		settingsButton.setLayoutX(740);
		settingsButton.setLayoutY(5);
		settingsButton.setGraphic(imageView);
		pane.getChildren().add(settingsButton);



		//add "pass" button
		Text pass = new Text(387, 95, "PASS");
		Circle passButton = new Circle();
		passButton.setFill(Color.RED);
		passButton.setCenterX(402.5);
		passButton.setCenterY(90);
		passButton.setRadius(40);
		passButton.setOnMouseClicked(event -> passMove());
		pane.getChildren().add(passButton);
		pane.getChildren().add(pass);



		//Text boxes needed
		//Add "Player 1" and "Player 2" text boxes
		//Player1
		Text player1 = new Text(155,90, "Player 1: ");
		Text player1CustomName = new Text(p1Name);
		player1CustomName.setStyle("-fx-font: 18 arial; -fx-stroke: black; -fx-stroke-width: .5;");
		player1CustomName.setLayoutX(140);
		player1CustomName.setLayoutY(118);
		pane.getChildren().add(player1);
		pane.getChildren().add(player1CustomName);
		//		player1Box.appendText("gi");

		//Player2
		Text player2 = new Text(600,90, "Player 2: ");
		Text player2CustomName = new Text(p2Name);
		player2CustomName.setStyle("-fx-font: 18 arial; -fx-stroke: black; -fx-stroke-width: .5;");
		player2CustomName.setLayoutX(575);
		player2CustomName.setLayoutY(120);
		pane.getChildren().add(player2);
		pane.getChildren().add(player2CustomName);

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

		placeHolderTime = new Text(275,39, currGame.PlayerOneTime.toString());
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
		placeHolderTime2 = new Text(495,39, currGame.PlayerTwoTime.toString());
		placeHolderTime2.setFill(Color.WHITE);
		Rectangle p2TimerBox = new Rectangle();
		p2TimerBox.setFill(Color.DARKBLUE);
		p2TimerBox.setWidth(100);
		p2TimerBox.setHeight(30);
		p2TimerBox.setLayoutX(460);
		p2TimerBox.setLayoutY(20);
		pane.getChildren().add(p2TimerBox);
		pane.getChildren().add(placeHolderTime2);

		//Display Current Player's Turn:
		Text currentPlayerTurn = new Text(715,770, "Current Turn:");

		currentPlayerTurn.setStyle("-fx-font: 14 arial; -fx-stroke: black; -fx-stroke-width: .5;");

		pane.getChildren().add(currentPlayerTurn);

		//Circle representing player turn (with square as backdrop)
		Rectangle indicatorBG = new Rectangle();
		indicatorBG.setFill(Color.GREEN);
		indicatorBG.setWidth(70);
		indicatorBG.setHeight(70);
		indicatorBG.setLayoutX(720.5);
		indicatorBG.setLayoutY(775);
		pane.getChildren().add(indicatorBG);		
		Circle turnIndicator = new Circle(75/2, 75/2, 28);
		turnIndicator.setFill(Color.BLACK);
		turnIndicator.setLayoutX(718);
		turnIndicator.setLayoutY(775);
		pane.getChildren().add(turnIndicator);

		Button quitButton = new Button("Quit");
		quitButton.setMaxWidth(150);
		quitButton.setMaxHeight(150);
		pane.getChildren().add(quitButton); 
		quitButton.setOnAction(value -> System.exit(0));

	}
	public static void main(String[] args) {
		launch(args);
	}


	private void SetTimer() {
		//stoping timer and saving values
		if(timer != null) {
			timer.stop();
//			tempTimerDuration = currGame.PlayerOneTime;
			
		}

		//creating a new Timer

		if(currGame.LastTurn == currGame.PlayerOneName) {
			//for other player
			currGame.PlayerTwoTime =tempTimerDuration;
			tempTimerDuration = currGame.PlayerOneTime;
		}else { 
			//for this player
			currGame.PlayerOneTime =tempTimerDuration;
			tempTimerDuration = currGame.PlayerTwoTime;
		}
		timer = new Timeline(new KeyFrame(Duration.seconds(1),
				new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(tempTimerDuration >0) 
				{
					tempTimerDuration--;
					
					//					System.out.println("newTIME"+tempTimerDuration);
					if(currGame.LastTurn == currGame.PlayerOneName) 
						placeHolderTime.setText(tempTimerDuration.toString());
					else placeHolderTime2.setText(tempTimerDuration.toString());
				}
				else {
					currGame.EndGame();
					
					// TODO
					//need code for what happens if TimeOUT!!!!!
				}
			}
		}));

		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();

	}
}
