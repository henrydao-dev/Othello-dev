package othello_UI;

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
	public Integer PlayerOneTime=120; // In Seconds
	@CsvBindByName
	public Integer PlayerTwoTime = 120; // In Seconds

	public Date getStart() throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.StartString);
	}

	public Date getEnd() throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.EndString);
	}

	public Game() {

	}

	public void EndGame() {
		this.EndString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
	}

	public Game(String playerOne, String playerTwo) {
		this.PlayerOneName = playerOne;
		this.PlayerTwoName = playerTwo;
		this.LastTurn = playerOne; // No one has played
		this.StartString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();

	}

	public void SwitchTurn() {
		if (this.LastTurn == PlayerOneName) {
			this.LastTurn = PlayerTwoName;
		} else {
			this.LastTurn = PlayerOneName;
		}
		System.out.println("now its "+this.LastTurn+"\'s Turn");
	}

	public String playerUpNext() {
		if (PlayerOneName == this.LastTurn)
			return PlayerOneName;
		else
			return PlayerTwoName;
	}
	
	public int getTimeforPlayerwithTrun(){
		if (this.LastTurn == PlayerOneName) {
		return this.PlayerOneTime;
		}
		else return this.PlayerTwoTime;
	}
	
	void setTimeforPlayerwithTrun(int Time){
		if (this.LastTurn == PlayerOneName) {
		this.PlayerOneTime = Time;
		}
		this.PlayerTwoTime = Time;
	}

}