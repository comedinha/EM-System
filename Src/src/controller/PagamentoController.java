package controller;

import java.sql.ResultSet;
import java.sql.Timestamp;

import dao.Configuracao;
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

/**
 * Classe define o controller do pagamento
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi.
 * @version 1.0
 */
public class PagamentoController {
	private int mode = 0;
	private int idproduto;
	private int id;
	Timestamp time;
	private Parent parent;
	ComandaController controller;

    @FXML
    private TextField txf_valorPagar;

    @FXML
    private ChoiceBox<MeioPagamentoEnum> cb_meioPagamento;

    @FXML
    private Text txt_caixa;

    @FXML
    private TextField txf_caixa;

    @FXML
    private TextField txf_troco;

    @FXML
    private void act_alteravalor(ActionEvent event) {
    	float troco = Float.valueOf(txf_caixa.getText()) - Float.valueOf(txf_valorPagar.getText());
	    if (troco > 0)
	    	txf_troco.setText(Float.toString(troco));
	    else
	    	txf_troco.setText("0");
    }

    @FXML
    private void act_cancelar(ActionEvent event) {
    	controller.refresh();
    	parent.setDisable(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void act_salvar(ActionEvent event) {
    	try {
    		float troco = Float.valueOf(txf_caixa.getText()) - Float.valueOf(txf_valorPagar.getText());
    	    if (troco > 0)
    	    	txf_troco.setText(Float.toString(troco));
    	    else
    	    	txf_troco.setText("0");

    		float valorPagar = Float.valueOf(txf_caixa.getText());
    		if (valorPagar < 0)
    			throw new Exception("O valor não pode ser menor que 0!");

    		if (Float.valueOf(txf_troco.getText()) > 0)
    			valorPagar -= Float.valueOf(txf_troco.getText());

	    	if (mode == 0) {
	    		Pagamento.pagamentoComanda(id, time, valorPagar, Valores.getUsuario().getId(), cb_meioPagamento.getValue().getValor(), false);
	    	} else if (mode == 1) {
	    		Pagamento.pagamentoComanda(id, time, Float.valueOf(txf_caixa.getText()), Valores.getUsuario().getId(), cb_meioPagamento.getValue().getValor(), true);
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
    private void initialize() {
    	try {
    	if (Valores.getConnection() == null || Valores.getUsuario() == null || Valores.getController() == null)
    		Platform.exit();

    	cb_meioPagamento.getItems().addAll(MeioPagamentoEnum.values());
    	ResultSet result = Configuracao.getConfigFuncionario(Valores.getUsuario().getId(), "Global.Meio de Pagamento");
    	if (result.next()) {
    		cb_meioPagamento.setValue(MeioPagamentoEnum.getKey(result.getString("value")));
    	} else
    		cb_meioPagamento.setValue(MeioPagamentoEnum.Dinheiro);
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
	}
    
    /**
     * Metodo chamado quando acessa-se a interface no modo edição para dar descontos a comanda
     * @param id da comanda
     * @param time data da comanda
     * @param valor valor do pagamento
     * @param root root da comanda
     * @param comandaController controlador da comanda
     */
    public void adicionaDesconto(int id, Timestamp time, float valor, Parent root, ComandaController comandaController) {
    	this.mode = 1;
    	this.id = id;
    	this.time = time;
    	this.parent = root;
    	this.controller = comandaController;
    	txf_valorPagar.setText(Float.toString(valor));
    	txt_caixa.setText(String.format(txt_caixa.getText(), "Desconto"));
    }
    
    /**
     * Metodo chamado quando acessa-se a interface no modo adição de pagamento da comanda
     * @param id id da comanda
     * @param time data da comanda
     * @param valor valor do pagamento
     * @param root root da comanda
     * @param comandaController controlador da comanda
     */
    public void adicionaPagamento(int id, Timestamp time, float valor, Parent root, ComandaController comandaController) {
    	this.id = id;
    	this.time = time;
    	this.parent = root;
    	this.controller = comandaController;
    	txf_valorPagar.setText(Float.toString(valor));
    	txt_caixa.setText(String.format(txt_caixa.getText(), "Pagamento"));
    }
    
    /**
     * Metodo chamado quando acessa-se a interface no modo adição de pagamento do produto
     * @param id id do pagamento
     * @param idComanda id da comanda
     * @param timeComanda data da comanda
     * @param valorPagar valor a pagar
     * @param root root da comanda
     * @param comandaController controlador da comanda
     */
	public void adicionaProdutoPagamento(int id, int idComanda, Timestamp timeComanda, float valorPagar, Parent root, ComandaController comandaController) {
		this.mode = 2;
    	this.idproduto = id;
    	this.id = idComanda;
    	this.time = timeComanda;
    	this.parent = root;
    	this.controller = comandaController;
		txf_valorPagar.setText(Float.toString(valorPagar));
    	txt_caixa.setText(String.format(txt_caixa.getText(), "Pagamento"));
	}
}