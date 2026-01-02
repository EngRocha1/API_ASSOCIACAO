package com.example.work3.v1.InformacoesSensiveis.controller;

import com.example.work3.v1.InformacoesSensiveis.Service.NeurodiversidadeService;
import com.example.work3.v1.InformacoesSensiveis.domain.Listas.Neurodiversidade;
import com.example.work3.v1.InformacoesSensiveis.dto.NeurodiversidadeResponseDTO;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SQLDelete(sql = "UPDATE neurodiversidade SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/neurodiversidade")
@Tag(name = "Neurodiversidade",
        description = "Operações relacionadas ao gerenciamento e consulta de tipos de neurodiversidade (TDAH, Dislexia, etc.), incluindo criação, atualização, listagem e exclusão.")
public class NeurodiversidadeController extends AbstractController<Neurodiversidade, NeurodiversidadeResponseDTO> {

    private final NeurodiversidadeService neurodiversidadeService;

    @Autowired
    public NeurodiversidadeController(NeurodiversidadeService neurodiversidadeService) {
        this.neurodiversidadeService = neurodiversidadeService;
    }

    @Override
    protected List<Neurodiversidade> getAllEntities() {
        return neurodiversidadeService.getAll();
    }

    @Override
    protected Neurodiversidade saveEntity(@RequestBody @Valid Neurodiversidade entity) {
        return neurodiversidadeService.register(entity);
    }

    @Override
    protected Neurodiversidade updateEntity(@PathVariable String id, @RequestBody @Valid Neurodiversidade entity) throws Exception {
        return neurodiversidadeService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(@PathVariable String id) {
        neurodiversidadeService.delete(id);
    }

    @Override
    protected Neurodiversidade findEntityById(@PathVariable String id) {
        return neurodiversidadeService.validarId(id);
    }

    @Override
    protected Neurodiversidade toEntity(NeurodiversidadeResponseDTO dto) {
        return new Neurodiversidade(dto.getId(), dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected NeurodiversidadeResponseDTO toResponseDTO(Neurodiversidade entity, UserDetails userDetails) {
        NeurodiversidadeResponseDTO dto = new NeurodiversidadeResponseDTO(entity.getId(), entity.getName(), entity.getDescricao(), entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }
}