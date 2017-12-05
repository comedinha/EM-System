package controller;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Optional;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import system.Comanda;
import system.Comanda.TableViewComandaProduto;
import system.Funcionario;
import system.Pagamento;
import system.Produto;
import util.Stages;
import util.Valores;

public class ComandaController {
	private Timestamp comandaData;
	private FXMLLoader loader;

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
    private Button btn_desconto;

    @FXML
    private Button btn_pagamento;

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
    private Button btn_adicionar;

    @FXML
    private void act_addDesconto(ActionEvent event) {
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
	    	st.novoStage("Atribuir Desconto", "Pagamento");
	    	st.getStage().setOnCloseRequest(e -> {
	    		root.setDisable(false);
	    		refresh();
			});
	    	st.getLoader().<PagamentoController>getController().adicionaDesconto(Integer.valueOf(txf_comid.getText()), comandaData, valorPagar, root, loader.<ComandaController>getController());
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    private void act_addPagamento(ActionEvent event) {
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
	    	st.novoStage("Atribuir Pagamento", "Pagamento");
	    	st.getStage().setOnCloseRequest(e -> {
				root.setDisable(false);
				refresh();
			});
	    	st.getLoader().<PagamentoController>getController().adicionaPagamento(Integer.valueOf(txf_comid.getText()), comandaData, valorPagar, root, loader.<ComandaController>getController());
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    private void act_verPagamentos(ActionEvent event) {
    	try {
    		if (txf_comid.getText().isEmpty())
    			throw new Exception("Você não tem uma comanda criada.");
    		if (!chb_comid.isDisable())
    			throw new Exception("A comanda deve ter ao menos um produto.");

	    	Parent root = ((Node) event.getSource()).getScene().getRoot();
	    	root.setDisable(true);
	    	Stages st = new Stages();
	    	st.novoStage("Visualizar Pagamento", "ResumoPagamento");
	    	st.getStage().setOnCloseRequest(e -> {
				root.setDisable(false);
				refresh();
			});
	    	st.getLoader().<ResumoPagamentoController>getController().vizualizaPagamento(Integer.valueOf(txf_comid.getText()), comandaData, btn_pagamento.isDisable(), root, loader.<ComandaController>getController());
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

	@FXML
	private void act_addProduto(ActionEvent event) {
		try {
			if (txf_comid.getText().isEmpty()) {
				ResultSet result = Comanda.criaComanda(0, Valores.getUsuario().getId());
				this.comandaData = result.getTimestamp("data");
				txf_comid.setText(Integer.toString(result.getInt("comandaId")));
				chb_comid.setSelected(false);
				chb_comid.setDisable(true);
			} else {
				if (!chb_comid.isDisable()) {
					//Criar comanda com ID especificado
					ResultSet result = Comanda.criaComanda(Integer.parseInt(txf_comid.getText()), Valores.getUsuario().getId());
					this.comandaData = result.getTimestamp("data");
					chb_comid.setSelected(false);
					chb_comid.setDisable(true);
				}
			}
			
			int idProduto = Integer.parseInt(txf_produto.getText().substring(0, txf_produto.getText().indexOf(' ')));
			if(!Comanda.existeNaComanda(Integer.valueOf(txf_comid.getText()), comandaData, idProduto)) {
				if (Integer.parseInt(txf_qtde.getText()) < 0)
					throw new Exception("O valor não pode ser menor que 0!");
				Comanda.addProduto(Integer.valueOf(txf_comid.getText()), comandaData, idProduto, Integer.parseInt(txf_qtde.getText()));
				txf_produto.clear();
				txf_qtde.setText("1");
				refresh();
			} else {
				int qtde = Comanda.getQtdePrdoutoComanda(Integer.valueOf(txf_comid.getText()), comandaData, idProduto);
				if ((qtde + Integer.parseInt(txf_qtde.getText())) > 0)
					if (Integer.parseInt(txf_qtde.getText()) > 0)
						Comanda.updateQtde(Integer.valueOf(txf_comid.getText()), comandaData, idProduto, qtde + Integer.parseInt(txf_qtde.getText()));
					else {
						Alert alert = Stages.novoAviso("Você deseja remover o produto?");
						ButtonType buttonConfirm = new ButtonType("Continuar", ButtonData.OK_DONE);
						ButtonType buttonCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
						alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == buttonConfirm) {
							Comanda.updateQtde(Integer.valueOf(txf_comid.getText()), comandaData, idProduto, qtde + Integer.parseInt(txf_qtde.getText()));
						}
					}
				else {
					Alert alert = Stages.novoAviso("Você deseja remover o produto?");
					ButtonType buttonConfirm = new ButtonType("Continuar", ButtonData.OK_DONE);
					ButtonType buttonCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
					alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == buttonConfirm) {
						Comanda.removeProdutoComanda(Integer.valueOf(txf_comid.getText()), comandaData, idProduto);
					}
				}
				refresh();
			}
			Valores.getController().refresh(3);
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
    }

	@FXML
	private void act_cancelar(ActionEvent event) {
		fecharComanda();
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML
	private void act_salvar(ActionEvent event) {
		try {
			if (txf_comid.getText().isEmpty())
				throw new Exception("A comanda não tem um id.\nAdicione ao menos um produto.");
			if (txf_mesa.getText().isEmpty())
				throw new Exception("A mesa deve ser definida!");

			int idGarcom = Valores.getUsuario().getId();
			if (!cb_garcom.getValue().isEmpty())
				idGarcom = Integer.parseInt(cb_garcom.getValue().substring(0, cb_garcom.getValue().indexOf(' ')));

			if (!chb_finalizar.isSelected()) {
				if (!Comanda.updateComanda(Integer.valueOf(txf_comid.getText()), comandaData, txf_mesa.getText(), idGarcom, false)) {
					throw new Exception("Erro ao atualizar comanda!");
				}
			} else {
				float valorTotal = Float.valueOf(ta_valorTotal.getText());
	    		float valorPago = Float.valueOf(ta_valorPago.getText());
	    		float valorPagar = valorTotal - valorPago;
		    	if (valorPagar > 0)
		    		throw new Exception("A comanda deve estar paga.");

				if (!Comanda.updateComanda(Integer.valueOf(txf_comid.getText()), comandaData, txf_mesa.getText(), idGarcom, true)) {
					throw new Exception("Erro ao atualizar comanda!");
				}
			}

			Valores.getController().refresh(3);
	    	Valores.editCheck().remove("Comanda" + Integer.valueOf(txf_comid.getText()));
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
			
			txf_qtde.setText("1");
			chb_comid.selectedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {
	    		public void changed(ObservableValue<? extends Boolean> ov,
	                Boolean old_val, Boolean new_val) {
	    			txf_comid.setDisable(!new_val);
	            }
	        });
			chb_comid.setSelected(false);
			txf_comid.setDisable(true);
			if (!Valores.getUsuario().getGarcom())
				cb_garcom.getItems().add(Valores.getUsuario().getId() + " - " + Valores.getUsuario().getNome());
			cb_garcom.getItems().addAll(Funcionario.getFuncionariosNome());
			cb_garcom.setValue(Valores.getUsuario().getId() + " - " + Valores.getUsuario().getNome());
			txf_mesa.setText("-");
			
    		TextFields.bindAutoCompletion(txf_produto, Produto.getProdutoNome());
    		
    		iniciaTableProdutos();
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getLocalizedMessage(), "", true);
    	}
	}

	/**
	 * Inicia a tableView da inerface
	 * @throws Exception
	 */
	private void iniciaTableProdutos() throws Exception {
		tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		tc_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tc_qtde.setCellValueFactory(new PropertyValueFactory<>("qtde"));
		tc_valorIndividual.setCellValueFactory(new PropertyValueFactory<>("valorIndividual"));
		tc_valorPago.setCellValueFactory(new PropertyValueFactory<>("valorPago"));
		tc_valorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

		if (!txf_comid.getText().isEmpty()) {
			tv_produtos.setItems(Comanda.getAllProduto(Integer.valueOf(txf_comid.getText()), comandaData));
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
					if (!txf_produto.isDisable()) {
						float valorPagar = row.getItem().getValorTotal() - row.getItem().getValorPago();
						if (valorPagar <= 0)
							throw new Exception("O produto já está pago!");
						if (valorPagar > (Float.valueOf(ta_valorTotal.getText()) - Float.valueOf(ta_valorPago.getText())))
							throw new Exception("O valor necessário para pagamento é menor que o deste produto.");
	
						Parent root = row.getTableView().getScene().getRoot();
				    	root.setDisable(true);
				    	Stages st = new Stages();
				    	st.novoStage("Atribuir Pagamento", "Pagamento");
				    	st.getStage().setOnCloseRequest(e -> {
							root.setDisable(false);
							refresh();
						});
				    	st.getLoader().<PagamentoController>getController().adicionaProdutoPagamento(row.getItem().getId(), Integer.parseInt(txf_comid.getText()), comandaData, valorPagar, root, loader.<ComandaController>getController());
					} else
						throw new Exception("Essa comanda está finalizada, não é possível fazer esta ação.");
				} catch (Exception e) {
					Stages.novoAlerta(e.getMessage(), "", true);
				}
			});
			
			//Remover Um Produto
			removerUm.setOnAction((ActionEvent event) -> {
				try {
					if (!txf_produto.isDisable()) {
						Alert alert = Stages.novoAviso("Você deseja remover o produto?");
    					ButtonType buttonConfirm = new ButtonType("Continuar", ButtonData.OK_DONE);
    					ButtonType buttonCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
    					alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

    					Optional<ButtonType> result = alert.showAndWait();
    					if (result.get() == buttonConfirm) {
    						int qtde = Comanda.getQtdePrdoutoComanda(Integer.valueOf(txf_comid.getText()), comandaData, row.getItem().getId());
    						if (qtde - 1 > 0)
    							Comanda.updateQtde(Integer.valueOf(txf_comid.getText()), comandaData, row.getItem().getId(), qtde - 1);
    						else
    							Comanda.removeProdutoComanda(Integer.valueOf(txf_comid.getText()), comandaData, row.getItem().getId());
    					}
						refresh();
					} else
						throw new Exception("Essa comanda está finalizada, não é possível fazer esta ação.");
				} catch (Exception e) {
					Stages.novoAlerta(e.getMessage(), "", true);
				}
			});
			
			//Remover Tudo
			removerTudo.setOnAction((ActionEvent event) -> {
				try {
					if (!txf_produto.isDisable()) {
						Alert alert = Stages.novoAviso("Você deseja remover o produto?");
    					ButtonType buttonConfirm = new ButtonType("Continuar", ButtonData.OK_DONE);
    					ButtonType buttonCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
    					alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

    					Optional<ButtonType> result = alert.showAndWait();
    					if (result.get() == buttonConfirm) {
    						Comanda.removeProdutoComanda(Integer.valueOf(txf_comid.getText()), comandaData, row.getItem().getId());
    					}
						refresh();
					} else
						throw new Exception("Essa comanda está finalizada, não é possível fazer esta ação.");
				} catch (Exception e) {
					Stages.novoAlerta(e.getMessage(), "", true);
				}
			});

			rowMenu.getItems().addAll(pagarProduto, removerUm, removerTudo);
			row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu)null));
			return row;
		});
	}
	
	/**
	 * Calcula o valor total da a ser pago na comanda
	 * @throws Exception
	 */
	private void valorTotal() throws Exception {
		float somaValor = 0;
		
		for(TableViewComandaProduto lista : tv_produtos.getItems())
			somaValor += lista.getValorTotal();

		somaValor -= Pagamento.getAllValor(Integer.parseInt(txf_comid.getText()), comandaData, true);
		ta_valorTotal.setText(Float.toString(somaValor));
	}
	
	/**
	 * Calcula o valor que já foi pago
	 * @throws Exception
	 */
	private void valorPago() throws Exception {
		float somaValor = 0;
		
		somaValor += Pagamento.getAllValor(Integer.parseInt(txf_comid.getText()), comandaData, false);
		ta_valorPago.setText(Float.toString(somaValor));
	}
	
	/**
	 * Atualiza a interface
	 */
	public void refresh() {
		try {
			tv_produtos.setItems(Comanda.getAllProduto(Integer.valueOf(txf_comid.getText()), comandaData));
			valorTotal();
			valorPago();
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
	}

	public void iniciaComanda(FXMLLoader loader) {
		this.loader = loader;
	}
	
	/**
	 * Metodo chamado quando se entra na interface no modo edição
	 * @param id
	 * @param data
	 * @param loader
	 */
	public void editaComanda(int id, Timestamp data, FXMLLoader loader) {
		try {
			this.loader = loader;
			this.comandaData = data;
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

	/**
	 * Metodo chamado quando se entra na interface no modo visualização
	 * @param id
	 * @param data
	 * @param loader
	 */
	public void visualizaComanda(int id, Timestamp data, FXMLLoader loader) {
		try {
			this.loader = loader;
			this.comandaData = data;
			ResultSet result = Comanda.getComanda(id, data);
			if (result.next()) {
				txf_comid.setText(Integer.toString(id));
				chb_comid.setSelected(false);
				chb_comid.setDisable(true);
				txf_mesa.setText(result.getString("mesa"));
				txf_mesa.setDisable(true);
				if (Funcionario.getGarcomById(result.getInt("funcionarioId")))
					cb_garcom.setValue(result.getInt("funcionarioId") + " - " + Funcionario.getNomebyId(result.getInt("funcionarioId")));
				cb_garcom.setDisable(true);
				btn_adicionar.setDisable(true);
				btn_desconto.setDisable(true);
				btn_pagamento.setDisable(true);
				txf_produto.setDisable(true);
				txf_qtde.setDisable(true);
				chb_finalizar.setDisable(true);
				refresh();
			} else
				throw new Exception("Essa comanda não existe!");
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
	}

	/**
	 * Metodo chamado para fechar a janela da comanda de maneira segura
	 */
	public void fecharComanda() {
		try {
			Valores.getController().refresh(3);
	    	Valores.editCheck().remove("Comanda" + Integer.valueOf(txf_comid.getText()));
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
	}
}