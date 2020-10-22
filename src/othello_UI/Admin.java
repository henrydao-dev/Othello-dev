package othello_UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

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
		
		GameRepository gameRepo = new GameRepository();
		try {
			List<Game> allGames = gameRepo.GetGames();
			List<Game> usersGames = new ArrayList<Game>();
			for(Game game : allGames) {
				if((game.PlayerOne.equalsIgnoreCase(player1.Name) || game.PlayerTwo.equalsIgnoreCase(player1.Name)) && (game.PlayerOne.equalsIgnoreCase(player2.Name) || game.PlayerTwo.equalsIgnoreCase(player2.Name)) ) {
					usersGames.add(game);
				}
			}
			// Gets the last game, which would be the most recent and ensures the game is not complete
			if(usersGames.size() > 0 && usersGames.get(usersGames.size() -1).End == null) {
				return usersGames.get(usersGames.size() -1); 
			} else {
				Game newGame = new Game(player1.Name, player2.Name);
				gameRepo.InsertGame(newGame);
				return newGame;
			}
		} finally {
			gameRepo.dispose();
		}
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
	
	/**
	 * Registers a new player
	 * @param name
	 * @param password
	 * @return
	 * @throws CsvDataTypeMismatchException
	 * @throws CsvRequiredFieldEmptyException
	 * @throws IOException thrown if file doesn't exist
	 * @throws IllegalArgumentException thrown if the user already exists in the CSV
	 */
	public static Player RegisterPlayer(String name, String password) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IllegalArgumentException, IOException {
		PlayerRepository playerRepo = new PlayerRepository();
		
		Player player = new Player(name, password);
		playerRepo.InsertUser(player);
		return player;
	}
	
	/**
	 * Checks the password for the given player's name against the repository
	 * @param name
	 * @param password
	 * @return true if the password matches, false if it doesn't
	 */
	public static boolean Login(String name, String password) {
		PlayerRepository playerRepo = new PlayerRepository();
		
		Player player = playerRepo.GetPlayerByName(name);
		if(player.Password == password) {
			return true;
		} else {
			return false;
		}
	}
}
