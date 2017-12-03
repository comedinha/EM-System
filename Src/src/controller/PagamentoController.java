package controller;

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
import util.MeioPagamento;
import util.Valores;

public class PagamentoController {
	private Parent parent;
	private int mode = 0;

    @FXML
    private TextField tf_valorpagar;

    @FXML
    private CheckBox chb_valorsec;

    @FXML
    private TextField tf_valorsec;

    @FXML
    private ChoiceBox<MeioPagamento> cb_meioPagamento;

    @FXML
    private Text txt_caixa;

    @FXML
    private TextField tf_caixa;

    @FXML
    private TextField tf_troco;

    @FXML
    void btn_cancelar(ActionEvent event) {    	
    	parent.setDisable(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void btn_salvar(ActionEvent event) {
    	parent.setDisable(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
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

    	cb_meioPagamento.getItems().addAll(MeioPagamento.values());
	}

    public void adicionaDesconto(float valor, Parent root) {
    	tf_valorpagar.setText(Float.toString(valor));
    	parent = root;
    	chb_valorsec.setDisable(true);
    	tf_valorsec.setDisable(true);
    	mode = 1;
    	txt_caixa.setText(String.format(txt_caixa.getText(), "Desconto"));
    }

    public void adicionaPagamento(float valor, Parent root) {
    	tf_valorpagar.setText(Float.toString(valor));
    	parent = root;
    	txt_caixa.setText(String.format(txt_caixa.getText(), "Pagamento"));
    }

	public void adicionaProdutoPagamento(float valorPagar, Parent root) {
		tf_valorpagar.setText(Float.toString(valorPagar));
    	parent = root;
    	mode = 2;
    	txt_caixa.setText(String.format(txt_caixa.getText(), "Pagamento"));
	}
}