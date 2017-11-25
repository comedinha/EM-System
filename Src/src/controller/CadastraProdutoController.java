package controller;

import javafx.event.ActionEvent;
import sistema.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CadastraProdutoController {

    @FXML
    private TextField txf_id;

    @FXML
    private TextField txf_nome;

    @FXML
    private TextField txf_valor;

    @FXML
    private TextField txf_quantidade;

    @FXML
    private Button btn_cadastrar;

    @FXML
    void cadastrar(ActionEvent event) {
    	Produto produto = new Produto();
    	int id = Integer.parseInt(txf_id.getText());
    	String nome = txf_nome.getText();
    	float valor = Float.parseFloat(txf_valor.getText());
    	int qtde = Integer.parseInt(txf_quantidade.getText());
    	
    	produto.adicionaProduto(id, nome, valor, qtde);
    }

}
