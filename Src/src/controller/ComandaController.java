package controller;

import java.sql.ResultSet;
import java.sql.Timestamp;

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
import javafx.scene.control.ChoiceBox;
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
import system.Funcionario;
import system.Produto;
import util.Stages;
import util.Valores;

public class ComandaController {
	private Timestamp comandatime;

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
    private TableColumn<TableViewComandaProduto, String> tc_data;
    
    @FXML
    private TextArea ta_valorTotal;

    @FXML
    private TextArea ta_valorPago;

    @FXML
    private TextField txf_mesa;

    @FXML
    private ChoiceBox<String> cb_garcom;

    @FXML
    private CheckBox chb_finalizar;

    @FXML
    private CheckBox chb_comid;

    @FXML
    private TextField txf_comid;

    @FXML
    private TextField txf_produto;

    @FXML
    private TextField txf_qtde;
    
    @FXML
	public void initialize() {
		try {
			if (Valores.getConnection() == null || Valores.getUsuario() == null || Valores.getController() == null)
	    		Platform.exit();
			
			txf_qtde.setText("1");
			chb_comid.selectedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {
	    		public void changed(ObservableValue<? extends Boolean> ov,
	                Boolean old_val, Boolean new_val) {
	    			txf_comid.setDisable(!new_val);
	            }
	        });
			chb_comid.setSelected(false);
			txf_comid.setDisable(true);
			cb_garcom.getItems().addAll(Funcionario.getFuncionariosNome());
			txf_mesa.setText("-");
			
    		TextFields.bindAutoCompletion(txf_produto, Produto.getProdutoNome());
    		
    		iniciaTableView();
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getLocalizedMessage(), "", true);
    	}
	}

    @FXML
    void act_addDesconto(ActionEvent event) {
    	try {
    		if (txf_comid.getText().isEmpty())
    			throw new Exception("Você não tem uma comanda criada.");
    		if (!chb_comid.isDisable())
    			throw new Exception("A comanda deve ter ao menos um produto.");

    		float valorTotal = Float.valueOf(ta_valorTotal.getText());
    		float valorPago = Float.valueOf(ta_valorPago.getText());
    		float valorPagar = valorTotal - valorPago;
	    	if (valorPagar <= 0)
	    		throw new Exception("Produto pago.");

	    	Parent root = ((Node) event.getSource()).getScene().getRoot();
	    	root.setDisable(true);
	    	Stages st = new Stages();
	    	FXMLLoader pagamentoLoader = st.novoStage("Atribuir Desconto", "Pagamento", root);
	    	pagamentoLoader.<PagamentoController>getController().adicionaDesconto(Integer.valueOf(txf_comid.getText()), comandatime, valorPagar, root);
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    void act_addPagamento(ActionEvent event) {
    	try {
    		if (txf_comid.getText().isEmpty())
    			throw new Exception("Você não tem uma comanda criada.");
    		if (!chb_comid.isDisable())
    			throw new Exception("A comanda deve ter ao menos um produto.");

    		float valorTotal = Float.valueOf(ta_valorTotal.getText());
    		float valorPago = Float.valueOf(ta_valorPago.getText());
    		float valorPagar = valorTotal - valorPago;
	    	if (valorPagar <= 0)
	    		throw new Exception("Produto pago.");

	    	Parent root = ((Node) event.getSource()).getScene().getRoot();
	    	root.setDisable(true);
	    	Stages st = new Stages();
	    	FXMLLoader pagamentoLoader = st.novoStage("Atribuir Pagamento", "Pagamento", root);
	    	pagamentoLoader.<PagamentoController>getController().adicionaPagamento(Integer.valueOf(txf_comid.getText()), comandatime, valorPagar, root);
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

	@FXML
	private void addProduto(ActionEvent event) {
		try {
			if (txf_comid.getText().isEmpty()) {
				ResultSet result = Comanda.criaComanda(0, Valores.getUsuario().getId());
				this.comandatime = result.getTimestamp("data");
				txf_comid.setText(Integer.toString(result.getInt("comandaId")));
				chb_comid.setSelected(false);
				chb_comid.setDisable(true);
			} else {
				if (!chb_comid.isDisable()) {
					//Criar comanda com ID especificado
					ResultSet result = Comanda.criaComanda(Integer.parseInt(txf_comid.getText()), Valores.getUsuario().getId());
					this.comandatime = result.getTimestamp("data");
					chb_comid.setSelected(false);
					chb_comid.setDisable(true);
				}
			}
			
			int idProduto = Integer.parseInt(txf_produto.getText().substring(0, txf_produto.getText().indexOf(' ')));
			if(!Comanda.existeNaComanda(idProduto, Integer.valueOf(txf_comid.getText()))) {
				Comanda.addProduto(Integer.valueOf(txf_comid.getText()), idProduto, Integer.parseInt(txf_qtde.getText()));
				txf_produto.clear();
				txf_qtde.setText("1");
				refresh();
			} else {
				Comanda.updateQtde(idProduto, Integer.valueOf(txf_comid.getText()), Integer.parseInt(txf_qtde.getText()));
				refresh();
			}
			Valores.getController().refresh(3);
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
    }

	@FXML
	private void btnCancelar(ActionEvent event) {
		try {
			Valores.getController().refresh(3);
			((Node) event.getSource()).getScene().getWindow().hide();
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
	}

	@FXML
	void btnSalvar(ActionEvent event) {
		try {
			if (txf_comid.getText().isEmpty())
				throw new Exception("A comanda não tem um id.\nAdicione ao menos um produto.");
			if (txf_mesa.getText().isEmpty())
				throw new Exception("A mesa deve ser definida!");

			int idGarcom = Valores.getUsuario().getId();
			if (!cb_garcom.getValue().isEmpty())
				idGarcom = Integer.parseInt(cb_garcom.getValue().substring(0, cb_garcom.getValue().indexOf(' ')));

			if (!chb_finalizar.isSelected()) {
				if (!Comanda.updateComanda(Integer.valueOf(txf_comid.getText()), txf_mesa.getText(), idGarcom, false)) {
					throw new Exception("Erro ao atualizar comanda!");
				}
			} else {
				float valorTotal = Float.valueOf(ta_valorTotal.getText());
	    		float valorPago = Float.valueOf(ta_valorPago.getText());
	    		float valorPagar = valorTotal - valorPago;
		    	if (valorPagar > 0)
		    		throw new Exception("A comanda deve estar paga.");

				if (!Comanda.updateComanda(Integer.valueOf(txf_comid.getText()), txf_mesa.getText(), idGarcom, true)) {
					throw new Exception("Erro ao atualizar comanda!");
				}
			}

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

		if (!txf_comid.getText().isEmpty()) {
			tv_produtos.setItems(Comanda.getAllProduto(Integer.valueOf(txf_comid.getText())));
			chb_comid.setSelected(false);
			chb_comid.setDisable(true);
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
					float valorPagar = row.getItem().getValorTotal() - row.getItem().getValorPago();

					Parent root = row.getTableView().getScene().getRoot();
			    	root.setDisable(true);
			    	Stages st = new Stages();
			    	FXMLLoader pagamentoLoader = st.novoStage("Atribuir Pagamento", "Pagamento", root);
			    	pagamentoLoader.<PagamentoController>getController().adicionaProdutoPagamento(row.getItem().getId(), valorPagar, root);
				} catch (Exception e) {
					Stages.novoAlerta(e.getMessage(), "", true);
				}
			});
			
			//Remover Um Produto
			removerUm.setOnAction((ActionEvent event) -> {
				try {
					int qtde = Comanda.getQtdePrdoutoComanda(row.getItem().getId(), Integer.valueOf(txf_comid.getText()));
					Comanda.updateQtde(row.getItem().getId(), Integer.valueOf(txf_comid.getText()), qtde-1);
					refresh();
				} catch (Exception e) {
					Stages.novoAlerta(e.getMessage(), "", true);
				}
			});
			
			//Remover Tudo
			removerTudo.setOnAction((ActionEvent event) -> {
				try {
					Comanda.removeProdutoComanda(row.getItem().getId(), Integer.valueOf(txf_comid.getText()));
					refresh();
				} catch (Exception e) {
					Stages.novoAlerta(e.getMessage(), "", true);
				}
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

	private void valorPago() throws Exception {
		float somaValor = 0;

		for(TableViewComandaProduto lista : tv_produtos.getItems())
			somaValor += lista.getValorPago();
		
		somaValor += Comanda.getValorPagoComanda(Integer.parseInt((txf_comid.getText())));
		ta_valorPago.setText(Float.toString(somaValor));
	}

	private void refresh() throws Exception {
		tv_produtos.setItems(Comanda.getAllProduto(Integer.valueOf(txf_comid.getText())));
		valorTotal();
		valorPago();
	}

	public void editaComanda(int id, Timestamp data) {
		try {
			this.comandatime = data;
			ResultSet result = Comanda.getComanda(id, data);
			if (result.next()) {
				txf_comid.setText(Integer.toString(id));
				chb_comid.setSelected(false);
				chb_comid.setDisable(true);
				txf_mesa.setText(result.getString("mesa"));
				if (Funcionario.getGarcomById(result.getInt("funcionarioId")))
					cb_garcom.setValue(result.getInt("funcionarioId") + " - " + Funcionario.getNomebyId(result.getInt("funcionarioId")));
				refresh();
			} else
				throw new Exception("Essa comanda não existe!");
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
	}
}