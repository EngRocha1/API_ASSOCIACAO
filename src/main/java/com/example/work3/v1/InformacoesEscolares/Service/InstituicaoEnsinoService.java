package com.example.work3.v1.InformacoesEscolares.Service;

import com.example.work3.v1.InformacoesEscolares.domain.Listas.InstituicaoEnsino;
import com.example.work3.v1.InformacoesEscolares.repository.InstituicaoEnsinoRepository;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import com.example.work3.Exceptions.ExceptionHandlerMap;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InstituicaoEnsinoService extends AbstractBaseService<InstituicaoEnsino, String> {

    private static final Logger logger = LoggerFactory.getLogger(InstituicaoEnsinoService.class);

    @Autowired
    public InstituicaoEnsinoService(InstituicaoEnsinoRepository repository) {
        super(repository);
    }

    @Override
    public InstituicaoEnsino findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(InstituicaoEnsino instituicaoEnsino) {
        instituicaoEnsino.setAtivo(false);
    }

    @Override
    @Transactional
    public InstituicaoEnsino update(String id, InstituicaoEnsino instituicaoEnsino) {
        try {
            logger.debug("Iniciando atualização da instituição de ensino com ID: {}", id);
            logger.debug("Dados recebidos: {}", instituicaoEnsino);

            InstituicaoEnsino existingInstituicaoEnsino = validarId(id);

            updateEntityFields(existingInstituicaoEnsino, instituicaoEnsino);

            logger.debug("Atualização concluída com sucesso para a instituição de ensino com ID: {}", id);
            return repository.save(existingInstituicaoEnsino);
        } catch (Exception e) {
            logger.error("Erro ao atualizar a instituição de ensino com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(InstituicaoEnsino existingInstituicaoEnsino, InstituicaoEnsino newInstituicaoEnsino) {
        try {
            logger.debug("Atualizando campos da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId());

            if (newInstituicaoEnsino.getName() != null) {
                existingInstituicaoEnsino.setName(newInstituicaoEnsino.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getName());
            }

            if (newInstituicaoEnsino.getDescricao() != null) {
                existingInstituicaoEnsino.setDescricao(newInstituicaoEnsino.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getDescricao());
            }

            if (newInstituicaoEnsino.getRedesSociais() != null) {
                existingInstituicaoEnsino.setRedesSociais(newInstituicaoEnsino.getRedesSociais());
                logger.debug("Campo 'redesSociais' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'redesSociais' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getRedesSociais());
            }

            if (newInstituicaoEnsino.getSite() != null) {
                existingInstituicaoEnsino.setSite(newInstituicaoEnsino.getSite());
                logger.debug("Campo 'site' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'site' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getSite());
            }

            if (newInstituicaoEnsino.getNumeroProfessores() != null) {
                existingInstituicaoEnsino.setNumeroProfessores(newInstituicaoEnsino.getNumeroProfessores());
                logger.debug("Campo 'numeroProfessores' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'numeroProfessores' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getNumeroProfessores());
            }

            if (newInstituicaoEnsino.getNumeroAlunos() != null) {
                existingInstituicaoEnsino.setNumeroAlunos(newInstituicaoEnsino.getNumeroAlunos());
                logger.debug("Campo 'numeroAlunos' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'numeroAlunos' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getNumeroAlunos());
            }

            if (newInstituicaoEnsino.getDataFundacao() != null) {
                existingInstituicaoEnsino.setDataFundacao(newInstituicaoEnsino.getDataFundacao());
                logger.debug("Campo 'dataFundacao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'dataFundacao' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getDataFundacao());
            }

            if (newInstituicaoEnsino.getNivelEnsino() != null) {
                existingInstituicaoEnsino.setNivelEnsino(newInstituicaoEnsino.getNivelEnsino());
                logger.debug("Campo 'nivelEnsino' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'nivelEnsino' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getNivelEnsino());
            }

            if (newInstituicaoEnsino.getTipo() != null) {
                existingInstituicaoEnsino.setTipo(newInstituicaoEnsino.getTipo());
                logger.debug("Campo 'tipo' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'tipo' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getTipo());
            }

            if (newInstituicaoEnsino.getEmail() != null) {
                existingInstituicaoEnsino.setEmail(newInstituicaoEnsino.getEmail());
                logger.debug("Campo 'email' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'email' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getEmail());
            }

            if (newInstituicaoEnsino.getTelefone() != null) {
                existingInstituicaoEnsino.setTelefone(newInstituicaoEnsino.getTelefone());
                logger.debug("Campo 'telefone' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'telefone' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getTelefone());
            }

            if (newInstituicaoEnsino.getCnpj() != null) {
                existingInstituicaoEnsino.setCnpj(newInstituicaoEnsino.getCnpj());
                logger.debug("Campo 'cnpj' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'cnpj' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getCnpj());
            }

            if (newInstituicaoEnsino.getEndereco() != null) {
                existingInstituicaoEnsino.setEndereco(newInstituicaoEnsino.getEndereco());
                logger.debug("Campo 'endereco' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'endereco' não fornecido. Mantendo valor existente: {}", existingInstituicaoEnsino.getEndereco());
            }

            logger.debug("Campos da entidade InstituicaoEnsino atualizados com sucesso para o ID: {}", existingInstituicaoEnsino.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else if (e.getMessage().contains("redesSociais")) {
                logger.error("Erro ao atualizar o campo 'redesSociais' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else if (e.getMessage().contains("site")) {
                logger.error("Erro ao atualizar o campo 'site' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else if (e.getMessage().contains("numeroProfessores")) {
                logger.error("Erro ao atualizar o campo 'numeroProfessores' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else if (e.getMessage().contains("numeroAlunos")) {
                logger.error("Erro ao atualizar o campo 'numeroAlunos' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else if (e.getMessage().contains("dataFundacao")) {
                logger.error("Erro ao atualizar o campo 'dataFundacao' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else if (e.getMessage().contains("nivelEnsino")) {
                logger.error("Erro ao atualizar o campo 'nivelEnsino' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else if (e.getMessage().contains("tipo")) {
                logger.error("Erro ao atualizar o campo 'tipo' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else if (e.getMessage().contains("email")) {
                logger.error("Erro ao atualizar o campo 'email' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else if (e.getMessage().contains("telefone")) {
                logger.error("Erro ao atualizar o campo 'telefone' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else if (e.getMessage().contains("cnpj")) {
                logger.error("Erro ao atualizar o campo 'cnpj' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else if (e.getMessage().contains("endereco")) {
                logger.error("Erro ao atualizar o campo 'endereco' da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade InstituicaoEnsino com ID: {}", existingInstituicaoEnsino.getId(), e);
            }
            throw e;
        }
    }
}
