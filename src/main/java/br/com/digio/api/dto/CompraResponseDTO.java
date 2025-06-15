package br.com.digio.api.dto;

import br.com.digio.api.entity.Compra;

import java.math.BigDecimal;

public record CompraResponseDTO(
        String nomeCliente,
        String cpfCliente,
        String tipoVinho,
        BigDecimal preco,
        String safra,
        String anoCompra,
        Integer quantidade,
        BigDecimal valorTotal
) {
    public static CompraResponseDTO aPartirDaCompra(Compra compra) {
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