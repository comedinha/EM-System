package controller;

import java.sql.Date;
import java.sql.SQLException;
import org.controlsfx.control.PropertySheet;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import system.Produto;
import system.Comanda.TableViewComandaLista;
import system.Comanda.TableViewComandaPaga;
import system.Produto.TableViewProduto;
import util.FuncionarioEnum;
import util.MeioPagamentoEnum;
import util.Stages;
import util.Valores;
import system.Comanda;
import system.Configuracao;
import system.Funcionario;
import system.Funcionario.TableViewFuncionario;
import java.time.LocalDate;
import java.util.Optional;

public class MenuController {
	@FXML
    private TextArea ta_inicio;

	@FXML
    private Tab ab_financeiro;

	@FXML
    private DatePicker dt_finBuscaDe;

    @FXML
    private DatePicker dt_finBuscaAte;

    @FXML
    private TableView<TableViewComandaPaga> tv_financ;

    @FXML
    private TableColumn<TableViewComandaPaga, Integer> tc_financId;

    @FXML
    private TableColumn<TableViewComandaPaga, String> tc_financFunc;

    @FXML
    private TableColumn<TableViewComandaPaga, String> tc_financData;

    @FXML
    private TableColumn<TableViewComandaPaga, String> tc_financPerm;

    @FXML
    private TableColumn<TableViewComandaPaga, Float> tc_financVlr;

    @FXML
    private TextField txf_comandaBusca;

    @FXML
    private TableView<TableViewComandaLista> tv_comand;

    @FXML
    private TableColumn<TableViewComandaLista, Integer> tc_comandId;

    @FXML
    private TableColumn<TableViewComandaLista, String> tc_comandFunc;
    
    @FXML
    private TableColumn<TableViewComandaLista, String> tc_comandMesa;

    @FXML
    private TableColumn<TableViewComandaLista, String> tc_comandData;

    @FXML
    private TableColumn<TableViewComandaLista, Float> tc_comandVlr;

    @FXML
    private Tab ab_produtos;

    @FXML
    private TextField txf_prodBusca;

    @FXML
    private TableView<TableViewProduto> tv_prod;

    @FXML
    private TableColumn<TableViewProduto, Integer> tc_prodId;

    @FXML
    private TableColumn<TableViewProduto, String> tc_prodNome;

    @FXML
    private TableColumn<TableViewProduto, Float> tc_prodVlr;

    @FXML
    private Tab ab_funcionarios;

    @FXML
    private TextField txf_funcBusca;

    @FXML
    private TableView<TableViewFuncionario> tv_func;

    @FXML
    private TableColumn<TableViewFuncionario, Integer> tc_funcId;

    @FXML
    private TableColumn<TableViewFuncionario, String> tc_funcNome;

    @FXML
    private TableColumn<TableViewFuncionario, String> tc_funcLogin;

    @FXML
    private TableColumn<TableViewFuncionario, String> tc_funcCargo;

    @FXML
    private TableColumn<TableViewFuncionario, Boolean> tc_funcGarcom;

    @FXML
    private PropertySheet ps_configuracoes;

