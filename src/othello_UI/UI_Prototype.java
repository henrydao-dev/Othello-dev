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
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class UI_Prototype extends Application {
	
	//Creates our Primary Stage
	public void start(Stage primaryStage) {
		//Everything in here is in our main stage
			
			//Creates our pane
			Pane pane = new Pane();
			pane.setPadding(new Insets(0,0,0,0));
			
			//Creates our scene
			Scene scene = new Scene(pane,750,750);
			
			//Specific code for layout goes here
				
				//add grid lines (horizontal and vertical) to create board square
				
				//Needed buttons
					//add "settings" button
					//add "pass" button
			
				//Text boxes needed
					//Add "Player 1" and "Player 2" text boxes
					//add player 1 and player 2 score boxes
					//add player 1 and player 2 timer boxes
			
			
			
			primaryStage.setTitle("Orthello");
			primaryStage.setScene(scene);
			primaryStage.show(); 
		}

	public static void main(String[] args) {
		launch(args);
	}
	
}
