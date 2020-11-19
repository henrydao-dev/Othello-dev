package othello_UI;

/* Written by Henry, Dakota, and Jario,
 * with Devin and Sadra helping to debug and 
 * connect the UI to the backend logic.
 * 
 * 
 */

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sun.xml.internal.ws.api.pipe.NextAction;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class othello_UI extends Application  {
	private GridPane Gpane;
	private Pane pane, loginPane, loginPane2, registerPane, registerPane2, leaderboardPane, setParamPane;
	private Board gameBoard;
	private Player player1, player2;
	private TextField p1ScoreBox,p2ScoreBox;
	private Text placeHolderTime2, placeHolderTime;
	private Timeline timer ;
	private Integer tempTimerDuration;
	private Integer defaultTimeLimitInSeconds = 120;
	private StackPane mainMenuPane;
	private Button startGameButton, stats, login,admin;
	private Stage window;
	private Scene gameBoardScene, loginScene, loginScene2, registerScene, registerScene2, mainMenuScene, leaderboardScene, setParametersScene ;
	private Integer timeLimitInSeconds=300;
	//Creates our Primary Stage
	
	/* UI mainframe
	 * Henry,  Dakota, Jairo 
	 */
	public void start(Stage primaryStage) {
		//Everything in here is in our main stage
		window = primaryStage;
		
		createLoginScene1();

		createLoginScene2();

		createRegisterScene1();

		createRegisterScene2();

		createMainMenuScene();

		createLeaderboardScene();
		
		createSetParametersScene();

		primaryStage.setTitle("Othello");
		primaryStage.setScene(mainMenuScene);
		primaryStage.show();

	}

	/* 
	 * Henry, Devin, Dakota
	 */
	private void drawMove(int row, int col) {

		Player nextPlayer = resolvePlayerToName(gameBoard.CurrentGame.playerUpNext());
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
				//calls endGame() only after isGameOver() is true	 
				endGame();	
			}
			gameBoard.CurrentGame.SwitchTurn();
			this.SetTimer();
			gameBoard.CurrentGame.passing--;
			//Sets the color for turnIndicator
			if(Player.WHITE == nextPlayer.Color) {
				Circle turnIndicator = new Circle(75/2, 75/2, 28);
				turnIndicator.setFill(Color.BLACK);
				turnIndicator.setLayoutX(718);
				turnIndicator.setLayoutY(775);
				pane.getChildren().add(turnIndicator);
				
				Label playerTurnNameIndicator = new Label();
				playerTurnNameIndicator.setText(player1.Name);
				playerTurnNameIndicator.setPrefSize(250, 50);
				playerTurnNameIndicator.setFont(Font.font("Times New Roman",javafx.scene.text.FontWeight.BOLD,16));
				playerTurnNameIndicator.setTextFill(Color.WHITE);
				playerTurnNameIndicator.setLayoutX(735);;
				playerTurnNameIndicator.setLayoutY(785);
				pane.getChildren().add(playerTurnNameIndicator);
			} else {
				Circle turnIndicator = new Circle(75/2, 75/2, 28);
				turnIndicator.setFill(Color.WHITE);
				turnIndicator.setLayoutX(718);
				turnIndicator.setLayoutY(775);
				pane.getChildren().add(turnIndicator);
				
				Label playerTurnNameIndicator = new Label();
				playerTurnNameIndicator.setText(player2.Name);
				playerTurnNameIndicator.setPrefSize(250, 50);
				playerTurnNameIndicator.setFont(Font.font("Times New Roman",javafx.scene.text.FontWeight.BOLD,16));
				playerTurnNameIndicator.setTextFill(Color.BLACK);
				playerTurnNameIndicator.setLayoutX(735);;
				playerTurnNameIndicator.setLayoutY(785);
				pane.getChildren().add(playerTurnNameIndicator);
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
	
	/* turn indicator and double pass
	 *  Devin, Dakota, Sadra
	 */
	private void passMove() {
		Player nextPlayer = resolvePlayerToName(gameBoard.CurrentGame.playerUpNext());
		System.out.println("pass pressed");
		try {
			if (gameBoard.Pass(nextPlayer)) {
				gameBoard.CurrentGame.passing++;
				if(nextPlayer == player1) {
					Circle turnIndicator = new Circle(75/2, 75/2, 28);
					turnIndicator.setFill(Color.WHITE);
					turnIndicator.setLayoutX(718);
					turnIndicator.setLayoutY(775);
					pane.getChildren().add(turnIndicator);
					
					Label playerTurnNameIndicator = new Label();
					playerTurnNameIndicator.setText(player2.Name);
					playerTurnNameIndicator.setPrefSize(250, 50);
					playerTurnNameIndicator.setFont(Font.font("Times New Roman",javafx.scene.text.FontWeight.BOLD,16));
					playerTurnNameIndicator.setTextFill(Color.BLACK);
					playerTurnNameIndicator.setLayoutX(735);;
					playerTurnNameIndicator.setLayoutY(785);
					pane.getChildren().add(playerTurnNameIndicator);
		
				} else {
					Circle turnIndicator = new Circle(75/2, 75/2, 28);
					turnIndicator.setFill(Color.BLACK);
					turnIndicator.setLayoutX(718);
					turnIndicator.setLayoutY(775);
					pane.getChildren().add(turnIndicator);
					
					Label playerTurnNameIndicator = new Label();
					playerTurnNameIndicator.setText(player1.Name);
					playerTurnNameIndicator.setPrefSize(250, 50);
					playerTurnNameIndicator.setFont(Font.font("Times New Roman",javafx.scene.text.FontWeight.BOLD,16));
					playerTurnNameIndicator.setTextFill(Color.WHITE);
					playerTurnNameIndicator.setLayoutX(735);;
					playerTurnNameIndicator.setLayoutY(785);
					pane.getChildren().add(playerTurnNameIndicator);
				}
				
				
				updateBoard();
				gameBoard.CurrentGame.SwitchTurn();
				this.SetTimer();
			}
		} catch(IllegalArgumentException ex) {
			System.out.println("A valid move exists. You must make a valid move");
		}
		if (gameBoard.CurrentGame.passing>=2) {
			
			endGame();
			
			//add codes what should happen if double pass happend
		}

		return;

	}

	/* draws starting disk
	 * Henry, Devin, Dakota
	 */
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
	 * Henry, Dakota, Devin
	 */
	private void updateBoard(){

		Gpane.getChildren().clear();
		Player nextPlayer = resolvePlayerToName(gameBoard.CurrentGame.nextPlayer());

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


	/*Method for being the final aspect of the game | Asks user (up to 3 times) to play again or quit
	 * Henry, Dakota, Jairo
	 */
	private void endGame() {

		//TODO: *could also display the game count b/w players at this point, before moving to "play again?"*
		if(timer.getStatus()  == Status.RUNNING) timer.stop();
		try {
			gameBoard.CurrentGame.EndGame();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 

		//Pop up box alerting the players that the game is over
		Alert gameOverBox = new Alert(AlertType.CONFIRMATION);
		ButtonType yesButton = new ButtonType("Yes");
		ButtonType noButton = new ButtonType("No");
		gameOverBox.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		if(gameBoard.CurrentGame.passing >=2) gameOverBox.setTitle("Game Over(double pass)");
		else gameOverBox.setTitle("Game Over");
		if(gameBoard.CurrentGame.PlayerOneTime == 0 | gameBoard.CurrentGame.PlayerTwoTime==0) {
			gameOverBox.setTitle("Time Out!");
			gameOverBox.setHeaderText(declareWinner(true));
			gameOverBox.setContentText("Would you like play again?");
		}
		else {
		gameOverBox.setTitle("Game Over");

		gameOverBox.setHeaderText(declareWinner(false));
		gameOverBox.setContentText("Would you like play again?");
		}
		
		//Results of player clicking buttons
		Optional<ButtonType> result = gameOverBox.showAndWait();
		if (result.get() == ButtonType.YES) {
			//Restart Game and Board
			gameBoard = new Board(player1, player2,timeLimitInSeconds);
			tempTimerDuration = timeLimitInSeconds;
			this.gameBoard.CurrentGame = gameBoard.CurrentGame;
			
			placeHolderTime.setText(gameBoard.CurrentGame.PlayerOneTime.toString());
			placeHolderTime2.setText(gameBoard.CurrentGame.PlayerTwoTime.toString());
			
			gameBoard.CurrentGame.LastTurn = gameBoard.CurrentGame.nextPlayer();
			updateBoard();
			
			Player nextPlayer = resolvePlayerToName(gameBoard.CurrentGame.playerUpNext());
			if(nextPlayer == player1) {
				Circle turnIndicator = new Circle(75/2, 75/2, 28);
//				turnIndicator.setFont(Font.font("Times New Roman",javafx.scene.text.FontWeight.BOLD,16));
				turnIndicator.setFill(Color.WHITE);
				turnIndicator.setLayoutX(718);
				turnIndicator.setLayoutY(775);
				pane.getChildren().add(turnIndicator);
				
				Label playerTurnNameIndicator = new Label();
				playerTurnNameIndicator.setText(player2.Name);
				playerTurnNameIndicator.setPrefSize(250, 50);
				playerTurnNameIndicator.setFont(Font.font("Times New Roman",javafx.scene.text.FontWeight.BOLD,16));
				playerTurnNameIndicator.setTextFill(Color.BLACK);
				playerTurnNameIndicator.setLayoutX(735);;
				playerTurnNameIndicator.setLayoutY(785);
				pane.getChildren().add(playerTurnNameIndicator);
	
			} else {
				Circle turnIndicator = new Circle(75/2, 75/2, 28);
				turnIndicator.setFill(Color.BLACK);
				turnIndicator.setLayoutX(718);
				turnIndicator.setLayoutY(775);
				pane.getChildren().add(turnIndicator);
				
				Label playerTurnNameIndicator = new Label();
				playerTurnNameIndicator.setText(player1.Name);
				playerTurnNameIndicator.setPrefSize(250, 50);
				playerTurnNameIndicator.setFont(Font.font("Times New Roman",javafx.scene.text.FontWeight.BOLD,16));
				playerTurnNameIndicator.setTextFill(Color.WHITE);
				playerTurnNameIndicator.setLayoutX(735);;
				playerTurnNameIndicator.setLayoutY(785);
				pane.getChildren().add(playerTurnNameIndicator);
			}
//			gameBoard.CurrentGame.LastTurn = player1.Name;
			
			this.SetTimer();
//			tempTimerDuration = defultTime;
//			resetTimer();
			
			// TODO this does not function the way you'd expect right now

		} else if (result.get() == ButtonType.NO) {
			System.exit(0);
		}

	}

	/*
	 *  Devin, Dakota, Jairo, Sadra
	 */
	public String declareWinner (boolean isTimeout) {

		String winner;
		String returnMessage="";
		if(isTimeout) {
		if (gameBoard.CurrentGame.PlayerOneTime==0) {
			resolvePlayerToName(gameBoard.CurrentGame.PlayerOneName).Losses++;
			resolvePlayerToName(gameBoard.CurrentGame.PlayerTwoName).Wins++;
			
			returnMessage = "TimeOut for player " +gameBoard.CurrentGame.PlayerOneName+".\n The Winner Is player "
			+gameBoard.CurrentGame.PlayerTwoName+"!";
		}else if(gameBoard.CurrentGame.PlayerTwoTime==0) {
			resolvePlayerToName(gameBoard.CurrentGame.PlayerOneName).Wins++;
			resolvePlayerToName(gameBoard.CurrentGame.PlayerTwoName).Losses++;
			returnMessage =  "TimeOut for player " +gameBoard.CurrentGame.PlayerTwoName+".\n The Winner Is player "
						+gameBoard.CurrentGame.PlayerOneName+"!";
			}
		try {
			player1.Update();
			player2.Update();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMessage;
		
	}
		List <Integer> discCount = gameBoard.countDiscs();
		int whiteCount = discCount.get(0);
		int blackCount = discCount.get(1);

		//Determines who the winner of the game is and returns it as a String
		if (blackCount > whiteCount) {
			winner = player1.Name;
			player1.Wins++;
			player2.Losses++;
			try {
				player1.Update();
				player2.Update();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (blackCount < whiteCount) {
			winner = player2.Name;
			player2.Wins++;
			player1.Losses++;
			try {
				player2.Update();
				player1.Update();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return "The game is a tie!";
		}

		returnMessage = player1.Name+" had a score of: " + blackCount + ". " + player2.Name+ " had a score of: " +
				whiteCount + "." + "\nThe WINNER IS..." + winner +"!";
		if (gameBoard.CurrentGame.passing >=2)
			returnMessage = "Double Pass Happend! " + returnMessage;
		return returnMessage;

	}

	/*
	 *  Dakota, Henry
	 */
	private void updateScores(String black,String white) {
		this.p1ScoreBox.setText(black);
		this.p2ScoreBox.setText(white);
	}
	
	/*
	 * Henry, Dakota,Sadra
	 */
	
	private void drawButtonsAndLabels(String p1Name, String p2Name) {
		//Needed buttons

		//add "pass" button
		Text pass = new Text(387, 120, "PASS");
		Button passButton = new Button();
		passButton.setPrefSize(100, 25);
		passButton.setLayoutX(352.5);
		passButton.setLayoutY(100);
		passButton.setOnMouseClicked(event -> passMove());
		pane.getChildren().add(passButton);
		pane.getChildren().add(pass);



		//Text boxes needed
		//Add "Player 1" and "Player 2" text boxes
		//Player1
		Text player1 = new Text(100,90, "Player 1: ");
		Text player1CustomName = new Text(p1Name);
		player1CustomName.setStyle("-fx-font: 18 arial; -fx-stroke: black; -fx-stroke-width: .5;");
		player1CustomName.setLayoutX(100);
		player1CustomName.setLayoutY(118);
		pane.getChildren().add(player1);
		pane.getChildren().add(player1CustomName);

		//Player2
		Text player2 = new Text(575,90, "Player 2: ");
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

		placeHolderTime = new Text(275,39, gameBoard.CurrentGame.PlayerOneTime.toString());
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
		placeHolderTime2 = new Text(495,39, gameBoard.CurrentGame.PlayerTwoTime.toString());
		placeHolderTime2.setFill(Color.WHITE);
		Rectangle p2TimerBox = new Rectangle();
		p2TimerBox.setFill(Color.DARKBLUE);
		p2TimerBox.setWidth(100);
		p2TimerBox.setHeight(30);
		p2TimerBox.setLayoutX(460);
		p2TimerBox.setLayoutY(20);
		pane.getChildren().add(p2TimerBox);
		pane.getChildren().add(placeHolderTime2);
		
		//seetings for timer holder
		placeHolderTime.setStyle("-fx-font: 18 arial; -fx-stroke: white; -fx-stroke-width: 1;");
		placeHolderTime2.setStyle("-fx-font: 18 arial; -fx-stroke: white; -fx-stroke-width: 1;");


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
		
		//Labels to indicate which player goes with color of turn indicator
		Label playerTurnNameIndicator = new Label();
		playerTurnNameIndicator.setText(gameBoard.CurrentGame.PlayerOneName);
		playerTurnNameIndicator.setPrefSize(250, 50);
		playerTurnNameIndicator.setFont(Font.font("Times New Roman",FontWeight.BOLD,16));
		playerTurnNameIndicator.setTextFill(Color.WHITE);
		playerTurnNameIndicator.setLayoutX(738);;
		playerTurnNameIndicator.setLayoutY(785);
		pane.getChildren().add(playerTurnNameIndicator);

		Image quitImage = new Image("https://i.pinimg.com/originals/ae/df/dd/aedfdde5f2063b5d94acc6be3b29a7d3.png");
		ImageView quitImageView = new ImageView(quitImage);
		quitImageView.setFitWidth(45);
		quitImageView.setFitHeight(45);
		Button quitButton = new Button();
		quitButton.setLayoutX(5);
		quitButton.setLayoutY(5);
		pane.getChildren().add(quitButton); 
		quitButton.setGraphic(quitImageView);
		quitButton.setOnAction(value -> QuitGame());


	}

	//Method that allows for early quit by clicking "quit" button
	
	/*
	 *  Devin, Jairo, Sadra
	 */
	private void QuitGame() {
		timer.stop();
		Alert confirmQuit= new Alert(AlertType.CONFIRMATION);
		confirmQuit.getButtonTypes();
		confirmQuit.setTitle("Quit");
		confirmQuit.setHeaderText("Quit Game?");
		confirmQuit.setContentText("Are you sure you want to quit? Win will go to:" + gameBoard.CurrentGame.nextPlayer());

		Optional<ButtonType> result = confirmQuit.showAndWait();
		if(result.get() == ButtonType.OK) {
			
			this.resolvePlayerToName(gameBoard.CurrentGame.nextPlayer()).Wins++;
			this.resolvePlayerToName(gameBoard.CurrentGame.LastTurn).Losses++;
			try {
				
			player2.Update();
			player1.Update();
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			//user chooses yes button
			System.exit(0);
		} else {
			timer.play();
			return;
		}
	}

	/*
	 * Henry, Devin, Sadra
	 */
	private void SetTimer() {
		//stopping timer and saving values
		if(timer != null) {
			timer.stop();
		}

		//creating a new Timer

		if(gameBoard.CurrentGame.LastTurn == gameBoard.CurrentGame.PlayerOneName) {
			//for other player
			gameBoard.CurrentGame.PlayerTwoTime = tempTimerDuration;
			tempTimerDuration = gameBoard.CurrentGame.PlayerOneTime;
		}else { 
			//for this player
			gameBoard.CurrentGame.PlayerOneTime = tempTimerDuration;
			tempTimerDuration = gameBoard.CurrentGame.PlayerTwoTime;
		}
		timer = new Timeline(new KeyFrame(Duration.seconds(1),
				new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(tempTimerDuration > 0) 
				{
					
					tempTimerDuration--;

					if(gameBoard.CurrentGame.LastTurn == gameBoard.CurrentGame.PlayerOneName) {
						if(tempTimerDuration<11) placeHolderTime.setStyle("-fx-font: 18 arial; -fx-stroke: red; -fx-stroke-width: 1;");
						else placeHolderTime2.setStyle("-fx-font: 18 arial; -fx-stroke: white; -fx-stroke-width: 1;");
						placeHolderTime.setText(tempTimerDuration.toString());
					}
					else {
						if(tempTimerDuration<11) placeHolderTime2.setStyle("-fx-font: 18 arial; -fx-stroke: red; -fx-stroke-width: 1;");
						else placeHolderTime2.setStyle("-fx-font: 18 arial; -fx-stroke: white; -fx-stroke-width: 1;");
						placeHolderTime2.setText(tempTimerDuration.toString());

					}
				}
				else {
					try {
						timer.stop();
						if(gameBoard.CurrentGame.LastTurn == gameBoard.CurrentGame.PlayerOneName) { 
							gameBoard.CurrentGame.PlayerOneTime =tempTimerDuration;
						}
						else {
							gameBoard.CurrentGame.PlayerTwoTime =tempTimerDuration;
						}
		                Platform.runLater(() ->endGame());
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}));

		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();

	}
	
	/*
	 * Henry
	 */
	private void createMainMenuScene() {
		//---Main Menu (scene2) Assets-------
		//creates title screen panes
		mainMenuPane = new StackPane();
		mainMenuPane.setPadding(new Insets(0,0,0,0));

		//Creates our scene
		mainMenuScene = new Scene(mainMenuPane,400,500);

		//creates title
		Label title = new Label();
		title.setText("OTHELLO");
		title.setPrefSize(250, 50);
		title.setFont(Font.font("Times New Roman",35));
		title.setTranslateY(-200);
		title.setTranslateX(45);

		//creates Buttons
		startGameButton = new Button("Play Game");
		startGameButton.setPrefSize(150,50);
		startGameButton.setTranslateY(-100);

		//upon clicking "Play Game" transfers to game board
		startGameButton.setOnAction(e -> 
		{
			if( player1 == null || player2 == null) {
				
				Alert warning= new Alert(AlertType.WARNING);
				warning.setTitle("Login Needed");
				warning.setContentText("Players need to login to play");
				warning.show();
				
			}else {
			try {
				if(timeLimitInSeconds == null || tempTimerDuration == null){
				timeLimitInSeconds = defaultTimeLimitInSeconds;
				tempTimerDuration = defaultTimeLimitInSeconds; 	
				}
			createGameBoard();
				
			
			}
			catch(Exception err){
				
				Alert warning= new Alert(AlertType.WARNING);
				warning.setTitle("Error");
				warning.setContentText(err.getMessage());
				warning.show();
				err.printStackTrace();
			}
			}});
		
		stats = new Button("Statisics");
		stats.setPrefSize(150,50);
		stats.setTranslateY(-50);
		stats.setOnAction(e ->  {
			window.setScene(leaderboardScene);
		});
		
		login = new Button("Login");
		login.setPrefSize(150,50);


		login.setOnAction(e -> {
			
			window.setScene(loginScene);
			
		});
		
		admin = new Button("Set Parameters");
		admin.setPrefSize(150,50);
		admin.setTranslateY(50);
			
		admin.setOnAction(e -> {
			
			window.setScene(setParametersScene);
			
		});
		
		//adds to Pane
		mainMenuPane.getChildren().add(title);
		mainMenuPane.getChildren().add(startGameButton);
		mainMenuPane.getChildren().add(stats);
		mainMenuPane.getChildren().add(login);
		mainMenuPane.getChildren().add(admin);
	}

	/*
	 * Henry, Devin, Dakota, Sadra
	 */
	private void createGameBoard() {
		//Creates game pane
		pane = new Pane();
		Gpane = new GridPane();
		pane.setPadding(new Insets(0,0,0,0));

		//Creates our scene
		gameBoardScene = new Scene(pane,800,850);

		pane.getChildren().add(Gpane);

		//add grid lines (horizontal and vertical) to create board square
		//creates a 8x8 green grid with black lines

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

		// Create Game
		player1.setColor(Player.BLACK);
		player2.setColor(Player.WHITE);
		gameBoard = new Board(player1, player2,timeLimitInSeconds);
		try {
			gameBoard.CurrentGame.StartGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(tempTimerDuration == null) {
			gameBoard.CurrentGame.PlayerOneTime = defaultTimeLimitInSeconds;
			gameBoard.CurrentGame.PlayerTwoTime = defaultTimeLimitInSeconds;
				}
		
		this.SetTimer();
		drawButtonsAndLabels(player1.Name, player2.Name);

		window.setScene(gameBoardScene);
	}
	

	/*
	 * Henry, Dakota, sadra  
	 */
	private void createRegisterScene2() {
		//---Register Screen 2 Assets------
		//Create Register pane
		registerPane2 = new Pane();
		registerPane2.setPadding(new Insets(0,0,0,0));

		//Creates our scene
		registerScene2 = new Scene(registerPane2,400,225);

		//creates title
		Label registerTitle2 = new Label();
		registerTitle2.setText("Register Account");
		registerTitle2.setPrefSize(250, 50);
		registerTitle2.setFont(Font.font("Times New Roman",20));
		registerTitle2.setLayoutX(130);;
		registerTitle2.setLayoutY(5);
		registerPane2.getChildren().add(registerTitle2);

		//"username" Text
		Label regUsername2 = new Label();
		regUsername2.setText("Username:");
		regUsername2.setPrefSize(250, 50);
		regUsername2.setFont(Font.font("Times New Roman",15));
		regUsername2.setLayoutX(50);;
		regUsername2.setLayoutY(40);
		registerPane2.getChildren().add(regUsername2);

		//username Textfield
		TextField regUsernameField2 = new TextField();
		regUsernameField2.setMaxWidth(375);
		regUsernameField2.setLayoutX(130);
		regUsernameField2.setLayoutY(53);
		registerPane2.getChildren().add(regUsernameField2);

		//"password" Text
		Label regPassword2 = new Label();
		regPassword2.setText("Password:");
		regPassword2.setPrefSize(250, 50);
		regPassword2.setFont(Font.font("Times New Roman",15));
		regPassword2.setLayoutX(50);;
		regPassword2.setLayoutY(80);
		registerPane2.getChildren().add(regPassword2);

		Label messageregister2 = new Label();
		messageregister2.setText("");
		messageregister2.setStyle("-fx-text-inner-color: red;");
		messageregister2.setPrefSize(250, 50);
		messageregister2.setFont(Font.font("Times New Roman",15));
		messageregister2.setLayoutX(10);;
		messageregister2.setLayoutY(110);
		messageregister2.setTextFill(Color.RED);
		messageregister2.setWrapText(true);
		messageregister2.setTextAlignment(TextAlignment.CENTER);
		registerPane2.getChildren().add(messageregister2);

		//password Textfield
		TextField regPasswordField2 = new TextField();
		regPasswordField2.setMaxWidth(375);
		regPasswordField2.setLayoutX(130);
		regPasswordField2.setLayoutY(93);
		registerPane2.getChildren().add(regPasswordField2);

		//Register Button
		Button registerAccountButton2 = new Button("Register");
		registerAccountButton2.setPrefSize(150, 40);
		registerAccountButton2.setLayoutX(130);
		registerAccountButton2.setLayoutY(160);
		registerPane2.getChildren().add(registerAccountButton2);

		//upon clicking "Register" transfers to Main Menu Scene
		//Also needs to store inputs to player object (?)
		registerAccountButton2.setOnAction(e ->{

			try {
				Player tempPlayer = new Player(regUsernameField2.getText(),regPasswordField2.getText());
				tempPlayer.validateRegistration();
				tempPlayer.Register();
				player2 = Player.Login(regUsernameField2.getText(),regPasswordField2.getText());  
				window.setScene(mainMenuScene);
			} catch (Exception e2) {
				messageregister2.setText(e2.getMessage());
			}

		} );

		//---End Register Scene Assets--------
	}

	/*
	 * Henry, Dakota, Sadra
	 */
	private void createRegisterScene1() {
		//---Register Screen Assets------
		//Create Register pane
		registerPane = new Pane();
		registerPane.setPadding(new Insets(0,0,0,0));

		//Creates our scene
		registerScene = new Scene(registerPane,400,225);

		//creates title
		Label registerTitle = new Label();
		registerTitle.setText("Register Account");
		registerTitle.setPrefSize(250, 50);
		registerTitle.setFont(Font.font("Times New Roman",20));
		registerTitle.setLayoutX(130);;
		registerTitle.setLayoutY(5);
		registerPane.getChildren().add(registerTitle);

		//"username" Text
		Label regUsername = new Label();
		regUsername.setText("Username:");
		regUsername.setPrefSize(250, 50);
		regUsername.setFont(Font.font("Times New Roman",15));
		regUsername.setLayoutX(50);;
		regUsername.setLayoutY(40);
		registerPane.getChildren().add(regUsername);

		//username Textfield
		TextField regUsernameField = new TextField();
		regUsernameField.setMaxWidth(375);
		regUsernameField.setLayoutX(130);
		regUsernameField.setLayoutY(53);
		registerPane.getChildren().add(regUsernameField);

		//"password" Text
		Label regPassword = new Label();
		regPassword.setText("Password:");
		regPassword.setPrefSize(250, 50);
		regPassword.setFont(Font.font("Times New Roman",15));
		regPassword.setLayoutX(50);;
		regPassword.setLayoutY(80);
		registerPane.getChildren().add(regPassword);

		Label messageregister = new Label();
		messageregister.setText("");
		messageregister.setStyle("-fx-text-inner-color: red;");
		messageregister.setPrefSize(380, 50);
		messageregister.setFont(Font.font("Times New Roman",15));
		messageregister.setLayoutX(10);
		messageregister.setLayoutY(110);
		messageregister.setTextFill(Color.RED);	
		messageregister.setWrapText(true);
		messageregister.setTextAlignment(TextAlignment.CENTER);
		registerPane.getChildren().add(messageregister);

		//password Textfield
		TextField regPasswordField = new TextField();
		regPasswordField.setMaxWidth(375);
		regPasswordField.setLayoutX(130);
		regPasswordField.setLayoutY(93);
		registerPane.getChildren().add(regPasswordField);

		//Register Button
		Button registerAccountButton = new Button("Register");
		registerAccountButton.setPrefSize(150, 40);
		registerAccountButton.setLayoutX(130);
		registerAccountButton.setLayoutY(160);
		registerPane.getChildren().add(registerAccountButton);

		//upon clicking "Register" transfers to Main Menu Scene
		//Also needs to store inputs to player object (?)
		registerAccountButton.setOnAction(e -> {
			try {
				Player tempPlayer = new Player(regUsernameField.getText(),regPasswordField.getText());
				tempPlayer.validateRegistration();
				tempPlayer.Register();
				player1 = Player.Login(regUsernameField.getText(),regPasswordField.getText());  
				window.setScene(loginScene2);
			} catch (Exception e2) {
				messageregister.setText(e2.getMessage());
			}

		});

		//---End Register Scene Assets--------
	}
	
	/*
	 *  Devin,Sadra
	 */
	private void createLeaderboardScene() {
		//---Leaderboard Assets------

		leaderboardPane = new Pane();
		leaderboardPane.setPadding(new Insets(0,0,0,0));

		//Creates our scene
		leaderboardScene = new Scene(leaderboardPane,400,500);

		//creates title
		Label leaderboardTitle = new Label();
		leaderboardTitle.setText("Leaderboard");
		leaderboardTitle.setFont(new Font("Arial", 20));
		leaderboardPane.getChildren().add(leaderboardTitle);

		TableView<Player> table = new TableView<Player>();
		table.setEditable(false);
		table.setMaxHeight(400);

		TableColumn<Player, String> usernameCol = new TableColumn<Player, String>("Name");
		usernameCol.setMinWidth(100);
		usernameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("Name"));

		TableColumn<Player, Integer> winsCol = new TableColumn<Player, Integer>("Wins");
		winsCol.setMinWidth(100);
		winsCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("Wins"));

		TableColumn<Player, Integer> lossCol = new TableColumn<Player, Integer>("Losses");
		lossCol.setMinWidth(100);
		lossCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("Losses"));

		table.setItems(FXCollections.observableList(Admin.GetLeaderBoard()));

		table.getColumns().addAll(usernameCol, winsCol, lossCol);

		leaderboardPane.getChildren().add(table);				

		//Back Button
		Button backButton = new Button("Back");
		backButton.setPrefSize(150, 40);
		backButton.setLayoutX(130);
		backButton.setLayoutY(450);
		leaderboardPane.getChildren().add(backButton);

		backButton.setOnAction(e -> {
			window.setScene(mainMenuScene);
		});

		//---End Leaderboard Scene Assets--------
	}
	
	/*
	 * Dakota, Devin, Sadra
	 */
	private void createLoginScene2() {
		//---Login Scene 2 Assets ----
		//---Login(Player2) Scene Assets--------

		//Create Login pane
		loginPane2 = new Pane();
		loginPane2.setPadding(new Insets(0,0,0,0));

		//Creates our scene
		loginScene2 = new Scene(loginPane2,400,200);

		//creates title
		Label loginTitle2 = new Label();
		loginTitle2.setText("Login: (Player 2)");
		loginTitle2.setPrefSize(250, 50);
		loginTitle2.setFont(Font.font("Times New Roman",20));
		loginTitle2.setLayoutX(135);
		loginTitle2.setLayoutY(5);
		loginPane2.getChildren().add(loginTitle2);

		//"username" Text
		Label username2 = new Label();
		username2.setText("Username:");
		username2.setPrefSize(250, 50);
		username2.setFont(Font.font("Times New Roman",15));
		username2.setLayoutX(50);
		username2.setLayoutY(40);
		loginPane2.getChildren().add(username2);

		//username Textfield
		TextField usernameField2 = new TextField();
		usernameField2.setMaxWidth(375);
		usernameField2.setLayoutX(130);
		usernameField2.setLayoutY(53);
		loginPane2.getChildren().add(usernameField2);

		//"password" Text
		Label password2 = new Label();
		password2.setText("Password:");
		password2.setPrefSize(250, 50);
		password2.setFont(Font.font("Times New Roman",15));
		password2.setLayoutX(50);
		password2.setLayoutY(80);
		loginPane2.getChildren().add(password2);

		//password Textfield
		TextField passwordField2 = new TextField();
		passwordField2.setMaxWidth(375);
		passwordField2.setLayoutX(130);
		passwordField2.setLayoutY(93);
		loginPane2.getChildren().add(passwordField2);

		Label message2 = new Label();
		message2.setText("");
		message2.setStyle("-fx-text-inner-color: red;");
		message2.setPrefSize(250, 50);
		message2.setFont(Font.font("Times New Roman",15));
		message2.setLayoutX(140);
		message2.setLayoutY(108);
		message2.setTextFill(Color.RED);
		message2.setWrapText(true);
		loginPane2.getChildren().add(message2);
		//Register Button
		Button registerButton2 = new Button("Register");
		registerButton2.setPrefSize(150, 40);
		registerButton2.setLayoutX(35);
		registerButton2.setLayoutY(145);
		loginPane2.getChildren().add(registerButton2);

		//Login Button
		Button loginButton2 = new Button("Login");
		loginButton2.setPrefSize(150, 40);
		loginButton2.setLayoutX(195);
		loginButton2.setLayoutY(145);
		loginPane2.getChildren().add(loginButton2);

		//upon clicking "Register" transfers to Register Scene
		registerButton2.setOnAction(e -> window.setScene(registerScene2));

		//upon clicking "Login", if verified: transfers to Player2 Login screen. if not, (try again text?)	
		loginButton2.setOnAction(e -> 
		{
			try {

				message2.setText("Welcome "+usernameField2.getText()+"!");
				if (player1.Name.equals(usernameField2.getText())) {

					throw new IllegalStateException("SameUser");
				}
				player2 = Player.Login(usernameField2.getText(),passwordField2.getText()); 

				window.setScene(mainMenuScene);

			}catch (Exception e1) {

				System.out.println(e1.getMessage());
				if(e1.getMessage()=="SameUser") message2.setText("Player1 cannot login twice");
				else message2.setText("Invalid Credentials");
			}

		});

		//---End of Login Scene 2 Assets-----
	}

	/*
	 *  Devin, Dakota, Sadra
	 */
	private void createLoginScene1() {
		//---Login Scene 1 Assets--------
		//Create Login pane
		loginPane = new Pane();
		loginPane.setPadding(new Insets(0,0,0,0));

		//Creates our scene
		loginScene = new Scene(loginPane,400,200);

		//creates title
		Label loginTitle = new Label();
		loginTitle.setText("Login: (Player 1)");
		loginTitle.setPrefSize(250, 50);
		loginTitle.setFont(Font.font("Times New Roman",20));
		loginTitle.setLayoutX(135);;
		loginTitle.setLayoutY(5);
		loginPane.getChildren().add(loginTitle);

		//"username" Text
		Label username = new Label();
		username.setText("Username:");
		username.setPrefSize(250, 50);
		username.setFont(Font.font("Times New Roman",15));
		username.setLayoutX(50);;
		username.setLayoutY(40);
		loginPane.getChildren().add(username);

		Label message = new Label();
		message.setText("");
		message.setStyle("-fx-text-inner-color: red;");
		message.setPrefSize(250, 50);
		message.setFont(Font.font("Times New Roman",15));
		message.setLayoutX(140);
		message.setLayoutY(108);
		message.setWrapText(true);
		loginPane.getChildren().add(message);

		//username Textfield
		TextField usernameField = new TextField();
		usernameField.setMaxWidth(375);
		usernameField.setLayoutX(130);
		usernameField.setLayoutY(53);
		loginPane.getChildren().add(usernameField);

		//"password" Text
		Label password = new Label();
		password.setText("Password:");
		password.setPrefSize(250, 50);
		password.setFont(Font.font("Times New Roman",15));
		password.setLayoutX(50);;
		password.setLayoutY(80);
		loginPane.getChildren().add(password);

		//password Textfield
		TextField passwordField = new TextField();
		passwordField.setMaxWidth(375);
		passwordField.setLayoutX(130);
		passwordField.setLayoutY(93);
		loginPane.getChildren().add(passwordField);

		//Register Button
		Button registerButton = new Button("Register");
		registerButton.setPrefSize(150, 40);
		registerButton.setLayoutX(35);
		registerButton.setLayoutY(145);
		loginPane.getChildren().add(registerButton);

		//Login Button
		Button loginButton = new Button("Login");
		loginButton.setPrefSize(150, 40);
		loginButton.setLayoutX(195);
		loginButton.setLayoutY(145);
		loginPane.getChildren().add(loginButton);

		//upon clicking "Register" transfers to Register Scene
		registerButton.setOnAction(e -> window.setScene(registerScene));


		//upon clicking "Login", if verified: transfers to Player2 Login screen. if not, (try again text?)	
		loginButton.setOnAction(e -> 
		{
			try {
				player1 = Player.Login(usernameField.getText(),passwordField.getText()); 
				window.setScene(loginScene2);


			}catch (Exception e1) {

				System.out.println(e1.getMessage());
				message.setText("Invalid Credentials");
				message.setTextFill(Color.RED);
			}
		});

		//---End Login Scene 1 Assets ----
	}
	/*
	 * Jairo
	 */
	public void createSetParametersScene() {
		setParamPane = new Pane();
		setParamPane.setPadding(new Insets(0,0,0,0));
		
		setParametersScene = new Scene (setParamPane, 400, 200);
		Label time = new Label();
		time.setText("Enter game length in seconds: ");
		time.setPrefSize(250, 50);
		time.setFont(Font.font("Times New Roman",15));
		time.setLayoutX(40);
		time.setLayoutY(40);
		setParamPane.getChildren().add(time);
		
		Label message = new Label();
		message.setText("");
		message.setStyle("-fx-text-inner-color: red;");
		message.setPrefSize(250, 50);
		message.setFont(Font.font("Times New Roman",15));
		message.setLayoutX(140);
		message.setLayoutY(108);
		message.setWrapText(true);
		setParamPane.getChildren().add(message);
		
		TextField timeField = new TextField();
		timeField.setMaxWidth(40);
		timeField.setLayoutX(240);
		timeField.setLayoutY(53);
		timeField.setText(defaultTimeLimitInSeconds.toString());
		setParamPane.getChildren().add(timeField);
		
		Button okButton = new Button("OK");
		okButton.setPrefSize(35, 35);
		okButton.setLayoutX(290);
		okButton.setLayoutY(50);
		setParamPane.getChildren().add(okButton);
		
		//upon clicking ok button
		okButton.setOnAction(e -> 
		{
			try {
				timeLimitInSeconds = Integer.parseInt(timeField.getText());
				tempTimerDuration = Integer.parseInt(timeField.getText()); // instantiates the time
				window.setScene(mainMenuScene);
				
			}catch (Exception err){
				err.printStackTrace();
				message.setText("Please enter a valid length of time");
				message.setTextFill(Color.RED);
			}
			
		});
	}
	
	//Henry
	public static void main(String[] args) {
		launch(args);
	}
}

