package br.com.digio.api.controller;

import br.com.digio.api.dto.CompraResponseDTO;
import br.com.digio.api.service.CompraService;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public List<CompraResponseDTO> listarComprasOrdenadas() {
        return service.listarComprasOrdenadasPorValor();
    }
}
