package controller;

import java.sql.SQLException;

import org.controlsfx.control.textfield.TextFields;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import system.Produto;

public class ComandaController {

    @FXML
    private TextField txf_produto;

    @FXML
    private void initialize() throws SQLException {
    	TextFields.bindAutoCompletion(txf_produto, Produto.getProdutoNome());
    }
}
