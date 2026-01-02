package com.associacao.api.v1.Afastamentos.repository;
import com.associacao.api.v1.Afastamentos.domain.Listas.CID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CIDRepository extends JpaRepository<CID, String>, CrudRepository<CID, String> {

    List<CID> findByCodigoContainingIgnoreCaseOrNameContainingIgnoreCaseOrDescricaoContainingIgnoreCase(
            String codigoTerm, String nameTerm, String descricaoTerm);
}
