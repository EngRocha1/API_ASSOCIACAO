package com.associacao.api.v1.InformacoesSensiveis.repository;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.PronomePreferido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PronomePreferidoRepository extends JpaRepository<PronomePreferido, String>, CrudRepository<PronomePreferido, String> {
}
