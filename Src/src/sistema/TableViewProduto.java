package sistema;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public static class TableViewProduto {
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty nome;
	private final SimpleFloatProperty valor;
	private final SimpleIntegerProperty quantidade;
	

	public TableViewProduto(SimpleIntegerProperty id, SimpleStringProperty nome, SimpleFloatProperty valor,
			SimpleIntegerProperty quantidade) {
		this.id = id;
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
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
