package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.controlsfx.control.textfield.TextFields;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import system.Comanda;
import system.Comanda.TableViewComandaProduto;
import system.Produto;
import util.Stages;
import util.Valores;

public class ComandaController implements Initializable {	
	static int idComanda; 
	boolean editMode = false; //variavel pra ver se esta editando a comanda
	ObservableList<TableViewComandaProduto> list;
	
    @FXML
    private TextField txf_produto;
    
    @FXML
    private TextField txf_mesa;
    
    @FXML
    private TableView<TableViewComandaProduto> tv_produtos;

    @FXML
    private TableColumn<TableViewComandaProduto, Integer> tc_id;

    @FXML
    private TableColumn<TableViewComandaProduto, String> tc_nome;

    @FXML
    private TableColumn<TableViewComandaProduto, Integer> tc_qtde;

    @FXML
    private TableColumn<TableViewComandaProduto, Float> tc_valorIndividual;

    @FXML
    private TableColumn<TableViewComandaProduto, Float> tc_valorTotal;

    @FXML
    private TableColumn<TableViewComandaProduto, Float> tc_valorPago;

    @FXML
    private TableColumn<?, ?> tc_data;
    
    @FXML
    private CheckBox checkB_finalizar;
    
    @FXML
    private TextField txf_qtde;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txf_qtde.setText("1");
		try {
    		TextFields.bindAutoCompletion(txf_produto, Produto.getProdutoNome());
    		iniciaTableView();
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}		
	}
	
	@FXML
	void addProduto(ActionEvent event) throws Exception {
		int idProduto = Integer.parseInt(txf_produto.getText().substring(0, txf_produto.getText().indexOf(' ')));
		Comanda.addProduto(idComanda, idProduto, Integer.parseInt(txf_qtde.getText()));
		txf_produto.clear();
		txf_qtde.setText("1");
		reflesh();
    }
	
	private void iniciaTableView() throws Exception {
		tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		tc_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tc_qtde.setCellValueFactory(new PropertyValueFactory<>("qtde"));
		tc_valorIndividual.setCellValueFactory(new PropertyValueFactory<>("valorIndividual"));
		tc_valorPago.setCellValueFactory(new PropertyValueFactory<>("valorPago"));
		tc_valorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
		
		list = Comanda.getAllProduto(idComanda);
		tv_produtos.setItems(list);
	}
	
	@FXML
	void setNomeMesa(ActionEvent event) throws SQLException {
		Comanda.atualizarNomeMesa(txf_mesa.getText(), idComanda);
	}
	
	@FXML
	void btnCancelar(ActionEvent event) throws SQLException {
		if(editMode) {
			//não sei oq fazer
		} else {
			Comanda.delete(idComanda);
		}
	}
	
	@FXML
	void btnSalvar(ActionEvent event) throws Exception {
		Valores.getController().refresh(3);
		((Node) event.getSource()).getScene().getWindow().hide();
	}
	
	static void novaComanda(int id) {
		idComanda = id;
	}
	
	private void reflesh() throws Exception {
		list = Comanda.getAllProduto(idComanda);
		tv_produtos.setItems(list);
	}
}
