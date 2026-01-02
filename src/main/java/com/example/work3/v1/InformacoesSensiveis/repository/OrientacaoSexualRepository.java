package com.example.work3.v1.InformacoesSensiveis.repository;
import com.example.work3.v1.InformacoesSensiveis.domain.Listas.OrientacaoSexual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrientacaoSexualRepository extends JpaRepository<OrientacaoSexual, String>, CrudRepository<OrientacaoSexual, String> {
}
