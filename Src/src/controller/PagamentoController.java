package controller;

import java.sql.Timestamp;

import system.Pagamento;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
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
	ComandaController controller;

    @FXML
    private TextField tf_valorpagar;

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
    	float troco = Float.valueOf(tf_caixa.getText()) - Float.valueOf(tf_valorpagar.getText());
	    if (troco > 0)
	    	tf_troco.setText(Float.toString(troco));
	    else
	    	tf_troco.setText("0");
    }

    @FXML
    void btn_cancelar(ActionEvent event) {
    	controller.refresh();
    	parent.setDisable(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void btn_salvar(ActionEvent event) {
    	try {
    		float troco = Float.valueOf(tf_caixa.getText()) - Float.valueOf(tf_valorpagar.getText());
    	    if (troco > 0)
    	    	tf_troco.setText(Float.toString(troco));
    	    else
    	    	tf_troco.setText("0");

    		float valorPagar = Float.valueOf(tf_caixa.getText());

    		if (Float.valueOf(tf_troco.getText()) > 0)
    			valorPagar -= Float.valueOf(tf_troco.getText());

	    	if (mode == 0) {
	    		Pagamento.pagamentoComanda(id, time, valorPagar, Valores.getUsuario().getId(), cb_meioPagamento.getValue().getValor(), false);
	    	} else if (mode == 1) {
	    		Pagamento.pagamentoComanda(id, time, Float.valueOf(tf_caixa.getText()), Valores.getUsuario().getId(), cb_meioPagamento.getValue().getValor(), true);
	    	} else if (mode == 2) {
	    		Pagamento.pagamentoProduto(idproduto, id, time, valorPagar, Valores.getUsuario().getId(), cb_meioPagamento.getValue().getValor());
	    	} else {
	    		throw new Exception("Erro ao salvar produto!");
	    	}

	    	controller.refresh();
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

    	cb_meioPagamento.getItems().addAll(MeioPagamentoEnum.values());
    	cb_meioPagamento.setValue(MeioPagamentoEnum.Dinheiro);
	}
    
    /**
     * Metodo chamado quando acessa-se a interface no modo edição
     * @param id
     * @param time
     * @param valor
     * @param root
     * @param comandaController
     */
    public void adicionaDesconto(int id, Timestamp time, float valor, Parent root, ComandaController comandaController) {
    	this.mode = 1;
    	this.id = id;
    	this.time = time;
    	this.parent = root;
    	this.controller = comandaController;
    	tf_valorpagar.setText(Float.toString(valor));
    	txt_caixa.setText(String.format(txt_caixa.getText(), "Desconto"));
    }
    
    /**
     * Metodo chamado quando acessa-se a interface no modo adição de pagamento da comanda
     * @param id
     * @param time
     * @param valor
     * @param root
     * @param comandaController
     */
    public void adicionaPagamento(int id, Timestamp time, float valor, Parent root, ComandaController comandaController) {
    	this.id = id;
    	this.time = time;
    	this.parent = root;
    	this.controller = comandaController;
    	tf_valorpagar.setText(Float.toString(valor));
    	txt_caixa.setText(String.format(txt_caixa.getText(), "Pagamento"));
    }
    
    /**
     * Metodo chamado quando acessa-se a interface no modo adição de pagamento do produto
     * @param id
     * @param idComanda
     * @param timeComanda
     * @param valorPagar
     * @param root
     * @param comandaController
     */
	public void adicionaProdutoPagamento(int id, int idComanda, Timestamp timeComanda, float valorPagar, Parent root, ComandaController comandaController) {
		this.mode = 2;
    	this.idproduto = id;
    	this.id = idComanda;
    	this.time = timeComanda;
    	this.parent = root;
    	this.controller = comandaController;
		tf_valorpagar.setText(Float.toString(valorPagar));
    	txt_caixa.setText(String.format(txt_caixa.getText(), "Pagamento"));
	}
}