package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sistema.Usuario;

public class LoginController {

    @FXML
    private TextField txf_usr;

    @FXML
    private PasswordField txf_pass;

    @FXML
    void btn_enterClick(ActionEvent event) throws Exception {
    	if (Usuario.login(txf_usr.getText(), txf_pass.getText())) {
	    	((Node) event.getSource()).getScene().getWindow().hide();
			Stage stage = new Stage();
			stage.setTitle("EMSystem Menu");
			BorderPane root = FXMLLoader.load(getClass().getResource("/view/Menu.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
    	}
    }

    @FXML
    void btn_leaveClick(ActionEvent event) {
    	Platform.exit();
    }

}
