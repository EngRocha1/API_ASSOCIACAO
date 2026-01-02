package com.example.work3.v1.InformacoesEscolares.Service;

import com.example.work3.v1.InformacoesEscolares.domain.Listas.TipoInstituicao;
import com.example.work3.v1.InformacoesEscolares.repository.TipoInstituicaoRepository;
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
public class TipoInstituicaoService extends AbstractBaseService<TipoInstituicao, String> {

    private static final Logger logger = LoggerFactory.getLogger(TipoInstituicaoService.class);

    @Autowired
    public TipoInstituicaoService(TipoInstituicaoRepository repository) {
        super(repository);
    }

    @Override
    public TipoInstituicao findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(TipoInstituicao tipoInstituicao) {
        tipoInstituicao.setAtivo(false);
    }

    @Override
    @Transactional
    public TipoInstituicao update(String id, TipoInstituicao tipoInstituicao) {
        try {
            logger.debug("Iniciando atualização do tipo de instituição com ID: {}", id);
            logger.debug("Dados recebidos: {}", tipoInstituicao);

            TipoInstituicao existingTipoInstituicao = validarId(id);

            updateEntityFields(existingTipoInstituicao, tipoInstituicao);

            logger.debug("Atualização concluída com sucesso para o tipo de instituição com ID: {}", id);
            return repository.save(existingTipoInstituicao);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o tipo de instituição com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(TipoInstituicao existingTipoInstituicao, TipoInstituicao newTipoInstituicao) {
        try {
            logger.debug("Atualizando campos da entidade TipoInstituicao com ID: {}", existingTipoInstituicao.getId());

            if (newTipoInstituicao.getName() != null) {
                existingTipoInstituicao.setName(newTipoInstituicao.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingTipoInstituicao.getName());
            }

            if (newTipoInstituicao.getDescricao() != null) {
                existingTipoInstituicao.setDescricao(newTipoInstituicao.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingTipoInstituicao.getDescricao());
            }

            logger.debug("Campos da entidade TipoInstituicao atualizados com sucesso para o ID: {}", existingTipoInstituicao.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade TipoInstituicao com ID: {}", existingTipoInstituicao.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade TipoInstituicao com ID: {}", existingTipoInstituicao.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade TipoInstituicao com ID: {}", existingTipoInstituicao.getId(), e);
            }
            throw e;
        }
    }
}
