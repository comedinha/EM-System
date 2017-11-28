package controller;

import system.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.Valores;

public class FuncionarioController {
	private int mode = 0;
	private int type = 0;
	private int id;
	private boolean inicial = false;
	ObservableList<String> cb_cargoselection = FXCollections
			.observableArrayList("Gerente", "Funcionário");

    @FXML
    private ComboBox<String> txf_cargo;

    @FXML
    private TextField txf_username;

    @FXML
    private TextField txf_nickname;

    @FXML
    private PasswordField txf_password;

    @FXML
    private Button btn_funcok;

    @FXML
    private Button btn_funcquit;

    @FXML
    void btn_funcok(ActionEvent event) throws Exception {
    	//mode 0 = insirir novo funcionario
    	if (mode == 0) {
	    	int func = type;
	    	String nome = txf_username.getText();
	    	String usuario = txf_nickname.getText();
	    	String senha = txf_password.getText();
	
	    	Funcionario.criaUsuario(func, nome, usuario, senha);
	    	//mode 1 = editar funcionario j� existente
    	} else if (mode == 1) {
    		String nome = txf_username.getText();
	    	String usuario = txf_nickname.getText();
	    	String senha = txf_password.getText();
	    	
    		if(!Funcionario.editaFuncionario(id, usuario, nome, senha)) {
    			//Erro ao editar
    		}
    	}
    	((Node) event.getSource()).getScene().getWindow().hide();
    	if (inicial) {
    		Stage stage = new Stage();
    		stage.setTitle("EMSystem Login");
			BorderPane root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
    	} else {
    		Valores.getController().refresh(2);
    	}
    }

    @FXML
    void btn_funcquit(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void initialize() {
    	txf_cargo.setItems(cb_cargoselection);
    	txf_cargo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		if (newValue.toString() == "Gerente") {
    			type = 1;
    		} else if (newValue.toString() == "Funcionário") {
    			type = 2;
    		}
    	});
    }

    void cadastroInicial() {
    	inicial = true;
    	txf_cargo.setValue("Gerente");
    	txf_cargo.setDisable(true);
    }

    void editaFuncionario(int id, String nome, String username, String cargo) {
    	this.id = id;
    	mode = 1;
    	txf_cargo.setValue(cargo);
    	txf_cargo.setDisable(true);
    	txf_username.setText(nome);
    	txf_nickname.setText(username);
    }
}
