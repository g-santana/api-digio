package br.com.digio.api.dto;

public record RecomendacaoDTO(
        String tipoMaisComprado
) {
    public static RecomendacaoDTO montarRecomendacao(String tipoMaisComprado) {
        return new RecomendacaoDTO(tipoMaisComprado);
    }
}