package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Stages {
	public FXMLLoader novoStage(String title, String dir, Parent parent) {
		FXMLLoader loader = null;
		try {
			Stage stage = new Stage();
			stage.setTitle(title);
			loader = new FXMLLoader(getClass().getResource("/view/" + dir + ".fxml"));
			BorderPane root = loader.load();
			Scene scene = new Scene(root);
			stage.getIcons().add(new Image("file:icone.png"));
			stage.setScene(scene);
			stage.setOnCloseRequest(e -> {
				if (parent != null)
					parent.setDisable(false);
			});
			stage.show();
		} catch (Exception e) {
			novoAlerta(e.getMessage(), "", true);
		}
		return loader;
	}

	public static Alert novoAlerta(String erro, String aviso, Boolean show) {
		Alert alert = new Alert(AlertType.ERROR, "Mensagem de erro: " + erro);
		alert.setTitle("Caixa de avisos!");
		alert.setHeaderText("Notamos que o programa apresentou algum problema.\n" + aviso);

		if (show)
			alert.show();

		return alert;
	}
}
