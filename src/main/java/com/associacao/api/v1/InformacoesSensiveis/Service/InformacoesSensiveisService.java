package com.associacao.api.v1.InformacoesSensiveis.Service;

import com.associacao.api.v1.InformacoesSensiveis.domain.InformacoesSensiveis;
import com.associacao.api.v1.InformacoesSensiveis.repository.InformacoesSensiveisRepository;
import com.associacao.api.v1.Servidor.Service.ServidorService;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import com.associacao.api.Exceptions.ExceptionHandlerMap;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import lombok.Getter;

@Service
public class InformacoesSensiveisService extends AbstractBaseService<InformacoesSensiveis, String> {

    private static final Logger logger = LoggerFactory.getLogger(InformacoesSensiveisService.class);

    private final InformacoesSensiveisRepository informacoesSensiveisRepository;

    @Getter
    private final ServidorService servidorService;
    @Getter
    private final GeneroService generoService;
    @Getter
    private final EstadoCivilService estadoCivilService;
    @Getter
    private final RacaEtniaService racaEtniaService;
    @Getter
    private final CorPeleService corPeleService;
    @Getter
    private final OrientacaoSexualService orientacaoSexualService;
    @Getter
    private final ExpressaoDegeneroService expressaoDegeneroService;
    @Getter
    private final PronomePreferidoService pronomePreferidoService;
    @Getter
    private final NacionalidadeService nacionalidadeService;
    @Getter
    private final DeficienciasService deficienciasService;
    @Getter
    private final GeracaoService geracaoService;
    @Getter
    private final CordaoService cordaoService;
    @Getter
    private final DoencasCronicasRarasService doencasCronicasRarasService;
    @Getter
    private final NeurodiversidadeService neurodiversidadeService;

    @Autowired
    public InformacoesSensiveisService(
            InformacoesSensiveisRepository informacoesSensiveisRepository,
            ServidorService servidorService,
            GeneroService generoService,
            EstadoCivilService estadoCivilService,
            RacaEtniaService racaEtniaService,
            CorPeleService corPeleService,
            OrientacaoSexualService orientacaoSexualService,
            ExpressaoDegeneroService expressaoDegeneroService,
            PronomePreferidoService pronomePreferidoService,
            NacionalidadeService nacionalidadeService,
            DeficienciasService deficienciasService,
            GeracaoService geracaoService,
            CordaoService cordaoService,
            DoencasCronicasRarasService doencasCronicasRarasService,
            NeurodiversidadeService neurodiversidadeService) {
        super(informacoesSensiveisRepository);
        this.informacoesSensiveisRepository = informacoesSensiveisRepository;
        this.servidorService = servidorService;
        this.generoService = generoService;
        this.estadoCivilService = estadoCivilService;
        this.racaEtniaService = racaEtniaService;
        this.corPeleService = corPeleService;
        this.orientacaoSexualService = orientacaoSexualService;
        this.expressaoDegeneroService = expressaoDegeneroService;
        this.pronomePreferidoService = pronomePreferidoService;
        this.nacionalidadeService = nacionalidadeService;
        this.deficienciasService = deficienciasService;
        this.geracaoService = geracaoService;
        this.cordaoService = cordaoService;
        this.doencasCronicasRarasService = doencasCronicasRarasService;
        this.neurodiversidadeService = neurodiversidadeService;
    }


    private void initializeLazyRelations(InformacoesSensiveis entity) {
        if (entity == null) return;
        logger.debug("Inicializando relacionamentos LAZY para InformacoesSensiveis com ID: {}", entity.getId());

        if (entity.getServidor() != null) Hibernate.initialize(entity.getServidor());
        if (entity.getGenero() != null) Hibernate.initialize(entity.getGenero());
        if (entity.getEstadoCivil() != null) Hibernate.initialize(entity.getEstadoCivil());
        if (entity.getRacaEtnia() != null) Hibernate.initialize(entity.getRacaEtnia());
        if (entity.getCorPele() != null) Hibernate.initialize(entity.getCorPele());
        if (entity.getOrientacaoSexual() != null) Hibernate.initialize(entity.getOrientacaoSexual());
        if (entity.getExpressaoDegenero() != null) Hibernate.initialize(entity.getExpressaoDegenero());
        if (entity.getPronomePreferido() != null) Hibernate.initialize(entity.getPronomePreferido());
        if (entity.getNacionalidade() != null) Hibernate.initialize(entity.getNacionalidade());
        if (entity.getDeficiencias() != null) Hibernate.initialize(entity.getDeficiencias());
        if (entity.getGeracao() != null) Hibernate.initialize(entity.getGeracao());
        if (entity.getCordao() != null) Hibernate.initialize(entity.getCordao());
        if (entity.getDoencasCronicasRaras() != null) Hibernate.initialize(entity.getDoencasCronicasRaras());
        if (entity.getNeurodiversidade() != null) Hibernate.initialize(entity.getNeurodiversidade());

        logger.debug("Relacionamentos LAZY inicializados.");
    }


