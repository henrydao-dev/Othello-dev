package othello_UI;

import com.opencsv.bean.CsvBindByName;

public class User {
	@CsvBindByName
	int Id;
	@CsvBindByName
	String Name;
	@CsvBindByName
	String Password;
	@CsvBindByName
	int Wins;
	@CsvBindByName
	int Losses;
	
	public User() {
		
	}
	
	public User(String name, String password) {
		this.Name = name;
		this.Password = password;
		this.Wins = 0;
		this.Losses = 0;
	}
}