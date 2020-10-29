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
/**
 * Handles all the Create, Read, Update operations for the Game entity in the games.csv file
 * @author Devin Prejean
 *
 */
public class GameRepository {

	private final String USERSCSVPATH = "data/games.csv";

	/**
	 * Adds a game into the CSV file
	 * @param newGame
	 * @throws CsvDataTypeMismatchException If the game object isn't right
	 * @throws CsvRequiredFieldEmptyException If the game object isn't right
	 * @throws IOException If the file cannot be found
	 */
	public void InsertGame(Game newGame) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		newGame.Id = GetNextId();
		List<Game> games = GetGames();
		games.add(newGame);
		Writer writer = new FileWriter(USERSCSVPATH);
		StatefulBeanToCsv<Game> beanToCsv = new StatefulBeanToCsvBuilder<Game>(writer).build();
		beanToCsv.write(games);
		writer.close();
	}

	/**
	 * Updates the game in the CSV file, overwriting the entire record with the one passed in.
	 * @param updatedGame
	 * @throws CsvDataTypeMismatchException If the game object isn't right
	 * @throws CsvRequiredFieldEmptyException If the game object isn't right
	 * @throws IOException If the file cannot be found
	 */
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
	/**
	 * Gets all the games in the CSV
	 */
	public List<Game> GetGames() {
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
	/**
	 * Get's the next available ID in the list by getting the last and adding one
	 * @return Next ID available in the CSV
	 */
	private int GetNextId() {
		List<Game> games = GetGames();
		if(games.size() > 0) {
			return games.get(games.size() -1).Id + 1;
		} else {
			return 1;
		}
	}

	public void dispose() {
		try {
			super.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
