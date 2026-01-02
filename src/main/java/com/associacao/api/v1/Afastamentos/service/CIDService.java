package com.associacao.api.v1.Afastamentos.service;

import com.associacao.api.v1.Afastamentos.domain.Listas.CID;
import com.associacao.api.v1.Afastamentos.repository.CIDRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import com.associacao.api.Exceptions.ExceptionHandlerMap;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CIDService extends AbstractBaseService<CID, String> {

    private static final Logger logger = LoggerFactory.getLogger(CIDService.class);
    private static final String CID_API_URL = "https://cid10.cpp-ti.com.br/api/data";

    @Autowired
    public CIDService(CIDRepository repository) {
        super(repository);
    }

    @Override
    public CID findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(CID cid) {
        cid.setAtivo(false);
    }

    @Override
    @Transactional
    public CID update(String id, CID cid) {
        try {
            logger.debug("Iniciando atualização do CID com ID: {}", id);
            logger.debug("Dados recebidos: {}", cid);

            CID existingCID = validarId(id);

            updateEntityFields(existingCID, cid);

            logger.debug("Atualização concluída com sucesso para o CID com ID: {}", id);
            return repository.save(existingCID);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o CID com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(CID existingCID, CID newCID) {
        try {
            logger.debug("Atualizando campos da entidade CID com ID: {}", existingCID.getId());

            if (newCID.getCodigo() != null) {
                existingCID.setCodigo(newCID.getCodigo());
                logger.debug("Campo 'codigo' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'codigo' não fornecido. Mantendo valor existente: {}", existingCID.getCodigo());
            }

            if (newCID.getName() != null) {
                existingCID.setName(newCID.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingCID.getName());
            }

            if (newCID.getDescricao() != null) {
                existingCID.setDescricao(newCID.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingCID.getDescricao());
            }

            logger.debug("Campos da entidade CID atualizados com sucesso para o ID: {}", existingCID.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("codigo")) {
                logger.error("Erro ao atualizar o campo 'codigo' da entidade CID com ID: {}", existingCID.getId(), e);
            } else if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade CID com ID: {}", existingCID.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade CID com ID: {}", existingCID.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade CID com ID: {}", existingCID.getId(), e);
            }
            throw e;
        }
    }


    @Transactional
    public void atualizarCIDs() {
        RestTemplate restTemplate = new RestTemplate();
        String CID_API_URL = "https://cid10.cpp-ti.com.br/api";
        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(CID_API_URL, JsonNode.class);
            JsonNode data = response.getBody().get("data");

            logger.info("Iniciando a atualização dos CIDs a partir da API.");

            for (JsonNode cidNode : data) {
                String codigo = cidNode.get("codigo").asText();
                String nome = cidNode.get("nome").asText();
                String descricao = cidNode.get("codigo_nome").asText();

                CID cidFromApi = new CID(codigo, nome, descricao, true);
                CID existingCID = repository.findById(codigo).orElse(null);

                if (existingCID == null) {
                    repository.save(cidFromApi);
                    logger.info("Novo CID adicionado: Código = {}, Nome = {}", codigo, nome);
                } else if (!existingCID.getName().equals(cidFromApi.getName()) ||
                        !existingCID.getDescricao().equals(cidFromApi.getDescricao()) ||
                        !existingCID.getCodigo().equals(cidFromApi.getCodigo())) {
                    logger.info("Atualizando CID existente: Código = {}, Nome = {}", codigo, nome);
                    updateEntityFields(existingCID, cidFromApi);
                    repository.save(existingCID);
                    logger.info("CID atualizado com sucesso: Código = {}, Nome = {}", codigo, nome);
                } else {
                    logger.info("CID já está atualizado: Código = {}, Nome = {}", codigo, nome);
                }
            }

            logger.info("Atualização dos CIDs concluída com sucesso.");
        } catch (HttpClientErrorException e) {
            logger.error("Erro ao buscar dados da API de CID: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar dados da API de CID", e);
        }
    }

    @Autowired
    private CIDRepository cidRepository;

    public List<CID> searchCIDs(String searchTerm) {
         if (searchTerm == null || searchTerm.trim().length() < 2) {
            throw new IllegalArgumentException("O termo de busca deve ter pelo menos 2 caracteres.");
        }
        String termLower = searchTerm.trim().toLowerCase();
        List<CID> matchingCIDs = cidRepository.findByCodigoContainingIgnoreCaseOrNameContainingIgnoreCaseOrDescricaoContainingIgnoreCase(
                termLower, termLower, termLower);
        return matchingCIDs.stream()
                .limit(100)
                .collect(Collectors.toList());
    }

}