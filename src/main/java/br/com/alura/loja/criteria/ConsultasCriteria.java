package br.com.alura.loja.criteria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;

public class ConsultasCriteria {

	public static void paginandoResultados(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery)
				.setFirstResult(0) // primeiro registro da pagina
				.setMaxResults(2); // total de registros por pagina
		List<Produto> lista = typedQuery.getResultList();
		lista.forEach(produto -> System.out
				.println("Produto: " + produto.getNome() + ", preço: " + produto.getPreco() + "."));

	}
	public static void ordenandoResultados(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("nome")));
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		lista.forEach(produto -> System.out
				.println("Produto: " + produto.getNome() + ", preço: " + produto.getPreco() + "."));
		
	}

	public static void passandoParametros(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		Produto produto = typedQuery.getSingleResult();
		System.out.println(produto.getId() + ", " + produto.getNome());

	}

	public static void construindoObjetos(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(criteriaBuilder.construct(Produto.class, root.get("nome"), root.get("descricao"),
				root.get("preco"), root.get("categoria")));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		lista.forEach(produto -> System.out
				.println("Produto: " + produto.getNome() + ", preço: " + produto.getPreco() + "."));

	}

	public static void fazendoProjecoes(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.multiselect(root.get("id"), root.get("nome"), root.get("descricao"), root.get("preco"));

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();
		lista.forEach(arr -> System.out.println(String.format("%s, %s, %s, %s", arr)));

	}

	public static void escolhendoRetorno(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Categoria> criteriaQuery = criteriaBuilder.createQuery(Categoria.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root.get("id_categoria"));

		TypedQuery<Categoria> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Categoria> lista = typedQuery.getResultList();
		lista.forEach(categoria -> System.out.println("Categoria: " + categoria.getNome()));

	}

	public static void primeirasConsultas(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		lista.forEach(u -> System.out.println(u.toString()));

	}
}
