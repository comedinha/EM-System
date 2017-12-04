package controller;

import java.sql.Timestamp;

import system.Pagamento;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.MeioPagamentoEnum;
import util.Stages;
import util.Valores;

public class PagamentoController {
	private int mode = 0;
	private int idproduto;
	private int id;
	Timestamp time;
	private Parent parent;

    @FXML
    private TextField tf_valorpagar;

    @FXML
    private CheckBox chb_valorsec;

    @FXML
    private TextField tf_valorsec;

    @FXML
    private ChoiceBox<MeioPagamentoEnum> cb_meioPagamento;

    @FXML
    private Text txt_caixa;

    @FXML
    private TextField tf_caixa;

    @FXML
    private TextField tf_troco;

    @FXML
    void act_alteravalor(ActionEvent event) {
    	float troco = 0;
    	if (!tf_valorsec.isDisable() && chb_valorsec.isSelected()) {
    		troco = Float.valueOf(tf_valorsec.getText()) - Float.valueOf(tf_caixa.getText());
    	} else
    		troco = Float.valueOf(tf_caixa.getText()) - Float.valueOf(tf_valorpagar.getText());

	    if (troco > 0)
	    	tf_troco.setText(Float.toString(troco));
	    else
	    	tf_troco.setText("0");
    }

    @FXML
    void btn_cancelar(ActionEvent event) {    	
    	parent.setDisable(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void btn_salvar(ActionEvent event) {
    	try {
	    	if (mode == 0) {
	    		Pagamento.pagamentoComanda(id, time, Float.valueOf(tf_caixa.getText()), Valores.getUsuario().getId(), false);
	    	} else if (mode == 1) {
	    		Pagamento.pagamentoComanda(id, time, Float.valueOf(tf_caixa.getText()), Valores.getUsuario().getId(), true);
	    	} else if (mode == 2) {
	    		Pagamento.pagamentoProduto(idproduto, id, time, Float.valueOf(tf_caixa.getText()), Valores.getUsuario().getId());
	    	} else {
	    		throw new Exception("Erro ao salvar produto!");
	    	}
	    	parent.setDisable(false);
	    	((Node) event.getSource()).getScene().getWindow().hide();
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
	public void initialize() {
    	if (Valores.getConnection() == null || Valores.getUsuario() == null || Valores.getController() == null)
    		Platform.exit();

    	chb_valorsec.selectedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {
    		public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
    			tf_valorsec.setDisable(!new_val);
            }
        });

    	cb_meioPagamento.getItems().addAll(MeioPagamentoEnum.values());
	}

    public void adicionaDesconto(int id, Timestamp time, float valor, Parent root) {
    	this.mode = 1;
    	this.id = id;
    	this.time = time;
    	this.parent = root;
    	tf_valorpagar.setText(Float.toString(valor));
    	chb_valorsec.setDisable(true);
    	tf_valorsec.setDisable(true);
    	txt_caixa.setText(String.format(txt_caixa.getText(), "Desconto"));
    }

    public void adicionaPagamento(int id, Timestamp time, float valor, Parent root) {
    	this.id = id;
    	this.time = time;
    	this.parent = root;
    	tf_valorpagar.setText(Float.toString(valor));
    	txt_caixa.setText(String.format(txt_caixa.getText(), "Pagamento"));
    }

	public void adicionaProdutoPagamento(int id, int idComanda, Timestamp timeComanda, float valorPagar, Parent root) {
		this.mode = 2;
    	this.idproduto = id;
    	this.id = idComanda;
    	this.time = timeComanda;
    	this.parent = root;
		tf_valorpagar.setText(Float.toString(valorPagar));
    	txt_caixa.setText(String.format(txt_caixa.getText(), "Pagamento"));
	}
}