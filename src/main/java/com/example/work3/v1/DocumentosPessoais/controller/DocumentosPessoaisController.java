package com.example.work3.v1.DocumentosPessoais.controller;

import com.example.work3.v1.DocumentosPessoais.domain.DocumentosPessoais;
import com.example.work3.v1.DocumentosPessoais.dto.DocumentosPessoaisResponseDTO;
import com.example.work3.v1.DocumentosPessoais.service.DocumentosPessoaisService;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.work3.v1.DocumentosPessoais.mapper.DocumentosPessoaisMapper;
import java.util.List;

@RestController
@SQLDelete(sql = "UPDATE documentospessoais SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/documentospessoais")
@Tag(name = "DocumentosPessoais",
        description = "Operações relacionadas ao gerenciamento e consulta dos DocumentosPessoais dos usuários, incluindo criação, atualização, listagem e exclusão.")
public class DocumentosPessoaisController extends AbstractController<DocumentosPessoais, DocumentosPessoaisResponseDTO> {

    private final DocumentosPessoaisService documentosPessoaisService;
    private final DocumentosPessoaisMapper documentosPessoaisMapper = DocumentosPessoaisMapper.INSTANCE;

    @Autowired
    public DocumentosPessoaisController(
            DocumentosPessoaisService documentosPessoaisService
    ) {
        this.documentosPessoaisService = documentosPessoaisService;
    }

    /**
     * Retorna todas as entidades de documentos pessoais.
     *
     * @return Lista de entidades de documentos pessoais.
     */
    @Override
    protected List<DocumentosPessoais> getAllEntities() {
        return documentosPessoaisService.getAll();
    }

    /**
     * Salva uma nova entidade de documento pessoal.
     *
     * @param entity Entidade de documento pessoal a ser salva.
     * @return Entidade de documento pessoal salva.
     */
    @Override
    protected DocumentosPessoais saveEntity(DocumentosPessoais entity) {
        return documentosPessoaisService.register(entity);
    }

    /**
     * Atualiza uma entidade de documento pessoal existente.
     *
     * @param id     ID da entidade a ser atualizada.
     * @param entity Entidade de documento pessoal com os dados atualizados.
     * @return Entidade de documento pessoal atualizada.
     * @throws Exception Se ocorrer um erro durante a atualização.
     */
    @Override
    protected DocumentosPessoais updateEntity(String id, DocumentosPessoais entity) throws Exception {
        return documentosPessoaisService.update(id, entity);
    }

    /**
     * Exclui uma entidade de documento pessoal pelo ID.
     *
     * @param id ID da entidade a ser excluída.
     */
    @Override
    @Transactional
    protected void deleteEntity(String id) {
        documentosPessoaisService.delete(id);
    }

    /**
     * Encontra uma entidade de documento pessoal pelo ID.
     *
     * @param id ID da entidade a ser encontrada.
     * @return Entidade de documento pessoal encontrada.
     */
    @Override
    protected DocumentosPessoais findEntityById(String id) {
        return documentosPessoaisService.findById(id);
    }

    /**
     * Converte um DTO de resposta em uma entidade de documento pessoal.
     *
     * @param dto DTO de resposta a ser convertido.
     * @return Entidade de documento pessoal.
     */
    @Override
    protected DocumentosPessoais toEntity(DocumentosPessoaisResponseDTO dto) {
        return documentosPessoaisMapper.toEntity(dto);
    }

    /**
     * Converte uma entidade de documento pessoal em um DTO de resposta.
     *
     * @param entity      Entidade de documento pessoal a ser convertida.
     * @param userDetails Detalhes do usuário autenticado.
     * @return DTO de resposta.
     */
    @Override
    protected DocumentosPessoaisResponseDTO toResponseDTO(DocumentosPessoais entity, UserDetails userDetails) {
        DocumentosPessoaisResponseDTO dto = documentosPessoaisMapper.toResponseDTO(entity);
        return populateAdminFields(dto, entity, userDetails);
    }
}