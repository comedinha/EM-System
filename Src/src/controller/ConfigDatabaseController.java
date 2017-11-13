package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ConfigDatabaseController {

    @FXML
    private TextField txf_sqlusr;

    @FXML
    private PasswordField txf_sqlpass;

    @FXML
    private TextField txf_adminusr;

    @FXML
    private PasswordField txf_adminpass;

    @FXML
    private TextField txf_adminname;

    @FXML
    private Button btn_sqlok;

    @FXML
    private Button btn_sqlquit;

    @FXML
    void btn_csqlok(ActionEvent event) {

    }

    @FXML
    void btn_csqlquit(ActionEvent event) {

    }

}
