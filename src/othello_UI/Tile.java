package othello_UI;

import javafx.scene.shape.Rectangle;


/*Tile method
 * Devin, Dakota
 */
public class Tile extends Rectangle {

	public int row;
	public int col;
	
	public Tile(double width, double height, int row, int col) {
		super(width, height);
		this.row = row;
		this.col = col;
	}
}
