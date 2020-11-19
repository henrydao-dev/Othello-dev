package othello_UI;

import java.util.ArrayList;
import java.util.List;

public class Board {

	/**
	 * Current game being played
	 */
	public Game CurrentGame;
	/**
	 * Some configuration of the discs on the board
	 */
	public char[][] CurrentBoard;

	private static final char NO_DISC = ' ';
	private static final char WHITE = 'w';
	private static final char BLACK = 'b';
	private static final int ROW_UP = -1;
	private static final int ROW_DOWN = 1;
	private static final int COL_LEFT = -1;
	private static final int COL_RIGHT = 1;
	private static final int NONE = 0;
	
	/*
	 * Henry, Devin, Dakota, Jairo
	 */
	public Board(Player player1, Player player2,Integer timeLimitInSeconds) {
		CurrentBoard = new char[8][8];
		for (int i=0; i<CurrentBoard.length; i++)
		{
			for (int j=0; j<CurrentBoard[i].length; j++)
			{
				CurrentBoard[i][j]=NO_DISC;
			}
		}
		// Set starting discs
		CurrentBoard[3][3] = WHITE;
		CurrentBoard[3][4] = BLACK;
		CurrentBoard[4][3] = BLACK;
		CurrentBoard[4][4] = WHITE;
		
		CurrentGame = new Game(player1.Name, player2.Name,timeLimitInSeconds);

	}	
	/*
	 * Henry, Devin, Dakota 
	 */
	/**
	 * Place a disc on the board, checks if the move is valid, throws error if it is not valid
	 * @param player
	 * @param row
	 * @param col
	 * @throws IllegalArgumentException
	 */
	public void PlaceDisc(Player player, int col, int row) throws IllegalArgumentException {
		if(isMoveValid(player, row, col)) {
			flipDiscs(player.Color, row, col);
		} else {
			throw new IllegalArgumentException("Invalid move");
		}
	}

	/*
	 * Henry, Devin, Dakota, Jairo, Sadra
	 */
	
	/**
	 * Checks to see if there are any valid moves else forces pass().
	 * 
	 * @param player
	 * @param row
	 * @param col
	 * @return
	 */

	private boolean isPassValid(Player player) {
		boolean isOKToPass = true;

		for (int i=0; i<CurrentBoard.length; i++){

			for (int j=0; j<CurrentBoard[i].length; j++) {
				if(isMoveValid(player, i, j)) {
					isOKToPass = false;
				}
			}
		}
		return isOKToPass;
	}

	/*
	 * Henry, Devin, Dakota, Jairo, Sadra
	 */
	
	/**
	 * Passes play. Throws error if pass is not allowed
	 * @throws IllegalArgumentException
	 */
	public boolean Pass(Player player) throws IllegalArgumentException {
		if(isPassValid(player)) {
			return true; 

		} else {
			throw new IllegalArgumentException("Invalid move");
		}
	}

	/*
	 * Henry, Devin, Dakota, Jairo, Sadra
	 */
	
	/**
	 * Checks if the game is over. Returns true for yes, false for no.
	 * @return
	 */
	public boolean isGameOver() {
		boolean gameOver = false;
		int takenSpaces = 0;
		for (int i=0; i<CurrentBoard.length; i++)
		{
			for (int j=0; j<CurrentBoard[i].length; j++)
			{
				if(this.CurrentBoard[i][j] == Board.BLACK || this.CurrentBoard[i][j] == Board.WHITE) {
					// increment count of discs
					takenSpaces++;
				}
			}
		}
		if(takenSpaces >= 64) {
			gameOver = true;
		}
		return gameOver;
	}
	
	/*
	 *  Devin
	 */
	
	//Method for counting discs after "isGameOver()" returns true
		public List<Integer> countDiscs () {
			
			int whiteCount = 0;
			int blackCount = 0;
			
			//Loop for checking through the board to count discs
			for (int i=0; i<CurrentBoard.length; i++)
		    {
		      for (int j=0; j<CurrentBoard[i].length; j++)
		      {
		    	  if(this.CurrentBoard[i][j] == Board.BLACK) {
		    		  //increment amount of black discs
		    		  blackCount++;
		    	  } else if (this.CurrentBoard[i][j] == Board.WHITE) {
		    		  //increment amount of white discs
		    		  whiteCount++;
		    	  }
		      }
		    }
			
			//creates a list to store our variables to output
			List<Integer> list = new ArrayList<Integer>();
			list.add(whiteCount);
			list.add(blackCount);
			
			return list;
		}

		/*
		 *  Devin, Sadra
		 */

