package controller;

import org.controlsfx.control.textfield.TextFields;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import system.Comanda;
import system.Comanda.TableViewComandaProduto;
import system.Produto;
import util.Stages;
import util.Valores;

public class ComandaController {
	boolean editMode = false;
	
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
    private CheckBox cb_comid;

    @FXML
    private TextField tf_comid;
    
    @FXML
    private TextField txf_qtde;
    
    @FXML
    private TextArea ta_valorTotal;

    @FXML
    private TextArea ta_valorPago;
    
    @FXML
	public void initialize() {
		try {
			if (Valores.getConnection() == null || Valores.getUsuario() == null || Valores.getController() == null)
	    		Platform.exit();
	
			txf_qtde.setText("1");
			cb_comid.selectedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {
	    		public void changed(ObservableValue<? extends Boolean> ov,
	                Boolean old_val, Boolean new_val) {
	    			tf_comid.setDisable(!new_val);
	            }
	        });
			cb_comid.setSelected(false);
			tf_comid.setDisable(true);

    		TextFields.bindAutoCompletion(txf_produto, Produto.getProdutoNome());
    		iniciaTableView();
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
	}

    @FXML
    void act_addDesconto(ActionEvent event) {
    	try {
    		if (tf_comid.getText().isEmpty())
    			throw new Exception("Você não tem uma comanda criada.");
    		if (!cb_comid.isDisable())
    			throw new Exception("A comanda deve ter ao menos um produto.");

    		float valorTotal = Float.valueOf(ta_valorTotal.getText());
    		float valorPago = Float.valueOf(ta_valorPago.getText());
    		float valorPagar = valorTotal - valorPago;
	    	if (valorPagar < 0)
	    		throw new Exception("Produto pago.");

	    	Parent root = ((Node) event.getSource()).getScene().getRoot();
	    	root.setDisable(true);
	    	Stages st = new Stages();
	    	FXMLLoader pagamentoLoader = st.novoStage("Atribuir Desconto", "Pagamento", root);
	    	pagamentoLoader.<PagamentoController>getController().adicionaDesconto(valorPagar, root);
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    void act_addPagamento(ActionEvent event) {
    	try {
    		if (tf_comid.getText().isEmpty())
    			throw new Exception("Você não tem uma comanda criada.");
    		if (!cb_comid.isDisable())
    			throw new Exception("A comanda deve ter ao menos um produto.");

    		float valorTotal = Float.valueOf(ta_valorTotal.getText());
    		float valorPago = Float.valueOf(ta_valorPago.getText());
    		float valorPagar = valorTotal - valorPago;
	    	if (valorPagar < 0)
	    		throw new Exception("Produto pago.");

	    	Parent root = ((Node) event.getSource()).getScene().getRoot();
	    	root.setDisable(true);
	    	Stages st = new Stages();
	    	FXMLLoader pagamentoLoader = st.novoStage("Atribuir Pagamento", "Pagamento", root);
	    	pagamentoLoader.<PagamentoController>getController().adicionaPagamento(valorPagar, root);
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }
	
	@FXML
	private void addProduto(ActionEvent event) {
		try {
			if (tf_comid.getText().isEmpty()) {
				tf_comid.setText(Integer.toString(Comanda.criaComanda()));
				cb_comid.setSelected(false);
				cb_comid.setDisable(true);
			} else {
				if (!cb_comid.isDisable()) {
					//Criar comanda com ID especificado
					cb_comid.setSelected(false);
					cb_comid.setDisable(true);
				}
			}
			
			int idProduto = Integer.parseInt(txf_produto.getText().substring(0, txf_produto.getText().indexOf(' ')));
			if(!Comanda.existeNaComanda(idProduto, Integer.valueOf(tf_comid.getText()))) {
				Comanda.addProduto(Integer.valueOf(tf_comid.getText()), idProduto, Integer.parseInt(txf_qtde.getText()));
				txf_produto.clear();
				txf_qtde.setText("1");
				refresh();
			} else {
				Comanda.updateQtde(idProduto, Integer.valueOf(tf_comid.getText()), Integer.parseInt(txf_qtde.getText()));
				refresh();
			}
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
		
		
    }
	
	@FXML
	private void btnCancelar(ActionEvent event) {
		try {
			if(editMode) {
				//não sei oq fazer
			} else {

			}
			((Node) event.getSource()).getScene().getWindow().hide();
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
	}
	
	@FXML
	void btnSalvar(ActionEvent event) {
		try {
			if (tf_comid.getText().isEmpty())
				throw new Exception("A comanda não tem um id.\nAdicione ao menos um produto.");
	
			Valores.getController().refresh(3);
			((Node) event.getSource()).getScene().getWindow().hide();
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
	}
	
	private void iniciaTableView() throws Exception {
		tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		tc_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tc_qtde.setCellValueFactory(new PropertyValueFactory<>("qtde"));
		tc_valorIndividual.setCellValueFactory(new PropertyValueFactory<>("valorIndividual"));
		tc_valorPago.setCellValueFactory(new PropertyValueFactory<>("valorPago"));
		tc_valorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

		if (!tf_comid.getText().isEmpty()) {
			tv_produtos.setItems(Comanda.getAllProduto(Integer.valueOf(tf_comid.getText())));
			cb_comid.setSelected(false);
			cb_comid.setDisable(true);
		}

		tv_produtos.setRowFactory((TableView<TableViewComandaProduto> tableProdutoComanda) -> {
			final TableRow<TableViewComandaProduto> row = new TableRow<>();
			final ContextMenu rowMenu = new ContextMenu();
			MenuItem pagarProduto = new MenuItem("Pagar produto");
			MenuItem removerUm = new MenuItem("Remover um");
			MenuItem removerTudo = new MenuItem("Remover tudo");

			//Atualizar Produtos
			pagarProduto.setOnAction((ActionEvent event) -> {
				try {
					float valorPago = row.getItem().getValorPago();
					float valorTotal = row.getItem().getValorTotal();
					float valorPagar = valorTotal - valorPago;

					Parent root = row.getTableView().getScene().getRoot();
			    	root.setDisable(true);
			    	Stages st = new Stages();
			    	FXMLLoader pagamentoLoader = st.novoStage("Atribuir Pagamento", "Pagamento", root);
			    	pagamentoLoader.<PagamentoController>getController().adicionaProdutoPagamento(valorPagar, root);
				} catch (Exception e) {
					Stages.novoAlerta(e.getMessage(), "", true);
				}
			});

			//Remover Um Produto
			removerUm.setOnAction((ActionEvent event) -> {
				
			});

			//Remover Tudo
			removerTudo.setOnAction((ActionEvent event) -> {
				
			});

			rowMenu.getItems().addAll(pagarProduto, removerUm, removerTudo);
			row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu)null));
			return row;
		});
	}
	
	private void valorTotal() {
		float somaValor = 0;
		
		for(TableViewComandaProduto lista : tv_produtos.getItems())
			somaValor += lista.getValorTotal();
		
		ta_valorTotal.setText(Float.toString(somaValor));
	}
	
	private void valorPago() {
		float somaValor = 0;
		
		for(TableViewComandaProduto lista : tv_produtos.getItems())
			somaValor += lista.getValorPago();
		
		ta_valorPago.setText(Float.toString(somaValor));
	}
	
	private void refresh() throws Exception {
		tv_produtos.setItems(Comanda.getAllProduto(Integer.valueOf(tf_comid.getText())));
		valorTotal();
		valorPago();
	}

	public void editaComanda(int id) {
		try {
			tf_comid.setText(Integer.toString(id));
			cb_comid.setSelected(false);
			cb_comid.setDisable(true);
			refresh();
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
	}
}