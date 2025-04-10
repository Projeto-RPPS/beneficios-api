package com.rpps.beneficios.controller;
import com.rpps.beneficios.DTO.BeneficioDTO;

import com.rpps.beneficios.model.Beneficio;
import com.rpps.beneficios.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/beneficios")

public class BeneficioController {

    @Autowired
    private BeneficioService BeneficioService;


    // criar beneficio
    @PostMapping
    public ResponseEntity<BeneficioDTO> criarBeneficio(@RequestBody Beneficio beneficio) {
        Beneficio criado = BeneficioService.criarBeneficio(beneficio);

        // Cria um DTO a partir do objeto Beneficio salvo
        BeneficioDTO dto = new BeneficioDTO(
                criado.getIdBeneficio(),
                criado.getTipo(),
                criado.getDescricao(),
                criado.getTempoMinimoMeses(),
                criado.getPercentualBaseMedioContribuicoes(),
                criado.getMensagem(),
                criado.isAtivo()
        );

        return ResponseEntity.status(201).body(dto); // 201 Created
    }


    // listar beneficio
    @GetMapping
    public ResponseEntity<List<BeneficioDTO>> listarBeneficios() {
        List<Beneficio> beneficios = BeneficioService.listarBeneficio();

        // Converter cada Beneficio em BeneficioDTO
        List<BeneficioDTO> dtos = beneficios.stream()
                .map(b -> new BeneficioDTO(
                        b.getIdBeneficio(),
                        b.getTipo(),
                        b.getDescricao(),
                        b.getTempoMinimoMeses(),
                        b.getPercentualBaseMedioContribuicoes(),
                        b.getMensagem(),
                        b.isAtivo()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos); // status 200
    }

    // atualizar  beneficio
    @PatchMapping("/{id}")
    public ResponseEntity<BeneficioDTO> atualizarBeneficio(@PathVariable int id, @RequestBody Beneficio novoBeneficio) {
        Optional<Beneficio> atualizadoOpt = BeneficioService.atualizarBeneficio(id, novoBeneficio);

        if (atualizadoOpt.isPresent()) {
            Beneficio atualizado = atualizadoOpt.get();
            BeneficioDTO dto = new BeneficioDTO(
                    atualizado.getIdBeneficio(),
                    atualizado.getTipo(),
                    atualizado.getDescricao(),
                    atualizado.getTempoMinimoMeses(),
                    atualizado.getPercentualBaseMedioContribuicoes(),
                    atualizado.getMensagem(),
                    atualizado.isAtivo()
            );
            return ResponseEntity.ok(dto); // status 200
        } else {
            return ResponseEntity.notFound().build(); // status 404
        }
    }


    // deletar logicamente beneficios
    // nao precisa de dto, só retorna mensagem
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<String> desativarBeneficio(@PathVariable int id) {
        boolean resultado = BeneficioService.desativarBeneficio(id);

        if (resultado) {
            return ResponseEntity.ok("Benefício desativado com sucesso");
        } else {
            return ResponseEntity.status(404).body("Benefício não encontrado");
        }

      // acho que tenho que criar um endpoint que retorna um beneficio por vez
    }

}