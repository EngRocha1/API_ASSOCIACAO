package com.associacao.api.v1.Afastamentos.controller;

import com.associacao.api.v1.Afastamentos.domain.Afastamentos;
import com.associacao.api.v1.Afastamentos.domain.Listas.Suspensao;
import com.associacao.api.v1.Afastamentos.dto.SuspensaoResponseDTO;
import com.associacao.api.v1.Afastamentos.service.AfastamentosService;
import com.associacao.api.v1.Afastamentos.service.SuspensaoService;
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
@SQLDelete(sql = "UPDATE suspensao SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/suspensao")
@Tag(name = "Suspensao",
        description = "Operações relacionadas ao gerenciamento e consulta dos Tipo de Suspensao oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class SuspensaoController extends AbstractController<Suspensao, SuspensaoResponseDTO> {

    private final SuspensaoService suspensaoService;
    private final AfastamentosService afastamentosService;

    @Autowired
    public SuspensaoController(SuspensaoService suspensaoService, AfastamentosService afastamentosService) {
        this.suspensaoService = suspensaoService;
        this.afastamentosService = afastamentosService;
    }

    @Override
    protected List<Suspensao> getAllEntities() {
        return suspensaoService.getAll();
    }

    @Override
    protected Suspensao saveEntity(Suspensao entity) {
        return suspensaoService.register(entity);
    }

    @Override
    protected Suspensao updateEntity(String id, Suspensao entity) throws Exception {
        return suspensaoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        suspensaoService.delete(id);
    }

    @Override
    protected Suspensao findEntityById(String id) {
        return suspensaoService.validarId(id);
    }

    @Override
    protected Suspensao toEntity(SuspensaoResponseDTO dto) {
        Afastamentos afastamentos = afastamentosService.validarId(dto.getAfastamentosId());

        return new Suspensao(
                dto.getId(),
                dto.getName(),
                dto.getDescricao(),
                afastamentos,
                dto.isAtivo());
    }

    @Override
    protected SuspensaoResponseDTO toResponseDTO(Suspensao entity, UserDetails userDetails) {
        SuspensaoResponseDTO dto = SuspensaoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .descricao(entity.getDescricao())
                .afastamentosId(entity.getAfastamentos().getId())
                .afastamentosNome(entity.getAfastamentos().getName())
                .afastamentosPeriodoInicial(entity.getAfastamentos().getPeriodoInicio().toString())
                .afastamentosPeriodoFinal(entity.getAfastamentos().getPeriodoFim().toString())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
}

}
