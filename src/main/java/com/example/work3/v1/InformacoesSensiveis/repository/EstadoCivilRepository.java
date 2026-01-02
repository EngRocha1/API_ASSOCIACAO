package com.example.work3.v1.InformacoesSensiveis.repository;
import com.example.work3.v1.InformacoesSensiveis.domain.Listas.EstadoCivil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, String>, CrudRepository<EstadoCivil, String> {
}
