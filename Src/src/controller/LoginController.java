package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import system.Funcionario;
import util.Stages;
import util.Valores;

public class LoginController implements Initializable {

    @FXML
    private TextField txf_usr;

    @FXML
    private PasswordField txf_pass;
    
    @FXML
    private Label lbl_erro;

    @FXML
    private Button btn_enter;

    @FXML
    void btn_enterClick(ActionEvent event) throws Exception {
    	if (Funcionario.login(txf_usr.getText(), txf_pass.getText())) {
	    	((Node) event.getSource()).getScene().getWindow().hide();
	    	Stages st = new Stages();
	    	FXMLLoader menuLoader = st.novoStage("EMSystem Menu", "Menu");
    		Valores.setController(menuLoader.<MenuController>getController());
    	} else {
    		lbl_erro.setText("Nome de Usuario ou senha incorreta");
    	}
    }

    @FXML
    void btn_leaveClick(ActionEvent event) {
    	Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {
    	btn_enter.setDefaultButton(true);
    }
}
