package com.associacao.api.v1.SuperClasses.interfaceOpenAPI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;

public interface InterfaceOpenAPI<DTO> {


    @Operation(summary = "Listar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Entrada inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sem autorização", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERINTENDENT', 'DIRECTOR', 'COORDINATOR', 'EMPLOYEE')")
    ResponseEntity<List<DTO>> getAll();


    @Operation(summary = "Criar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Entrada inválida"),
            @ApiResponse(responseCode = "403", description = "Sem autorização"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERINTENDENT', 'DIRECTOR', 'COORDINATOR', 'EMPLOYEE')")
    ResponseEntity<DTO> register(@RequestBody @Valid DTO data) throws Exception;


    @Operation(summary = "Atualizar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Entrada inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sem autorização", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERINTENDENT', 'DIRECTOR', 'COORDINATOR', 'EMPLOYEE')")
    ResponseEntity<DTO> update(@PathVariable String id, @RequestBody @Valid DTO data) throws Exception;


    @Operation(summary = "Deletar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Entrada inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sem autorização", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERINTENDENT', 'DIRECTOR')")
    ResponseEntity<Void> delete(@PathVariable String id);


    @Operation(summary = "Buscar por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item recuperado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Entrada inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sem autorização", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<DTO> getById(@PathVariable String id);


}
