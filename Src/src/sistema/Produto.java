package sistema;

public class Produto {
	
	public void adicionaProduto(int id, String nome, float valor, int qtde) {
		dao.Produto cadastro = new dao.Produto();
		
		cadastro.inserir(id, nome, valor, qtde);
	}

	public void getProduto() {
		
	}
	
	public void getAllProduto() {
		
	}

	public void editaProduto() {

	}

}
