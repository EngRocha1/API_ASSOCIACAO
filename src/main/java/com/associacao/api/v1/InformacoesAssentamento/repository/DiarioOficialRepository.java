package com.associacao.api.v1.InformacoesAssentamento.repository;
import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.DiarioOficial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DiarioOficialRepository extends JpaRepository<DiarioOficial, String>, CrudRepository<DiarioOficial, String> {
}
