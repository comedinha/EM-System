package controller;

import system.Funcionario;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.Stages;
import util.Valores;

public class FuncionarioController {
	private int mode = 0;
	private int type = 0;
	private boolean inicial = false;

	ObservableList<String> cb_cargoselection = FXCollections.observableArrayList("Gerente", "Usuário");

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
    void btn_funcok(ActionEvent event) {
    	try {
	    	String nome = txf_name.getText();
	    	String login = txf_login.getText();
	    	String senha = txf_password.getText();
	
	    	if (nome.isEmpty())
    			throw new Exception("Insira um nome!");
	    	if (login.isEmpty())
	    		throw new Exception("Insira um login!");
	    	if (senha.isEmpty() && cb_senha.isSelected()) {
	    		throw new Exception("Insira uma senha!");
	    	}

	    	//mode 0 = insirir novo funcionario
	    	//mode 1 = editar funcionario
	    	if (mode == 0) {
		    	Funcionario.criaUsuario(type, nome, login, senha);
	    	} else if (mode == 1) {
	    		if (!Funcionario.editaFuncionario(type, nome, login, senha)) {
	    			throw new Exception("Erro ao editar funcionário!");
	    		}
	    	}
	    	((Node) event.getSource()).getScene().getWindow().hide();
	    	if (inicial) {
	    		Stages st = new Stages();
	    		st.novoStage("EMSystem Login", "Login");
	    	} else {
	    		Valores.getController().refresh(2);
	    	}
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    void btn_funcquit(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void initialize() {
    	if (Valores.getConnection() == null)
    		Platform.exit();

    	txf_cargo.setValue("Usuário");
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
    		} else if (newValue.toString() == "Usuário") {
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

    	cb_senha.setDisable(false);
    	cb_senha.setSelected(false);
    	lbl_aviso.setVisible(true);
    }
}
