package valores;

import bancodedados.ConectaBanco;
import sistema.Usuario;

public class Valores {

	private ConectaBanco database;

	private Usuario usuario;

	public Usuario setUsuario(Usuario usr) {
		usuario = usr;
		return usr;
	}

	public ConectaBanco setBanco(ConectaBanco db) {
		database = db;
		return db;
	}

}
