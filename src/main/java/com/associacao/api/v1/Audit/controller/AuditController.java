package com.associacao.api.v1.Audit.controller;

import com.associacao.api.v1.Audit.domain.CustomRevisionEntity;
import com.associacao.api.v1.Audit.service.AuditService;
import com.associacao.api.v1.Servidor.domain.Servidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por expor os endpoints de auditoria do sistema.
 * Permite o rastreamento de quem, quando e onde as alterações foram realizadas.
 */
@RestController
@RequestMapping("/v1/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    /**
     * Lista todos os metadados de auditoria registrados no sistema.
     * @return Lista de {@link CustomRevisionEntity} contendo login, IP e timestamp.
     */
    @GetMapping("/revisions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomRevisionEntity>> getAllRevisions() {
        return ResponseEntity.ok(auditService.listarTodasRevisoes());
    }

    /**
     * Busca todas as revisões realizadas por um usuário específico.
     * @param login O login (CPF) do usuário a ser pesquisado.
     * @return Lista de revisões associadas ao login informado.
     */
    @GetMapping("/revisions/user/{login}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomRevisionEntity>> getRevisionsByLogin(@PathVariable String login) {
        return ResponseEntity.ok(auditService.buscarPorLogin(login));
    }

    /**
     * Busca as revisões originadas de um endereço IP específico.
     * Essencial para auditoria de segurança na infraestrutura VPS.
     * @param ip Endereço IP de origem.
     * @return Lista de revisões realizadas a partir do IP informado.
     */
    @GetMapping("/revisions/ip")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomRevisionEntity>> getByIp(@RequestParam String ip) {
        return ResponseEntity.ok(auditService.buscarPorIp(ip));
    }

    /**
     * Recupera o histórico detalhado de alterações de um servidor específico.
     * Retorna o estado do objeto em cada revisão (Antes vs Depois).
     * @param id Identificador único (UUID) do servidor.
     * @return Lista contendo as versões históricas da entidade {@link Servidor}.
     */
    @GetMapping("/data/servidor/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<?>> getServidorHistory(@PathVariable String id) {
        return ResponseEntity.ok(auditService.buscarHistoricoDeDados(Servidor.class, id));
    }

    /**
     * Busca revisões dentro de um intervalo de tempo específico.
     * @param inicio Timestamp inicial em milissegundos.
     * @param fim Timestamp final em milissegundos.
     * @return Lista de revisões no período solicitado.
     */
    @GetMapping("/revisions/periodo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomRevisionEntity>> getByPeriodo(
            @RequestParam long inicio,
            @RequestParam long fim) {
        return ResponseEntity.ok(auditService.buscarPorPeriodo(inicio, fim));
    }

    /**
     * Busca revisões baseadas no dispositivo ou navegador utilizado (User-Agent).
     * @param ua String parcial ou completa do User-Agent.
     * @return Lista de revisões que coincidem com o dispositivo informado.
     */
    @GetMapping("/revisions/dispositivo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomRevisionEntity>> getByUserAgent(@RequestParam String ua) {
        return ResponseEntity.ok(auditService.buscarporUserAgent(ua));
    }
}