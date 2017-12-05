package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum com os meios de pagamento disponiveis
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi
 *
 */
public enum MeioPagamentoEnum {
	Dinheiro(1), Cartao1(2), Cartao2(3);

	private static final Map<Integer, MeioPagamentoEnum> Lookup = new HashMap<Integer, MeioPagamentoEnum>();
    static {
        for (MeioPagamentoEnum d : MeioPagamentoEnum.values()) {
        	Lookup.put(d.getValor(), d);
        }
    }

	private final int value;
	MeioPagamentoEnum(int value) {
		this.value = value;
	}

	public int getValor() {
		return value;
	}

	public static MeioPagamentoEnum get(int id) {
        return Lookup.get(id);
    }

	public static MeioPagamentoEnum getKey(String key) {
		if (key.contains("Dinheiro"))
			return Dinheiro;
		else if (key.contains("Cartao de Debito"))
			return Cartao1;
		else if (key.contains("Cartao de Credito"))
			return Cartao2;
		return null;
	}

	@Override
	public String toString() {
		switch (value) {
			case 1 :
				return "Dinheiro";
			case 2 :
				return "Cartao de Debito";
			case 3 :
				return "Cartao de Credito";
			default :
				return "Error";
		}
	}
}
