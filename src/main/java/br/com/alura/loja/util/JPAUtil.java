package br.com.alura.loja.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	/*
	 * O EnEntityManagerFactory deve ser instanciado com a classe/metodo
	 * Persistence.createEntityManagerFactory, que recebe dois parametros. O
	 * primeiro parâmetro é o nome da tag persistence-unit do arquivo
	 * persistence.xml
	 */
	private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("loja");

	public static EntityManager getEntityManager() {
		return FACTORY.createEntityManager();
	}
}
