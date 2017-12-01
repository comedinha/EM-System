package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import system.Produto;
import util.Stages;
import util.Valores;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class ProdutoController {
	private int type = 0;

	@FXML
    private CheckBox chb_enableid;

    @FXML
    private TextField txf_nome;

    @FXML
    private TextField txf_valor;

    @FXML
    private TextField txf_id;

    @FXML
    void act_cadastro(ActionEvent event) {
    	try {
	    	int id = 0;
	    	if (type == 0) {
		    	if (!txf_id.isDisable() && !txf_id.getText().isEmpty())
		    		id = Integer.parseInt(txf_id.getText());
		
		    	String nome = txf_nome.getText();
		    	float valor = Float.parseFloat(txf_valor.getText());
		
		    	if(Produto.adicionaProduto(id, nome, valor)) {
		    		((Node) event.getSource()).getScene().getWindow().hide();
		    	} else {
		    		throw new Exception("Erro no cadastro!");
		    	}
	    	} else if (type == 1) {
	    		id = Integer.parseInt(txf_id.getText());
	    		String nome = txf_nome.getText();
		    	float valor = Float.parseFloat(txf_valor.getText());
		    	
		    	if(Produto.editaProduto(id, nome, valor)) {
		    		((Node) event.getSource()).getScene().getWindow().hide();
		    	} else {
		    		throw new Exception("Erro na edição!");
		    	}
	    	}
	    	Valores.getController().refresh(1);
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    void act_cancelar(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void initialize() {
    	if (Valores.getConnection() == null || Valores.getUsuario() == null || Valores.getController() == null)
    		Platform.exit();

    	txf_id.setDisable(true);
    	chb_enableid.selectedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {
    		public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
            	txf_id.setDisable(!new_val);
            }
        });
    }

    void editaProduto(int id, String nome, float valor) {
    	type = 1;
    	chb_enableid.setDisable(true);
    	txf_id.setText(Integer.toString(id));
    	txf_nome.setText(nome);
    	txf_valor.setText(Float.toString(valor));
    }
}
