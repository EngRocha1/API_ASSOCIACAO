package com.example.work3.v1.DadosBancarios.controller;

import com.example.work3.v1.DadosBancarios.domain.DadosBancarios;
import com.example.work3.v1.DadosBancarios.domain.Listas.Banco;
import com.example.work3.v1.DadosBancarios.dto.DadosBancariosResponseDTO;
import com.example.work3.v1.DadosBancarios.mapper.DadosBancariosMapper;
import com.example.work3.v1.DadosBancarios.service.BancoService;
import com.example.work3.v1.Servidor.Service.ServidorService;
import com.example.work3.v1.Servidor.domain.Servidor;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.work3.v1.DadosBancarios.service.DadosBancariosService;
import java.util.List;

@RestController
@SQLDelete(sql = "UPDATE dadosbancarios SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/dadosbancarios")
@Tag(name = "Dados Bancarios",
        description = "Operações relacionadas ao gerenciamento e consulta das Dados Bancarios dos usuários, incluindo criação, atualização, listagem e exclusão.")

public class DadosBancariosController extends AbstractController<DadosBancarios, DadosBancariosResponseDTO> {

    private final DadosBancariosService dadosBancariosService;
    private final ServidorService servidorService;
    private final BancoService bancoService;

    @Autowired
    public DadosBancariosController(
            DadosBancariosService dadosBancariosService,
            ServidorService servidorService,
            BancoService bancoService
    ) {
        this.dadosBancariosService = dadosBancariosService;
        this.servidorService = servidorService;
        this.bancoService = bancoService;
    }

    @Override
    protected List<DadosBancarios> getAllEntities() {
        return dadosBancariosService.getAll();
    }

    @Override
    protected DadosBancarios saveEntity(DadosBancarios entity) {
        return dadosBancariosService.register(entity);
    }

    @Override
    protected DadosBancarios updateEntity(String id, DadosBancarios entity) throws Exception {
        return dadosBancariosService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        dadosBancariosService.delete(id);
    }

    @Override
    protected DadosBancarios findEntityById(String id) {
        return dadosBancariosService.findById(id);
    }

    @Override
    protected DadosBancarios toEntity(DadosBancariosResponseDTO dto) {
        Servidor servidor = servidorService.validarId(dto.getServidorId());
        Banco banco = bancoService.validarId(dto.getBancoId());

        return new DadosBancarios(
                servidor,
                banco,
                dto.getConta(),
                dto.getAgencia(),
                dto.getChavePix(),
                dto.getTipo(),
                dto.isAtivo()
                        );
    }

    @Override
    protected DadosBancariosResponseDTO toResponseDTO(DadosBancarios entity, UserDetails userDetails) {
        DadosBancariosResponseDTO dto = DadosBancariosMapper.INSTANCE.toResponseDTO(entity);
        return populateAdminFields(dto, entity, userDetails);
    }

}
