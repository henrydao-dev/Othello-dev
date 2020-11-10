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
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.TODO; 


public class UI_Prototype extends Application  {
	private GridPane Gpane;
	private Pane pane, loginPane, loginPane2, registerPane, registerPane2;
	private Board gameBoard;
	private Game currGame;
	private Player player1;
	private Player player2;
	private TextField p1ScoreBox,p2ScoreBox;
	private Integer tempTimerDuration ;
	private Text placeHolderTime2, placeHolderTime;
	private Timeline timer ;
	Scanner input = new Scanner(System.in);
	private StackPane pane2;
	private Button startgame, stats, logout;
	Stage window;
	Scene currentScene,scene, scene2, loginScene, loginScene2, registerScene, registerScene2;
	
	//Creates our Primary Stage
	public void start(Stage primaryStage) {
		//Everything in here is in our main stage
		window = primaryStage;
		currentScene = primaryStage.getScene();
		
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
		message.setLayoutX(140);;
		message.setLayoutY(108);
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
//				message.setText("Welcome "+usernameField.getText()+"!");
//				try {
//				    Thread.sleep(1 * 1000);
//				} catch (InterruptedException ie) {
//				    Thread.currentThread().interrupt();
//				}
				window.setScene(loginScene2);
				
			
			}catch (Exception e1) {
				
				System.out.println(e1.getMessage());
				message.setText("Invalid Cerdinals!");
				message.setTextFill(Color.RED);
				// TODO: handle exception
			}
//			usernameField.setStyle("-fx-text-inner-color: black;");
//			System.out.println();
			//if(loginIsVerified)	
			
		//else {
		//Try again text
	//}	
		});
		
		//---End Login Scene 1 Assets ----

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
		loginTitle2.setLayoutX(135);;
		loginTitle2.setLayoutY(5);
		loginPane2.getChildren().add(loginTitle2);
		
		//"username" Text
		Label username2 = new Label();
		username2.setText("Username:");
		username2.setPrefSize(250, 50);
		username2.setFont(Font.font("Times New Roman",15));
		username2.setLayoutX(50);;
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
		password2.setLayoutX(50);;
		password2.setLayoutY(80);
		loginPane.getChildren().add(password2);
		
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
		message2.setLayoutX(140);;
		message2.setLayoutY(108);
		message2.setTextFill(Color.RED);	
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
				
				message.setText("Welcome "+usernameField.getText()+"!");
				if (player1.Name.equals(usernameField2.getText())) {

					throw new IllegalStateException("SameUser");
				}
				player2 = Player.Login(usernameField2.getText(),passwordField2.getText()); 

				window.setScene(scene2);
				
			
			}catch (Exception e1) {
				
				System.out.println(e1.getMessage());
				if(e1.getMessage()=="SameUser") message2.setText("Player1 cannot login twice!");
				else message2.setText("Invalid Cerdinals!");
				
				// TODO: handle exception
			}

		});
		
		//---End of Login Scene 2 Assets-----
				
//---Register Screen Assets------
		//Create Register pane
		registerPane = new Pane();
		registerPane.setPadding(new Insets(0,0,0,0));
				
		//Creates our scene
		registerScene = new Scene(registerPane,400,200);
		
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
		messageregister.setPrefSize(250, 50);
		messageregister.setFont(Font.font("Times New Roman",15));
		messageregister.setLayoutX(140);;
		messageregister.setLayoutY(108);
		messageregister.setTextFill(Color.RED);	
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
		registerAccountButton.setLayoutY(145);
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
				// TODO: handle exception
				messageregister.setText(e2.getMessage());
			}
			
		});
				
		//---End Register Scene Assets--------
		
//---Register Screen 2 Assets------
		//Create Register pane
		registerPane2 = new Pane();
		registerPane2.setPadding(new Insets(0,0,0,0));
					
		//Creates our scene
		registerScene2 = new Scene(registerPane2,400,200);
				
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
		messageregister2.setLayoutX(140);;
		messageregister2.setLayoutY(108);
		messageregister2.setTextFill(Color.RED);	
		registerPane.getChildren().add(messageregister2);
		
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
		registerAccountButton2.setLayoutY(145);
		registerPane2.getChildren().add(registerAccountButton2);
				
		//upon clicking "Register" transfers to Main Menu Scene
		//Also needs to store inputs to player object (?)
		registerAccountButton2.setOnAction(e ->{
			
			
			try {
				Player tempPlayer = new Player(regUsernameField2.getText(),regPasswordField2.getText());
				tempPlayer.validateRegistration();
				tempPlayer.Register();
				player2 = Player.Login(regUsernameField2.getText(),regPasswordField2.getText());  
				window.setScene(scene2);
			} catch (Exception e2) {
				// TODO: handle exception
				messageregister2.setText(e2.getMessage());
			}
			
			
		} );
						
		//---End Register Scene Assets--------
		
