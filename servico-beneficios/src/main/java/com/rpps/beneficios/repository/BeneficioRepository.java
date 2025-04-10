package com.rpps.beneficios.repository;

import com.rpps.beneficios.model.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Integer> {

    // Método para listar apenas os benefícios ativos
    List<Beneficio> findByAtivoTrue();
   
}
