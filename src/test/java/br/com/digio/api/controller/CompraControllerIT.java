package br.com.digio.api.controller;

import br.com.digio.api.dto.RecomendacaoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompraControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getUrl(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void testListarComprasOrdenadas() {
        ResponseEntity<List> response = restTemplate.getForEntity(getUrl("/compras"), List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testMaiorCompraPorAnoNaoEncontrada() {
        ResponseEntity<String> response = restTemplate.getForEntity(getUrl("/maior_compra/2025"), String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("Nenhuma compra encontrada"));
    }

    @Test
    void testClientesMaisFieis() {
        ResponseEntity<List> response = restTemplate.getForEntity(getUrl("/clientes_fieis"), List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRecomendacaoTipoVinho() {
        ResponseEntity<String> response = restTemplate.getForEntity(getUrl("/recomendacao/88901767767/tipo"), String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            RecomendacaoDTO dto = restTemplate.getForObject(getUrl("/recomendacao/88901767767/tipo"), RecomendacaoDTO.class);
            assertNotNull(dto);
        } else {
            assertTrue(response.getBody().contains("Nenhuma"));
        }
    }
}
