package com.associacao.api.v1.DadosBancarios.repository;
import com.associacao.api.v1.DadosBancarios.domain.Listas.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BancoRepository extends JpaRepository<Banco, String>, CrudRepository<Banco, String> {
}
