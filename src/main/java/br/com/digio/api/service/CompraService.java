package br.com.digio.api.service;

import br.com.digio.api.dto.ClienteFielDTO;
import br.com.digio.api.dto.CompraResponseDTO;
import br.com.digio.api.entity.Compra;
import br.com.digio.api.exception.NotFoundException;
import br.com.digio.api.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompraService {
    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public List<CompraResponseDTO> listarComprasOrdenadasPorValor() {
        return compraRepository.findAll().stream()
                .sorted(Comparator.comparing(Compra::getValorTotal))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CompraResponseDTO obterMaiorCompraPorAno(String ano) {
        return compraRepository.findAll().stream()
                .filter(c -> Objects.equals(c.getProduto().getAnoCompra(), ano))
                .max(Comparator.comparing(Compra::getValorTotal))
                .map(this::toDTO)
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

                                    return new ClienteFielDTO(
                                            cliente.getNome(),
                                            cliente.getCpf(),
                                            totalCompras,
                                            totalGasto
                                    );
                                }
                        )
                ))
                .values().stream()
                .sorted(Comparator.comparing(ClienteFielDTO::totalGasto).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    private CompraResponseDTO toDTO(Compra compra) {
        return new CompraResponseDTO(
                compra.getCliente().getNome(),
                compra.getCliente().getCpf(),
                compra.getProduto().getTipoVinho(),
                compra.getProduto().getPreco(),
                compra.getProduto().getSafra(),
                compra.getProduto().getAnoCompra(),
                compra.getQuantidade(),
                compra.getValorTotal()
        );
    }
}
