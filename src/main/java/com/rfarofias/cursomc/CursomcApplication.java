package com.rfarofias.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rfarofias.cursomc.domain.Categoria;
import com.rfarofias.cursomc.domain.Cidade;
import com.rfarofias.cursomc.domain.Cliente;
import com.rfarofias.cursomc.domain.Estado;
import com.rfarofias.cursomc.domain.Morada;
import com.rfarofias.cursomc.domain.Pagamento;
import com.rfarofias.cursomc.domain.PagamentoComBoleto;
import com.rfarofias.cursomc.domain.PagamentoComCartao;
import com.rfarofias.cursomc.domain.Pedido;
import com.rfarofias.cursomc.domain.Produto;
import com.rfarofias.cursomc.domain.enums.EstadoPagamento;
import com.rfarofias.cursomc.domain.enums.TipoCliente;
import com.rfarofias.cursomc.repositories.CategoriaRepository;
import com.rfarofias.cursomc.repositories.CidadeRepository;
import com.rfarofias.cursomc.repositories.ClienteRepository;
import com.rfarofias.cursomc.repositories.EstadoRepository;
import com.rfarofias.cursomc.repositories.MoradaRepository;
import com.rfarofias.cursomc.repositories.PagamentoRepository;
import com.rfarofias.cursomc.repositories.PedidoRepository;
import com.rfarofias.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private MoradaRepository moradaRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 980.00);
		Produto p2 = new Produto(null, "Impressora", 150.00);
		Produto p3 = new Produto(null, "Mouse", 25.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll( Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "11211323", TipoCliente.PESSOA_SINGULAR);
		cli1.getTelefones().addAll(Arrays.asList("266999409", "966752324"));
		
		Morada mor1 = new Morada(null, "Rua Flores", "300", "Apto 203", "Jardim", "355996", cli1, c1);
		Morada mor2 = new Morada(null, "Avenida Matos", "105", "Sala 800", "Centro", "232656556", cli1, c2);
		
		cli1.getMoradas().addAll(Arrays.asList(mor1, mor2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		moradaRepository.saveAll(Arrays.asList(mor1, mor2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"),cli1, mor1); 
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"),cli1, mor2);
		
		Pagamento pagt1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagt1);
		
		Pagamento pagt2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagt2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagt1, pagt2));
	}

}
