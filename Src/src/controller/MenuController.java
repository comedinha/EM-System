package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import system.Produto;
import system.Produto.TableViewProduto;
import util.Valores;
import system.Funcionario;
import system.Funcionario.TableViewFuncionario;

public class MenuController implements Initializable {
	@FXML
    private TextField lbl_finbuscade;

    @FXML
    private TextField lbl_finbuscaate;

    @FXML
    private TableView<?> tableFinanc;

    @FXML
    private TableColumn<?, ?> tb_financid;

    @FXML
    private TableColumn<?, ?> tb_financfunc;

    @FXML
    private TableColumn<?, ?> tb_financdata;

    @FXML
    private TableColumn<?, ?> tb_financperm;

    @FXML
    private TableColumn<?, ?> tb_financvlr;

    @FXML
    private TextField txf_comandaBusca;

    @FXML
    private TableView<?> tableComand;

    @FXML
    private TableColumn<?, ?> tb_comandid;

    @FXML
    private TableColumn<?, ?> tb_comanddata;

    @FXML
    private TableColumn<?, ?> tb_comandvlr;

    @FXML
    private RadioButton cho_prodid;

    @FXML
    private RadioButton cho_prodnome;

    @FXML
    private RadioButton cho_prodvlr;

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
    private RadioButton cho_funcid;

    @FXML
    private RadioButton cho_funcnome;

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
    
    ObservableList<TableViewProduto> ol;

    @FXML
    void act_financbusca(ActionEvent event) {
    	tableFinanc.setItems(null);
    }

    @FXML
    void act_comandbusca(ActionEvent event) {
    	tableComand.setItems(null);
    }

    @FXML
    void act_funcbusca(ActionEvent event) {
    	tableFunc.setItems(null);
    }

    @FXML
    void act_prodbusca(ActionEvent event) {
    	tableProd.setItems(null);
    }

