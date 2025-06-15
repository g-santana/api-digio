package br.com.digio.api.service;

import br.com.digio.api.dto.CompraResponseDTO;
import br.com.digio.api.entity.Compra;
import br.com.digio.api.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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
