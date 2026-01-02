package com.associacao.api.v1.InformacoesAssentamento.repository;
import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.Lotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LotacaoRepository extends JpaRepository<Lotacao, String>, CrudRepository<Lotacao, String> {
}
