package com.associacao.api.v1.Audit.service;

import com.associacao.api.v1.Audit.domain.CustomRevisionEntity;
import com.associacao.api.v1.Audit.repository.AuditRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Camada de serviço para gestão de auditoria e recuperação de histórico.
 * Utiliza o Hibernate Envers para rastrear mudanças nos dados mastigados da API. [cite: 2025-12-29]
 */
@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Recupera todos os registros de metadados de auditoria (revisões).
     * @return Lista de todas as revisões contendo IP, Login e Timestamp.
     */
    @Transactional(readOnly = true)
    public List<CustomRevisionEntity> listarTodasRevisoes() {
        return auditRepository.findAll();
    }

    /**
     * Busca revisões realizadas por um login específico.
     * @param login Login do usuário (geralmente o CPF).
     * @return Lista de revisões filtradas por usuário.
     */
    @Transactional(readOnly = true)
    public List<CustomRevisionEntity> buscarPorLogin(String login) {
        return auditRepository.findByLogin(login);
    }

    /**
     * Filtra revisões originadas de um endereço IP específico.
     * @param ip Endereço IP para auditoria de segurança. [cite: 2025-11-08]
     * @return Lista de revisões do IP solicitado.
     */
    @Transactional(readOnly = true)
    public List<CustomRevisionEntity> buscarPorIp(String ip) {
        return auditRepository.findByIp(ip);
    }

    /**
     * Busca revisões dentro de um intervalo de tempo (milissegundos).
     * @param inicio Timestamp inicial.
     * @param fim Timestamp final.
     * @return Lista de revisões no período.
     */
    @Transactional(readOnly = true)
    public List<CustomRevisionEntity> buscarPorPeriodo(long inicio, long fim) {
        return auditRepository.findByTimestampBetween(inicio, fim);
    }

    /**
     * Filtra revisões pelo cabeçalho User-Agent (navegador/dispositivo).
     * @param ua String do User-Agent.
     * @return Lista de revisões compatíveis.
     */
    @Transactional(readOnly = true)
    public List<CustomRevisionEntity> buscarporUserAgent(String ua) {
        return auditRepository.findByUserAgentContaining(ua);
    }

    /**
     * Recupera o histórico completo de valores de uma entidade específica.
     * Permite comparar o estado dos campos antes e depois de cada alteração. [cite: 2025-11-18]
     * * @param classe A classe da entidade (ex: Servidor.class).
     * @param id O identificador único da entidade.
     * @return Lista de revisões contendo o estado do objeto em cada ponto do tempo.
     */
    @Transactional(readOnly = true)
    public List<?> buscarHistoricoDeDados(Class<?> classe, Object id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        return reader.createQuery()
                .forRevisionsOfEntity(classe, false, true)
                .add(AuditEntity.id().eq(id))
                .getResultList();
    }
}