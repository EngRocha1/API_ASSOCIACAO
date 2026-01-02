package com.associacao.api.v1.InformacoesEscolares.repository;
import com.associacao.api.v1.InformacoesEscolares.domain.Listas.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PeriodoRepository extends JpaRepository<Periodo, String>, CrudRepository<Periodo, String> {
}
