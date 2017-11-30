package controller;

import org.controlsfx.control.textfield.TextFields;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import system.Produto;
import util.Stages;

public class ComandaController {

    @FXML
    private TextField txf_produto;

    @FXML
    private void initialize() {
    	try {
    		TextFields.bindAutoCompletion(txf_produto, Produto.getProdutoNome());
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }
}
