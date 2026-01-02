package com.example.work3.v1.DadosBancarios.repository;
import com.example.work3.v1.DadosBancarios.domain.Listas.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BancoRepository extends JpaRepository<Banco, String>, CrudRepository<Banco, String> {
}
