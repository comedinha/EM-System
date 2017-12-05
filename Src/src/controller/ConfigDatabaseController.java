package controller;

import java.io.FileWriter;
import java.sql.SQLException;

import dao.ConectaBanco;
import dao.Funcionario;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Crypto;
import util.Stages;
import util.Valores;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Classe define o controller da configuração do banco de dados
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi.
 * @version 1.0
 */
public class ConfigDatabaseController {
	ObservableList<String> cb_sqlTypeList = FXCollections
			.observableArrayList("postgresql");

	@FXML
    private ComboBox<String> txf_sqlType;

    @FXML
    private TextField txf_sqlHost;

    @FXML
    private TextField txf_sqlPort;

    @FXML
    private TextField txf_sqlUsr;

    @FXML
    private PasswordField txf_sqlPass;

    @FXML
    private void act_confirma(ActionEvent event) throws SQLException {
		try {
			Crypto cr = new Crypto();
			String encryptPass = cr.encrypt(txf_sqlPass.getText());
			ConectaBanco.conectaBanco(txf_sqlType.getValue(), txf_sqlHost.getText(), txf_sqlPort.getText(), txf_sqlUsr.getText(), encryptPass, false);

			FileWriter file = new FileWriter("database.ini");
			file.append("DBtype=" + txf_sqlType.getValue() + "\n");
			file.append("DBaddr=" + txf_sqlHost.getText() + "\n");
			file.append("DBport=" + txf_sqlPort.getText() + "\n");
			file.append("DBuser=" + txf_sqlUsr.getText() + "\n");
			file.append("DBpassword=" + encryptPass + "\n");
			file.close();

			((Node) event.getSource()).getScene().getWindow().hide();
			if (!Funcionario.verificaGerente(Valores.getConnection())) {
				Stages st = new Stages();
		    	st.novoStage("Adicionar Gerente", "Funcionario");
		    	st.getLoader().<FuncionarioController>getController().cadastroInicial();
			} else {
				Stages st = new Stages();
		    	st.novoStage("EMSystem Login", "Login");
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.setTitle("Caixa de avisos!");
			alert.setHeaderText("Erro!");
			alert.show();
		}
    }

    @FXML
    private void act_cancela(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    private void initialize() {
    	txf_sqlType.setItems(cb_sqlTypeList);
    	txf_sqlType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		if (newValue.toString() == "postgresql") {
    			txf_sqlHost.setText("localhost");
    			txf_sqlPort.setText("5432");
    			txf_sqlUsr.setText("postgres");
    		}
    	});
    }
}
