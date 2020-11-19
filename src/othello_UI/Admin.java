package othello_UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/*
 * Devin, Sadra
 */

public class Admin {

	/**
	 * Gets the user's last game that was not finished, essentially resuming play. 
	 * @param player1
	 * @return Game
	 * @throws IOException 
	 * @throws CsvRequiredFieldEmptyException 
	 * @throws CsvDataTypeMismatchException 
	 */
	public static Game GetLastUnfinishedGameForPlayers(Player player1, Player player2) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {

		
		return new Game(); //added this just for now, in fact we should uncomment upper codes and sync them with the library.
	}
	/**
	 * Gets the players in ascending order by number of wins
	 * @return
	 */
	public static List<Player> GetLeaderBoard() {
		PlayerRepository playerRepo = new PlayerRepository();
		try {
			List<Player> allUsers = playerRepo.GetPlayers();
			Collections.sort(allUsers);
			return allUsers;
		} finally {
			playerRepo.dispose(); 
		}
	}
}
