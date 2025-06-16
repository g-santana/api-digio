package br.com.digio.api.service;

import br.com.digio.api.dto.ClienteFielDTO;
import br.com.digio.api.dto.CompraResponseDTO;
import br.com.digio.api.dto.RecomendacaoDTO;
import br.com.digio.api.entity.Cliente;
import br.com.digio.api.entity.Compra;
import br.com.digio.api.entity.Produto;
import br.com.digio.api.exception.NotFoundException;
import br.com.digio.api.repository.CompraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompraServiceTest {

    private CompraRepository compraRepository;
    private CompraService compraService;

    @BeforeEach
    void setUp() {
        compraRepository = mock(CompraRepository.class);
        compraService = new CompraService(compraRepository);
    }

    @Test
    void listarComprasOrdenadasPorValor_retornaLista() {
        Cliente cliente = new Cliente(1L, "Ana", "123");
        Produto produto = new Produto(1, "Tinto", new BigDecimal("100.0"), "2018", "2019");
        Compra compra = new Compra(1L, cliente, produto, 1, new BigDecimal("100.0"));

        when(compraRepository.findAllWithClienteProdutoOrderByValor()).thenReturn(List.of(compra));

        List<CompraResponseDTO> result = compraService.listarComprasOrdenadasPorValor();

        assertEquals(1, result.size());
        assertEquals("Tinto", result.getFirst().tipoVinho());
    }

    @Test
    void listarComprasOrdenadasPorValor_listaVazia() {
        when(compraRepository.findAllWithClienteProdutoOrderByValor()).thenReturn(Collections.emptyList());

        List<CompraResponseDTO> result = compraService.listarComprasOrdenadasPorValor();

        assertTrue(result.isEmpty());
    }

    @Test
    void obterMaiorCompraPorAno_retornaCompra() {
        Cliente cliente = new Cliente(1L, "Ana", "123");
        Produto produto = new Produto(1, "Tinto", new BigDecimal("100.0"), "2020", "2020");
        Compra compra = new Compra(1L, cliente, produto, 1, new BigDecimal("100.0"));

        when(compraRepository.findByAnoCompraOrderByValorDesc("2020")).thenReturn(List.of(compra));

        CompraResponseDTO dto = compraService.obterMaiorCompraPorAno("2020");

        assertEquals("Tinto", dto.tipoVinho());
        assertEquals(new BigDecimal("100.0"), dto.valorTotal());
    }

    @Test
    void obterMaiorCompraPorAno_arremessaNotFound() {
        when(compraRepository.findByAnoCompraOrderByValorDesc("2021")).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> compraService.obterMaiorCompraPorAno("2021"));
    }

    @Test
    void obterTop3ClientesFieis_retornaTop3Clientes() {
        Cliente a = new Cliente(1L, "Ana", "111");
        Cliente b = new Cliente(2L, "Bruno", "222");
        Cliente c = new Cliente(3L, "Cris", "333");
        Produto p = new Produto(1, "Tinto", new BigDecimal("100.0"), "2018", "2020");

        List<Compra> todas = List.of(
                new Compra(1L, a, p, 1, new BigDecimal("100.00")),
                new Compra(2L, a, p, 2, new BigDecimal("200.00")),
                new Compra(3L, b, p, 1, new BigDecimal("100.00")),
                new Compra(4L, c, p, 3, new BigDecimal("300.00")),
                new Compra(5L, c, p, 2, new BigDecimal("200.00"))
        );

        when(compraRepository.findAll()).thenReturn(todas);

        List<ClienteFielDTO> top = compraService.obterTop3ClientesFieis();

        assertEquals(3, top.size());
        assertEquals("Cris", top.getFirst().nome());
        assertTrue(top.getFirst().totalGasto().compareTo(top.get(1).totalGasto()) > 0);
    }

    @Test
    void obterTop3ClientesFieis_vazio() {
        when(compraRepository.findAll()).thenReturn(Collections.emptyList());

        List<ClienteFielDTO> top = compraService.obterTop3ClientesFieis();

        assertTrue(top.isEmpty());
    }

    @Test
    void recomendarTipoMaisComprado_retornaTipo() {
        Cliente cliente = new Cliente(1L, "Luis", "999");
        Produto p1 = new Produto(1, "Tinto", new BigDecimal("100.0"), "2018", "2020");
        Produto p2 = new Produto(2, "Branco", new BigDecimal("120.0"), "2019", "2020");

        Compra c1 = new Compra(1L, cliente, p1, 3, new BigDecimal("300.00"));
        Compra c2 = new Compra(2L, cliente, p2, 1, new BigDecimal("120.00"));
        Compra c3 = new Compra(3L, cliente, p1, 2, new BigDecimal("200.00"));

        when(compraRepository.findByClienteCpf("999")).thenReturn(List.of(c1, c2, c3));

        RecomendacaoDTO recomendacao = compraService.recomendarTipoMaisComprado("999");

        assertEquals("Tinto", recomendacao.tipoMaisComprado());
    }

    @Test
    void recomendarTipoMaisComprado_arremessaNotFound() {
        when(compraRepository.findByClienteCpf("000")).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> compraService.recomendarTipoMaisComprado("000"));
    }
}