package othello_UI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Admin {

	/**
	 * Gets the user's last game that was not finished, essentially resuming play. 
	 * @param user
	 * @return Game
	 */
	public static Game GetLastUnfinishedGameForUser(Player user) {
		
		GameRepository gameRepo = new GameRepository();
		try {
			List<Game> allGames = gameRepo.GetGames();
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
		} finally {
			gameRepo.dispose();
		}
	}
	/**
	 * Gets the players in ascending order by number of wins
	 * @return
	 */
	public static List<Player> GetLeaderBoard() {
		PlayerRepository userRepo = new PlayerRepository();
		try {
			List<Player> allUsers = userRepo.GetUsers();
			Collections.sort(allUsers);
			return allUsers;
		} finally {
			userRepo.dispose(); 
		}
	}
}
