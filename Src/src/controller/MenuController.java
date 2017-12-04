package controller;

import java.sql.Date;
import java.sql.SQLException;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.dialog.WizardPane;
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
    private WizardPane wp_tip1;

	@FXML
    private TextArea ta_txtinicio;

	@FXML
    private Tab ab_financeiro;

	@FXML
    private DatePicker dt_finbuscade;

    @FXML
    private DatePicker dt_finbuscaate;

    @FXML
    private TableView<TableViewComandaPaga> tableFinanc;

    @FXML
    private TableColumn<TableViewComandaPaga, Integer> tb_financid;

    @FXML
    private TableColumn<TableViewComandaPaga, String> tb_financfunc;

    @FXML
    private TableColumn<TableViewComandaPaga, String> tb_financdata;

    @FXML
    private TableColumn<TableViewComandaPaga, String> tb_financperm;

    @FXML
    private TableColumn<TableViewComandaPaga, Float> tb_financvlr;

    @FXML
    private TextField txf_comandaBusca;

    @FXML
    private TableView<TableViewComandaLista> tableComand;

    @FXML
    private TableColumn<TableViewComandaLista, Integer> tb_comandId;

    @FXML
    private TableColumn<TableViewComandaLista, String> tb_comandFunc;
    
    @FXML
    private TableColumn<TableViewComandaLista, String> tb_comandMesa;

    @FXML
    private TableColumn<TableViewComandaLista, String> tb_comandData;

    @FXML
    private TableColumn<TableViewComandaLista, Float> tb_comandVlr;

    @FXML
    private Tab ab_produtos;

    @FXML
    private TextField txf_prodbusca;

    @FXML
    private TableView<TableViewProduto> tableProd;

    @FXML
    private TableColumn<TableViewProduto, Integer> tb_prodid;

    @FXML
    private TableColumn<TableViewProduto, String> tb_prodnome;

    @FXML
    private TableColumn<TableViewProduto, Float> tb_prodvlr;

    @FXML
    private Tab ab_funcionarios;

    @FXML
    private TextField txf_funcbusca;

    @FXML
    private TableView<TableViewFuncionario> tableFunc;

    @FXML
    private TableColumn<TableViewFuncionario, Integer> tb_funcid;

    @FXML
    private TableColumn<TableViewFuncionario, String> tb_funcnome;

    @FXML
    private TableColumn<TableViewFuncionario, String> tb_funclogin;

    @FXML
    private TableColumn<TableViewFuncionario, String> tb_funccargo;

    @FXML
    private TableColumn<TableViewFuncionario, Boolean> tb_funcgarcom;

    @FXML
    private PropertySheet ps_configuracoes;

    @FXML
    void act_BuscaFinanceiro(ActionEvent event) {
    	try {
	    	if (Date.valueOf(dt_finbuscade.getValue()).before(Date.valueOf(dt_finbuscaate.getValue()))) {
		    	tableFinanc.getItems().clear();
		    	tableFinanc.setItems(Comanda.getAllComandaPaga(Date.valueOf(dt_finbuscade.getValue()), Date.valueOf(dt_finbuscaate.getValue())));
	    	} else
	    		throw new Exception("A data DE deve ser menor que a data ATÉ!");
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    void btn_addComanda(ActionEvent event) throws SQLException {
    	Stages st = new Stages();
    	st.novoStage("Adicionar Comanda", "Comanda");
    	st.getLoader().<ComandaController>getController().iniciaComanda(st.getLoader());
    }

    @FXML
    void btn_addFunc(ActionEvent event) {
    	Stages st = new Stages();
    	st.novoStage("Adicionar Funcionário", "Funcionario");
    }

    @FXML
    void btn_addProd(ActionEvent event) {
    	Stages st = new Stages();
    	st.novoStage("Adicionar Produto", "Produto");
    }

    @FXML
    void btn_desconecta(ActionEvent event) {
	    ((Node) event.getSource()).getScene().getWindow().hide();
	    Valores.setController(null);
	    Valores.setFuncionario(null);
	    Configuracao.configDataClean();
	    Stages st = new Stages();
	    st.novoStage("EMSystem Login", "Login");
    }

    @FXML
    void btn_salvaConfig(ActionEvent event) {
    	for (String key : Configuracao.configDataMapKeySet())
    		System.out.println(Configuracao.configDataGetValue(key));
    }

    @FXML
    public void initialize() {
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
    	ta_txtinicio.setText(String.format(ta_txtinicio.getText(), Valores.getUsuario().getNome(), FuncionarioEnum.get(Valores.getUsuario().getFuncao()), null));
    }
    
    /**
     * Inicia a interface na aba Finaças
     */
    private void iniciaFinanc() {
    	try {
	    	dt_finbuscade.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1));
	    	dt_finbuscaate.setValue(LocalDate.now());
	
	    	tb_financid.setCellValueFactory(new PropertyValueFactory<>("id"));
	    	tb_financfunc.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
	    	tb_financdata.setCellValueFactory(new PropertyValueFactory<>("data"));
	    	tb_financperm.setCellValueFactory(new PropertyValueFactory<>("permanencia"));
	    	tb_financvlr.setCellValueFactory(new PropertyValueFactory<>("valor"));

			tableFinanc.setItems(Comanda.getAllComandaPaga(Date.valueOf(dt_finbuscade.getValue()), Date.valueOf(dt_finbuscaate.getValue())));
			tableFinanc.setRowFactory((TableView<TableViewComandaPaga> tableComandaFinanc) -> {
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
	    	tb_comandId.setCellValueFactory(new PropertyValueFactory<>("id"));
	    	tb_comandFunc.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
	    	tb_comandMesa.setCellValueFactory(new PropertyValueFactory<>("mesa"));
	    	tb_comandData.setCellValueFactory(new PropertyValueFactory<>("data"));
	    	tb_comandVlr.setCellValueFactory(new PropertyValueFactory<>("valor"));
    	
			tableComand.setItems(Comanda.getAllComanda());
			tableComand.setRowFactory((TableView<TableViewComandaLista> tableComanda) -> {
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
    						tableComand.setItems(Comanda.getAllComanda());
    						return;
    					}

    					ObservableList<TableViewComandaLista> tableItems = FXCollections.observableArrayList();
    					ObservableList<TableColumn<TableViewComandaLista, ?>> cols = tableComand.getColumns();
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
    					tableComand.setItems(tableItems);
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
	    	tb_prodid.setCellValueFactory(new PropertyValueFactory<>("id"));
	    	tb_prodnome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	    	tb_prodvlr.setCellValueFactory(new PropertyValueFactory<>("valor"));

    		tableProd.setItems(Produto.getAllProduto());
    		
    		tableProd.setRowFactory((TableView<TableViewProduto> tableProduto) -> {
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
    		txf_prodbusca.textProperty().addListener(new InvalidationListener() {
    			@Override
    			public void invalidated(Observable observable) {
    				try {
    					if(txf_prodbusca.textProperty().get().isEmpty()) {
    						tableProd.setItems(Produto.getAllProduto());
    						return;
    					}

    					ObservableList<TableViewProduto> tableItems = FXCollections.observableArrayList();
    					ObservableList<TableColumn<TableViewProduto, ?>> cols = tableProd.getColumns();
    					for (int i = 0; i < Produto.getAllProduto().size(); i++) {
    						for (int j = 0; j < cols.size(); j++) {
    							TableColumn<TableViewProduto, ?> col = cols.get(j);
    							String cellValue = col.getCellData(Produto.getAllProduto().get(i)).toString();
    							cellValue = cellValue.toLowerCase();
    							if(cellValue.contains(txf_prodbusca.textProperty().get().toLowerCase())) {
    								tableItems.add(Produto.getAllProduto().get(i));
    								break;
    							}
    						}
    					}
    					tableProd.setItems(tableItems);
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
	    	tb_funcid.setCellValueFactory(new PropertyValueFactory<>("id"));
	        tb_funcnome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	        tb_funclogin.setCellValueFactory(new PropertyValueFactory<>("login"));
	        tb_funccargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
	        tb_funcgarcom.setCellValueFactory(new PropertyValueFactory<>("garcom"));

        	tableFunc.setItems(Funcionario.getAllFuncionario());
        	tableFunc.setRowFactory((TableView<TableViewFuncionario> tableFuncionario) -> {
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
        	txf_funcbusca.textProperty().addListener(new InvalidationListener() {
        		@Override
        		public void invalidated(Observable observable) {
        			try {
        				if(txf_funcbusca.textProperty().get().isEmpty()) {
        					tableFunc.setItems(Funcionario.getAllFuncionario());
        					return;
        				}

        				ObservableList<TableViewFuncionario> tableItems = FXCollections.observableArrayList();
        				ObservableList<TableColumn<TableViewFuncionario, ?>> cols = tableFunc.getColumns();
        				for (int i = 0; i < Funcionario.getAllFuncionario().size(); i++) {
        					for (int j = 0; j < cols.size(); j++) {
        						TableColumn<TableViewFuncionario, ?> col = cols.get(j);
        						String cellValue = col.getCellData(Funcionario.getAllFuncionario().get(i)).toString();
        						cellValue = cellValue.toLowerCase();
        						if(cellValue.contains(txf_funcbusca.textProperty().get().toLowerCase())) {
        							tableItems.add(Funcionario.getAllFuncionario().get(i));
        							break;
        						}
        					}
        				}
        				tableFunc.setItems(tableItems);
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

    void refresh(int type) throws Exception {
    	try {
	    	if (type == 1) {
	    		tableProd.getItems().clear();
	    		tableProd.setItems(Produto.getAllProduto());
	    		txf_prodbusca.setText(txf_prodbusca.getText());
	    	} else if (type == 2) {
	    		tableFunc.getItems().clear();
	    		tableFunc.setItems(Funcionario.getAllFuncionario());
	    		txf_funcbusca.setText(txf_funcbusca.getText());
	    	} else if (type == 3) {
	    		tableComand.getItems().clear();
	    		tableComand.setItems(Comanda.getAllComanda());
	    		txf_comandaBusca.setText(txf_comandaBusca.getText());
	    		tableFinanc.getItems().clear();
	    		tableFinanc.setItems(Comanda.getAllComandaPaga(Date.valueOf(dt_finbuscade.getValue()), Date.valueOf(dt_finbuscaate.getValue())));
	    	}
    	} catch (Exception e) {
    		throw new Exception("Erro ao atualizar tabelas.");
    	}
    }
}