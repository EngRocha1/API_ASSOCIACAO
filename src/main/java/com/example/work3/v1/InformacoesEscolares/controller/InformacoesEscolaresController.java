package com.example.work3.v1.InformacoesEscolares.controller;

import com.example.work3.v1.InformacoesEscolares.InterfaceOpenAPI.InterfaceOpenAPI;
import com.example.work3.v1.InformacoesEscolares.Service.*;
import com.example.work3.v1.InformacoesEscolares.domain.InformacoesEscolares;
import com.example.work3.v1.InformacoesEscolares.domain.Listas.*;
import com.example.work3.v1.InformacoesEscolares.dto.InformacoesEscolaresResponseDTO;
import com.example.work3.v1.Servidor.Service.ServidorService;
import com.example.work3.v1.Servidor.domain.Servidor;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@SQLDelete(sql = "UPDATE informacoesescolares SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/informacoesescolares")
@Tag(name = "Informações Escolares",
        description = "Operações relacionadas ao gerenciamento e consulta das informações escolares dos usuários, incluindo criação, atualização, listagem e exclusão.")

public class InformacoesEscolaresController
        extends AbstractController<InformacoesEscolares, InformacoesEscolaresResponseDTO> implements InterfaceOpenAPI<InformacoesEscolaresResponseDTO>
        {

    private final InformacoesEscolaresService informacoesEscolaresService;
    private final ServidorService servidorService;
    private final CursoService cursoService;
    private final StatusFormacaoService statusFormacaoService;
    private final FormacaoAcademicaService formacaoAcademicaService;
    private final InstituicaoEnsinoService instituicaoEnsinoService;
    private final PeriodoService periodoService;
    private final SemestreService semestreService;

    @Autowired
    public InformacoesEscolaresController(
            InformacoesEscolaresService informacoesEscolaresService,
            ServidorService servidorService,
            CursoService cursoService,
            StatusFormacaoService statusFormacaoService, FormacaoAcademicaService formacaoAcademicaService,
            InstituicaoEnsinoService instituicaoEnsinoService,
            PeriodoService periodoService,
            SemestreService semestreService
    ) {
        this.informacoesEscolaresService = informacoesEscolaresService;
        this.servidorService = servidorService;
        this.cursoService = cursoService;
        this.statusFormacaoService = statusFormacaoService;
        this.formacaoAcademicaService = formacaoAcademicaService;
        this.instituicaoEnsinoService = instituicaoEnsinoService;
        this.periodoService = periodoService;
        this.semestreService = semestreService;
    }

    @Override
    protected List<InformacoesEscolares> getAllEntities() {
        return informacoesEscolaresService.getAll();
    }

    @Override
    protected InformacoesEscolares saveEntity(InformacoesEscolares entity) {
        return informacoesEscolaresService.register(entity);
    }

    @Override
    protected InformacoesEscolares updateEntity(String id, InformacoesEscolares entity) throws Exception {
        return informacoesEscolaresService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        informacoesEscolaresService.delete(id);
    }

    @Override
    protected InformacoesEscolares findEntityById(String id) {
        return informacoesEscolaresService.findById(id);
    }

    @Override
    protected InformacoesEscolares toEntity(InformacoesEscolaresResponseDTO dto) {
        Servidor servidor = servidorService.validarId(dto.getServidorId());
        Curso curso = cursoService.validarId(dto.getCursoId());
        FormacaoAcademica formacaoAcademica = formacaoAcademicaService.validarId(dto.getFormacaoAcademicaId());
        StatusFormacao statusFormacao = statusFormacaoService.validarId(dto.getStatusFormacaoId());
        InstituicaoEnsino instituicaoEnsino = instituicaoEnsinoService.validarId(dto.getInstituicaoEnsinoId());
        Periodo periodoDoDia = periodoService.validarId(dto.getPeriodoDoDiaId());
        Semestre semestre = semestreService.validarId(dto.getSemestreId());

        return new InformacoesEscolares(
                curso,
                dto.getInicioCurso(),
                dto.getCargaHorariaAtual(),
                dto.getCargaHorariaTotal(),
                formacaoAcademica,
                statusFormacao,
                instituicaoEnsino,
                dto.getMatriculaInstitucional(),
                periodoDoDia,
                semestre,
                servidor,
                dto.getTerminoPrevisto(),
                dto.isAtivo()
                );
    }

    @Override
    protected InformacoesEscolaresResponseDTO toResponseDTO(InformacoesEscolares entity, UserDetails userDetails) {
        InformacoesEscolaresResponseDTO dto = InformacoesEscolaresResponseDTO.builder()
                .id(entity.getId())
                .cursoId(entity.getCurso().getId())
                .cursoNome(entity.getCurso().getName())
                .cursoDescricao(entity.getCurso().getDescricao())
                .inicioCurso(entity.getInicioCurso())
                .cargaHorariaAtual(entity.getCargaHorariaAtual())
                .cargaHorariaTotal(entity.getCargaHorariaTotal())
                .formacaoAcademicaId(entity.getFormacaoAcademica().getId())
                .formacaoAcademicaNome(entity.getFormacaoAcademica().getName())
                .formacaoAcademicaDescricao(entity.getFormacaoAcademica().getDescricao())
                .statusFormacaoId(entity.getStatusFormacao().getId())
                .statusFormacaoNome(entity.getStatusFormacao().getName())
                .statusFormacaoDescricao(entity.getStatusFormacao().getDescricao())
                .instituicaoEnsinoId(entity.getInstituicaoEnsino().getId())
                .instituicaoEnsinoNome(entity.getInstituicaoEnsino().getName())
                .instituicaoEnsinoDescricao(entity.getInstituicaoEnsino().getDescricao())
                .matriculaInstitucional(entity.getMatriculaInstitucional())
                .periodoDoDiaId(entity.getPeriodoDoDia().getId())
                .periodoDoDiaNome(entity.getPeriodoDoDia().getName())
                .periodoDoDiaDescricao(entity.getPeriodoDoDia().getDescricao())
                .semestreId(entity.getSemestre().getId())
                .semestreNome(entity.getSemestre().getName())
                .semestreDescricao(entity.getSemestre().getDescricao())
                .servidorId(entity.getServidor().getId())
                .servidorNome(entity.getServidor().getName())
                .terminoPrevisto(entity.getTerminoPrevisto())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/byServidorId/{servidorId}")
    public ResponseEntity<InformacoesEscolaresResponseDTO> getInformacoesEscolaresByServidorId(
            @PathVariable String servidorId,
            @AuthenticationPrincipal UserDetails userDetails) {
        InformacoesEscolares informacoesEscolares = informacoesEscolaresService.findByServidorId(servidorId);
        if (informacoesEscolares == null) {
            return ResponseEntity.noContent().build();
        }
        InformacoesEscolaresResponseDTO dto = toResponseDTO(informacoesEscolares, userDetails);
        return ResponseEntity.ok(dto);
    }

}
