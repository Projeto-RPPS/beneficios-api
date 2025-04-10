package com.rpps.beneficios.mockapi;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contribuintes")

public class MockContribuinteController {
    // Endpoint que retorna um tempo de contribuição mockado
    @GetMapping("/tempo/{cpf}")
    public int obterTempoContribuicao(@PathVariable String cpf) {
        // Retornando um valor mockado fixo para teste (ex: 40 meses)
        return 40;
    }
}
