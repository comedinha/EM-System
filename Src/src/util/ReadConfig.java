package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import dao.ConectaBanco;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ReadConfig {
	public ReadConfig(Stage stage) throws Exception {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("database.ini"));
			ConectaBanco.conectaBanco(p.getProperty("DBtype"), p.getProperty("DBaddr"), p.getProperty("DBport"), p.getProperty("DBuser"), p.getProperty("DBpassword"));
		} catch (FileNotFoundException e) {
			ConfigDatabase(stage);
		} catch (Exception e) {
			System.out.println("Erro:" + e);
			ConfigDatabase(stage);
		}
	}

	public void ConfigDatabase(Stage stage) throws Exception {
		stage.setTitle("SQLConfig");
		BorderPane root = FXMLLoader.load(getClass().getResource("/view/ConfigDatabase.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
