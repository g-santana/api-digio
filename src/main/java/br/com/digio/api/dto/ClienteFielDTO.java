package br.com.digio.api.dto;

import java.math.BigDecimal;

public record ClienteFielDTO(
        String nome,
        String cpf,
        Long totalCompras,
        BigDecimal totalGasto
) {}