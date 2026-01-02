package com.associacao.api.v1.InformacoesAssentamento.repository;
import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.Simbolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SimboloRepository extends JpaRepository<Simbolo, String>, CrudRepository<Simbolo, String> {
}

