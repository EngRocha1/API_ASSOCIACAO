package com.example.work3.v1.Treinamentos.repository;

import com.example.work3.v1.Treinamentos.domain.Listas.GrupoDoCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GrupoDoCursoRepository extends JpaRepository<GrupoDoCurso, String>, CrudRepository<GrupoDoCurso, String> {
}