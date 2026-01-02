package com.associacao.api.v1.Servidor.repository;
import com.associacao.api.v1.Servidor.domain.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ServidorRepository extends JpaRepository<Servidor, String>, CrudRepository<Servidor, String> {

//    @Query("SELECT s FROM servidor s LEFT JOIN FETCH s.informacoesSensiveis LEFT JOIN FETCH s.informacoesEscolares")
//    List<Servidor> findAllWithDetails();
//
//    @Query("SELECT s FROM servidor s LEFT JOIN FETCH s.informacoesSensiveis LEFT JOIN FETCH s.informacoesEscolares WHERE s.id = :id")
//    Servidor findByIdWithDetails(@Param("id") String id);

    @Query("SELECT s FROM servidor s " +
            "LEFT JOIN FETCH s.informacoesEscolares " +
            "LEFT JOIN FETCH s.informacoesAssentamentos " +
            "LEFT JOIN FETCH s.documentosPessoais " +
            "LEFT JOIN FETCH s.dadosBancarios " +
            "LEFT JOIN FETCH s.afastamentos")
    List<Servidor> findAllWithAllDetails();

    @Query("SELECT s FROM servidor s " +
            "LEFT JOIN FETCH s.informacoesSensiveis " +
            "LEFT JOIN FETCH s.informacoesEscolares " +
            "LEFT JOIN FETCH s.informacoesAssentamentos " +
            "LEFT JOIN FETCH s.documentosPessoais " +
            "LEFT JOIN FETCH s.dadosBancarios " +
            "LEFT JOIN FETCH s.afastamentos " +
            "WHERE s.id = :id")
    Servidor findByIdWithDetails(@Param("id") String id);

    @Query("SELECT s FROM servidor s WHERE s.usuario.id = :userId")
    Servidor findByUsuario(@Param("userId") String userId);

    @Query("SELECT s FROM servidor s WHERE s.cpf = :cpf")
    Servidor findByCpf(@Param("cpf") String cpf);
}
