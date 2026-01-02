package com.associacao.api.v1.InformacoesEscolares.Service;
import com.associacao.api.v1.InformacoesEscolares.domain.InformacoesEscolares;
import com.associacao.api.v1.InformacoesEscolares.repository.InformacoesEscolaresRepository;
import com.associacao.api.v1.Servidor.Service.ServidorService;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import com.associacao.api.Exceptions.ExceptionHandlerMap;
import lombok.Getter;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class InformacoesEscolaresService
        extends AbstractBaseService<InformacoesEscolares, String> {

    private static final Logger logger = LoggerFactory.getLogger(InformacoesEscolaresService.class);
    private final InformacoesEscolaresRepository informacoesEscolaresRepository;
    @Getter
    private final ServidorService servidorService;
    @Getter
    private final InstituicaoEnsinoService instituicaoEnsinoService;
    @Getter
    private final CursoService cursoService;
    @Getter
    private final PeriodoService periodoDoDiaService;
    @Getter
    private final SemestreService semestreService;
    @Getter
    private final FormacaoAcademicaService formacaoAcademicaService;
    @Getter
    private final StatusFormacaoService statusFormacaoService;


    @Autowired
    public InformacoesEscolaresService(
            InformacoesEscolaresRepository informacoesEscolaresRepository,
            ServidorService servidorService,
            InstituicaoEnsinoService instituicaoEnsinoService,
            CursoService cursoService,
            PeriodoService periodoDoDiaService,
            SemestreService semestreService,
            FormacaoAcademicaService formacaoAcademicaService,
            StatusFormacaoService statusFormacaoService
    ) {
        super(informacoesEscolaresRepository);
        this.informacoesEscolaresRepository = informacoesEscolaresRepository;
        this.servidorService = servidorService;
        this.cursoService=cursoService;
        this.semestreService=semestreService;
        this.periodoDoDiaService=periodoDoDiaService;
        this.formacaoAcademicaService=formacaoAcademicaService;
        this.statusFormacaoService=statusFormacaoService;
        this.instituicaoEnsinoService=instituicaoEnsinoService;
    }

    private void initializeLazyRelations(InformacoesEscolares entity) {
        if (entity == null) return;
        logger.debug("Inicializando relacionamentos LAZY para InformacoesEscolaridade com ID: {}", entity.getId());
        if (entity.getServidor() != null) Hibernate.initialize(entity.getServidor());
        if (entity.getCurso() != null) Hibernate.initialize(entity.getCurso());
        if (entity.getSemestre() != null) Hibernate.initialize(entity.getSemestre());
        if (entity.getPeriodoDoDia() != null) Hibernate.initialize(entity.getPeriodoDoDia());
        if (entity.getFormacaoAcademica() != null) Hibernate.initialize(entity.getFormacaoAcademica());
        if (entity.getStatusFormacao() != null) Hibernate.initialize(entity.getStatusFormacao());
        if (entity.getInstituicaoEnsino() != null) Hibernate.initialize(entity.getInstituicaoEnsino());
        logger.debug("Relacionamentos LAZY inicializados.");
    }

    @Transactional(readOnly = true)
    public InformacoesEscolares findByServidorId(String servidorId) {
        logger.debug("Tentando encontrar informações escolares para o ID do servidor: {}", servidorId);
        Optional<InformacoesEscolares> entityOptional = informacoesEscolaresRepository.findByServidorId(servidorId);
        if (entityOptional.isEmpty()) {
            logger.info("Nenhuma informação escolares encontrada para o ID do Servidor: {}. Retornando null.", servidorId);
            return null;
        }
        InformacoesEscolares entity = entityOptional.get();
        initializeLazyRelations(entity);
        return entity;
    }

    @Override
    public InformacoesEscolares findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(InformacoesEscolares informacoesEscolares) {
        informacoesEscolares.setAtivo(false);
    }


    @Override
    @Transactional
    public InformacoesEscolares update(String id, InformacoesEscolares informacoesEscolares) {
        try {
            logger.debug("Iniciando atualização das informações escolares com ID: {}", id);
            logger.debug("Dados recebidos: {}", informacoesEscolares);

            InformacoesEscolares existingInformacoesEscolares = validarId(id);

            updateEntityFields(existingInformacoesEscolares, informacoesEscolares);

            logger.debug("Atualização concluída com sucesso para as informações escolares com ID: {}", id);
            return repository.save(existingInformacoesEscolares);
        } catch (Exception e) {
            logger.error("Erro ao atualizar as informações escolares com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        InformacoesEscolares informacoesEscolares = validarId(id);
        try {
            setAtivoFalse(informacoesEscolares);
            informacoesEscolaresRepository.save(informacoesEscolares);
            logger.debug("Informações Escolares com ID {} marcadas como inativas com sucesso.", id);
        } catch (Exception e) {
            logger.error("Erro ao marcar as informações escolares com ID: {} como inativas", id, e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao marcar as informações escolares como inativas.", e);
        }
    }

    public void updateEntityFields(InformacoesEscolares existingInformacoesEscolares, InformacoesEscolares newInformacoesEscolares) {
        try {
            logger.debug("Atualizando campos da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId());

            if (newInformacoesEscolares.getCurso().getId() != null) {
                existingInformacoesEscolares.getCurso().setId(newInformacoesEscolares.getCurso().getId());
                logger.debug("Campo 'cursoId' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'cursoId' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getCurso().getId());
            }

            if (newInformacoesEscolares.getCurso().getName() != null) {
                existingInformacoesEscolares.getCurso().setName(newInformacoesEscolares.getCurso().getName());
                logger.debug("Campo 'cursoNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'cursoNome' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getCurso().getName());
            }

            if (newInformacoesEscolares.getCurso().getDescricao() != null) {
                existingInformacoesEscolares.getCurso().setDescricao(newInformacoesEscolares.getCurso().getDescricao());
                logger.debug("Campo 'cursoDescricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'cursoDescricao' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getCurso().getDescricao());
            }

            if (newInformacoesEscolares.getInicioCurso() != null) {
                existingInformacoesEscolares.setInicioCurso(newInformacoesEscolares.getInicioCurso());
                logger.debug("Campo 'inicioCurso' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'inicioCurso' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getInicioCurso());
            }

            if (newInformacoesEscolares.getCargaHorariaAtual() != null) {
                existingInformacoesEscolares.setCargaHorariaAtual(newInformacoesEscolares.getCargaHorariaAtual());
                logger.debug("Campo 'cargaHorariaAtual' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'cargaHorariaAtual' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getCargaHorariaAtual());
            }

            if (newInformacoesEscolares.getCargaHorariaTotal() != null) {
                existingInformacoesEscolares.setCargaHorariaTotal(newInformacoesEscolares.getCargaHorariaTotal());
                logger.debug("Campo 'cargaHorariaTotal' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'cargaHorariaTotal' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getCargaHorariaTotal());
            }

            if (newInformacoesEscolares.getFormacaoAcademica().getId() != null) {
                existingInformacoesEscolares.getFormacaoAcademica().setId(newInformacoesEscolares.getFormacaoAcademica().getId());
                logger.debug("Campo 'formacaoAcademicaId' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'formacaoAcademicaId' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getFormacaoAcademica().getId());
            }

            if (newInformacoesEscolares.getFormacaoAcademica().getName() != null) {
                existingInformacoesEscolares.getFormacaoAcademica().setName(newInformacoesEscolares.getFormacaoAcademica().getName());
                logger.debug("Campo 'formacaoAcademicaNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'formacaoAcademicaNome' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getFormacaoAcademica().getName());
            }

            if (newInformacoesEscolares.getFormacaoAcademica().getDescricao() != null) {
                existingInformacoesEscolares.getFormacaoAcademica().setDescricao(newInformacoesEscolares.getFormacaoAcademica().getDescricao());
                logger.debug("Campo 'formacaoAcademicaDescricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'formacaoAcademicaDescricao' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getFormacaoAcademica().getDescricao());
            }

            if (newInformacoesEscolares.getStatusFormacao().getId() != null) {
                existingInformacoesEscolares.getStatusFormacao().setId(newInformacoesEscolares.getStatusFormacao().getId());
                logger.debug("Campo 'statusFormacaoId' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'statusFormacaoId' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getStatusFormacao().getId());
            }

            if (newInformacoesEscolares.getStatusFormacao().getName() != null) {
                existingInformacoesEscolares.getStatusFormacao().setName(newInformacoesEscolares.getStatusFormacao().getName());
                logger.debug("Campo 'statusFormacaoNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'statusFormacaoNome' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getStatusFormacao().getName());
            }

            if (newInformacoesEscolares.getStatusFormacao().getDescricao() != null) {
                existingInformacoesEscolares.getStatusFormacao().setDescricao(newInformacoesEscolares.getStatusFormacao().getDescricao());
                logger.debug("Campo 'statusFormacaoDescricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'statusFormacaoDescricao' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getStatusFormacao().getDescricao());
            }

            if (newInformacoesEscolares.getInstituicaoEnsino().getId() != null) {
                existingInformacoesEscolares.getInstituicaoEnsino().setId(newInformacoesEscolares.getInstituicaoEnsino().getId());
                logger.debug("Campo 'instituicaoEnsinoId' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'instituicaoEnsinoId' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getInstituicaoEnsino().getId());
            }

            if (newInformacoesEscolares.getInstituicaoEnsino().getName() != null) {
                existingInformacoesEscolares.getInstituicaoEnsino().setName(newInformacoesEscolares.getInstituicaoEnsino().getName());
                logger.debug("Campo 'instituicaoEnsinoNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'instituicaoEnsinoNome' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getInstituicaoEnsino().getName());
            }

            if (newInformacoesEscolares.getInstituicaoEnsino().getDescricao() != null) {
                existingInformacoesEscolares.getInstituicaoEnsino().setDescricao(newInformacoesEscolares.getInstituicaoEnsino().getDescricao());
                logger.debug("Campo 'instituicaoEnsinoDescricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'instituicaoEnsinoDescricao' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getInstituicaoEnsino().getDescricao());
            }

            if (newInformacoesEscolares.getMatriculaInstitucional() != null) {
                existingInformacoesEscolares.setMatriculaInstitucional(newInformacoesEscolares.getMatriculaInstitucional());
                logger.debug("Campo 'matriculaInstitucional' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'matriculaInstitucional' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getMatriculaInstitucional());
            }

            if (newInformacoesEscolares.getPeriodoDoDia().getId() != null) {
                existingInformacoesEscolares.getPeriodoDoDia().setId(newInformacoesEscolares.getPeriodoDoDia().getId());
                logger.debug("Campo 'periodoDoDiaId' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'periodoDoDiaId' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getPeriodoDoDia().getId());
            }

            if (newInformacoesEscolares.getPeriodoDoDia().getName() != null) {
                existingInformacoesEscolares.getPeriodoDoDia().setName(newInformacoesEscolares.getPeriodoDoDia().getName());
                logger.debug("Campo 'periodoDoDiaNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'periodoDoDiaNome' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getPeriodoDoDia().getName());
            }

            if (newInformacoesEscolares.getPeriodoDoDia().getDescricao() != null) {
                existingInformacoesEscolares.getPeriodoDoDia().setDescricao(newInformacoesEscolares.getPeriodoDoDia().getDescricao());
                logger.debug("Campo 'periodoDoDiaDescricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'periodoDoDiaDescricao' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getPeriodoDoDia().getDescricao());
            }

            if (newInformacoesEscolares.getSemestre().getId() != null) {
                existingInformacoesEscolares.getSemestre().setId(newInformacoesEscolares.getSemestre().getId());
                logger.debug("Campo 'semestreId' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'semestreId' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getSemestre().getId());
            }

            if (newInformacoesEscolares.getSemestre().getName() != null) {
                existingInformacoesEscolares.getSemestre().setName(newInformacoesEscolares.getSemestre().getName());
                logger.debug("Campo 'semestreNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'semestreNome' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getSemestre().getName());
            }

            if (newInformacoesEscolares.getSemestre().getDescricao() != null) {
                existingInformacoesEscolares.getSemestre().setDescricao(newInformacoesEscolares.getSemestre().getDescricao());
                logger.debug("Campo 'semestreDescricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'semestreDescricao' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getSemestre().getDescricao());
            }

            if (newInformacoesEscolares.getServidor().getId() != null) {
                existingInformacoesEscolares.getServidor().setId(newInformacoesEscolares.getServidor().getId());
                logger.debug("Campo 'servidorId' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'servidorId' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getServidor().getId());
            }

            if (newInformacoesEscolares.getServidor().getName() != null) {
                existingInformacoesEscolares.getServidor().setName(newInformacoesEscolares.getServidor().getName());
                logger.debug("Campo 'servidorNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'servidorNome' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getServidor().getName());
            }

            if (newInformacoesEscolares.getTerminoPrevisto() != null) {
                existingInformacoesEscolares.setTerminoPrevisto(newInformacoesEscolares.getTerminoPrevisto());
                logger.debug("Campo 'terminoPrevisto' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'terminoPrevisto' não fornecido. Mantendo valor existente: {}", existingInformacoesEscolares.getTerminoPrevisto());
            }

            logger.debug("Campos da entidade InformacoesEscolares atualizados com sucesso para o ID: {}", existingInformacoesEscolares.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("cursoId")) {
                logger.error("Erro ao atualizar o campo 'cursoId' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("cursoNome")) {
                logger.error("Erro ao atualizar o campo 'cursoNome' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("cursoDescricao")) {
                logger.error("Erro ao atualizar o campo 'cursoDescricao' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("inicioCurso")) {
                logger.error("Erro ao atualizar o campo 'inicioCurso' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("cargaHorariaAtual")) {
                logger.error("Erro ao atualizar o campo 'cargaHorariaAtual' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("cargaHorariaTotal")) {
                logger.error("Erro ao atualizar o campo 'cargaHorariaTotal' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("formacaoAcademicaId")) {
                logger.error("Erro ao atualizar o campo 'formacaoAcademicaId' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("formacaoAcademicaNome")) {
                logger.error("Erro ao atualizar o campo 'formacaoAcademicaNome' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("formacaoAcademicaDescricao")) {
                logger.error("Erro ao atualizar o campo 'formacaoAcademicaDescricao' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("statusFormacaoId")) {
                logger.error("Erro ao atualizar o campo 'statusFormacaoId' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("statusFormacaoNome")) {
                logger.error("Erro ao atualizar o campo 'statusFormacaoNome' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("statusFormacaoDescricao")) {
                logger.error("Erro ao atualizar o campo 'statusFormacaoDescricao' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("instituicaoEnsinoId")) {
                logger.error("Erro ao atualizar o campo 'instituicaoEnsinoId' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("instituicaoEnsinoNome")) {
                logger.error("Erro ao atualizar o campo 'instituicaoEnsinoNome' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("instituicaoEnsinoDescricao")) {
                logger.error("Erro ao atualizar o campo 'instituicaoEnsinoDescricao' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("matriculaInstitucional")) {
                logger.error("Erro ao atualizar o campo 'matriculaInstitucional' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("periodoDoDiaId")) {
                logger.error("Erro ao atualizar o campo 'periodoDoDiaId' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("periodoDoDiaNome")) {
                logger.error("Erro ao atualizar o campo 'periodoDoDiaNome' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("periodoDoDiaDescricao")) {
                logger.error("Erro ao atualizar o campo 'periodoDoDiaDescricao' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("semestreId")) {
                logger.error("Erro ao atualizar o campo 'semestreId' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("semestreNome")) {
                logger.error("Erro ao atualizar o campo 'semestreNome' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("semestreDescricao")) {
                logger.error("Erro ao atualizar o campo 'semestreDescricao' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("servidorId")) {
                logger.error("Erro ao atualizar o campo 'servidorId' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("servidorNome")) {
                logger.error("Erro ao atualizar o campo 'servidorNome' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else if (e.getMessage().contains("terminoPrevisto")) {
                logger.error("Erro ao atualizar o campo 'terminoPrevisto' da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade InformacoesEscolares com ID: {}", existingInformacoesEscolares.getId(), e);
            }
            throw e;
        }
    }
}