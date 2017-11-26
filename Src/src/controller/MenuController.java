package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sistema.Produto;

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
    private TableColumn<TableViewProduto, Integer> tb_prodquant;

    @FXML
    void novoProduto(ActionEvent event) {

    }
    
    public void initialize(URL location, ResourceBundle resources) {
        tb_prodid.setCellValueFactory(
        		new PropertyValueFactory<>("id"));
        tb_prodnome.setCellValueFactory(
        		new PropertyValueFactory<>("nome"));
        tb_prodvlr.setCellValueFactory(
        		new PropertyValueFactory<>("valor"));
        tb_prodquant.setCellValueFactory(
        		new PropertyValueFactory<>("quantidade"));
        
        Produto p = new Produto();
        try {
			tableProd.setItems(p.getAllProduto());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    //codigo teste tableview
    /*
    private ObservableList<TableViewProduto> listp() {
        return FXCollections.observableArrayList(
                new TableViewProduto (1, "banana", 2, 10),
                new TableViewProduto (2, "morango", 3, 10)
        );
    }
    */
    
     //classe interna para servir de Modelo para a tableView produto
    public static class TableViewProduto {
    	private final SimpleIntegerProperty id;
    	private final SimpleStringProperty nome;
    	private final SimpleFloatProperty valor;
    	private final SimpleIntegerProperty quantidade;
    	

    	public TableViewProduto(int id, String nome, float valor, int quantidade) {
    		this.id = new SimpleIntegerProperty(id);
    		this.nome = new SimpleStringProperty(nome);
    		this.valor = new SimpleFloatProperty(valor);
    		this.quantidade = new SimpleIntegerProperty(quantidade);
    	}

    	public int getId() {
    		return id.get();
    	}
    	public String getNome() {
    		return nome.get();
    	}
    	public float getValor() {
    		return valor.get();
    	}
    	public int getQuantidade() {
    		return quantidade.get();
    	}    		
    }
}
