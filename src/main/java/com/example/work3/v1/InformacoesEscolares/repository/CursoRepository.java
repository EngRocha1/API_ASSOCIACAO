package com.example.work3.v1.InformacoesEscolares.repository;
import com.example.work3.v1.InformacoesEscolares.domain.Listas.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends JpaRepository<Curso, String>, CrudRepository<Curso, String> {
}
