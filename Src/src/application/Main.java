package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import system.ReadConfig;
import util.Stages;
import util.Valores;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			new ReadConfig(stage);
			if (Valores.getConnection() != null) {
				Stages st = new Stages();
		    	st.novoStage("EMSystem Login", "Login", null);
			}
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}