package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum com os cargos disponiveis
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi
 *
 */
public enum FuncionarioEnum {
		Gerente(1), Usuario(2);

		private static final Map<Integer, FuncionarioEnum> lookup = new HashMap<Integer, FuncionarioEnum>();
	    static {
	        for (FuncionarioEnum d : FuncionarioEnum.values()) {
	            lookup.put(d.getValor(), d);
	        }
	    }

		private final int value;
		FuncionarioEnum(int value) {
			this.value = value;
		}

		public int getValor() {
			return value;
		}

		public static FuncionarioEnum get(int id) {
	        return lookup.get(id);
	    }

		@Override
		public String toString() {
			switch (value) {
				case 1 :
					return "Gerente";
				case 2 :
					return "Usuario";
				default :
					return "Error";
			}
		}
	}