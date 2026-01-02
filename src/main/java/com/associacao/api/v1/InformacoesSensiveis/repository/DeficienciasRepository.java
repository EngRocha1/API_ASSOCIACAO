package com.associacao.api.v1.InformacoesSensiveis.repository;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.Deficiencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DeficienciasRepository extends JpaRepository<Deficiencias, String>, CrudRepository<Deficiencias, String> {
}
