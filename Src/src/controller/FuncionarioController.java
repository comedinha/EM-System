package controller;

import dao.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FuncionarioController {

    @FXML
    private ComboBox<?> txf_cargo;

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
    void btn_funcok(ActionEvent event) {
    	int id = 1;
    	String nome = txf_username.getText();
    	String usuario = txf_nickname.getText();
    	String senha = txf_password.getText();

    	Usuario.inserir(id, nome, usuario, senha);
    }

    @FXML
    void btn_funcquit(ActionEvent event) {
    	Platform.exit();
    }

}
