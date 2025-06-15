package br.com.digio.api.controller;

import br.com.digio.api.service.CompraService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CompraController {
    private final CompraService service;

    public CompraController(CompraService service) {
        this.service = service;
    }
}
