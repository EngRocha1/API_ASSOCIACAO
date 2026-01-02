package com.example.work3.v1.DocumentosPessoais.repository;
import com.example.work3.v1.DocumentosPessoais.domain.Listas.TipoDeDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TipoDeDocumentoRepository extends JpaRepository<TipoDeDocumento, String>, CrudRepository<TipoDeDocumento, String> {
}
