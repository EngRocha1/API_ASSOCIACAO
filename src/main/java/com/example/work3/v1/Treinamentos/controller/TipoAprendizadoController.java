package com.example.work3.v1.Treinamentos.controller;

import com.example.work3.v1.Treinamentos.domain.Listas.TipoAprendizado;
import com.example.work3.v1.Treinamentos.dto.TipoAprendizadoResponseDTO;
import com.example.work3.v1.Treinamentos.service.TipoAprendizadoService;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
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
@SQLDelete(sql = "UPDATE tipotreinamentos SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/tipotreinamentos")
@Tag(name = "TipoAprendizado",
        description = "Operações relacionadas ao gerenciamento e consulta dos TipoAprendizado oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class TipoAprendizadoController extends AbstractController<TipoAprendizado, TipoAprendizadoResponseDTO> {

    private final TipoAprendizadoService TipoAprendizadoService;

    @Autowired
    public TipoAprendizadoController(TipoAprendizadoService TipoAprendizadoService) {
        this.TipoAprendizadoService = TipoAprendizadoService;
    }

    @Override
    protected List<TipoAprendizado> getAllEntities() {
        return TipoAprendizadoService.getAll();
    }

    @Override
    protected TipoAprendizado saveEntity(TipoAprendizado entity) {
        return TipoAprendizadoService.register(entity);
    }

    @Override
    protected TipoAprendizado updateEntity(String id, TipoAprendizado entity) throws Exception {
        return TipoAprendizadoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        TipoAprendizadoService.delete(id);
    }

    @Override
    protected TipoAprendizado findEntityById(String id) {
        return TipoAprendizadoService.validarId(id);
    }

    @Override
    protected TipoAprendizado toEntity(TipoAprendizadoResponseDTO dto) {
        return new TipoAprendizado(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected TipoAprendizadoResponseDTO toResponseDTO(TipoAprendizado entity, UserDetails userDetails) {
        TipoAprendizadoResponseDTO dto = TipoAprendizadoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
