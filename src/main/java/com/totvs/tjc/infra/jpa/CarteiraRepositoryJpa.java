package com.totvs.tjc.infra.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.totvs.tjc.carteira.CarteiraRepository;
import com.totvs.tjc.carteira.Conta;
import com.totvs.tjc.carteira.ContaId;

@Repository
public interface CarteiraRepositoryJpa extends CarteiraRepository, JpaRepository<Conta, ContaId> {}
