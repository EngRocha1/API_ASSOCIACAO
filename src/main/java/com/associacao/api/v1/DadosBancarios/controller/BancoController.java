package com.associacao.api.v1.DadosBancarios.controller;

import com.associacao.api.v1.DadosBancarios.domain.Listas.Banco;
import com.associacao.api.v1.DadosBancarios.dto.BancoResponseDTO;
import com.associacao.api.v1.DadosBancarios.service.BancoService;
import com.associacao.api.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SQLDelete(sql = "UPDATE banco SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/banco")
@Tag(name = "Banco",
        description = "Operações relacionadas ao gerenciamento e consulta dos Banco oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class BancoController extends AbstractController<Banco, BancoResponseDTO> {

    private final BancoService bancoService;

    @Autowired
    public BancoController(BancoService bancoService) {
        this.bancoService = bancoService;
    }

    @Override
    protected List<Banco> getAllEntities() {
        return bancoService.getAll();
    }

    @Override
    protected Banco saveEntity(Banco entity) {
        return bancoService.register(entity);
    }

    @Override
    protected Banco updateEntity(String id, Banco entity) throws Exception {
        return bancoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        bancoService.delete(id);
    }

    @Override
    protected Banco findEntityById(String id) {
        return bancoService.validarId(id);
    }

    @Override
    protected Banco toEntity(BancoResponseDTO dto) {
        return new Banco(
                dto.getId(),
                dto.getName(),
                dto.getDescricao(),
                dto.isAtivo());
    }

    @Override
    protected BancoResponseDTO toResponseDTO(Banco entity, UserDetails userDetails) {
        BancoResponseDTO dto = BancoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .descricao(entity.getDescricao())
                .ativo(entity.isAtivo())
                .build();;
        return populateAdminFields(dto, entity, userDetails);
    }

}
