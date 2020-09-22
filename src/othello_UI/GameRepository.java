package othello_UI;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class GameRepository {

	private final String USERSCSVPATH = "data/games.csv";

	public Game GetLastUnfinishedGameForUser(User user) {
		List<Game> allGames = GetGames();
		List<Game> usersGames = new ArrayList<Game>();
		for(Game game : allGames) {
			if(game.PlayerOne.equalsIgnoreCase(user.Name) || game.PlayerTwo.equalsIgnoreCase(user.Name)) {
				usersGames.add(game);
			}
		}
		if(usersGames.size() > 0) {
			return usersGames.get(usersGames.size() -1); // Gets the last game, which would be the most recent TODO: ensure this game is not complete
		} else {
			return null;
		}
	}
	
	public void InsertGame(Game newGame) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException, IllegalArgumentException {
		newGame.Id = GetNextId();
		List<Game> games = GetGames();
		games.add(newGame);
		Writer writer = new FileWriter(USERSCSVPATH);
	    StatefulBeanToCsv<Game> beanToCsv = new StatefulBeanToCsvBuilder<Game>(writer).build();
	    beanToCsv.write(games);
	    writer.close();
	}
	
	public void UpdateGame(Game updatedGame) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		// Read existing file
		List<Game> games = GetGames();
		int index = 0;
		for(Game game : games) {
			if(game.Id == updatedGame.Id) {
				games.set(index, updatedGame);
			}
			index++;
		}

        // Write to CSV file which is open
		 Writer writer = new FileWriter(USERSCSVPATH);
	     StatefulBeanToCsv<Game> beanToCsv = new StatefulBeanToCsvBuilder<Game>(writer).build();
	     beanToCsv.write(games);
	     writer.close();
	}
	
	private List<Game> GetGames() {
		try {
			FileReader file = new FileReader(USERSCSVPATH);
			List<Game> games = new CsvToBeanBuilder<Game>(file)
				       .withType(Game.class).build().parse();
			return games;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private int GetNextId() {
		List<Game> games = GetGames();
		if(games.size() > 0) {
			return games.get(games.size() -1).Id + 1;
		} else {
			return 1;
		}
	}
}
