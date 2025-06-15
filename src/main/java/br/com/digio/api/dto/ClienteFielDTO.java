package br.com.digio.api.dto;

import br.com.digio.api.entity.Cliente;

import java.math.BigDecimal;

public record ClienteFielDTO(
        String nome,
        String cpf,
        Long totalCompras,
        BigDecimal totalGasto
) {
    public static ClienteFielDTO montarClienteFiel(Cliente cliente, Long totalCompras, BigDecimal totalGasto) {
        return new ClienteFielDTO(
                cliente.getNome(),
                cliente.getCpf(),
                totalCompras,
                totalGasto
        );
    }
}