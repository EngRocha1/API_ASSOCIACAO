package com.associacao.api.v1.InformacoesSensiveis.repository;

import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.Cordao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CordaoRepository extends JpaRepository<Cordao, String>, CrudRepository<Cordao, String> {
}