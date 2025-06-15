package br.com.digio.api.dto;

import java.math.BigDecimal;

public record CompraResponseDTO(
        String nomeCliente,
        String cpfCliente,
        String tipoVinho,
        BigDecimal preco,
        String safra,
        Integer anoCompra,
        Integer quantidade,
        BigDecimal valorTotal
) {}