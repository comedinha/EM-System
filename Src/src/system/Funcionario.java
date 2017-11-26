package system;

public class Funcionario {

	private static String nome;
	private static int funcionarioId;
	private static int funcao;

	public String getNome() {
		return nome;
	}

	public int getId() {
		return funcionarioId;
	}

	public int getFuncao() {
		return funcao;
	}

	public Funcionario(String nome, int id, int funcao) {
		Funcionario.nome = nome;
		Funcionario.funcionarioId = id;
		Funcionario.funcao = funcao;
	}

	public void criaUsuario(int func, String nome, String usr, String pass) throws Exception {
		dao.Funcionario.inserir(1, nome, usr, pass);
	}

	public static boolean login(String usr, String pass) throws Exception {
		return dao.Funcionario.login(usr, pass);
	}
}
