package othello_UI;

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
	
	
	public Board() {
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
	}
	/**
	 * Place a disc on the board, checks if the move is valid, throws error if it is not valid
	 * @param player
	 * @param row
	 * @param col
	 * @throws IllegalArgumentException
	 */
	public void PlaceDisc(Player player, int row, int col) throws IllegalArgumentException {
		if(validateMove(player, row, col)) {
			// Do something here about placing the disc
		} else {
			throw new IllegalArgumentException("Invalid move");
		}
	}
	/**
	 * Passes play. Throws error if pass is not allowed
	 * @throws IllegalArgumentException
	 */
	public void Pass() throws IllegalArgumentException {
		if(validatePass()) {
			// Do something here about passing
		} else {
			throw new IllegalArgumentException("Invalid move");
		}
	}
	
	/**
	 * Checks if the game is over. Returns true for yes, false for no.
	 * @return
	 */
	public boolean GameOver() {
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
	
	
	private boolean validateMove(Player player, int row, int col) {
		boolean valid = false;
		// Get the opponent's color
		char opponent = Player.BLACK;
		if(player.Color == Player.BLACK) {
			opponent = Player.WHITE;
		}
		
		if(this.CurrentBoard[row][col] == NO_DISC ) {
			if(row+1<8 && col+1<8 && this.CurrentBoard[row+1][col+1] == opponent) {
				// If it's in the board range (8x8) and the BOTTOM RIGHT diagonal row and column is an opponents
				valid = true;
			} else if(row-1>-1 && col-1>-1 && this.CurrentBoard[row-1][col-1] == opponent) {
				// if it's in the board range and the TOP LEFT diagonal is an opponents
				valid = true;
			} else if(row+1<8 && col-1>-1 && this.CurrentBoard[row+1][col-1] == opponent) {
				// if it's in the board range and the BOTTOM LEFT diagonal this is an opponents
				valid = true;
			} else if(row-1>-1 && col+1<8 && this.CurrentBoard[row-1][col+1] == opponent) {
				// if it's in the board range and the TOP RIGHT diagonal this is an opponents
				valid = true;
			} else if(row+1<8 && this.CurrentBoard[row+1][col] == opponent) {
				// if it's in the board range and the one above this is an opponents TOP
				valid = true;
			} else if(col+1<8 && this.CurrentBoard[row][col+1] == opponent) {
				// if it's in the board range and the one to the right is an opponents RIGHT
				valid = true;
			} else if(row-1>-1 && this.CurrentBoard[row-1][col] == opponent) {
				// if it's in the board range and the one under this is an opponents BOTTOM
				valid = true;
			} else if(col-1>-1 && this.CurrentBoard[row][col-1] == opponent) {
				// if it's in the board range and the one to the left is an opponents LEFT
				valid = true;
			}  
		}
		
		return valid;
	}
	private boolean validatePass() {
		return true;
	}
	
	/**
	 * Flips the discs in each direction 
	 * @param color (player's color)
	 * @param row
	 * @param col
	 */
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
		char cellToFlip = this.CurrentBoard[rowToFlip][colToFlip];
		// If we're off the game board for this direction of movement
		if(rowToFlip == 8 || rowToFlip < 0 || colToFlip == 8 || colToFlip < 0) {
			return; // We're off the board and there is nothing to flip in this cell
		}
		while(cellIsBlack(rowToFlip, colToFlip) || cellIsWhite(rowToFlip, colToFlip)) {
			if(cellToFlip == colorToSet) { 
				// Cell is already the color it needs to be
				// Check in the negative directions until it hits the row and column we clicked
				boolean exactCellThatWasClicked = (row == rowToFlip && col == colToFlip);
				while(!(exactCellThatWasClicked)) {
					this.CurrentBoard[rowToFlip][colToFlip] = colorToSet;
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
			if(rowToFlip < 0 || rowToFlip == 8 || colToFlip < 0 || colToFlip == 8) {
				break;
			}
		}
		
	}
	private boolean cellIsBlack(int row, int col) {
		return this.CurrentBoard[row][col] == Board.BLACK;
	}
	private boolean cellIsWhite(int row, int col) {
		return this.CurrentBoard[row][col] == Board.WHITE;
	}
}
