package othello_UI;

import java.io.IOException;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

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
	
	/**
	 * Registers the user
	 * @throws IllegalStateException If the password isn't set on this Player object
	 * @throws IllegalArgumentException If the user already exists
	 * @throws CsvDataTypeMismatchException If it has an issues with the object
	 * @throws CsvRequiredFieldEmptyException If it has an issue with the object
	 * @throws IOException if it can't find the CSV
	 */
	public void Register() throws IllegalStateException, IllegalArgumentException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		if(this.Password == null) {
			throw new IllegalStateException("User does not have a password set");
		} else {
			PlayerRepository playerRepo = new PlayerRepository();
			playerRepo.InsertUser(this);
			playerRepo.dispose();
		}
	}
	
	/**
	 * Checks the user's name and password combination
	 * @throws IllegalArgumentException if Invalid password or user doesn't exist
	 */
	public void Login() throws IllegalArgumentException {
		PlayerRepository playerRepo = new PlayerRepository();
		Player existingPlayer = playerRepo.GetPlayerByName(this.Name);
		if(existingPlayer != null) {
			if(existingPlayer.Password == this.Password) {
				return; 
			} else {
				throw new IllegalArgumentException("Invalid Password, try again");
			}
		} else {
			throw new IllegalArgumentException(this.Name + " not found in the database");
		}
	}

	@Override
	public int compareTo(Player compareUser) {
		int compareWins = ((Player) compareUser).Wins;
		//ascending order
		return this.Wins - compareWins;
	}
}