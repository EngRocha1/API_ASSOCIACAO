package com.example.work3.v1.InformacoesEscolares.InterfaceOpenAPI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface InterfaceOpenAPI<DTO> {

    @Operation(summary = "Busca informações de Escolares pelo ID do servidor",
            description = "Retorna a informação Escolares associada a um determinado ID de servidor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informação Escolares recuperada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sem autorização", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Informação Escolares não encontrada para o servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/byServidorId/{servidorId}")
    ResponseEntity<DTO> getInformacoesEscolaresByServidorId(
            @PathVariable String servidorId,
            @AuthenticationPrincipal UserDetails userDetails);
}