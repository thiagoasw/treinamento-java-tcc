package com.totvs.tj.tcc.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.totvs.tj.tcc.domain.conta.Conta;
import com.totvs.tj.tcc.domain.conta.ContaId;
import com.totvs.tj.tcc.domain.conta.ContaRepository;

@Repository
public interface ContaRepositoryJpa extends ContaRepository, JpaRepository<Conta, ContaId> {

}
