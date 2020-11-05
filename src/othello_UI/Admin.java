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

		//		GameRepository gameRepo = new GameRepository();
		//		try {
		//			List<Game> allGames = gameRepo.GetGames();
		//			List<Game> usersGames = new ArrayList<Game>();
		//			for(Game game : allGames) {
		//				if((game.PlayerOne.equalsIgnoreCase(player1.Name) || game.PlayerTwo.equalsIgnoreCase(player1.Name)) && (game.PlayerOne.equalsIgnoreCase(player2.Name) || game.PlayerTwo.equalsIgnoreCase(player2.Name)) ) {
		//					usersGames.add(game);
		//				}
		//			}
		//			// Gets the last game, which would be the most recent and ensures the game is not complete
		//			if(usersGames.size() > 0 && usersGames.get(usersGames.size() -1).End == null) {
		//				return usersGames.get(usersGames.size() -1); 
		//			} else {
		//				Game newGame = new Game(player1.Name, player2.Name);
		//				gameRepo.InsertGame(newGame);
		//				return newGame;
		//			}
		//		} finally {
		//			gameRepo.dispose();
		//		}
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
