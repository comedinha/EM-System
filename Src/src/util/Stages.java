package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Faz a criação e admnistração das Stages.
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi
 * @version 1.0
 */
public class Stages {
	FXMLLoader loader;
	Stage stage;
	
	/**
	 * Cria um novo Stage
	 * @param title Título
	 * @param dir Localização do arquivo.fxml
	 */
	public void novoStage(String title, String dir) {
		try {
			Stage stage = new Stage();
			stage.setTitle(title);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + dir + ".fxml"));
			BorderPane root = loader.load();
			Scene scene = new Scene(root);
			stage.getIcons().add(new Image("file:icone.png"));
			stage.setScene(scene);
			stage.show();
			this.loader = loader;
			this.stage = stage;
		} catch (Exception e) {
			novoAlerta(e.getMessage(), "", true);
		}
	}
	
	public FXMLLoader getLoader() {
		return loader;
	}

	public Stage getStage() {
		return stage;
	}
	
	/**
	 * Cria as mensagens de erro
	 * @param erro
	 * @param aviso
	 * @param show
	 * @return
	 */
	public static Alert novoAlerta(String erro, String aviso, Boolean show) {
		Alert alert = new Alert(AlertType.ERROR, "Mensagem de erro: " + erro);
		alert.setTitle("Caixa de avisos!");
		alert.setHeaderText("Notamos que o programa apresentou algum problema.\n" + aviso);

		if (show)
			alert.show();

		return alert;
	}

	/**
	 * Cria as mensagens de alerta
	 * @param erro
	 * @return
	 */
	public static Alert novoAviso(String erro) {
		Alert alert = new Alert(AlertType.WARNING, erro);
		alert.setTitle("Caixa de avisos!");
		alert.setHeaderText("Precisamos da sua confirmação.\n");
		return alert;
	}
}
