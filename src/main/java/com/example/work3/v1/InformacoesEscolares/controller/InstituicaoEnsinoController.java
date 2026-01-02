package com.example.work3.v1.InformacoesEscolares.controller;

import com.example.work3.v1.InformacoesEscolares.Service.InstituicaoEnsinoService;
import com.example.work3.v1.InformacoesEscolares.domain.Listas.InstituicaoEnsino;
import com.example.work3.v1.InformacoesEscolares.dto.InstituicaoEnsinoResponseDTO;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController

@SQLDelete(sql = "UPDATE instituicaoensino SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/instituicaoensino")
@Tag(name = "Instituição de Ensino",
        description = "Operações relacionadas ao gerenciamento e consulta das instituições de ensino, incluindo criação, atualização, listagem e exclusão.")

public class InstituicaoEnsinoController extends AbstractController<InstituicaoEnsino, InstituicaoEnsinoResponseDTO> {

    private final InstituicaoEnsinoService instituicaoEnsinoService;

    @Autowired
    public InstituicaoEnsinoController(InstituicaoEnsinoService instituicaoEnsinoService) {
        this.instituicaoEnsinoService = instituicaoEnsinoService;
    }

    @Override
    protected List<InstituicaoEnsino> getAllEntities() {
        return instituicaoEnsinoService.getAll();
    }

    @Override
    protected InstituicaoEnsino saveEntity(InstituicaoEnsino entity) {
        return instituicaoEnsinoService.register(entity);
    }

    @Override
    protected InstituicaoEnsino updateEntity(String id, InstituicaoEnsino entity) throws Exception {
        return instituicaoEnsinoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        instituicaoEnsinoService.delete(id);
    }

    @Override
    protected InstituicaoEnsino findEntityById(String id) {
        return instituicaoEnsinoService.validarId(id);
    }

    @Override
    protected InstituicaoEnsino toEntity(InstituicaoEnsinoResponseDTO dto) {
        return new InstituicaoEnsino(
                dto.getId(),
                dto.getName(),
                dto.getCnpj(),
                dto.getEmail(),
                dto.getEndereco(),
                dto.getRedesSociais(),
                dto.getSite(),
                dto.getTelefone(),
                dto.getNivelEnsino(),
                dto.getDataFundacao(),
                dto.getNumeroAlunos(),
                dto.getNumeroProfessores(),
                dto.getTipo()
        );
    }

    @Override
    protected InstituicaoEnsinoResponseDTO toResponseDTO(InstituicaoEnsino entity, UserDetails userDetails) {
        InstituicaoEnsinoResponseDTO dto = InstituicaoEnsinoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cnpj(entity.getCnpj())
                .email(entity.getEmail())
                .endereco(entity.getEndereco())
                .redesSociais(entity.getRedesSociais())
                .site(entity.getSite())
                .telefone(entity.getTelefone())
                .nivelEnsino(entity.getNivelEnsino())
                .dataFundacao(entity.getDataFundacao())
                .numeroAlunos(entity.getNumeroAlunos())
                .numeroProfessores(entity.getNumeroProfessores())
                .tipo(entity.getTipo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
