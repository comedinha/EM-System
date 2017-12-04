package controller;

import java.sql.Timestamp;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import system.Pagamento;
import system.Pagamento.TableViewPagamento;
import util.Stages;
import util.Valores;

public class ResumoPagamentoController {
	int id;
	Timestamp data;
	boolean enable;
	Parent root;

    @FXML
    private TableView<TableViewPagamento> tv_pagamento;

    @FXML
    private TableColumn<TableViewPagamento, Integer> tc_pagamentoId;

    @FXML
    private TableColumn<TableViewPagamento, String> tc_pagamentoDesc;

    @FXML
    private TableColumn<TableViewPagamento, String> tc_pagamentoFunc;

    @FXML
    private TableColumn<TableViewPagamento, String> tc_pagamentoPag;

    @FXML
    private TableColumn<TableViewPagamento, String> tc_pagamentoData;

    @FXML
    private TableColumn<TableViewPagamento, Float> tc_pagamentoValor;

    @FXML
    void act_Fechar(ActionEvent event) {
    	if (root != null)
    		root.setDisable(false);

    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
	public void initialize() {
		try {
			if (Valores.getConnection() == null || Valores.getUsuario() == null || Valores.getController() == null)
	    		Platform.exit();
			
			try {
				tc_pagamentoId.setCellValueFactory(new PropertyValueFactory<>("id"));
				tc_pagamentoDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
				tc_pagamentoFunc.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
				tc_pagamentoPag.setCellValueFactory(new PropertyValueFactory<>("pagamento"));
				tc_pagamentoData.setCellValueFactory(new PropertyValueFactory<>("data"));
				tc_pagamentoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

	        	tv_pagamento.setRowFactory((TableView<TableViewPagamento> tablePagamento) -> {
	        		final TableRow<TableViewPagamento> row = new TableRow<>();
	        		final ContextMenu rowMenu = new ContextMenu();
	        		MenuItem removePagamento = new MenuItem("Deletar pagamento");

	        		//Remover Pagamento
	        		removePagamento.setOnAction((ActionEvent event) -> {
	        			try {
	        				if (!enable) {
		        				Alert alert = Stages.novoAviso("Você deseja remover o pagamento?");
		    					ButtonType buttonConfirm = new ButtonType("Continuar", ButtonData.OK_DONE);
		    					ButtonType buttonCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		    					alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);
	
		    					Optional<ButtonType> result = alert.showAndWait();
		    					if (result.get() == buttonConfirm) {
		    						if (!Pagamento.removePagamento(row.getItem().getId())) {
		            					throw new Exception("Erro ao remover pagamento");
		            				}
		    						reload();
		    					}
		        			} else
								throw new Exception("Essa comanda está finalizada, não é possível fazer esta ação.");
	        			} catch (Exception e) {
	        				Stages.novoAlerta(e.getMessage(), "", true);
	        			}
	        		});
	        		rowMenu.getItems().addAll(removePagamento);
	        		row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu)null));
	        		return row;
	        	});
	        } catch (Exception e) {
	        	Stages.novoAlerta(e.getMessage(), "", true);
	        }
    	} catch (Exception e) {
    		Stages.novoAlerta(e.getLocalizedMessage(), "", true);
    	}
	}

	public void vizualizaPagamento(int id, Timestamp data, boolean enable, Parent root) {
		try {
			this.root = root;
			this.enable = enable;
			this.id = id;
			this.data = data;
			tv_pagamento.setItems(Pagamento.getAllPagamento(id, data));
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", false);
		}
	}

	private void reload() throws Exception {
		tv_pagamento.getItems().clear();
		tv_pagamento.setItems(Pagamento.getAllPagamento(id, data));
	}
}