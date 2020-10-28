package othello_UI;

import com.opencsv.bean.CsvBindByName;

public class Player implements Comparable<Player> {
	@CsvBindByName
	int Id;
	@CsvBindByName
	String Name;
	@CsvBindByName
	String Password;
	@CsvBindByName
	int Wins;
	@CsvBindByName
	int Losses;
	char Color;
	
	public static final char BLACK = 'b';
	public static final char WHITE = 'w';
	
	/**
	 * New Player with Color choice
	 * @param Name
	 * @param color
	 */
	public Player(String Name,char color) {
		this.Color = color ;
		this.Name = Name;
		
	}
	
	public Player(String name, String password) {
		this.Name = name;
		this.Password = password;
		this.Wins = 0;
		this.Losses = 0;
	}

	@Override
	public int compareTo(Player compareUser) {
		int compareWins = ((Player) compareUser).Wins;
		//ascending order
		return this.Wins - compareWins;
	}
}