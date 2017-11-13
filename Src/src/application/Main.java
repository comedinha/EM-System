package application;
	
import java.sql.SQLException;

import dao.ConectaBanco;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("EMSystem");
		BorderPane root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		try {
			ConectaBanco.conectaBanco();
		} catch (SQLException e) {
			root = FXMLLoader.load(getClass().getResource("/view/ConfigDatabase.fxml"));
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
