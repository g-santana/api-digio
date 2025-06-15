package br.com.digio.api.service;

import br.com.digio.api.repository.CompraRepository;
import org.springframework.stereotype.Service;

@Service
public class CompraService {
    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }
}