    @Override
    public List<InformacoesSensiveis> getAll() {
        List<InformacoesSensiveis> entities = super.getAll();
        entities.forEach(this::initializeLazyRelations);
        return entities;
    }

    @Override
    public InformacoesSensiveis findById(String id) {
        InformacoesSensiveis entity = super.validarId(id);
        initializeLazyRelations(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    public InformacoesSensiveis findByServidorId(String servidorId) {
        logger.debug("Tentando encontrar informações sensíveis para o ID do servidor: {}", servidorId);
        Optional<InformacoesSensiveis> entityOptional = informacoesSensiveisRepository.findByServidorId(servidorId);

        if (entityOptional.isEmpty()) {
            logger.info("Nenhuma informação sensível encontrada para o ID do Servidor: {}. Retornando null.", servidorId);
            return null;
        }

        InformacoesSensiveis entity = entityOptional.get();
        initializeLazyRelations(entity);
        return entity;
    }

    @Transactional
    public InformacoesSensiveis register(InformacoesSensiveis newInformacoesSensiveis) {
        try {
            logger.debug("Registrando novas informações sensíveis para servidorId: {}", newInformacoesSensiveis.getServidor().getId());

            informacoesSensiveisRepository.findByServidorId(newInformacoesSensiveis.getServidor().getId())
                    .ifPresent(existingInfo -> {
                        logger.warn("Tentativa de criar Informações Sensíveis com servidorId duplicado: {}", newInformacoesSensiveis.getServidor().getId());
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Já existe uma informação sensível associada a este servidor."
                        );
                    });
            InformacoesSensiveis savedEntity = informacoesSensiveisRepository.save(newInformacoesSensiveis);
            initializeLazyRelations(savedEntity);
            logger.debug("Informações Sensíveis registradas com sucesso e relacionamentos inicializados.");
            return savedEntity;
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao registrar as informações sensíveis.", e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    @Override
    @Transactional
    public InformacoesSensiveis update(String id, InformacoesSensiveis newInformacoesSensiveis) {
        try {
            logger.debug("Iniciando atualização das informações sensíveis com ID: {}", id);
            logger.debug("Dados recebidos (entidade montada para atualização): {}", newInformacoesSensiveis);

            InformacoesSensiveis existingInformacoesSensiveis = validarId(id);

            if (newInformacoesSensiveis.getServidor() != null && newInformacoesSensiveis.getServidor().getId() != null) {
                String novoServidorId = newInformacoesSensiveis.getServidor().getId();
                boolean isServidorIdChanged = existingInformacoesSensiveis.getServidor() == null || !novoServidorId.equals(existingInformacoesSensiveis.getServidor().getId());

                if (isServidorIdChanged) {
                    informacoesSensiveisRepository.findByServidorIdAndIdIsNot(novoServidorId, id)
                            .ifPresent(existingOtherInfo -> {
                                logger.warn("Tentativa de atualizar Informações Sensíveis com servidorId duplicado: {}", novoServidorId);
                                throw new ResponseStatusException(
                                        HttpStatus.CONFLICT,
                                        "Já existe uma informação sensível associada a este servidor."
                                );
                            });
                }
            }

            existingInformacoesSensiveis.setNomeSocial(newInformacoesSensiveis.getNomeSocial());
            existingInformacoesSensiveis.setAtivo(newInformacoesSensiveis.isAtivo());

            if (newInformacoesSensiveis.getServidor() != null) {
                existingInformacoesSensiveis.setServidor(newInformacoesSensiveis.getServidor());
            }
            if (newInformacoesSensiveis.getGenero() != null) {
                existingInformacoesSensiveis.setGenero(newInformacoesSensiveis.getGenero());
            }
            if (newInformacoesSensiveis.getEstadoCivil() != null) {
                existingInformacoesSensiveis.setEstadoCivil(newInformacoesSensiveis.getEstadoCivil());
            }
            if (newInformacoesSensiveis.getRacaEtnia() != null) {
                existingInformacoesSensiveis.setRacaEtnia(newInformacoesSensiveis.getRacaEtnia());
            }
            if (newInformacoesSensiveis.getCorPele() != null) {
                existingInformacoesSensiveis.setCorPele(newInformacoesSensiveis.getCorPele());
            }
            if (newInformacoesSensiveis.getOrientacaoSexual() != null) {
                existingInformacoesSensiveis.setOrientacaoSexual(newInformacoesSensiveis.getOrientacaoSexual());
            }
            if (newInformacoesSensiveis.getExpressaoDegenero() != null) {
                existingInformacoesSensiveis.setExpressaoDegenero(newInformacoesSensiveis.getExpressaoDegenero());
            }
            if (newInformacoesSensiveis.getPronomePreferido() != null) {
                existingInformacoesSensiveis.setPronomePreferido(newInformacoesSensiveis.getPronomePreferido());
            }
            if (newInformacoesSensiveis.getNacionalidade() != null) {
                existingInformacoesSensiveis.setNacionalidade(newInformacoesSensiveis.getNacionalidade());
            }
            if (newInformacoesSensiveis.getDeficiencias() != null) {
                existingInformacoesSensiveis.setDeficiencias(newInformacoesSensiveis.getDeficiencias());
            }
            if (newInformacoesSensiveis.getGeracao() != null) {
                existingInformacoesSensiveis.setGeracao(newInformacoesSensiveis.getGeracao());
            }
            if (newInformacoesSensiveis.getCordao() != null) {
                existingInformacoesSensiveis.setCordao(newInformacoesSensiveis.getCordao());
            }
            if (newInformacoesSensiveis.getDoencasCronicasRaras() != null) {
                existingInformacoesSensiveis.setDoencasCronicasRaras(newInformacoesSensiveis.getDoencasCronicasRaras());
            }
            if (newInformacoesSensiveis.getNeurodiversidade() != null) {
                existingInformacoesSensiveis.setNeurodiversidade(newInformacoesSensiveis.getNeurodiversidade());
            }
            if (newInformacoesSensiveis.getPossuiDeficiencia() != null) {
                existingInformacoesSensiveis.setPossuiDeficiencia(newInformacoesSensiveis.getPossuiDeficiencia());
            }
            if (newInformacoesSensiveis.getFazUsoCordao() != null) {
                existingInformacoesSensiveis.setFazUsoCordao(newInformacoesSensiveis.getFazUsoCordao());
            }
            if (newInformacoesSensiveis.getPossuiDoencaCronicaRara() != null) {
                existingInformacoesSensiveis.setPossuiDoencaCronicaRara(newInformacoesSensiveis.getPossuiDoencaCronicaRara());
            }
            if (newInformacoesSensiveis.getEhNeurodivergente() != null) {
                existingInformacoesSensiveis.setEhNeurodivergente(newInformacoesSensiveis.getEhNeurodivergente());
            }

            InformacoesSensiveis updatedEntity = informacoesSensiveisRepository.save(existingInformacoesSensiveis);
            initializeLazyRelations(updatedEntity);

            logger.debug("Atualização concluída com sucesso para as informações sensíveis com ID: {}", id);
            return updatedEntity;
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao atualizar as informações sensíveis com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    @Override
    protected void setAtivoFalse(InformacoesSensiveis informacoesSensiveis) {
        informacoesSensiveis.setAtivo(false);
    }

    @Override
    @Transactional
    public void delete(String id) {
        InformacoesSensiveis informacoesSensiveis = validarId(id);
        try {
            setAtivoFalse(informacoesSensiveis);
            informacoesSensiveisRepository.save(informacoesSensiveis);
            logger.debug("Informações Sensíveis com ID {} marcadas como inativas com sucesso.", id);
        } catch (Exception e) {
            logger.error("Erro ao marcar as informações sensíveis com ID: {} como inativas", id, e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao marcar as informações sensíveis como inativas.", e);
        }
    }
}