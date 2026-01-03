package com.associacao.api.v1.Audit.repository;
import com.associacao.api.v1.Audit.domain.CustomRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<CustomRevisionEntity, Integer> {

    // Busca todas as revisões feitas por um usuário específico
    List<CustomRevisionEntity> findByLogin(String login);

    // Busca revisões originadas de um IP específico (segurança contra acessos indevidos)
    List<CustomRevisionEntity> findByIp(String ip);

    // Busca revisões dentro de um intervalo de tempo
    List<CustomRevisionEntity> findByTimestampBetween(long startTimestamp, long endTimestamp);

    // Busca personalizada combinando Login e IP
    List<CustomRevisionEntity> findByLoginAndIp(String login, String ip);

    // Busca revisões por parte do User-Agent (ex: filtrar quem usou 'Chrome' ou 'Postman')
    List<CustomRevisionEntity> findByUserAgentContaining(String userAgent);
}
