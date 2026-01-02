package com.associacao.api.v1.Afastamentos.repository;
import com.associacao.api.v1.Afastamentos.domain.Afastamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AfastamentosRepository extends JpaRepository<Afastamentos, String>, CrudRepository<Afastamentos, String> {
}