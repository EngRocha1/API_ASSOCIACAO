package com.associacao.api.v1.InformacoesEscolares.repository;
import com.associacao.api.v1.InformacoesEscolares.domain.Listas.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SemestreRepository extends JpaRepository<Semestre, String>, CrudRepository<Semestre, String> {
}