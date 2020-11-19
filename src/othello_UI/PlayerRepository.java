package othello_UI;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
/**
 * Handles all the Create, Read, Update operations for the Player entity in the players.csv file
 * @author Devin Prejean
 *
 */
public class PlayerRepository {

	private final String USERSCSVPATH = "data/players.csv";

	/**
	 * Gets the User object from the user name
	 * @param userName
	 * @return User
	 * @throws NullPointerException thrown if the user does not exist
	 */
	public Player GetPlayerByName(String name) throws NullPointerException {
		List<Player> players = GetPlayers();
		for(Player player : players) {
			if(player.Name.equalsIgnoreCase(name)) {
				return player;
			}
		}
		throw new NullPointerException("Player does not exist");		
	}
	/**
	 * Adds Player to the CSV file at the end
	 * @param newPlayer
	 * @throws CsvDataTypeMismatchException
	 * @throws CsvRequiredFieldEmptyException
	 * @throws IOException thrown if file doesn't exist
	 * @throws IllegalArgumentException thrown if the user already exists in the CSV
	 */
	public void InsertUser(Player newPlayer) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException, IllegalArgumentException {
		newPlayer.Id = GetNextId();
		List<Player> players = GetPlayers();
		for(Player player : players) {
			if(player.Name.equals(newPlayer.Name)) {
				throw new IllegalArgumentException("A Player with this name already exists");
			}
		}
		players.add(newPlayer);
		Writer writer = new FileWriter(USERSCSVPATH);
		StatefulBeanToCsv<Player> beanToCsv = new StatefulBeanToCsvBuilder<Player>(writer).build();
		beanToCsv.write(players);
		writer.close();
	}
	/**
	 * Updates the user in the CSV file by replacing the entire record with the one passed
	 * @param updatedPlayer
	 * @throws IOException
	 * @throws CsvDataTypeMismatchException
	 * @throws CsvRequiredFieldEmptyException
	 */
	public void UpdatePlayer(Player updatedPlayer) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		// Read existing file
		List<Player> players = GetPlayers();
		int index = 0;
		for(Player player : players) {
			if(player.Id == updatedPlayer.Id) {
				players.set(index, updatedPlayer);
			}
			index++;
		}

		// Write to CSV file which is open
		Writer writer = new FileWriter(USERSCSVPATH);
		StatefulBeanToCsv<Player> beanToCsv = new StatefulBeanToCsvBuilder<Player>(writer).build();
		beanToCsv.write(players);
		writer.close();
	}
	/**
	 * Gets all the Users in the CSV file
	 * @return List<Player>
	 */
	public List<Player> GetPlayers() {
		try {
			FileReader file = new FileReader(USERSCSVPATH);
			List<Player> players = new CsvToBeanBuilder<Player>(file)
					.withType(Player.class).build().parse();
			return players;
		} catch (FileNotFoundException e) {
			System.out.println("File can't be found! " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Gets the next available ID in the CSV by getting the last one and adding one
	 * @return
	 */
	private int GetNextId() {
		List<Player> players = GetPlayers();
		if(players.size() > 0) {
			return players.get(players.size() -1).Id + 1;
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
