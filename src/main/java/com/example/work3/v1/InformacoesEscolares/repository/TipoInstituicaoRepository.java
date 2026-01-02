package com.example.work3.v1.InformacoesEscolares.repository;
import com.example.work3.v1.InformacoesEscolares.domain.Listas.TipoInstituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TipoInstituicaoRepository extends JpaRepository<TipoInstituicao, String>, CrudRepository<TipoInstituicao, String> {
}
