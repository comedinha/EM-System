package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import system.Produto;
import system.Produto.TableViewProduto;

public class MenuController implements Initializable {

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
    void novoProduto(ActionEvent event) {

    }
    
    @FXML
    void buscaProduto(ActionEvent event) {

    }
    
    public void initialize(URL location, ResourceBundle resources) {
        tb_prodid.setCellValueFactory(
        		new PropertyValueFactory<>("id"));
        tb_prodnome.setCellValueFactory(
        		new PropertyValueFactory<>("nome"));
        tb_prodvlr.setCellValueFactory(
        		new PropertyValueFactory<>("valor"));
        
        Produto p = new Produto();
        try {
			tableProd.setItems(p.getAllProduto());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
