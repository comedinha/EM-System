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

	private static final Map<Integer, MeioPagamentoEnum> lookup = new HashMap<Integer, MeioPagamentoEnum>();
    static {
        for (MeioPagamentoEnum d : MeioPagamentoEnum.values()) {
            lookup.put(d.getValor(), d);
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
        return lookup.get(id);
    }

	@Override
	public String toString() {
		switch (value) {
			case 1 :
				return "Dinheiro";
			case 2 :
				return "Cartão de Debito";
			case 3 :
				return "Cartão de Crédito";
			default :
				return "Error";
		}
	}
}