	public boolean isMoveValid(Player player, int row, int col) {
		boolean isValid = false;
		// Get the opponent's color
		char opponent = Player.BLACK;
		if(player.Color == Player.BLACK) {
			opponent = Player.WHITE;
		}

		if(CurrentBoard[row][col] == NO_DISC ) {
			if(row+1<8 && col+1<8 && validFlip(player.Color, row, col, ROW_DOWN, COL_RIGHT)) {
				// If it's in the board range (8x8) and the BOTTOM RIGHT diagonal row and column is an opponents
				isValid = true;
			} else if(row+1<8 && validFlip(player.Color, row, col, ROW_DOWN, NONE)) {
				// if it's in the board range and the one under this is an opponents BOTTOM
				isValid = true;
			} else if(col+1<8 && validFlip(player.Color, row, col, NONE, COL_RIGHT)) {
				// if it's in the board range and the one to the right is an opponents RIGHT
				isValid = true;
			} else if(col-1>-1 && validFlip(player.Color, row, col, NONE, COL_LEFT)) {
				// if it's in the board range and the one to the left is an opponents LEFT
				isValid = true;
			} else if(row-1>-1 && col-1>-1 && validFlip(player.Color, row, col, ROW_UP, COL_LEFT)) {
				// if it's in the board range and the TOP LEFT diagonal is an opponents
				isValid = true;
			} else if(row-1>-1 && validFlip(player.Color, row, col, ROW_UP, NONE)) {
				// if it's in the board range and the one above this is an opponents TOP
				isValid = true;
			} else if(row-1>-1 && col+1<8 && validFlip(player.Color, row, col, ROW_UP, COL_RIGHT)) {
				// if it's in the board range and the TOP RIGHT diagonal this is an opponents
				isValid = true;
			} else if(row+1<8 && col-1>-1 && validFlip(player.Color, row, col, ROW_DOWN, COL_LEFT)) {
				// if it's in the board range and the BOTTOM LEFT diagonal this is an opponents
				isValid = true;
			}     
		}

		return isValid;
	}
	
	/*
	 *  Devin, Sadra
	 */
	private boolean validFlip(char color, int r, int c, int rowDir, int colDir) {
		char opponent = Player.BLACK;
		if(color == Player.BLACK) {
			opponent = Player.WHITE;
		}
		int row = r, col = c;
		boolean valid = false;
		for(int i=0; i<8; i++) {
			row+= rowDir;
			col+= colDir;
			if(!isCellOutOfBounds(row, col) && CurrentBoard[row][col] == opponent){
				valid = true;
			}
			else if(!isCellOutOfBounds(row, col) && CurrentBoard[row][col] == color){
				if(valid) {
					return true;
				} else{
					return false;
				}
			}
			else return false;
		}
		return false;
	}


	/**
	 * Flips the discs in each direction 
	 * @param color (player's color)
	 * @param row
	 * @param col
	 */
	
	//Devin
	public void flipDiscs(char color, int row, int col) {
		// flip the one that is clicked first because it's empty
		this.CurrentBoard[row][col] = color;
		// Check in each direction

		//top
		flipDisc(color, row, col, ROW_UP, NONE);

		//top right
		flipDisc(color, row, col, ROW_UP, COL_RIGHT);

		//right
		flipDisc(color, row, col, NONE, COL_RIGHT);

		//bottom right
		flipDisc(color, row, col, ROW_DOWN, COL_RIGHT);

		//bottom
		flipDisc(color, row, col, ROW_DOWN, NONE);

		//bottom left
		flipDisc(color, row, col, ROW_DOWN, COL_LEFT);

		//left
		flipDisc(color, row, col, NONE, COL_LEFT);

		//top left
		flipDisc(color, row, col, ROW_UP, COL_LEFT);

	}
	
	/*
	 *  Devin
	 */
	
	/**
	 * Flips the discs in the direction given for the columns and rows
	 * @param colorToSet
	 * @param row
	 * @param col
	 * @param rowDir using the static UP, DOWN, NONE
	 * @param colDir using the static LEFT, RIGHT, NONE
	 */
	private void flipDisc(char colorToSet, int row, int col, int rowDir, int colDir) {
		int rowToFlip = row + rowDir;
		int colToFlip = col + colDir;
		// If we're off the game board for this direction of movement
		if(isCellOutOfBounds(rowToFlip, colToFlip)) {
			return; // We're off the board and there is nothing to flip in this cell
		}
		while(cellIsBlack(rowToFlip, colToFlip) || cellIsWhite(rowToFlip, colToFlip)) {
			if(CurrentBoard[rowToFlip][colToFlip] == colorToSet) { 
				// Cell is already the color it needs to be
				// Check in the negative directions until it hits the row and column we clicked
				while(!(row == rowToFlip && col == colToFlip)) {
					CurrentBoard[rowToFlip][colToFlip] = colorToSet;
					// move in the negative direction
					rowToFlip = rowToFlip - rowDir;
					colToFlip = colToFlip - colDir;
				}
				break;
			} else {
				// move the cell in the positive direction to check it
				rowToFlip = rowToFlip + rowDir;
				colToFlip = colToFlip + colDir;
			}
			// Stop when you get to the edge of the board
			if(isCellOutOfBounds(rowToFlip, colToFlip)) {
				break;
			}
		}

	}
	private boolean cellIsBlack(int row, int col) {
		return CurrentBoard[row][col] == Board.BLACK;
	}
	private boolean cellIsWhite(int row, int col) {
		return CurrentBoard[row][col] == Board.WHITE;
	}
	private boolean isCellOutOfBounds(int row, int col) {
		if(row < 0 || row == 8 || col < 0 || col == 8) {
			return true;
		} else {
			return false;
		}
	}
	
	//Devin
	public String toString() {
		String matrix = "";
		// Loop through all rows 
		for (int i = 0; i < CurrentBoard.length; i++) {
			matrix += "\n";
			// Loop through all elements of current row 
			for (int j = 0; j < CurrentBoard[i].length; j++) { 
				matrix += (CurrentBoard[i][j] + " ");
			}
		}
		return matrix;
	}
}
