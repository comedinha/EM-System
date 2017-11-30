package util;

import java.io.FileInputStream;
import java.util.Optional;
import java.util.Properties;

import dao.ConectaBanco;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

public class ReadConfig {
	public ReadConfig(Stage stage) throws Exception {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("database.ini"));
			ConectaBanco.conectaBanco(p.getProperty("DBtype"), p.getProperty("DBaddr"), p.getProperty("DBport"), p.getProperty("DBuser"), p.getProperty("DBpassword"), true);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, "Mensagem de erro: " + e.getMessage());
			alert.setTitle("Caixa de avisos!");
			alert.setHeaderText("Notamos que seu banco apresentou algum problema.\n"
	    			+ "Somente continue se for um administrador deste programa.\n");

			ButtonType buttonConfirm = new ButtonType("Continuar", ButtonData.OK_DONE);
			ButtonType buttonCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonConfirm) {
				ConfigDatabase(stage);
			} else if (result.get() == buttonCancel) {
				Platform.exit();
			}
			return;
		}
	}

	public void ConfigDatabase(Stage stage) throws Exception {
		Stages st = new Stages();
    	st.novoStage("EMSystem SQLConfig", "ConfigDatabase");
	}
}
