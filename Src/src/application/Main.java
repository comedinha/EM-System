package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import util.ReadConfig;
import util.Valores;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		new ReadConfig(stage);
		if (Valores.getConnection() != null) {
			stage.setTitle("EMSystem");
			BorderPane root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root);
			stage.getIcons().add(new Image("file:icone.png"));
			stage.setScene(scene);
			stage.show();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
