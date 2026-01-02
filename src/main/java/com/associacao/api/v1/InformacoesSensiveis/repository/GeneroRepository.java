package com.associacao.api.v1.InformacoesSensiveis.repository;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GeneroRepository extends JpaRepository<Genero, String>, CrudRepository<Genero, String> {
}
