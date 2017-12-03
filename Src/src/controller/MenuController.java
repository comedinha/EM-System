package controller;

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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import system.Produto;
import system.Comanda.TableViewComandaLista;
import system.Produto.TableViewProduto;
import util.MeioPagamento;
import util.Stages;
import util.Valores;
import system.Comanda;
import system.Configuracao;
import system.Funcionario;
import system.Funcionario.FuncionarioCargoEnum;
import system.Funcionario.TableViewFuncionario;

import java.time.LocalDate;

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
    private TableView<TableViewComandaLista> tableFinanc;

    @FXML
    private TableColumn<TableViewComandaLista, Integer> tb_financid;

    @FXML
    private TableColumn<TableViewComandaLista, String> tb_financfunc;

    @FXML
    private TableColumn<TableViewComandaLista, String> tb_financdata;

    @FXML
    private TableColumn<TableViewComandaLista, String> tb_financperm;

    @FXML
    private TableColumn<TableViewComandaLista, Float> tb_financvlr;

    @FXML
    private TextField txf_comandaBusca;

    @FXML
    private TableView<TableViewComandaLista> tableComand;

    @FXML
    private TableColumn<TableViewComandaLista, Integer> tb_comandId;
    
    @FXML
    private TableColumn<TableViewComandaLista, String> tb_comandMesa;

    @FXML
    private TableColumn<?, ?> tb_comandData;

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

    }

    @FXML
    void btn_addComanda(ActionEvent event) throws SQLException {
    	Stages st = new Stages();
    	st.novoStage("Adicionar Comanda", "Comanda", null);
    }

    @FXML
    void btn_addFunc(ActionEvent event) {
    	Stages st = new Stages();
    	st.novoStage("Adicionar Funcionário", "Funcionario", null);
    }

    @FXML
    void btn_addProd(ActionEvent event) {
    	Stages st = new Stages();
    	st.novoStage("Adicionar Produto", "Produto", null);
    }

    @FXML
    void btn_desconecta(ActionEvent event) {
	    ((Node) event.getSource()).getScene().getWindow().hide();
	    Valores.setController(null);
	    Valores.setFuncionario(null);
	    Configuracao.configDataClean();
	    Stages st = new Stages();
	    st.novoStage("EMSystem Login", "Login", null);
    }

    @FXML
    void btn_salvaConfig(ActionEvent event) {
    	for (String key : Configuracao.configDataMapKeySet())
    		System.out.println(Configuracao.configDataGetValue(key));
    }

    @FXML
    public void initialize() {
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
    }

    private void iniciaInicio() {
    	ta_txtinicio.setText(String.format(ta_txtinicio.getText(), Valores.getUsuario().getNome(), FuncionarioCargoEnum.get(Valores.getUsuario().getFuncao()), null));
    }

    private void iniciaFinanc() {
    	try {
	    	dt_finbuscade.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1));
	    	dt_finbuscaate.setValue(LocalDate.now());
	
	    	tb_financid.setCellValueFactory(new PropertyValueFactory<>("id"));
	    	//tb_financfunc.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
	    	tb_financdata.setCellValueFactory(new PropertyValueFactory<>("data"));
	    	//tb_financperm.setCellValueFactory(new PropertyValueFactory<>("permanencia"));
	    	tb_financvlr.setCellValueFactory(new PropertyValueFactory<>("valor"));

			tableFinanc.setItems(Comanda.getAllComanda());
			tableFinanc.setRowFactory((TableView<TableViewComandaLista> tableComandaFinanc) -> {
    			final TableRow<TableViewComandaLista> row = new TableRow<>();
    			final ContextMenu rowMenu = new ContextMenu();
    			MenuItem verComanda = new MenuItem("Visualizar Comanda");
    			MenuItem verPagamento = new MenuItem("Visualizar Pagamentos");

    			//Visualizar Comandas
    			verComanda.setOnAction((ActionEvent event) -> {
    				try {
    					Stages st = new Stages();
        		    	FXMLLoader comandaLoader = st.novoStage("Editar Comanda", "Comanda", null);
        		    	comandaLoader.<ComandaController>getController().editaComanda(row.getItem().getId());
    				} catch (Exception e) {
    					Stages.novoAlerta(e.getMessage(), "", true);
    				}
    			});

    			rowMenu.getItems().addAll(verComanda, verPagamento);
    			row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu)null));
    			return row;
    		});
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    private void iniciaComanda() {
    	try {
	    	tb_comandId.setCellValueFactory(new PropertyValueFactory<>("id"));
	    	tb_comandMesa.setCellValueFactory(new PropertyValueFactory<>("mesa"));
	    	tb_comandData.setCellValueFactory(new PropertyValueFactory<>("data"));
	    	tb_comandVlr.setCellValueFactory(new PropertyValueFactory<>("valor"));
    	
			tableComand.setItems(Comanda.getAllComanda());
			tableComand.setRowFactory((TableView<TableViewComandaLista> tableComanda) -> {
    			final TableRow<TableViewComandaLista> row = new TableRow<>();
    			final ContextMenu rowMenu = new ContextMenu();
    			MenuItem editItem = new MenuItem("Editar");

    			//Atualizar Produtos
    			editItem.setOnAction((ActionEvent event) -> {
    				try {
    					Stages st = new Stages();
        		    	FXMLLoader comandaLoader = st.novoStage("Editar Comanda", "Comanda", null);
        		    	comandaLoader.<ComandaController>getController().editaComanda(row.getItem().getId());
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
    					Stages st = new Stages();
        		    	FXMLLoader produtoLoader = st.novoStage("Edita Produto", "Produto", null);
        		    	produtoLoader.<ProdutoController>getController().editaProduto(row.getItem().getId(), row.getItem().getNome(), row.getItem().getValor());
    				} catch (Exception e) {
    					Stages.novoAlerta(e.getMessage(), "", true);
    				}
    			});

    			//Remover Produtos
    			removeItem.setOnAction((ActionEvent event) -> {
    				try {
	    				if (!Produto.delete(row.getItem().getId())) {
	    					throw new Exception("Erro ao remover produto!");
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
        				Stages st = new Stages();
        		    	FXMLLoader menuLoader = st.novoStage("Edita Funcionário", "Funcionario", null);
        				menuLoader.<FuncionarioController>getController().editaFuncionario(row.getItem().getId(), row.getItem().getNome(), row.getItem().getLogin(), row.getItem().getCargo(), row.getItem().getGarcom());
        			} catch (Exception e) {
        				Stages.novoAlerta(e.getMessage(), "", true);
        			}
        		});

        		//Remover Funcionários
        		removeFuncionario.setOnAction((ActionEvent event) -> {
        			try {
        				if (!Funcionario.removeFuncionario(row.getItem().getId(), row.getItem().getCargo())) {
        					refresh(2);
        					throw new Exception("Erro ao remover funcionário");
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

    private void iniciaConfig() {
    	Configuracao.configDataPut("Global.Meio de Pagamento", MeioPagamento.Dinheiro);

    	if (Valores.getUsuario().getFuncao() == 1) {
    		Configuracao.configDataPut("Sistema.Permitir descontos", true);
    	}

    	for (String key : Configuracao.configDataMapKeySet())
    		ps_configuracoes.getItems().add(new Configuracao(key));
    }

    void refresh(int type) throws Exception {
    	try {
	    	if (type == 1) {
	    		tableProd.setItems(Produto.getAllProduto());
	    		txf_prodbusca.setText(txf_prodbusca.getText());
	    	} else if (type == 2) {
	    		tableFunc.setItems(Funcionario.getAllFuncionario());
	    		txf_funcbusca.setText(txf_funcbusca.getText());
	    	} else if (type == 3) {
	    		tableComand.setItems(Comanda.getAllComanda());
	    		txf_comandaBusca.setText(txf_comandaBusca.getText());
	    	}
    	} catch (Exception e) {
    		throw new Exception("Erro ao atualizar tabelas.");
    	}
    }
}