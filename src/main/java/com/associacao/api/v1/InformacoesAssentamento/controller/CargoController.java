package com.associacao.api.v1.InformacoesAssentamento.controller;

import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.Cargo;
import com.associacao.api.v1.InformacoesAssentamento.dto.CargoResponseDTO;
import com.associacao.api.v1.InformacoesAssentamento.service.CargoService;
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
@SQLDelete(sql = "UPDATE cargo SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/cargo")
@Tag(name = "Cargo",
        description = "Operações relacionadas ao gerenciamento e consulta dos Cargo oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class CargoController extends AbstractController<Cargo, CargoResponseDTO> {

    private final CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @Override
    protected List<Cargo> getAllEntities() {
        return cargoService.getAll();
    }

    @Override
    protected Cargo saveEntity(Cargo entity) {
        return cargoService.register(entity);
    }

    @Override
    protected Cargo updateEntity(String id, Cargo entity) throws Exception {
        return cargoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        cargoService.delete(id);
    }

    @Override
    protected Cargo findEntityById(String id) {
        return cargoService.validarId(id);
    }

    @Override
    protected Cargo toEntity(CargoResponseDTO dto) {
        return new Cargo(
                dto.getId(),
                dto.getName(),
                dto.isAtivo()
        );
    }

    @Override
    protected CargoResponseDTO toResponseDTO(Cargo entity, UserDetails userDetails) {
        CargoResponseDTO dto = CargoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
