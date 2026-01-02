package com.associacao.api.v1.DocumentosPessoais.repository;
import com.associacao.api.v1.DocumentosPessoais.domain.DocumentosPessoais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DocumentosPessoaisRepository extends JpaRepository<DocumentosPessoais, String>, CrudRepository<DocumentosPessoais, String> {
}