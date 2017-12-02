package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private PasswordField txf_pass;

    @FXML
    private Button btn_enter;

    @FXML
    void btn_enterClick(ActionEvent event) {
    	try {
    		if (Funcionario.login(txf_usr.getText(), txf_pass.getText())) {
			    ((Node) event.getSource()).getScene().getWindow().hide();
			    Stages st = new Stages();
			    FXMLLoader menuLoader = st.novoStage("EMSystem Menu", "Menu", null);
		    	Valores.setController(menuLoader.<MenuController>getController());
    		} else {
    			throw new Exception("Usuário ou senha incorreto.");
    		}
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    void btn_leaveClick(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    public void initialize() {
    	btn_enter.setDefaultButton(true);
    }
}
