package othello_UI;



import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.FormatterClosedException;
import java.util.Locale;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class Game {
	@CsvBindByName
	public int Id;
	@CsvBindByName
	public String PlayerOneName; // Player 1 is always the Black colored discs
	@CsvBindByName
	public String PlayerTwoName;
	@CsvBindByName
	public String LastTurn; // Player's Name
	@CsvBindByName
	public String StartString;
	@CsvBindByName
	public String EndString;
	public Date Start;
	public Date End;
	@CsvBindByName
	public Integer PlayerOneTime; // In Seconds
	@CsvBindByName
	public Integer PlayerTwoTime; // In Seconds
	public Integer passing=0;//keeps pass

	
	/*
	 *  Devin
	 */
	public Date getStart() throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.StartString);
	}

	/*
	 *  Devin
	 */
	public Date getEnd() throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.EndString);
	}

	/*
	 *  Devin
	 */
	public Game() {

	}
	
	/*
	 * Devin
	 */
	public Game(String playerOne, String playerTwo,Integer timeLimitInSeconds) {
		this.PlayerOneName = playerOne;
		this.PlayerTwoName = playerTwo;
		this.LastTurn = playerOne; // No one has played
		this.PlayerOneTime = timeLimitInSeconds;
		this.PlayerTwoTime = timeLimitInSeconds;
	}
	/*
	 * Sadra
	 */
	public void SwitchTurn() {
		if (this.LastTurn == PlayerOneName) {
			this.LastTurn = PlayerTwoName;
		} else {
			this.LastTurn = PlayerOneName;
		}
		System.out.println("now its "+this.LastTurn+"\'s Turn");
	}
	
	/*
	 *  Devin , Sadra
	 */
    public String playerUpNext() {
        if (PlayerOneName == this.LastTurn)
            return PlayerOneName;
        else
            return PlayerTwoName;
    }

    /*
     *  Devin, Sadra
     */
	public String nextPlayer() {
		if (PlayerOneName == this.LastTurn)
			return PlayerTwoName;
		else
			return PlayerOneName;
	}

	/*
	 *  Devin, Sadra
	 */
	/**
	 * gets the player's time
	 * @return time in seconds
	 */
	public int getTimeforPlayerwithTurn(){
		if (this.LastTurn == PlayerOneName) {
		return this.PlayerOneTime;
		}
		else return this.PlayerTwoTime;
	}

	/*
	 *  Devin, Jairo 
	 */
	/**
	 * Ends the game by setting the end time and updating the csv
	 * @throws IllegalStateException if the game hasn't begun or the players are not set
	 * @throws CsvDataTypeMismatchException If the game object isn't right
	 * @throws CsvRequiredFieldEmptyException If the game object isn't right
	 * @throws IOException If the file cannot be found
	 */
	public void EndGame() throws IllegalStateException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		if(PlayerOneName == null || PlayerTwoName == null || this.StartString == null) {
			throw new IllegalStateException("Cannot end game until player one and two are set and the game has begun");
		} else {
			this.EndString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
			GameRepository gameRepo = new GameRepository();
			gameRepo.UpdateGame(this);
			gameRepo.dispose();
		}
	}

	/*
	 *  Devin,Jairo
	 */
	/**
	 * Starts the game by setting the start time string and saving it to the CSV
	 * @throws IllegalStateException if the player's are not set
	 * @throws CsvDataTypeMismatchException If the game object isn't right
	 * @throws CsvRequiredFieldEmptyException If the game object isn't right
	 * @throws IOException If the file cannot be found
	 */
	public void StartGame() throws IllegalStateException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		if(PlayerOneName == null || PlayerTwoName == null) {
			throw new IllegalStateException("Cannot start game until player one and two are set");
		} else {
			this.StartString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
			GameRepository gameRepo = new GameRepository();
			gameRepo.InsertGame(this);
			gameRepo.dispose();
		}
		
	}
	/*Sadra
	 * Saves the time to the player 
	 * @param Time
	 */
	public void setTimeforPlayerwithTurn(int Time){
		if (this.LastTurn == PlayerOneName) {
		this.PlayerOneTime = Time;
		}
		this.PlayerTwoTime = Time;
	}

}