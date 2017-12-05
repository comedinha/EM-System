package controller;

import java.sql.ResultSet;

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

/**
 * Classe define o controller do produto
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi.
 * @version 1.0
 */
public class ProdutoController {
	private int mode = 0;

	@FXML
    private CheckBox chb_enableId;

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
	    	if (!txf_id.isDisable() && !txf_id.getText().isEmpty())
	    		id = Integer.parseInt(txf_id.getText());

	    	String nome = txf_nome.getText();
	    	float valor = Float.parseFloat(txf_valor.getText().replace(',', '.'));

	    	if (valor < 0)
	    		throw new Exception("O valor não pode ser menor que 0!");

	    	if (mode == 0) {
	    		ResultSet existe = Produto.verificaExistenciaProduto(id);
	    		if (existe.next()) {
	    			if (existe.getInt(1) == 1)
		    			throw new Exception("Produto já cadastrado!");
	    			else
	    				Produto.reativaProduto(id, nome, valor);
	    		} else
		    		if (!Produto.adicionaProduto(id, nome, valor))
		    			throw new Exception("Erro no cadastro!");
	    	} else if (mode == 1) {
		    	if (!Produto.editaProduto(id, nome, valor))
		    		throw new Exception("Erro na edição!");
	    	}

	    	fecharProduto();
		    ((Node) event.getSource()).getScene().getWindow().hide();
    	} catch (NumberFormatException e) {
    		Stages.novoAlerta("Valor digitado inválido. Não utilize valores como '1.000,00'\n Utilize '1000,00'", "", true);
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    private void act_cancelar(ActionEvent event) {
    	fecharProduto();
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void initialize() {
    	if (Valores.getConnection() == null || Valores.getUsuario() == null || Valores.getController() == null)
    		Platform.exit();

    	txf_id.setDisable(true);
    	chb_enableId.selectedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {
    		public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
            	txf_id.setDisable(!new_val);
            }
        });
    }
    
    /**
     * Metodo chamado quando acessa-se a interface no modo edição
     * @param id ID do produto a ser editado
     * @param nome Nome do produto a ser editado
     * @param valor Valor do produto a ser editado
     */
    void editaProduto(int id, String nome, float valor) {
    	mode = 1;
    	chb_enableId.setDisable(true);
    	txf_id.setText(Integer.toString(id));
    	txf_nome.setText(nome);
    	txf_valor.setText(Float.toString(valor));
    	vbox_aviso.setVisible(false);
    }

    /**
	 * Metodo chamado para fechar a janela de produto de maneira segura
	 */
	public void fecharProduto() {
		try {
			if (!txf_id.isDisable() && !txf_id.getText().isEmpty())
				Valores.editCheck().remove("Produto" + Integer.valueOf(txf_id.getText()));
	    	Valores.getController().refresh(1);
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
	}
}
