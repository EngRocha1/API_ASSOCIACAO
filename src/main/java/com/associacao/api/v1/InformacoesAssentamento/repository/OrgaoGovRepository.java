package com.associacao.api.v1.InformacoesAssentamento.repository;
import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.OrgaoGov;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrgaoGovRepository extends JpaRepository<OrgaoGov, String>, CrudRepository<OrgaoGov, String> {
}
