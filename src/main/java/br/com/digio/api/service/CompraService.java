package br.com.digio.api.service;

import br.com.digio.api.dto.ClienteFielDTO;
import br.com.digio.api.dto.CompraResponseDTO;
import br.com.digio.api.dto.RecomendacaoDTO;
import br.com.digio.api.entity.Compra;
import br.com.digio.api.exception.NotFoundException;
import br.com.digio.api.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompraService {
    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public List<CompraResponseDTO> listarComprasOrdenadasPorValor() {
        return compraRepository.findAllWithClienteProdutoOrderByValor()
                .stream().map(CompraResponseDTO::aPartirDaCompra).toList();
    }

    public CompraResponseDTO obterMaiorCompraPorAno(String ano) {
        return compraRepository.findByAnoCompraOrderByValorDesc(ano).stream()
                .findFirst()
                .map(CompraResponseDTO::aPartirDaCompra)
                .orElseThrow(() -> new NotFoundException("Nenhuma compra encontrada para o ano " + ano));
    }

    public List<ClienteFielDTO> obterTop3ClientesFieis() {
        return compraRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        compra -> compra.getCliente().getCpf(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                compras -> {
                                    var cliente = compras.getFirst().getCliente();
                                    long totalCompras = compras.size();
                                    BigDecimal totalGasto = compras.stream()
                                            .map(Compra::getValorTotal)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                                    return ClienteFielDTO.montarClienteFiel(cliente, totalCompras, totalGasto);
                                }
                        )
                ))
                .values().stream()
                .sorted(Comparator.comparing(ClienteFielDTO::totalGasto).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public RecomendacaoDTO recomendarTipoMaisComprado(String cpf) {
        return compraRepository.findByClienteCpf(cpf).stream()
                .collect(Collectors.groupingBy(
                        c -> c.getProduto().getTipoVinho(),
                        Collectors.summingInt(Compra::getQuantidade)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> RecomendacaoDTO.montarRecomendacao(e.getKey()))
                .orElseThrow(() -> new NotFoundException("Nenhuma compra encontrada para o CPF informado."));
    }
}
