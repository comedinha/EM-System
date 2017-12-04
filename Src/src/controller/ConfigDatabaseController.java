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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ConfigDatabaseController {
	ObservableList<String> cb_sqltypeList = FXCollections
			.observableArrayList("postgresql");

	@FXML
    private ComboBox<String> txf_sqltype;

    @FXML
    private TextField txf_sqlhost;

    @FXML
    private TextField txf_sqlport;

    @FXML
    private TextField txf_sqlusr;

    @FXML
    private PasswordField txf_sqlpass;

    @FXML
    private Button btn_sqlok;

    @FXML
    private Button btn_sqlquit;

    @FXML
    private void act_confirma(ActionEvent event) throws SQLException {
		try {
			Crypto cr = new Crypto();
			String encryptPass = cr.encrypt(txf_sqlpass.getText());
			ConectaBanco.conectaBanco(txf_sqltype.getValue(), txf_sqlhost.getText(), txf_sqlport.getText(), txf_sqlusr.getText(), encryptPass, false);

			FileWriter file = new FileWriter("database.ini");
			file.append("DBtype=" + txf_sqltype.getValue() + "\n");
			file.append("DBaddr=" + txf_sqlhost.getText() + "\n");
			file.append("DBport=" + txf_sqlport.getText() + "\n");
			file.append("DBuser=" + txf_sqlusr.getText() + "\n");
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
    	txf_sqltype.setItems(cb_sqltypeList);
    	txf_sqltype.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		if (newValue.toString() == "postgresql") {
    			txf_sqlhost.setText("localhost");
    			txf_sqlport.setText("5432");
    			txf_sqlusr.setText("postgres");
    		}
    	});
    }
}
