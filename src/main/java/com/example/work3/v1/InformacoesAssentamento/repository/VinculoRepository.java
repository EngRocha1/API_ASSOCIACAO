package com.example.work3.v1.InformacoesAssentamento.repository;
import com.example.work3.v1.InformacoesAssentamento.domain.Listas.Vinculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface VinculoRepository extends JpaRepository<Vinculo, String>, CrudRepository<Vinculo, String> {
}

