package com.example.work3.v1.InformacoesEscolares.repository;
import com.example.work3.v1.InformacoesEscolares.domain.InformacoesEscolares;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InformacoesEscolaresRepository extends JpaRepository<InformacoesEscolares, String>, CrudRepository<InformacoesEscolares, String> {
    Optional<InformacoesEscolares> findByServidorId(String servidorId);
}