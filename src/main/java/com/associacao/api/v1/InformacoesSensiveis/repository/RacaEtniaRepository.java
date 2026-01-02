package com.associacao.api.v1.InformacoesSensiveis.repository;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.RacaEtnia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RacaEtniaRepository extends JpaRepository<RacaEtnia, String>, CrudRepository<RacaEtnia, String> {
}
