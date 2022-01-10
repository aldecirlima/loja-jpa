package br.com.alura.loja.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.alura.loja.criteria.ConsultasCriteria;
import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProdutos {

	public static void main(String[] args) {

		Categoria celulares = new Categoria("celulares");
		Categoria livros = new Categoria("livros");
		Categoria informatica = new Categoria("informatica");

		Produto celular = new Produto("Xiaomi Redmi", "Muito Legal", new BigDecimal("800"), celulares);
		Produto livro = new Produto("Xhtml e criteria", "Muito bom", new BigDecimal("120"), livros);
		Produto mouse = new Produto("Mouse", "Basico", new BigDecimal("60"), informatica);
		Produto notebook = new Produto("Notebook Asus", "Usado, mas em bom estado", new BigDecimal("800"), informatica);

		EntityManager em = JPAUtil.getEntityManager();

		CategoriaDao categoriaDao = new CategoriaDao(em);
		ProdutoDao produtoDao = new ProdutoDao(em);

		em.getTransaction().begin();
		
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(livros);
		categoriaDao.cadastrar(informatica);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(livro);
		produtoDao.cadastrar(mouse);
		produtoDao.cadastrar(notebook);
		
		
		
//		flush() sincroniza a entidade com o banco de dados, sem persistir/commit
		
//		clear() limpa os dados no entityManager - a entidade deixa de ser gerenciada
		
//		merge() insere novamente a entidade no entityManager, ela volta a ser gerenciada.
		
		celulares.setNome("categ celulares");
		
		em.flush();
//		ConsultasCriteria.primeirasConsultas(em);
		em.clear();
		
//		Alterações na entidade após o comando close() no entityManager, não são persistidas no banco de dados.
//		Exemplo:
		
		celulares.setNome("cell");
		
		celulares = em.merge(celulares);
		
		celulares.setNome("1234"); 
		
		System.out.println(celulares.getNome());
		
		em.flush();
		
		produtoDao.remover(celular);
		
		ConsultasCriteria.primeirasConsultas(em);
		ConsultasCriteria.escolhendoRetorno(em);
		
		
		em.close();

	}

}
