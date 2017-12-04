package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import system.Funcionario;
import util.Stages;
import util.Valores;

public class LoginController {

    @FXML
    private TextField txf_usr;

    @FXML
    private PasswordField pf_pass;

    @FXML
    private Button btn_entrar;

    @FXML
    private void act_entrar(ActionEvent event) {
    	try {
    		if (Funcionario.login(txf_usr.getText(), pf_pass.getText())) {
			    ((Node) event.getSource()).getScene().getWindow().hide();
			    Stages st = new Stages();
			    st.novoStage("EMSystem Menu", "Menu");
		    	Valores.setController(st.getLoader().<MenuController>getController());
    		} else {
    			throw new Exception("Usuário ou senha incorreto.");
    		}
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    private void act_cancelar(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    private void initialize() {
    	btn_entrar.setDefaultButton(true);
    }
}
