package br.com.digio.api.repository;

import br.com.digio.api.entity.Cliente;
import br.com.digio.api.entity.Compra;
import br.com.digio.api.entity.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@DataJpaTest
class CompraRepositoryIT {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CompraRepository compraRepository;

    private Cliente cliente;
    private Produto produto;
    private Compra compra;

    @BeforeEach
    void setup() {
        cliente = new Cliente(1L, "João Silva", "12345678900");
        produto = new Produto(101, "Tinto", BigDecimal.valueOf(150.00), "2022", "2024");
        cliente = clienteRepository.save(cliente);
        produto = produtoRepository.save(produto);
        compra = new Compra(1L, cliente, produto, 2, BigDecimal.valueOf(300.00));
        compraRepository.save(compra);
    }

    @Test
    void testFindAllWithClienteProdutoOrderByValor() {
        List<Compra> compras = compraRepository.findAllWithClienteProdutoOrderByValor();
        assertFalse(compras.isEmpty());
        assertEquals(1, compras.size());
        assertEquals(BigDecimal.valueOf(300.00), compras.getFirst().getValorTotal());
    }

    @Test
    void testFindByAnoCompraOrderByValorDesc() {
        List<Compra> compras = compraRepository.findByAnoCompraOrderByValorDesc("2024");
        assertFalse(compras.isEmpty());
        assertEquals("2024", compras.getFirst().getProduto().getAnoCompra());
    }

    @Test
    void testFindByClienteCpf() {
        List<Compra> compras = compraRepository.findByClienteCpf("12345678900");
        assertFalse(compras.isEmpty());
        assertEquals("João Silva", compras.getFirst().getCliente().getNome());
    }
}