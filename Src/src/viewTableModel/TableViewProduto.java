package viewTableModel;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

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

	public SimpleIntegerProperty getId() {
		return id;
	}
	public SimpleStringProperty getNome() {
		return nome;
	}
	public SimpleFloatProperty getValor() {
		return valor;
	}
	public SimpleIntegerProperty getQuantidade() {
		return quantidade;
	}
		
}
