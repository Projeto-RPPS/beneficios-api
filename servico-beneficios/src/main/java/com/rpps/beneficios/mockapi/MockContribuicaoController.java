//package com.rpps.beneficios.mockapi;
//
//import com.rpps.beneficios.DTO.ContribuicaoDTO;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@RestController
//@RequestMapping("/contribuicoes")
//public class    MockContribuicaoController {
//
//    @GetMapping("/cpf/{cpf}")
//    public List<ContribuicaoDTO> listarContribuicoes(@PathVariable String cpf) {
//        // CPF com contribuições suficientes
//        if (cpf.equals("11111111111")) {
//            return List.of(
//                    new ContribuicaoDTO(1, 100, LocalDate.of(2021, 1, 1), 2000.0, LocalDate.of(2021, 1, 1), 1),
//                    new ContribuicaoDTO(2, 100, LocalDate.of(2021, 2, 1), 2000.0, LocalDate.of(2021, 2, 1), 1),
//                    new ContribuicaoDTO(3, 100, LocalDate.of(2021, 3, 1), 2000.0, LocalDate.of(2021, 3, 1), 1),
//                    new ContribuicaoDTO(4, 100, LocalDate.of(2021, 4, 1), 2000.0, LocalDate.of(2021, 4, 1), 1),
//                    new ContribuicaoDTO(5, 100, LocalDate.of(2021, 5, 1), 2000.0, LocalDate.of(2021, 5, 1), 1)
//            );
//        }
//
//        // CPF com contribuições insuficientes
//        if (cpf.equals("22222222222")) {
//            return List.of(
//                    new ContribuicaoDTO(10, 200, LocalDate.of(2023, 1, 1), 1000.0, LocalDate.of(2023, 1, 1), 1)
//            );
//        }
//
//        // CPF sem contribuição (retorna lista vazia)
//        return List.of();
//    }
//}