//---Main Menu (scene2) Assets-------
		//creates title screen panes
		pane2 = new StackPane();
		pane2.setPadding(new Insets(0,0,0,0));
		
		//creates title
		Label title = new Label();
		title.setText("OTHELLO");
		title.setPrefSize(250, 50);
		title.setFont(Font.font("Times New Roman",35));
		title.setTranslateY(-200);
		title.setTranslateX(45);
		
		//creates Buttons
		startgame = new Button("Play Game");
		startgame.setPrefSize(150,50);
		startgame.setTranslateY(-100);
		
		//upon clicking "Play Game" transfers to game board
		startgame.setOnAction(e -> 
		{
			
			//Creates game pane
			pane = new Pane();
			Gpane = new GridPane();
			pane.setPadding(new Insets(0,0,0,0));

			//Creates our scene
			scene = new Scene(pane,800,850);

			pane.getChildren().add(Gpane);

			//add grid lines (horizontal and vertical) to create board square
			//creates a 8x8 green grid with black lines

			int k = 0;
			int r = 0;
			String[][] color={{"GREEN","GREEN"},{"GREEN","GREEN"}};
			
//			Player tempPlayer = resolvePlayerToName(currGame.playerUpNext());
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
//			TextInputDialog dialog = new TextInputDialog("Player1");
//			dialog.setTitle("Name Entry");
//			dialog.setHeaderText("Player 1 Name Entry");
//			dialog.setContentText("Please enter your name: (max 10 characters)");
//			Optional<String> result = dialog.showAndWait();
//			String p1Name = "player 1";
//			if (result.isPresent()&& result.get().length() < 11){
//				p1Name = result.get();
//			}
//
//			//Text Input for Player 2's Name:
//			TextInputDialog dialog2 = new TextInputDialog("Player2");
//			dialog2.setTitle("Name Entry");
//			dialog2.setHeaderText("Player 2 Name Entry");
//			dialog2.setContentText("Please enter your name: (max 10 characters)");
//			Optional<String> result2 = dialog2.showAndWait();
//			String p2Name = "player 2";
//			if (result2.isPresent()  && result2.get().length() < 11){
//				p2Name = result2.get();
//			}
			
			// Create Game
//			player1 = new Player(p1Name, Player.BLACK);
//			player2 = new Player(p2Name, Player.WHITE);
			player1.setColor(Player.BLACK);
			player2.setColor(Player.WHITE);
			gameBoard = new Board(player1, player2);
			this.currGame = gameBoard.CurrentGame;
			tempTimerDuration = currGame.PlayerOneTime;
			this.SetTimer();
			drawButtonsAndLabels(player1.Name, player2.Name);
			
			window.setScene(scene);
		
		});
		
		stats = new Button("Statisics");
		stats.setPrefSize(150,50);
		stats.setTranslateY(-50);
		
		logout = new Button("Logout");
		logout.setPrefSize(150,50);
		
		
		//log in notifs
//		Label player1label = new Label();
//		player1label.setText("Welcome "+ usernameField.getText()+"! Black discs!" );
//		player1label.setPrefSize(250, 50);
//		player1label.setFont(Font.font("Times New Roman",15));
//		player1label.setLayoutX(140);
//		player1label.setLayoutY(250);
//		
//		Label player2label = new Label();
//		player2label.setText("Welcome "+ usernameField2.getText()+"! White discs!" );
//		player2label.setPrefSize(250, 50);
//		player2label.setFont(Font.font("Times New Roman",15));
//		player2label.setLayoutX(140);;
//		player2label.setLayoutY(250);
//		
		//adds to Pane
		pane2.getChildren().add(title);
		pane2.getChildren().add(startgame);
		pane2.getChildren().add(stats);
		pane2.getChildren().add(logout);
//		pane2.getChildren().add(player1label);
//		pane2.getChildren().add(player2label);
		
		scene2 = new Scene(pane2, 350, 500);
		
		primaryStage.setTitle("Othello");
		primaryStage.setScene(loginScene);
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
		//stopping timer and saving values
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
					if(currGame.LastTurn == currGame.PlayerOneName) { 
						placeHolderTime.setText(tempTimerDuration.toString());
						placeHolderTime.setStyle("-fx-font: 18 arial; -fx-stroke: white; -fx-stroke-width: 1;");
						placeHolderTime2.setStyle("-fx-font: 18 arial; -fx-stroke: white; -fx-stroke-width: 1;");
					}
					else { placeHolderTime2.setText(tempTimerDuration.toString());
					
				}
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
