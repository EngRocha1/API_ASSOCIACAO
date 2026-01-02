package com.example.work3.v1.InformacoesSensiveis.repository;

import com.example.work3.v1.InformacoesSensiveis.domain.Listas.Geracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GeracaoRepository extends JpaRepository<Geracao, String>, CrudRepository<Geracao, String> {
}