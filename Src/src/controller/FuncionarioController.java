package controller;

import system.Funcionario;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.FuncionarioEnum;
import util.Stages;
import util.Valores;

public class FuncionarioController {
	private int mode = 0;
	private int id = 0;
	private boolean inicial = false;

	@FXML
    private ComboBox<FuncionarioEnum> txf_cargo;

    @FXML
    private CheckBox chb_garcom;

    @FXML
    private TextField txf_name;

    @FXML
    private TextField txf_login;

    @FXML
    private CheckBox chb_senha;

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
	    	if (senha.isEmpty() && chb_senha.isSelected()) {
	    		throw new Exception("Insira uma senha!");
	    	}

	    	//mode 0 = insirir novo funcionario
	    	//mode 1 = editar funcionario
	    	if (mode == 0) {
		    	Funcionario.criaUsuario(txf_cargo.getValue().getValor(), nome, login, senha, chb_garcom.isSelected());
	    	} else if (mode == 1) {
	    		if (!Funcionario.editaFuncionario(id, nome, login, senha, chb_garcom.isSelected())) {
	    			throw new Exception("Erro ao editar funcionário!");
	    		}
	    	}
	    	((Node) event.getSource()).getScene().getWindow().hide();
	    	if (inicial) {
	    		Stages st = new Stages();
	    		st.novoStage("EMSystem Login", "Login", null);
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

    	chb_senha.setDisable(true);
    	chb_senha.setSelected(true);
    	chb_senha.selectedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {
    		public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
    			txf_password.setDisable(!new_val);
            }
        });

    	txf_cargo.getItems().addAll(FuncionarioEnum.values());
    	txf_cargo.setValue(FuncionarioEnum.Usuário);
    }

    void cadastroInicial() {
    	inicial = true;

    	txf_cargo.setValue(FuncionarioEnum.Gerente);
    	txf_cargo.setDisable(true);
    }

    void editaFuncionario(int id, String nome, String login, String cargo, boolean garcom) {
    	mode = 1;
    	this.id = id;
    	txf_cargo.setValue(FuncionarioEnum.valueOf(cargo));
    	txf_cargo.setDisable(true);

    	txf_name.setText(nome);
    	txf_login.setText(login);
    	chb_garcom.setSelected(garcom);

    	chb_senha.setDisable(false);
    	chb_senha.setSelected(false);
    	lbl_aviso.setVisible(true);
    }
}
