package com.example.work3.v1.Afastamentos.repository;
import com.example.work3.v1.Afastamentos.domain.Listas.FluxoAprovacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FluxoAprovacaoRepository extends JpaRepository<FluxoAprovacao, String>, CrudRepository<FluxoAprovacao, String> {
}