    @FXML
    private void act_BuscaFinanceiro(ActionEvent event) {
    	try {
	    	if (Date.valueOf(dt_finBuscaDe.getValue()).before(Date.valueOf(dt_finBuscaAte.getValue()))) {
		    	tv_financ.getItems().clear();
		    	tv_financ.setItems(Comanda.getAllComandaPaga(Date.valueOf(dt_finBuscaDe.getValue()), Date.valueOf(dt_finBuscaAte.getValue())));
	    	} else
	    		throw new Exception("A data DE deve ser menor que a data ATÉ!");
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    private void btn_addComanda(ActionEvent event) throws SQLException {
    	Stages st = new Stages();
    	st.novoStage("Adicionar Comanda", "Comanda");
    	st.getLoader().<ComandaController>getController().iniciaComanda(st.getLoader());
    }

    @FXML
    private void btn_addFunc(ActionEvent event) {
    	Stages st = new Stages();
    	st.novoStage("Adicionar Funcionário", "Funcionario");
    }

    @FXML
    private void btn_addProd(ActionEvent event) {
    	Stages st = new Stages();
    	st.novoStage("Adicionar Produto", "Produto");
    }

    @FXML
    private void btn_desconecta(ActionEvent event) {
	    ((Node) event.getSource()).getScene().getWindow().hide();
	    Valores.setController(null);
	    Valores.setFuncionario(null);
	    Configuracao.configDataClean();
	    Stages st = new Stages();
	    st.novoStage("EMSystem Login", "Login");
    }

    @FXML
    private void btn_salvaConfig(ActionEvent event) {
    	for (String key : Configuracao.configDataMapKeySet())
    		System.out.println(Configuracao.configDataGetValue(key));
    }

    @FXML
    private void initialize() {
    	try {
	    	if (Valores.getConnection() == null || Valores.getUsuario() == null)
	    		Platform.exit();
	
	    	iniciaInicio();
	
	    	if (Valores.getUsuario().getFuncao() == 1) {
	    		iniciaFinanc();
	    	} else {
	    		ab_financeiro.setDisable(true);
	    	}
	
	    	iniciaComanda();
	
	    	if (Valores.getUsuario().getFuncao() == 1) {
		    	iniciaProduto();
		    	iniciaFuncionario();
	    	} else {
	    		ab_funcionarios.setDisable(true);
	    		ab_produtos.setDisable(true);
	    	}
	
	    	iniciaConfig();
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }
    
    /**
     * Inicia a aba Inicio
     * @throws Exception
     */
    private void iniciaInicio() throws Exception {
    	ta_inicio.setText(String.format(ta_inicio.getText(), Valores.getUsuario().getNome(), FuncionarioEnum.get(Valores.getUsuario().getFuncao()), null));
    }
    
    /**
     * Inicia a interface na aba Finaças
     */
    private void iniciaFinanc() {
    	try {
	    	dt_finBuscaDe.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1));
	    	dt_finBuscaAte.setValue(LocalDate.now());
	
	    	tc_financId.setCellValueFactory(new PropertyValueFactory<>("id"));
	    	tc_financFunc.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
	    	tc_financData.setCellValueFactory(new PropertyValueFactory<>("data"));
	    	tc_financPerm.setCellValueFactory(new PropertyValueFactory<>("permanencia"));
	    	tc_financVlr.setCellValueFactory(new PropertyValueFactory<>("valor"));

			tv_financ.setItems(Comanda.getAllComandaPaga(Date.valueOf(dt_finBuscaDe.getValue()), Date.valueOf(dt_finBuscaAte.getValue())));
			tv_financ.setRowFactory((TableView<TableViewComandaPaga> tv_comandaFinanc) -> {
    			final TableRow<TableViewComandaPaga> row = new TableRow<>();
    			final ContextMenu rowMenu = new ContextMenu();
    			MenuItem verComanda = new MenuItem("Visualizar Comanda");

    			//Visualizar Comandas
    			verComanda.setOnAction((ActionEvent event) -> {
    				try {
    					Stages st = new Stages();
        		    	st.novoStage("Visualizar Comanda", "Comanda");
        		    	st.getLoader().<ComandaController>getController().visualizaComanda(row.getItem().getId(), row.getItem().getTimeStamp(), st.getLoader());
    				} catch (Exception e) {
    					Stages.novoAlerta(e.getMessage(), "", true);
    				}
    			});

    			rowMenu.getItems().addAll(verComanda);
    			row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu)null));
    			return row;
    		});
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }
    
    /**
     * Inicia interface na aba Comanda
     */
    private void iniciaComanda() {
    	try {
	    	tc_comandId.setCellValueFactory(new PropertyValueFactory<>("id"));
	    	tc_comandFunc.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
	    	tc_comandMesa.setCellValueFactory(new PropertyValueFactory<>("mesa"));
	    	tc_comandData.setCellValueFactory(new PropertyValueFactory<>("data"));
	    	tc_comandVlr.setCellValueFactory(new PropertyValueFactory<>("valor"));
    	
			tv_comand.setItems(Comanda.getAllComanda());
			tv_comand.setRowFactory((TableView<TableViewComandaLista> tv_comanda) -> {
    			final TableRow<TableViewComandaLista> row = new TableRow<>();
    			final ContextMenu rowMenu = new ContextMenu();
    			MenuItem editItem = new MenuItem("Editar");

    			//Atualizar Comanda
    			editItem.setOnAction((ActionEvent event) -> {
    				try {
    					if (Valores.editCheck().contains(("Comanda" + row.getItem().getId())))
    						throw new Exception("Esta comanda está sendo editada!");

    					Valores.editCheck().add("Comanda" + row.getItem().getId());
    					Stages st = new Stages();
        		    	st.novoStage("Editar Comanda", "Comanda");
        		    	st.getStage().setOnCloseRequest(e -> {
        		    		Valores.editCheck().remove("Comanda" + row.getItem().getId());
						});
        		    	st.getLoader().<ComandaController>getController().editaComanda(row.getItem().getId(), row.getItem().getTimeStamp(), st.getLoader());
    				} catch (Exception e) {
    					Stages.novoAlerta(e.getMessage(), "", true);
    				}
    			});

    			rowMenu.getItems().addAll(editItem);
    			row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu)null));
    			return row;
    		});

			//Busca
    		txf_comandaBusca.textProperty().addListener(new InvalidationListener() {
    			@Override
    			public void invalidated(Observable observable) {
    				try {
    					if(txf_comandaBusca.textProperty().get().isEmpty()) {
    						tv_comand.setItems(Comanda.getAllComanda());
    						return;
    					}

    					ObservableList<TableViewComandaLista> tableItems = FXCollections.observableArrayList();
    					ObservableList<TableColumn<TableViewComandaLista, ?>> cols = tv_comand.getColumns();
    					for (int i = 0; i < Comanda.getAllComanda().size(); i++) {
    						for (int j = 0; j < cols.size(); j++) {
    							TableColumn<TableViewComandaLista, ?> col = cols.get(j);
    							String cellValue = col.getCellData(Comanda.getAllComanda().get(i)).toString();
    							cellValue = cellValue.toLowerCase();
    							if(cellValue.contains(txf_comandaBusca.textProperty().get().toLowerCase())) {
    								tableItems.add(Comanda.getAllComanda().get(i));
    								break;
    							}
    						}
    					}
    					tv_comand.setItems(tableItems);
    				} catch (Exception e) {
    					Stages.novoAlerta(e.getMessage(), "", true);
    				}
    			}
    		});
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }
    
    /**
     * Inicia interface na aba Produto
     */
    private void iniciaProduto() {
    	try {
	    	tc_prodId.setCellValueFactory(new PropertyValueFactory<>("id"));
	    	tc_prodNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	    	tc_prodVlr.setCellValueFactory(new PropertyValueFactory<>("valor"));

    		tv_prod.setItems(Produto.getAllProduto());
    		
    		tv_prod.setRowFactory((TableView<TableViewProduto> tv_produto) -> {
    			final TableRow<TableViewProduto> row = new TableRow<>();
    			final ContextMenu rowMenu = new ContextMenu();
    			MenuItem editItem = new MenuItem("Editar");
    			MenuItem removeItem = new MenuItem("Deletar");

    			//Atualizar Produtos
    			editItem.setOnAction((ActionEvent event) -> {
    				try {
    					if (Valores.editCheck().contains(("Produto" + row.getItem().getId())))
    						throw new Exception("Este produto está sendo editado!");

    					Valores.editCheck().add("Produto" + row.getItem().getId());
    					Stages st = new Stages();
        		    	st.novoStage("Edita Produto", "Produto");
        		    	st.getStage().setOnCloseRequest(e -> {
        		    		Valores.editCheck().remove("Produto" + row.getItem().getId());
						});
        		    	st.getLoader().<ProdutoController>getController().editaProduto(row.getItem().getId(), row.getItem().getNome(), row.getItem().getValor());
    				} catch (Exception e) {
    					Stages.novoAlerta(e.getMessage(), "", true);
    				}
    			});

    			//Remover Produtos
    			removeItem.setOnAction((ActionEvent event) -> {
    				try {
    					if (Valores.editCheck().contains(("Produto" + row.getItem().getId())))
    						throw new Exception("Este produto já está sendo editado!");

    					Alert alert = Stages.novoAviso("Você deseja remover o produto?");
    					ButtonType buttonConfirm = new ButtonType("Continuar", ButtonData.OK_DONE);
    					ButtonType buttonCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
    					alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

    					Optional<ButtonType> result = alert.showAndWait();
    					if (result.get() == buttonConfirm) {
    						if (!Produto.delete(row.getItem().getId())) {
    							refresh(1);
    	    					throw new Exception("Erro ao remover produto!");
    	    				}
    					}
	    				refresh(1);
    				} catch (Exception e) {
    					Stages.novoAlerta(e.getMessage(), "", true);
    				}
    			});

    			rowMenu.getItems().addAll(editItem, removeItem);
    			row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu)null));
    			return row;
    		});

			//Busca
    		txf_prodBusca.textProperty().addListener(new InvalidationListener() {
    			@Override
    			public void invalidated(Observable observable) {
    				try {
    					if(txf_prodBusca.textProperty().get().isEmpty()) {
    						tv_prod.setItems(Produto.getAllProduto());
    						return;
    					}

    					ObservableList<TableViewProduto> tableItems = FXCollections.observableArrayList();
    					ObservableList<TableColumn<TableViewProduto, ?>> cols = tv_prod.getColumns();
    					for (int i = 0; i < Produto.getAllProduto().size(); i++) {
    						for (int j = 0; j < cols.size(); j++) {
    							TableColumn<TableViewProduto, ?> col = cols.get(j);
    							String cellValue = col.getCellData(Produto.getAllProduto().get(i)).toString();
    							cellValue = cellValue.toLowerCase();
    							if(cellValue.contains(txf_prodBusca.textProperty().get().toLowerCase())) {
    								tableItems.add(Produto.getAllProduto().get(i));
    								break;
    							}
    						}
    					}
    					tv_prod.setItems(tableItems);
    				} catch (Exception e) {
    					Stages.novoAlerta(e.getMessage(), "", true);
    				}
    			}
    		});
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }
    
    /**
     * Inicia interface na aba Funcionario
     */
    private void iniciaFuncionario() {
    	try {
	    	tc_funcId.setCellValueFactory(new PropertyValueFactory<>("id"));
	        tc_funcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	        tc_funcLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
	        tc_funcCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
	        tc_funcGarcom.setCellValueFactory(new PropertyValueFactory<>("garcom"));

        	tv_func.setItems(Funcionario.getAllFuncionario());
        	tv_func.setRowFactory((TableView<TableViewFuncionario> tv_funcionario) -> {
        		final TableRow<TableViewFuncionario> row = new TableRow<>();
        		final ContextMenu rowMenu = new ContextMenu();
        		MenuItem editFuncionario = new MenuItem("Editar");
        		MenuItem removeFuncionario = new MenuItem("Deletar");

        		//Editar Funcionários
        		editFuncionario.setOnAction((ActionEvent event) -> {
        			try {
        				if (Valores.editCheck().contains(("Funcionario" + row.getItem().getId())))
    						throw new Exception("Este funcionário está sendo editado!");

        				Valores.editCheck().add("Funcionario" + row.getItem().getId());
        				Stages st = new Stages();
        		    	st.novoStage("Edita Funcionário", "Funcionario");
        		    	st.getStage().setOnCloseRequest(e -> {
        		    		Valores.editCheck().remove("Funcionario" + row.getItem().getId());
						});
        		    	st.getLoader().<FuncionarioController>getController().editaFuncionario(row.getItem().getId(), row.getItem().getNome(), row.getItem().getLogin(), row.getItem().getCargo(), row.getItem().getGarcom());
        			} catch (Exception e) {
        				Stages.novoAlerta(e.getMessage(), "", true);
        			}
        		});

        		//Remover Funcionários
        		removeFuncionario.setOnAction((ActionEvent event) -> {
        			try {
        				if (Valores.editCheck().contains(("Funcionario" + row.getItem().getId())))
    						throw new Exception("Este funcionário já está sendo editado!");

        				Alert alert = Stages.novoAviso("Você deseja remover o produto?");
    					ButtonType buttonConfirm = new ButtonType("Continuar", ButtonData.OK_DONE);
    					ButtonType buttonCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
    					alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

    					Optional<ButtonType> result = alert.showAndWait();
    					if (result.get() == buttonConfirm) {
    						if (!Funcionario.removeFuncionario(row.getItem().getId(), row.getItem().getCargo())) {
            					refresh(2);
            					throw new Exception("Erro ao remover funcionário");
            				}
    					}
        				refresh(2);
        			} catch (Exception e) {
        				Stages.novoAlerta(e.getMessage(), "", true);
        			}
        		});
        		rowMenu.getItems().addAll(editFuncionario, removeFuncionario);
        		row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu)null));
        		return row;
        	});

        	//Busca
        	txf_funcBusca.textProperty().addListener(new InvalidationListener() {
        		@Override
        		public void invalidated(Observable observable) {
        			try {
        				if(txf_funcBusca.textProperty().get().isEmpty()) {
        					tv_func.setItems(Funcionario.getAllFuncionario());
        					return;
        				}

        				ObservableList<TableViewFuncionario> tableItems = FXCollections.observableArrayList();
        				ObservableList<TableColumn<TableViewFuncionario, ?>> cols = tv_func.getColumns();
        				for (int i = 0; i < Funcionario.getAllFuncionario().size(); i++) {
        					for (int j = 0; j < cols.size(); j++) {
        						TableColumn<TableViewFuncionario, ?> col = cols.get(j);
        						String cellValue = col.getCellData(Funcionario.getAllFuncionario().get(i)).toString();
        						cellValue = cellValue.toLowerCase();
        						if(cellValue.contains(txf_funcBusca.textProperty().get().toLowerCase())) {
        							tableItems.add(Funcionario.getAllFuncionario().get(i));
        							break;
        						}
        					}
        				}
        				tv_func.setItems(tableItems);
        			} catch (Exception e) {
        				Stages.novoAlerta(e.getMessage(), "", true);
        			}
        		}
        	});
        } catch (Exception e) {
        	Stages.novoAlerta(e.getMessage(), "", true);
        }
    }
    
    /**
     * Inicia interface na aba Configurações
     * @throws Exception
     */
    private void iniciaConfig() throws Exception {
    	Configuracao.configDataPut("Global.Meio de Pagamento", MeioPagamentoEnum.Dinheiro);

    	if (Valores.getUsuario().getFuncao() == 1) {
    		Configuracao.configDataPut("Sistema.Permitir descontos", true);
    	}

    	for (String key : Configuracao.configDataMapKeySet())
    		ps_configuracoes.getItems().add(new Configuracao(key));
    }

    /**
     * Atualiza os valores das tabelas
     * @throws Exception
     */
    public void refresh(int type) throws Exception {
    	try {
	    	if (type == 1) {
	    		tv_prod.getItems().clear();
	    		tv_prod.setItems(Produto.getAllProduto());
	    		txf_prodBusca.setText(txf_prodBusca.getText());
	    	} else if (type == 2) {
	    		tv_func.getItems().clear();
	    		tv_func.setItems(Funcionario.getAllFuncionario());
	    		txf_funcBusca.setText(txf_funcBusca.getText());
	    		iniciaInicio();
	    	}

	    	tv_comand.getItems().clear();
	    	tv_comand.setItems(Comanda.getAllComanda());
	    	txf_comandaBusca.setText(txf_comandaBusca.getText());
	    	tv_financ.getItems().clear();
	    	tv_financ.setItems(Comanda.getAllComandaPaga(Date.valueOf(dt_finBuscaDe.getValue()), Date.valueOf(dt_finBuscaAte.getValue())));
    	} catch (Exception e) {
    		throw new Exception("Erro ao atualizar tabelas.");
    	}
    }
}