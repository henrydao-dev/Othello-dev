package othello_UI;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import javafx.application.Application;
import javafx.stage.Stage;

public class othello_UI extends Application{



	public static void main(String[] args) throws IOException {

		String userName = "djpkk";
		String password = "12356";
		
		
		Player p1 = new Player(userName, password);
		
		try{
			p1.validateRegistration();
			try {
				p1.Register();
			} catch (IllegalStateException e) {
				System.out.println(e.getMessage());
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (CsvDataTypeMismatchException e) {
				System.out.println(e.getMessage());
			} catch (CsvRequiredFieldEmptyException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} catch(IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
		}
		
		
		
		
//		try{
//			Player p = Player.Login(userName, password);
//		} catch (IllegalArgumentException ex) {
//			System.out.println(ex.getMessage());
//		}
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {


	}

}
