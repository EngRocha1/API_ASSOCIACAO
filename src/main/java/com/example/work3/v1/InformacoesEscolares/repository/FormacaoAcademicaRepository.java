package com.example.work3.v1.InformacoesEscolares.repository;
import com.example.work3.v1.InformacoesEscolares.domain.Listas.FormacaoAcademica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FormacaoAcademicaRepository extends JpaRepository<FormacaoAcademica, String>, CrudRepository<FormacaoAcademica, String> {
}
