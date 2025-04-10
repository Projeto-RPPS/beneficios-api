package com.rpps.beneficios.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Beneficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBeneficio;
    private String tipo;
    private String descricao;
    private int tempoMinimoMeses;
    private int percentualBaseMedioContribuicoes;
    private String mensagem;
    private boolean ativo = true;  // Controle de desativação lógica
}
