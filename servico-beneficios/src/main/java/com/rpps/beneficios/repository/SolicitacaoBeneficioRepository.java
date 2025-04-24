package com.rpps.beneficios.repository;



import com.rpps.beneficios.model.SolicitacaoBeneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository


public interface SolicitacaoBeneficioRepository extends JpaRepository<SolicitacaoBeneficio, Integer> {


    List<SolicitacaoBeneficio> findSolicitacaoBeneficioByAtivoAndCpf(Boolean ativo, String cpf);


}
