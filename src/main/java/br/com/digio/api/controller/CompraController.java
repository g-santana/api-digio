package br.com.digio.api.controller;

import br.com.digio.api.dto.ClienteFielDTO;
import br.com.digio.api.dto.CompraResponseDTO;
import br.com.digio.api.dto.RecomendacaoDTO;
import br.com.digio.api.service.CompraService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class CompraController {
    private final CompraService service;

    public CompraController(CompraService service) {
        this.service = service;
    }

    @GetMapping("compras")
    public List<CompraResponseDTO> listarComprasOrdenadas() {
        return service.listarComprasOrdenadasPorValor();
    }

    @GetMapping("maior_compra/{ano}")
    public CompraResponseDTO maiorCompraPorAno(@PathVariable String ano) {
        return service.obterMaiorCompraPorAno(ano);
    }

    @GetMapping("/clientes_fieis")
    public List<ClienteFielDTO> clientesMaisFieis() {
        return service.obterTop3ClientesFieis();
    }

    @GetMapping("/recomendacao/{cpf}/tipo")
    public RecomendacaoDTO recomendacaoTipoVinho(@PathVariable String cpf) {
        return service.recomendarTipoMaisComprado(cpf);
    }
}
