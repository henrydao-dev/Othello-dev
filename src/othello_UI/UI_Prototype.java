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
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.awt.Label;

public class UI_Prototype extends Application {
	private GridPane Gpane;
	//Creates our Primary Stage
	public void start(Stage primaryStage) {
		//Everything in here is in our main stage
		
		
			//Creates our pane
			Pane pane = new Pane();
			Gpane = new GridPane();
			pane.setPadding(new Insets(0,0,0,0));
			
			//Creates our scene
			Scene scene = new Scene(Gpane,750,750);
			
			//Specific code for layout goes here
				
			//add grid lines (horizontal and vertical) to create board square
			//creates a 8x8 green grid with black lines
			
			int k = 0;
			int r = 0;
			String[][] color={{"GREEN","GREEN"},{"GREEN","GREEN"}};
			Gpane.setAlignment(Pos.CENTER);   

			for(int k1 = 0; k1 < 8; k1++) {
			if(r > 1)
			r = 0;
			for(int k2 = 0; k2 < 8; k2++) {
			if(k > 1)
			k = 0;
			Rectangle r1 = new Rectangle(50,50);
			r1.setStroke(Color.BLACK);
			r1.setFill(Paint.valueOf(color[r][k]));
			Gpane.add(r1,k1,k2);
			k++;
			}
			r++;
			}
			
				
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
