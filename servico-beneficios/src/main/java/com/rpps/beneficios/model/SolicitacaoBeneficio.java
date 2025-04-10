package com.rpps.beneficios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

public class SolicitacaoBeneficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSolicitacao;
    private String cpf;
    private int tempoContribuicaoCalculado;
    private double valorMedioContribuicoes;
    private double valorConcedido;
    private String status;
    private String mensagem;
    private double totalBeneficios;
    private String tipoBeneficio;
    private boolean ativo = true;
}
