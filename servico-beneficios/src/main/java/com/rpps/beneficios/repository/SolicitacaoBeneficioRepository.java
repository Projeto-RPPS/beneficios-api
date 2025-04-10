package com.rpps.beneficios.repository;



import com.rpps.beneficios.model.SolicitacaoBeneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository


public interface SolicitacaoBeneficioRepository extends JpaRepository<SolicitacaoBeneficio, Integer> {

    // Método para listar apenas as solicitações ativas
    List<SolicitacaoBeneficio> findByAtivoTrue();

    List<SolicitacaoBeneficio> findByCpf(String cpf);


}
