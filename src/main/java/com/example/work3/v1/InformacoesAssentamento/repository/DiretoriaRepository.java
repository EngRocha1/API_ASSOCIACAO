package com.example.work3.v1.InformacoesAssentamento.repository;
import com.example.work3.v1.InformacoesAssentamento.domain.Listas.Diretoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DiretoriaRepository extends JpaRepository<Diretoria, String>, CrudRepository<Diretoria, String> {
}

