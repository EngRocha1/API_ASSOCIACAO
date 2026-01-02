package com.example.work3.v1.InformacoesEscolares.repository;
import com.example.work3.v1.InformacoesEscolares.domain.Listas.NivelEnsino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface NivelEnsinoRepository extends JpaRepository<NivelEnsino, String>, CrudRepository<NivelEnsino, String> {
}
