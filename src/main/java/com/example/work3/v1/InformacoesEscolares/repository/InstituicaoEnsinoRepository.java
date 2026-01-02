package com.example.work3.v1.InformacoesEscolares.repository;
import com.example.work3.v1.InformacoesEscolares.domain.Listas.InstituicaoEnsino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface InstituicaoEnsinoRepository extends JpaRepository<InstituicaoEnsino, String>, CrudRepository<InstituicaoEnsino, String> {
}
