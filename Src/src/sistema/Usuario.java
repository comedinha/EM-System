package sistema;

public class Usuario {

	private static String nome;
	private static int funcionarioId;
	private static int funcao;

	public String getNome() {
		return Usuario.nome;
	}

	public int getId() {
		return Usuario.funcionarioId;
	}

	public int getFuncao() {
		return Usuario.funcao;
	}

	public Usuario(String nome, int id, int funcao) {
		Usuario.nome = nome;
		Usuario.funcionarioId = id;
		Usuario.funcao = funcao;
	}

	public void criaUsuario(int func, String nome, String usr, String pass) throws Exception {
		dao.Usuario.inserir(1, nome, usr, pass);
	}

	public static boolean login(String usr, String pass) throws Exception {
		return dao.Usuario.login(usr, pass);
	}
}
