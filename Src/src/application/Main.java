package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("EMSystem");
		BorderPane root = FXMLLoader.load(getClass().getResource("Layout.fxml"));
		Scene scene = new Scene(root, 300, 200);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
