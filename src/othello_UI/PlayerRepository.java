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
 * Handles all the Create, Read, Update operations for the User entity in the users.csv file
 * @author Devin Prejean
 *
 */
public class PlayerRepository {

	private final String USERSCSVPATH = "data/users.csv";

	/**
	 * Gets the User object from the user name
	 * @param userName
	 * @return User
	 * @throws NullPointerException thrown if the user does not exist
	 */
	public Player GetUserByUserName(String userName) throws NullPointerException {
		List<Player> users = GetUsers();
		for(Player user : users) {
			if(user.Name.equalsIgnoreCase(userName)) {
				return user;
			}
		}
		System.out.println("User not found");
		throw new NullPointerException("User does not exist");		
	}
	/**
	 * Adds user to the CSV file at the end
	 * @param newUser
	 * @throws CsvDataTypeMismatchException
	 * @throws CsvRequiredFieldEmptyException
	 * @throws IOException thrown if file doesn't exist
	 * @throws IllegalArgumentException thrown if the user already exists in the CSV
	 */
	public void InsertUser(Player newUser) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException, IllegalArgumentException {
		newUser.Id = GetNextId();
		List<Player> users = GetUsers();
		for(Player user : users) {
			if(user.Name.equals(newUser.Name)) {
				throw new IllegalArgumentException("A user with this name already exists");
			}
		}
		users.add(newUser);
		Writer writer = new FileWriter(USERSCSVPATH);
	    StatefulBeanToCsv<Player> beanToCsv = new StatefulBeanToCsvBuilder<Player>(writer).build();
	    beanToCsv.write(users);
	    writer.close();
	}
	/**
	 * Updates the user in the CSV file by replacing the entire record with the one passed
	 * @param updatedUser
	 * @throws IOException
	 * @throws CsvDataTypeMismatchException
	 * @throws CsvRequiredFieldEmptyException
	 */
	public void UpdateUser(Player updatedUser) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		// Read existing file
		List<Player> users = GetUsers();
		int index = 0;
		for(Player user : users) {
			if(user.Id == updatedUser.Id) {
				users.set(index, updatedUser);
			}
			index++;
		}

        // Write to CSV file which is open
		 Writer writer = new FileWriter(USERSCSVPATH);
	     StatefulBeanToCsv<Player> beanToCsv = new StatefulBeanToCsvBuilder<Player>(writer).build();
	     beanToCsv.write(users);
	     writer.close();
	}
	/**
	 * Gets all the Users in the CSV file
	 * @return List<User>
	 */
	public List<Player> GetUsers() {
		try {
			FileReader file = new FileReader(USERSCSVPATH);
			List<Player> users = new CsvToBeanBuilder<Player>(file)
				       .withType(Player.class).build().parse();
			return users;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Gets the next available ID in the CSV by getting the last one and adding one
	 * @return
	 */
	private int GetNextId() {
		List<Player> users = GetUsers();
		if(users.size() > 0) {
			return users.get(users.size() -1).Id + 1;
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