    @FXML
    void btn_addComanda(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
		stage.setTitle("Adiciona Comanda");
		BorderPane root = FXMLLoader.load(getClass().getResource("/view/Comanda.fxml"));
		Scene scene = new Scene(root);
		stage.getIcons().add(new Image("file:icone.png"));
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void btn_addFunc(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
		stage.setTitle("Configura Funcionário");
		BorderPane root = FXMLLoader.load(getClass().getResource("/view/Funcionario.fxml"));
		Scene scene = new Scene(root);
		stage.getIcons().add(new Image("file:icone.png"));
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void btn_addProd(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
		stage.setTitle("Adiciona Produto");
		BorderPane root = FXMLLoader.load(getClass().getResource("/view/Produto.fxml"));
		Scene scene = new Scene(root);
		stage.getIcons().add(new Image("file:icone.png"));
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void btn_desconecta(ActionEvent event) throws IOException {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	Valores.setController(null);
    	Valores.setFuncionario(null);
    	Stage stage = new Stage();
		stage.setTitle("EMSystem Login");
		BorderPane root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene scene = new Scene(root);
		stage.getIcons().add(new Image("file:icone.png"));
		stage.setScene(scene);
		stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {
    	iniciaInicio();
    	iniciaFinanc();
    	iniciaComanda();
    	iniciaProduto();
    	iniciaFuncionario();
    	iniciaConfig();
    }

    private void iniciaInicio() {
    	
    }

    private void iniciaFinanc() {
    	
    }

    private void iniciaComanda() {

    }

    private void iniciaProduto() {
    	//Botões de Busca
    	ToggleGroup group = new ToggleGroup();
    	cho_prodid.setSelected(true);
    	cho_prodid.setToggleGroup(group);
    	cho_prodnome.setToggleGroup(group);
    	cho_prodvlr.setToggleGroup(group);

    	tb_prodid.setCellValueFactory(new PropertyValueFactory<>("id"));
    	tb_prodnome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	tb_prodvlr.setCellValueFactory(new PropertyValueFactory<>("valor"));
        try {
			tableProd.setItems(Produto.getAllProduto());
			tableProd.setRowFactory(
			        (TableView<TableViewProduto> tableView1) -> {
			            final TableRow<TableViewProduto> row = new TableRow<>();
			            final ContextMenu rowMenu = new ContextMenu();
			            MenuItem editItem = new MenuItem("Editar");
			            MenuItem removeItem = new MenuItem("Deletar");
			            
			            //Atualizar Produtos
			            editItem.setOnAction((ActionEvent event) -> {
							try {
								Stage stage = new Stage();
				        		stage.setTitle("Edita Produto");
				        		FXMLLoader produtoLoader = new FXMLLoader(getClass().getResource("/view/Produto.fxml"));
				        		BorderPane root = produtoLoader.load();
				        		ProdutoController controller = produtoLoader.<ProdutoController>getController();
								controller.editaProduto(row.getItem().getId(), row.getItem().getNome(), row.getItem().getValor());
				        		Scene scene = new Scene(root);
				        		stage.getIcons().add(new Image("file:icone.png"));
				        		stage.setScene(scene);
				        		stage.show();
							} catch (IOException e) {
								e.printStackTrace();
							}
			            });
			            
			            //Remover Produtos
			            removeItem.setOnAction((ActionEvent event) -> {
			            	if (!Produto.delete(row.getItem().getId())) {
			            		//Erro ao remover
			            	}
			                tableView1.getItems().remove(row.getItem());			                
			            });
			            
			            rowMenu.getItems().addAll(editItem, removeItem);
			            row.contextMenuProperty().bind(
			                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
			                            .then(rowMenu)
			                            .otherwise((ContextMenu)null));
			            return row;
			        });
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    private void iniciaFuncionario() {
    	//Botões de Busca
    	ToggleGroup group = new ToggleGroup();
    	cho_funcid.setSelected(true);
    	cho_funcid.setToggleGroup(group);
    	cho_funcnome.setToggleGroup(group);

    	tb_funcid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tb_funcnome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tb_funclogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        tb_funccargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        try {
        	tableFunc.setItems(Funcionario.getAllFuncionario());
        	tableFunc.setRowFactory(
			        (TableView<TableViewFuncionario> tableView1) -> {
			            final TableRow<TableViewFuncionario> row = new TableRow<>();
			            final ContextMenu rowMenu = new ContextMenu();
			            MenuItem editFuncionario = new MenuItem("Editar");
			            MenuItem removeFuncionario = new MenuItem("Deletar");
			            
			            //Editar Funcionários
			            editFuncionario.setOnAction((ActionEvent event) -> {
							try {
								Stage stage = new Stage();
				        		stage.setTitle("Edita Funcionário");
				        		FXMLLoader funcionarioLoader = new FXMLLoader(getClass().getResource("/view/Funcionario.fxml"));
				        		BorderPane root = funcionarioLoader.load();
								FuncionarioController controller = funcionarioLoader.<FuncionarioController>getController();
								controller.editaFuncionario(row.getItem().getId(), row.getItem().getNome(), row.getItem().getLogin(), row.getItem().getCargo());
				        		Scene scene = new Scene(root);
				        		stage.getIcons().add(new Image("file:icone.png"));
				        		stage.setScene(scene);
				        		stage.show();
							} catch (IOException e) {
								e.printStackTrace();
							}
			            });
			            
			            //Remover Funcionários
			            removeFuncionario.setOnAction((ActionEvent event) -> {
			            	try {
								if (!Funcionario.removeFuncionario(row.getItem().getId(), 
										row.getItem().getCargo())) {
									//ERRO AO REMOVER FUNCIONARIO
								} else {	
					                tableView1.getItems().remove(row.getItem());
								}
							} catch (SQLException e) {}
			            });
			            rowMenu.getItems().addAll(editFuncionario, removeFuncionario);
			            row.contextMenuProperty().bind(
			                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
			                            .then(rowMenu)
			                            .otherwise((ContextMenu)null));
			            return row;
			        });
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    private void iniciaConfig() {
    	
    }

    void refresh(int type) throws SQLException {
    	if (type == 1) {
    		tableProd.setItems(Produto.getAllProduto());
    	} else if (type == 2) {
    		tableFunc.setItems(Funcionario.getAllFuncionario());
    	}
    }
}