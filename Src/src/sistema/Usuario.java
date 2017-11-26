package sistema;

public class Usuario {

	private String nome;

	private int funcao;

	public String getNome() {
		return nome;
	}

	public int getFuncao() {
		return funcao;
	}

	public void criaUsuario(int func, String nome, String usr, String pass) throws Exception {
		dao.Usuario.inserir(1, nome, usr, pass);
	}

	public boolean login(String usr, String pass) throws Exception {
		return dao.Usuario.login(usr, pass);
	}
}
