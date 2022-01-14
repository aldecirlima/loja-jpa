package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class CadastroDePedido {

	public static void main(String[] args) {

		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		Produto produto1 = produtoDao.buscarPorId(1L);
		Produto produto2 = produtoDao.buscarPorId(2L);
		Produto produto3 = produtoDao.buscarPorId(3L);
		ClienteDao clienteDao = new ClienteDao(em);
		Cliente cliente = clienteDao.buscarPorId(1L);

		em.getTransaction().begin();

		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, produto1));
		pedido.adicionarItem(new ItemPedido(20, pedido, produto2));
		pedido.adicionarItem(new ItemPedido(15, pedido, produto3));

		PedidoDao pedidoDao = new PedidoDao(em);
		pedidoDao.cadastrar(pedido);

		em.getTransaction().commit();

		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
		System.out.println("VALOR TOTAL: " + totalVendido);

		List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();
		System.out.println("Produto             Quant   Data");
		for (RelatorioDeVendasVo rel : relatorio) {
			String space = " ";
			System.out.println((rel.getNomeProduto().toString().length() < 20 
					? rel.getNomeProduto()
					: rel.getNomeProduto().toString().substring(0, 11) + " ")
					+ space.repeat(20 - rel.getNomeProduto().toString().length() > 0
							? 20 - rel.getNomeProduto().toString().length()
							: 0)
					+ rel.getQuantidadeVendida() + space.repeat(8 - rel.getQuantidadeVendida().toString().length())
					+ rel.getDataUltimaVenda());
		}

		em.close();

	}

	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("celulares");
		Categoria livros = new Categoria("livros");
		Categoria informatica = new Categoria("informatica");

		Produto celular = new Produto("Xiaomi Redmi", "Muito Legal", new BigDecimal("800"), celulares);
		Produto livro = new Produto("Xhtml e criteria", "Muito bom", new BigDecimal("120"), livros);
		Produto mouse = new Produto("Mouse", "Basico", new BigDecimal("60"), informatica);
		Produto mouse2 = new Produto("Mouse", "Plus", new BigDecimal("80"), informatica);
		Produto notebook = new Produto("Notebook Asus", "Usado, mas em bom estado", new BigDecimal("1200"),
				informatica);
		Cliente cliente = new Cliente("Rodrigo", "123456");

		EntityManager em = JPAUtil.getEntityManager();

		CategoriaDao categoriaDao = new CategoriaDao(em);
		ProdutoDao produtoDao = new ProdutoDao(em);
		ClienteDao clienteDao = new ClienteDao(em);

		em.getTransaction().begin();

		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(livros);
		categoriaDao.cadastrar(informatica);

		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(livro);
		produtoDao.cadastrar(mouse);
		produtoDao.cadastrar(mouse2);
		produtoDao.cadastrar(notebook);
		clienteDao.cadastrar(cliente);

		em.getTransaction().commit();

		em.close();
	}

}
