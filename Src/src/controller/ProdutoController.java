package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import system.Produto;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class ProdutoController {

	@FXML
    private CheckBox chb_enableid;

    @FXML
    private TextField txf_nome;

    @FXML
    private TextField txf_valor;

    @FXML
    private TextField txf_id;

    @FXML
    void act_cadastro(ActionEvent event) throws Exception {
    	int id = 0;
    	if (!txf_id.isDisable() && !txf_id.getText().isEmpty())
    		id = Integer.parseInt(txf_id.getText());

    	String nome = txf_nome.getText();
    	float valor = Float.parseFloat(txf_valor.getText());

    	if(Produto.adicionaProduto(id, nome, valor)) {    		
    		((Node) event.getSource()).getScene().getWindow().hide();
    	} else {
    		//alerta de erro, cadastro não concluido
    	}
    	
    }

    @FXML
    void act_cancelar(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void initialize() {
    	txf_id.setDisable(true);
    	chb_enableid.selectedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {
    		public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
            	txf_id.setDisable(!new_val);
            }
        });
    }
}
