package controller;

import dao.Funcionario;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FuncionarioController {
	private int type = 0;
	ObservableList<String> cb_cargoselection = FXCollections
			.observableArrayList("Gerente", "Funcionario");

    @FXML
    private ComboBox<String> txf_cargo;

    @FXML
    private TextField txf_username;

    @FXML
    private TextField txf_nickname;

    @FXML
    private PasswordField txf_password;

    @FXML
    private Button btn_funcok;

    @FXML
    private Button btn_funcquit;

    @FXML
    void btn_funcok(ActionEvent event) throws Exception {
    	int func = type;
    	String nome = txf_username.getText();
    	String usuario = txf_nickname.getText();
    	String senha = txf_password.getText();

    	Funcionario.inserir(func, nome, usuario, senha);
    	Platform.exit();
    }

    @FXML
    void btn_funcquit(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    private void initialize() {
    	txf_cargo.setItems(cb_cargoselection);
    	txf_cargo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		if (newValue.toString() == "Gerente") {
    			type = 1;
    		} else if (newValue.toString() == "Funcionario") {
    			type = 2;
    		}
    	});
    }

    void cadastroInicial() {
    	txf_cargo.setValue("Gerente");
    	txf_cargo.setDisable(true);
    }
}
