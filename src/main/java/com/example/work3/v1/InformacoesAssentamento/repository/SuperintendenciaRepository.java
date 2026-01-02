package com.example.work3.v1.InformacoesAssentamento.repository;
import com.example.work3.v1.InformacoesAssentamento.domain.Listas.Superintendencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SuperintendenciaRepository extends JpaRepository<Superintendencia, String>, CrudRepository<Superintendencia, String> {
}
