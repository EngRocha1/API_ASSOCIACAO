package com.associacao.api.v1.InformacoesSensiveis.repository;

import com.associacao.api.v1.InformacoesSensiveis.domain.InformacoesSensiveis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InformacoesSensiveisRepository extends JpaRepository<InformacoesSensiveis, String> {
    Optional<InformacoesSensiveis> findByServidorId(String servidorId);
    Optional<Object> findByServidorIdAndIdIsNot(String novoServidorId, String id);
}