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
	public Player PlayerOne; // Player 1 is always the Black colored discs
	@CsvBindByName
	public Player PlayerTwo;
	@CsvBindByName
	public String LastTurn; // 1 or 2 to signify the player's turn
	@CsvBindByName
	public String StartString;
	@CsvBindByName
	public String EndString;
	public Date Start;
	public Date End;
	@CsvBindByName
	public int PlayerOneTime; // In Seconds
	@CsvBindByName
	public int PlayerTwoTime; // In Seconds

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

	public Game(Player playerOne, Player playerTwo) {
		this.PlayerOne = playerOne;
		this.PlayerTwo = playerTwo;
		this.LastTurn = playerOne.Name; // No one has played
		this.StartString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();

	}

	public void SwitchTurn() {
		if (this.LastTurn == PlayerOne.Name) {
			this.LastTurn = PlayerTwo.Name;
		} else {
			this.LastTurn = PlayerOne.Name;
		}
		System.out.println("now its "+this.LastTurn+"\'s Turn");
	}

	public Player getPlayerwithTurn() {
		if (PlayerOne.Name == this.LastTurn)
			return PlayerOne;
		else
			return PlayerTwo;
	}

}