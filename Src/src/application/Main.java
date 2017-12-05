package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import system.ReadConfig;
import util.Stages;
import util.Valores;

/**
 * Classe main que inicia o programa e executa as primeiras iterações.
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi.
 * @version 1.0
 */
public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			new ReadConfig(stage);
			if (Valores.getConnection() != null) {
				Stages st = new Stages();
		    	st.novoStage("EMSystem Login", "Login");
			}
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
	}

	/**
	 * Metodo que inicia o programa e lança o javafx.
	 * @param args parametro padrão do sistema.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}