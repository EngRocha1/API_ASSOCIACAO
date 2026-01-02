package com.example.work3.v1.InformacoesSensiveis.repository;
import com.example.work3.v1.InformacoesSensiveis.domain.Listas.ExpressaoDegenero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ExpressaoDegeneroRepository extends JpaRepository<ExpressaoDegenero, String>, CrudRepository<ExpressaoDegenero, String> {
}
