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
import javafx.scene.layout.VBox;

public class ProdutoController {
	private int mode = 0;

	@FXML
    private CheckBox chb_enableid;

    @FXML
    private TextField txf_nome;

    @FXML
    private TextField txf_valor;

    @FXML
    private TextField txf_id;

    @FXML
    private VBox vbox_aviso;

    @FXML
    private void act_cadastro(ActionEvent event) {
    	try {
	    	int id = 0;
	    	if (mode == 0) {
		    	if (!txf_id.isDisable() && !txf_id.getText().isEmpty())
		    		id = Integer.parseInt(txf_id.getText());

		    	String nome = txf_nome.getText();
		    	float valor = Float.parseFloat(txf_valor.getText().replace(',', '.'));

		    	if((!txf_id.isDisable() && !txf_id.getText().isEmpty()) && Produto.verificaExistenciaProduto(id))
		    		Produto.delete(id);
		    	else {	    	
		    		if(Produto.adicionaProduto(id, nome, valor)) {
		    			((Node) event.getSource()).getScene().getWindow().hide();
		    	} else {
		    		throw new Exception("Erro no cadastro!");
		    	}
	    	}} else if (mode == 1) {
	    		id = Integer.parseInt(txf_id.getText());
	    		String nome = txf_nome.getText();
		    	float valor = Float.parseFloat(txf_valor.getText());
		    	
		    	if(Produto.editaProduto(id, nome, valor)) {
		    		((Node) event.getSource()).getScene().getWindow().hide();
		    	} else {
		    		throw new Exception("Erro na edição!");
		    	}

		    	Valores.editCheck().remove("Produto" + id);
	    	}
	    	Valores.getController().refresh(1);
    	} catch (NumberFormatException e) {
    		Stages.novoAlerta("Valor digitado inválido. Não utilize valores como '1.000,00'\n Utilize '1000,00'", "", true);
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    private void act_cancelar(ActionEvent event) {
    	Valores.editCheck().remove("Produto" + Integer.valueOf(txf_id.getText()));
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
    	mode = 1;
    	chb_enableid.setDisable(true);
    	txf_id.setText(Integer.toString(id));
    	txf_nome.setText(nome);
    	txf_valor.setText(Float.toString(valor));
    	vbox_aviso.setVisible(false);
    }
}
