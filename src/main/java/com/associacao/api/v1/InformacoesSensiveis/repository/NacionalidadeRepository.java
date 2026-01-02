package com.associacao.api.v1.InformacoesSensiveis.repository;

import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.Nacionalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface NacionalidadeRepository extends JpaRepository<Nacionalidade, String>, CrudRepository<Nacionalidade, String> {
}