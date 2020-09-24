package othello_UI;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.opencsv.bean.CsvToBeanBuilder;

import javafx.application.Application;
import javafx.stage.Stage;

public class othello_UI extends Application{



	public static void main(String[] args) throws IOException {
		
		System.out.println("hello world");
		try {
			FileReader file = new FileReader("data/users.csv");
			List<User> beans = new CsvToBeanBuilder<User>(file)
				       .withType(User.class).build().parse();
			for(User user : beans) {
				System.out.println(user.Name);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
	}

}
