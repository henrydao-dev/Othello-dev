package othello_UI;

import java.io.IOException;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//This entire method was handled by Devin and sadra
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
	/*
	 * This can change between games so it's not saved in the CSV file
	 */
	char Color;

	public static final char BLACK = 'b';
	public static final char WHITE = 'w';
	
	public String getName() {
		return Name;
	}
	
	public Integer getWins() {
		return Wins;
	}
	public Integer getLosses() {
		return Losses;
	}

	public Player() {
		
	}
	
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
	
	public void setColor(char color) {
		this.Color = color;
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
	 * @param userName
	 * @param password
	 * @return The player with the wins and losses
	 * @throws IllegalArgumentException if Invalid password or user doesn't exist
	 */
	public static Player Login(String userName, String password) throws IllegalArgumentException {
		PlayerRepository playerRepo = new PlayerRepository();
		try {
			Player existingPlayer = playerRepo.GetPlayerByName(userName);
			if(existingPlayer.Password.equals(password)) {
				return existingPlayer; 
			} else {
				throw new IllegalArgumentException("Invalid Password, try again");
			}
		} catch (NullPointerException ex) {
			throw new IllegalArgumentException(userName + " not found in the database");
		}
	}
	
	/**
	 * Validates if the user password combo so you can register. This is to be used before you call register
	 * @throws IllegalArgumentException if the user or password does not meet requirements. Message is attached to exception to explain
	 */
	public void validateRegistration() throws IllegalArgumentException{
		Pattern userPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5}$", Pattern.CASE_INSENSITIVE); // 5 characters, at least one letter and one number:
		Pattern passwordPattern = Pattern.compile("^\\d{5}$");
		Matcher userMatcher = userPattern.matcher(this.Name);
		Matcher passwordMatcher = passwordPattern.matcher(this.Password);
		if(this.Name.length() > 5) {
			throw new IllegalArgumentException("Name is too long");
		}
		if(this.Name.length() < 5) {
			throw new IllegalArgumentException("Name is too short");
		}
		if(this.Password.length() > 5) {
			throw new IllegalArgumentException("Password is too long");
		}
		if(this.Password.length() < 5) {
			throw new IllegalArgumentException("Password is too short");
		}
		if(!userMatcher.find()) {
			throw new IllegalArgumentException("Name does not meet requirements: 5 alpha-numeric characters, must include at least one digit");
		}
		if(!passwordMatcher.find()) {
			throw new IllegalArgumentException("Password should only be 5 digits, no characters");
		}
	}
	
	public void Update() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		PlayerRepository playerRepo = new PlayerRepository();
		playerRepo.UpdatePlayer(this);
		playerRepo.dispose();
	}

	@Override
	public int compareTo(Player compareUser) {
		//ascending order
		return Integer.compare(compareUser.Wins, this.Wins);
	}
}