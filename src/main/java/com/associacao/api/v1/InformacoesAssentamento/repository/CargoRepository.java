package com.associacao.api.v1.InformacoesAssentamento.repository;
import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CargoRepository extends JpaRepository<Cargo, String>, CrudRepository<Cargo, String> {
}
