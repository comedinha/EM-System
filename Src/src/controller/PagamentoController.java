package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.MeioPagamento;

public class PagamentoController {

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

    }

    @FXML
    void btn_salvar(ActionEvent event) {

    }

    @FXML
	public void initialize() {
    	cb_meioPagamento.getItems().addAll(MeioPagamento.values());
	}
}