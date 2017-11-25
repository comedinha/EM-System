package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProdutoController {

    @FXML
    private Button btn_novoProduto;

    @FXML
    private TextField txf_id;

    @FXML
    private Button btm_buscar;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> tb_id;

    @FXML
    private TableColumn<?, ?> tb_nome;

    @FXML
    private TableColumn<?, ?> tb_valor;

    @FXML
    void buscar(ActionEvent event) {

    }

    @FXML
    void novoProduto(ActionEvent event) {

    }

}
