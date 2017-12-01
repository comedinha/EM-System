package util;

public enum MeioPagamento {
	Dinheiro(1), Cartão(2);

	private final int valor;
	MeioPagamento(int valorOpcao){
		valor = valorOpcao;
	}

	public int getValor(){
		return valor;
	}
}
