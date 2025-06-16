package br.com.digio.api.controller;

import br.com.digio.api.dto.ClienteFielDTO;
import br.com.digio.api.dto.CompraResponseDTO;
import br.com.digio.api.dto.RecomendacaoDTO;
import br.com.digio.api.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/", produces = "application/json")
public class CompraController {
    private final CompraService service;

    public CompraController(CompraService service) {
        this.service = service;
    }

    @Operation(summary = "GET /compras", description = "Retorna uma lista de todas as compras ordenadas por valor total (desc)")
    @ApiResponse(responseCode = "200", description = "Lista de todas as compras retornada com sucesso")
    @GetMapping(path = "compras", produces = "application/json")
    public List<CompraResponseDTO> listarComprasOrdenadas() {
        return service.listarComprasOrdenadasPorValor();
    }

    @Operation(summary = "GET /maior_compra/{ano}", description = "Retorna a maior compra realizada no ano informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maior compra do ano retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma compra encontrada para o ano informado")
    })
    @GetMapping(path = "maior_compra/{ano}", produces = "application/json")
    public CompraResponseDTO maiorCompraPorAno(@PathVariable String ano) {
        return service.obterMaiorCompraPorAno(ano);
    }

    @Operation(summary = "GET /clientes_fieis", description = "Retorna o top 3 dos clientes mais fiéis")
    @ApiResponse(responseCode = "200", description = "Top 3 clientes mais fiéis retornado com sucesso")
    @GetMapping(path = "/clientes_fieis", produces = "application/json")
    public List<ClienteFielDTO> clientesMaisFieis() {
        return service.obterTop3ClientesFieis();
    }

    @Operation(summary = "GET /recomendacao/{cpf}/tipo", description = "Recomenda o tipo de vinho mais comprado pelo cliente informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recomendação de tipo de vinho mais comprado retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado com o CPF informado")
    })
    @GetMapping(path = "/recomendacao/{cpf}/tipo", produces = "application/json")
    public RecomendacaoDTO recomendacaoTipoVinho(@PathVariable String cpf) {
        return service.recomendarTipoMaisComprado(cpf);
    }
}
