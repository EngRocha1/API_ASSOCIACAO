package com.associacao.api.v1.InformacoesAssentamento.repository;
import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.AtoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AtoMovimentacaoRepository extends JpaRepository<AtoMovimentacao, String>, CrudRepository<AtoMovimentacao, String> {
}
