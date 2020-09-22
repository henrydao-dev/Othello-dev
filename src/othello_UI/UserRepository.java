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

public class UserRepository {

	private final String USERSCSVPATH = "data/users.csv";

	public User GetUserByUserName(String userName) throws NullPointerException {
		List<User> users = GetUsers();
		for(User user : users) {
			if(user.Name.equalsIgnoreCase(userName)) {
				return user;
			}
		}
		System.out.println("User not found");
		throw new NullPointerException("User does not exist");		
	}
	
	public void InsertUser(User newUser) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException, IllegalArgumentException {
		newUser.Id = GetNextId();
		List<User> users = GetUsers();
		for(User user : users) {
			if(user.Name.equals(newUser.Name)) {
				throw new IllegalArgumentException("A user with this name already exists");
			}
		}
		users.add(newUser);
		Writer writer = new FileWriter(USERSCSVPATH);
	    StatefulBeanToCsv<User> beanToCsv = new StatefulBeanToCsvBuilder<User>(writer).build();
	    beanToCsv.write(users);
	    writer.close();
	}
	
	public void UpdateUser(User updatedUser) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		// Read existing file
		List<User> users = GetUsers();
		int index = 0;
		for(User user : users) {
			if(user.Id == updatedUser.Id) {
				users.set(index, updatedUser);
			}
			index++;
		}

        // Write to CSV file which is open
		 Writer writer = new FileWriter(USERSCSVPATH);
	     StatefulBeanToCsv<User> beanToCsv = new StatefulBeanToCsvBuilder<User>(writer).build();
	     beanToCsv.write(users);
	     writer.close();
	}
	
	private List<User> GetUsers() {
		try {
			FileReader file = new FileReader(USERSCSVPATH);
			List<User> users = new CsvToBeanBuilder<User>(file)
				       .withType(User.class).build().parse();
			return users;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private int GetNextId() {
		List<User> users = GetUsers();
		return users.get(users.size() -1).Id + 1;
	}
}
