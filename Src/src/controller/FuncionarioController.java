package controller;

import system.Funcionario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.Valores;

public class FuncionarioController {
	private int mode = 0;
	private int type = 0;
	private boolean inicial = false;
	private String oldName;

	ObservableList<String> cb_cargoselection = FXCollections
			.observableArrayList("Gerente", "Funcionário");

    @FXML
    private ComboBox<String> txf_cargo;

    @FXML
    private TextField txf_name;

    @FXML
    private TextField txf_login;

    @FXML
    private CheckBox cb_senha;

    @FXML
    private Label lbl_aviso;

    @FXML
    private PasswordField txf_password;

    @FXML
    void btn_funcok(ActionEvent event) throws Exception {
    	String nome = txf_name.getText();
    	String login = txf_login.getText();
    	String senha = txf_password.getText();

    	if (senha.isEmpty() && cb_senha.isSelected()) {
    		//ERRO SENHA VAZIA
    		return;
    	}
    	//mode 0 = insirir novo funcionario
    	//mode 1 = editar funcionario
    	if (mode == 0) {
	    	Funcionario.criaUsuario(type, nome, login, senha);
    	} else if (mode == 1) {
    		if (login == oldName)
        		login = null;

    		if (!Funcionario.editaFuncionario(type, nome, login, senha)) {
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
    	cb_senha.setDisable(true);
    	cb_senha.setSelected(true);
    	cb_senha.selectedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {
    		public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
    			txf_password.setDisable(!new_val);
            }
        });

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

    void editaFuncionario(int id, String nome, String login, String cargo) {
    	mode = 1;
    	txf_cargo.setValue(cargo);
    	txf_cargo.setDisable(true);

    	txf_name.setText(nome);
    	txf_login.setText(login);
    	oldName = login;

    	cb_senha.setDisable(false);
    	cb_senha.setSelected(false);
    	lbl_aviso.setVisible(true);
    }
}
