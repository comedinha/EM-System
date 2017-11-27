package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    
    ObservableList<TableViewProduto> ol;

    @FXML
    void btn_addFunc(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
		stage.setTitle("Configura Funcion√°rio");
		BorderPane root = FXMLLoader.load(getClass().getResource("/view/Funcionario.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void btn_addProd(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
		stage.setTitle("Adiciona Produto");
		BorderPane root = FXMLLoader.load(getClass().getResource("/view/Produto.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    @FXML
    void buscaProduto(ActionEvent event) {

    }
    
    public void initialize(URL location, ResourceBundle resources) {
    	// - PRODUTO
    	// N„o editar aqui, testes pra update...
    	tb_prodid.setCellValueFactory(new PropertyValueFactory<>("id"));
    	tb_prodnome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	tb_prodvlr.setCellValueFactory(new PropertyValueFactory<>("valor"));
    	
    	try {
    		ol = Produto.getAllProduto();
			tableProd.setItems(ol);
		} catch (SQLException e) {
			e.printStackTrace();
		}

        // - FUNCIONARIO
        tb_funcid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tb_funcnome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        try {
        	tableFunc.setItems(Funcionario.getAllFuncionario());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void btn_comandaAddOnAction(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
		stage.setTitle("Adiciona Comanda");
		BorderPane root = FXMLLoader.load(getClass().getResource("/view/Comanda.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void btn_comandaBuscaOnAction(ActionEvent event) {

    }
}