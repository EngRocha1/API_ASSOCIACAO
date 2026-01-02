package com.example.work3.v1.InformacoesEscolares.repository;
import com.example.work3.v1.InformacoesEscolares.domain.Listas.StatusFormacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface StatusFormacaoRepository extends JpaRepository<StatusFormacao, String>, CrudRepository<StatusFormacao, String> {
}
