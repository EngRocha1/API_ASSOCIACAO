package com.associacao.api.v1.Audit.domain;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Implementação personalizada do {@link RevisionListener} para o Hibernate Envers.
 * * <p>Esta classe é responsável por interceptar a criação de novas revisões no banco de dados
 * e enriquecê-las com metadados contextuais da requisição HTTP e do contexto de segurança.</p>
 * * <p>Os dados capturados incluem:</p>
 * <ul>
 * <li><b>IP Real do Cliente:</b> Identifica a origem da requisição, tratando corretamente
 * cenários de Proxy Reverso ou Ingress Controllers no Kubernetes através do cabeçalho {@code X-Forwarded-For}.</li>
 * <li><b>User-Agent:</b> Identifica o navegador, sistema operacional e dispositivo que realizou a operação.</li>
 * <li><b>Login do Usuário:</b> Identifica o autor da alteração através do contexto de autenticação do Spring Security.</li>
 * </ul>
 */
public class CustomRevisionListener implements RevisionListener {

    /**
     * Preenche os campos da entidade de revisão personalizada sempre que uma nova transação
     * auditada é detectada pelo Hibernate Envers.
     *
     * @param revisionEntity A entidade de revisão que será persistida (deve ser do tipo {@link CustomRevisionEntity}).
     */
    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevisionEntity rev = (CustomRevisionEntity) revisionEntity;

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attr != null) {
            HttpServletRequest request = attr.getRequest();

            String remoteAddr = request.getHeader("X-Forwarded-For");

            if (remoteAddr == null || remoteAddr.isEmpty()) {
                remoteAddr = request.getRemoteAddr();
            } else {
                remoteAddr = remoteAddr.split(",")[0].trim();
            }

            rev.setIp(remoteAddr);
            rev.setUserAgent(request.getHeader("User-Agent"));
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            rev.setLogin(auth.getName());
        }
    }
}