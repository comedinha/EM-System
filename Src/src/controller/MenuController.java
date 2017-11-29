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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import system.Produto;
import system.Produto.TableViewProduto;
import system.Funcionario;
import system.Funcionario.TableViewFuncionario;

public class MenuController implements Initializable {
	@FXML
    private Button btn_comandaAdd;

    @FXML
    private TextField lbl_comandaBusca;

    @FXML
    private Button btn_comandaBusca;

    @FXML
    private Button btn_novoProduto;

    @FXML
    private RadioButton cho_prodid;

    @FXML
    private RadioButton cho_prodnome;

    @FXML
    private RadioButton cho_prodvlr;

    @FXML
    private TextField txf_prodbusca;

    @FXML
    private Button btn_prodbuscar;

    @FXML
    private TableView<TableViewProduto> tableProd;

    @FXML
    private TableColumn<TableViewProduto, Integer> tb_prodid;

    @FXML
    private TableColumn<TableViewProduto, String> tb_prodnome;

    @FXML
    private TableColumn<TableViewProduto, Float> tb_prodvlr;

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
    void buscaProduto(ActionEvent event) {

    }
    
    public void initialize(URL location, ResourceBundle resources) {
    	// - PRODUTO
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
			            
			            //atualiza item
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
			            
			            //remove item
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

        // - FUNCIONARIO
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
			            
			            //Edita Funcionario
			            editFuncionario.setOnAction((ActionEvent event) -> {
							try {
								Stage stage = new Stage();
				        		stage.setTitle("Edita Funcionario");
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
			            
			            //Remove Funcionario
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

    @FXML
    void btn_comandaBuscaOnAction(ActionEvent event) {

    }

    void refresh(int type) throws SQLException {
    	if (type == 1) {
    		tableProd.setItems(Produto.getAllProduto());
    	} else if (type == 2) {
    		tableFunc.setItems(Funcionario.getAllFuncionario());
    	}
    }
}