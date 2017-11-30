package util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Stages {
	public FXMLLoader novoStage(String title, String dir) throws IOException {
		Stage stage = new Stage();
		stage.setTitle(title);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + dir + ".fxml"));
		BorderPane root = loader.load();
		Scene scene = new Scene(root);
		stage.getIcons().add(new Image("file:icone.png"));
		stage.setScene(scene);
		stage.show();
		return loader;
	}
}
