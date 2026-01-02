package com.example.work3.v1.InformacoesAssentamento.repository;
import com.example.work3.v1.InformacoesAssentamento.domain.InformacoesAssentamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InformacoesAssentamentoRepository extends JpaRepository<InformacoesAssentamento, String>, CrudRepository<InformacoesAssentamento, String> {
    Optional<InformacoesAssentamento> findByServidorId(String servidorId);
    Optional<Object> findByServidorIdAndIdIsNot(String novoServidorId, String id);

}