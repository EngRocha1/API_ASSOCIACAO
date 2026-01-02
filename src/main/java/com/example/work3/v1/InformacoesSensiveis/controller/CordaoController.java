package com.example.work3.v1.InformacoesSensiveis.controller;

import com.example.work3.v1.InformacoesSensiveis.Service.CordaoService; // Importe o serviço correto
import com.example.work3.v1.InformacoesSensiveis.dto.CordaoResponseDTO; // Importe o DTO de resposta correto
import com.example.work3.v1.InformacoesSensiveis.domain.Listas.Cordao; // Importe o modelo correto
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@SQLDelete(sql = "UPDATE cordao SET ativo = false WHERE id = ?") // ATENÇÃO: Atualize a tabela para 'cordao'
@Where(clause = "ativo = true")
@RequestMapping("/cordao") // ATENÇÃO: Atualize o endpoint para '/cordao'
@Tag(name = "Corda ou Cor/Raça dos Usuários", // Atualize a descrição da Tag
        description = "Operações relacionadas ao gerenciamento e consulta da corda (ou cor/raça) dos usuários, incluindo criação, atualização, listagem e exclusão.")
public class CordaoController extends AbstractController<Cordao, CordaoResponseDTO> {

    private final CordaoService cordaoService; // Injete o serviço de Cordao

    @Autowired
    public CordaoController(CordaoService cordaoService) { // Construtor com o serviço de Cordao
        this.cordaoService = cordaoService;
    }

    @Override
    protected List<Cordao> getAllEntities() {
        return cordaoService.getAll();
    }

    @Override
    protected Cordao saveEntity(@RequestBody @Valid Cordao entity) {
        return cordaoService.register(entity);
    }

    @Override
    protected Cordao updateEntity(@PathVariable String id, @RequestBody @Valid Cordao entity) throws Exception {
        return cordaoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(@PathVariable String id) {
        cordaoService.delete(id);
    }

    @Override
    protected Cordao findEntityById(@PathVariable String id) {
        return cordaoService.validarId(id);
    }

    @Override
    protected Cordao toEntity(CordaoResponseDTO dto) {
        // Crie uma nova instância de Cordao com base no DTO
        return new Cordao(dto.getId(), dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected CordaoResponseDTO toResponseDTO(Cordao entity, UserDetails userDetails) {
        // Crie uma nova instância de CordaoResponseDTO com base na entidade
        CordaoResponseDTO dto = new CordaoResponseDTO(entity.getId(), entity.getName(), entity.getDescricao(), entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }
}