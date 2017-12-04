package system;

import java.io.FileInputStream;
import java.util.Optional;
import java.util.Properties;

import dao.ConectaBanco;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import util.Stages;

/**
 * Possui toda parte lógica referente a leitura das configurações do BD
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi
 * @version 1.0
 */
public class ReadConfig {
	/**
	 * Verifica a existencia do arquivo, e faz e leitura, caso não exista ou contenha informações 
	 * incorretadas, ele chama a função ConfigDatabase
	 * @param stage
	 * @throws Exception
	 */
	public ReadConfig(Stage stage) throws Exception {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("database.ini"));
			ConectaBanco.conectaBanco(p.getProperty("DBtype"), p.getProperty("DBaddr"), p.getProperty("DBport"), p.getProperty("DBuser"), p.getProperty("DBpassword"), true);
		} catch (Exception e) {
			Alert alert = Stages.novoAlerta(e.getMessage(), "Somente continue se for um administrador deste programa.\n", false);

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
	
	/**
	 * Abre a interface de configuração de BD 
	 * @param stage
	 * @throws Exception
	 */
	public void ConfigDatabase(Stage stage) throws Exception {
		Stages st = new Stages();
    	st.novoStage("EMSystem SQLConfig", "ConfigDatabase");
	}
}
