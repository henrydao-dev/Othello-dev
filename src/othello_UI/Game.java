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
	public String PlayerOne; // Player 1 is always the Black colored discs
	@CsvBindByName
	public String PlayerTwo;
	@CsvBindByName
	public int LastTurn; // 1 or 2 to signify the player's turn
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
	
	public Game() {
		
	}
	
	public Game(String playerOne, String playerTwo) {
		this.PlayerOne = playerOne;
		this.PlayerTwo = playerTwo;
		this.LastTurn = 0; // No one has played
		this.StartString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
		
	}
}