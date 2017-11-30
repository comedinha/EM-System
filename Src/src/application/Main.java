package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import util.ReadConfig;
import util.Stages;
import util.Valores;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		new ReadConfig(stage);
		if (Valores.getConnection() != null) {
			Stages st = new Stages();
	    	st.novoStage("EMSystem Login", "Login");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
