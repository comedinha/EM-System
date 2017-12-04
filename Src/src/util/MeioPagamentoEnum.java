package util;

public enum MeioPagamentoEnum {
	Dinheiro(1), Cartao(2);

	private final int value;
	MeioPagamentoEnum(int value){
		this.value = value;
	}

	public int getValor(){
		return value;
	}

	@Override
	public String toString() {
		switch (value) {
			case 1 :
				return "Dinheiro";
			case 2 :
				return "Cartão";
			default :
				return "Error";
		}
	}
}
