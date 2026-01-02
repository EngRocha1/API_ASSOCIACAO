package com.associacao.api.v1.Treinamentos.controller;

import com.associacao.api.v1.Treinamentos.domain.Treinamentos;
import com.associacao.api.v1.Treinamentos.dto.TreinamentosResponseDTO;
import com.associacao.api.v1.Treinamentos.mapper.TreinamentosMapper;
import com.associacao.api.v1.Treinamentos.service.TreinamentosService;
import com.associacao.api.v1.Servidor.Service.ServidorService;
import com.associacao.api.v1.Servidor.domain.Servidor;
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
@SQLDelete(sql = "UPDATE treinamentos SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/treinamentos")
@Tag(name = "Treinamentos",
        description = "Operações relacionadas ao gerenciamento e consulta dos Treinamentos dos usuários, incluindo criação, atualização, listagem e exclusão.")
public class TreinamentosController extends AbstractController<Treinamentos, TreinamentosResponseDTO> {

    private final TreinamentosService treinamentosService;
    private final ServidorService servidorService;
    private final TreinamentosMapper treinamentosMapper;

    @Autowired
    public TreinamentosController(
            TreinamentosService treinamentosService,
            ServidorService servidorService,
            TreinamentosMapper treinamentosMapper
    ) {
        this.treinamentosService = treinamentosService;
        this.servidorService = servidorService;
        this.treinamentosMapper = treinamentosMapper;
    }

    @Override
    protected List<Treinamentos> getAllEntities() {
        return treinamentosService.getAll();
    }

    @Override
    protected Treinamentos saveEntity(Treinamentos entity) {
        return treinamentosService.register(entity);
    }

    @Override
    protected Treinamentos updateEntity(String id, Treinamentos entity) throws Exception {
        return treinamentosService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        treinamentosService.delete(id);
    }

    @Override
    protected Treinamentos findEntityById(String id) {
        return treinamentosService.findById(id);
    }

    @Override
    protected Treinamentos toEntity(TreinamentosResponseDTO dto) {
        Servidor servidor = servidorService.validarId(dto.getServidorId());
        Treinamentos treinamentos = treinamentosMapper.toEntity(dto);
        treinamentos.setServidor(servidor);
        return treinamentos;
    }

    @Override
    protected TreinamentosResponseDTO toResponseDTO(Treinamentos entity, UserDetails userDetails) {
        TreinamentosResponseDTO dto = treinamentosMapper.toResponseDTO(entity);
        return populateAdminFields(dto, entity, userDetails);
    }
}