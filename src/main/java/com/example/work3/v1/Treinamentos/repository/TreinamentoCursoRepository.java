package com.example.work3.v1.Treinamentos.repository;

import com.example.work3.v1.Treinamentos.domain.Listas.TreinamentoCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreinamentoCursoRepository extends JpaRepository<TreinamentoCurso, String>, CrudRepository<TreinamentoCurso, String> {
}