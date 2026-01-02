package com.example.work3.v1.DadosBancarios.repository;
import com.example.work3.v1.DadosBancarios.domain.DadosBancarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DadosBancariosRepository extends JpaRepository<DadosBancarios, String>, CrudRepository<DadosBancarios, String> {
}