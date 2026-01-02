package com.associacao.api.v1.InformacoesSensiveis.repository;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.ExpressaoDegenero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ExpressaoDegeneroRepository extends JpaRepository<ExpressaoDegenero, String>, CrudRepository<ExpressaoDegenero, String> {
}
