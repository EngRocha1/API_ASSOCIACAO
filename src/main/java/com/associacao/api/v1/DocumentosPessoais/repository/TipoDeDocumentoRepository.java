package com.associacao.api.v1.DocumentosPessoais.repository;
import com.associacao.api.v1.DocumentosPessoais.domain.Listas.TipoDeDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TipoDeDocumentoRepository extends JpaRepository<TipoDeDocumento, String>, CrudRepository<TipoDeDocumento, String> {
}
