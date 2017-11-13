package controller;

import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ConfigDatabaseController {

    @FXML
    private ComboBox<?> txf_sqltype;

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
    void btn_csqlok(ActionEvent event) {
		try {
			FileWriter cria = new FileWriter("database.ini");
			cria.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void btn_csqlquit(ActionEvent event) {

    }

}
