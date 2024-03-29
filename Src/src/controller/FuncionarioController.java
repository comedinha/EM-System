package controller;

import system.Funcionario;

import java.util.regex.Pattern;

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

/**
 * Classe define o controller dos funcionarios
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi.
 * @version 1.0
 */
public class FuncionarioController {
	private int mode = 0;
	private int id = 0;
	private boolean inicial = false;

	@FXML
    private ComboBox<FuncionarioEnum> cb_cargo;

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
    private void act_salvar(ActionEvent event) {
    	try {
	    	String nome = txf_name.getText();
	    	String login = txf_login.getText();
	    	String senha = txf_password.getText();
	
	    	if (nome.isEmpty())
    			throw new Exception("Insira um nome!");
	    	if (login.isEmpty())
	    		throw new Exception("Insira um login!");
	    	if (senha.isEmpty() && chb_senha.isSelected())
	    		throw new Exception("Insira uma senha!");

	    	Pattern digit = Pattern.compile("[0-9]");
	    	Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
	    	if (digit.matcher(nome).find() || special.matcher(nome).find())
	    		throw new Exception("O nome não pode conter números ou caracteres especiais!");
	    	

	    	//mode 0 = insirir novo funcionario
	    	//mode 1 = editar funcionario
	    	if (mode == 0) {
		    	Funcionario.criaUsuario(cb_cargo.getValue().getValor(), nome, login, senha, chb_garcom.isSelected());
	    	} else if (mode == 1) {
	    		if (!Funcionario.editaFuncionario(id, nome, login, senha, chb_garcom.isSelected())) {
	    			throw new Exception("Erro ao editar funcionário!");
	    		}
	    	}

	    	((Node) event.getSource()).getScene().getWindow().hide();
	    	if (inicial) {
	    		Stages st = new Stages();
	    		st.novoStage("EMSystem Login", "Login");
	    	} else {
	    		fecharFuncionario();
	    		Valores.getController().refresh(2);
	    	}
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getMessage(), "", true);
    	}
    }

    @FXML
    private void act_cancelar(ActionEvent event) {
    	fecharFuncionario();
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

    	cb_cargo.getItems().addAll(FuncionarioEnum.values());
    	cb_cargo.setValue(FuncionarioEnum.Usuario);
    }

    /**
	 * Metodo executado ao cadastrar o primeiro usuário
	 */
    void cadastroInicial() {
    	inicial = true;

    	cb_cargo.setDisable(true);
    	cb_cargo.setValue(FuncionarioEnum.Gerente);
    }

    /**
     * Metodo chamado quando acessa-se a interface no modo edição
     * @param id do funcionário
     * @param nome nome do funcionário
     * @param login login do funcionário
     * @param cargo cargo do funcionário
     * @param garcom verificação se funcionário é garçom
     */
    void editaFuncionario(int id, String nome, String login, String cargo, boolean garcom) {
    	mode = 1;
    	this.id = id;
    	cb_cargo.setValue(FuncionarioEnum.valueOf(cargo));
    	cb_cargo.setDisable(true);

    	txf_name.setText(nome);
    	txf_login.setText(login);
    	chb_garcom.setSelected(garcom);

    	chb_senha.setDisable(false);
    	chb_senha.setSelected(false);
    	lbl_aviso.setVisible(true);
    }

    /**
	 * Metodo chamado para fechar a janela de funcionario de maneira segura
	 */
	public void fecharFuncionario() {
		try {
			Valores.editCheck().remove("Funcionario" + id);
	    	Valores.getController().refresh(2);
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
	}
}
