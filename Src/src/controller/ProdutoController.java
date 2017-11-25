package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sistema.TableViewProduto;

public class ProdutoController {

    @FXML
    private Button btn_novoProduto;

    @FXML
    private TextField txf_id;

    @FXML
    private Button btm_buscar;

    @FXML
    private TableView<TableViewProduto> tableView;

    @FXML
    private TableColumn<TableViewProduto, Integer> tb_id;

    @FXML
    private TableColumn<TableViewProduto, String> tb_nome;

    @FXML
    private TableColumn<TableViewProduto, Float> tb_valor;
    
    @FXML
    private TableColumn<TableViewProduto, Integer> tb_quantidade;
    
    

    @FXML
    void buscar(ActionEvent event) {
    	
    }

    @FXML
    void novoProduto(ActionEvent event) {
    	
    }

}
