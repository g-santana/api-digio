package br.com.digio.api.repository;

import br.com.digio.api.entity.Compra;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(statements = {
        "INSERT INTO clientes (id, nome, cpf) VALUES (1, 'João Silva', '12345678900')",
        "INSERT INTO produtos (codigo, tipo_vinho, preco, safra, ano_compra) VALUES (101, 'Tinto', 150.00, '2024', '2024')",
        "INSERT INTO compras (cliente_id, produto_codigo, quantidade, valor_total) VALUES (1, 101, 2, 300.00)"
})
public class CompraRepositoryTest {

    @Autowired
    private CompraRepository compraRepository;

    @Test
    void verifyTablesExist() {
        var compras = compraRepository.findAll();
        assertFalse(compras.isEmpty());
    }

    @Test
    void testFindAllWithClienteProdutoOrderByValor() {
        List<Compra> compras = compraRepository.findAllWithClienteProdutoOrderByValor();
        assertNotNull(compras);
        assertFalse(compras.isEmpty());
        assertEquals(1, compras.size());
        assertEquals(new BigDecimal("300.00"), compras.getFirst().getValorTotal());
    }

    @Test
    void testFindByAnoCompraOrderByValorDesc() {
        List<Compra> compras = compraRepository.findByAnoCompraOrderByValorDesc("2024");
        assertNotNull(compras);
        assertFalse(compras.isEmpty());
        assertEquals("2024", compras.getFirst().getProduto().getAnoCompra());
    }

    @Test
    void testFindByClienteCpf() {
        List<Compra> compras = compraRepository.findByClienteCpf("12345678900");
        assertNotNull(compras);
        assertFalse(compras.isEmpty());
        assertEquals(1, compras.size());
        assertEquals("Tinto", compras.getFirst().getProduto().getTipoVinho());
        assertEquals("João Silva", compras.getFirst().getCliente().getNome());
    }
}