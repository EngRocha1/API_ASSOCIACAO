package com.associacao.api.v1.Afastamentos.repository;
import com.associacao.api.v1.Afastamentos.domain.Listas.TipoAfastamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TipoAfastamentoRepository extends JpaRepository<TipoAfastamento, String>, CrudRepository<TipoAfastamento, String> {
}
