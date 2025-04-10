package com.rpps.beneficios.service;
import com.rpps.beneficios.model.Beneficio;
import com.rpps.beneficios.repository.BeneficioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficioService {

    @Autowired
    private BeneficioRepository beneficioRepository;

    // Método para criar um novo benefício
    public Beneficio criarBeneficio(Beneficio beneficio) {
        beneficio.setAtivo(true); // Marca o benefício como ativo ao criar
        beneficio.setMensagem("Benefício criado com sucesso");
        return beneficioRepository.save(beneficio);
    }

    // Método para listar todos os benefícios ativos
    public List<Beneficio> listarBeneficio() {
        return beneficioRepository.findByAtivoTrue();
    }

    // Método para atualizar um benefício existente
    public Optional<Beneficio> atualizarBeneficio(int id, Beneficio novoBeneficio) {
        return beneficioRepository.findById(id).map(beneficio -> {
            beneficio.setTipo(novoBeneficio.getTipo());
            beneficio.setDescricao(novoBeneficio.getDescricao());
            beneficio.setTempoMinimoMeses(novoBeneficio.getTempoMinimoMeses());
            beneficio.setPercentualBaseMedioContribuicoes(novoBeneficio.getPercentualBaseMedioContribuicoes());
            beneficio.setMensagem(novoBeneficio.getMensagem());
            beneficio.setAtivo(novoBeneficio.isAtivo());
            beneficio.setMensagem("Benefício atualizado com sucesso");
            return beneficioRepository.save(beneficio);
        });
    }

    // Método para desativar um benefício logicamente
    public boolean desativarBeneficio(int id) {
        return beneficioRepository.findById(id).map(beneficio -> {
            beneficio.setAtivo(false);
            beneficio.setMensagem("Benefício desativado");
            beneficioRepository.save(beneficio);
            return true;
        }).orElse(false);
    }



}
