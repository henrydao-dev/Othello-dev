package othello_UI;

import com.opencsv.bean.CsvBindByName;

public class User {
	@CsvBindByName
	String Name;
	@CsvBindByName
	String Password;
	@CsvBindByName
	int Wins;
	@CsvBindByName
	int Losses;
}