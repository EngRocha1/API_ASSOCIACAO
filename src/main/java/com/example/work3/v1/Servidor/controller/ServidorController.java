package com.example.work3.v1.Servidor.controller;
import com.example.work3.v1.Users.service.UserService;
import com.example.work3.Exceptions.ExceptionHandlerMap;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import com.example.work3.v1.Servidor.Service.ServidorService;
import com.example.work3.v1.Servidor.domain.Servidor;
import com.example.work3.v1.Servidor.dto.ServidorResponseDTO;
import com.example.work3.v1.Servidor.repository.ServidorRepository;
import com.example.work3.v1.Users.domain.Users;
import com.example.work3.v1.Users.dto.UserResponseDTO;
import com.example.work3.v1.Users.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.net.URLConnection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@SQLDelete(sql = "UPDATE servidor SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/servidor")
@Tag(name = "Servidor",
        description = "Operações relacionadas ao gerenciamento e consulta dos servidores públicos, incluindo criação, atualização, listagem e exclusão.")

public class ServidorController extends AbstractController<Servidor, ServidorResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(ServidorController.class);

    private final ServidorRepository repository;
    private final UserRepository userRepository;
    private final ServidorService servidorService;
    private final UserService userService;


    @Autowired
    public ServidorController(ServidorRepository repository,
                              UserRepository userRepository,
                              ServidorService servidorService,
                              UserService userService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.servidorService = servidorService;
        this.userService = userService;
    }

    @Override
    protected List<Servidor> getAllEntities() {
        return repository.findAll();
    }

    @GetMapping("/cpf/{cpf}")
    public Servidor buscarServidorPorCpf(@PathVariable String cpf) {
        return servidorService.buscarServidorPorCpf(cpf);
    }

    @GetMapping("/usuario/{usuarioId}")
    public Servidor buscarServidorPorUsuarioId(@PathVariable String usuarioId) {
        return servidorService.buscarServidorPorUsuarioId(usuarioId);
    }

    @Override
    protected Servidor saveEntity(@RequestBody @Valid Servidor entity) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (entity.getUsuario().getId() == null) {
                Optional<Users> usuarioOptional = Optional.ofNullable(userService.criarUsuarioParaServidor(entity, userDetails));
                if (usuarioOptional.isPresent()) {
                    Users usuario = usuarioOptional.get();
                    entity.setUsuario(usuario);
                } else {
                    // Lidar com o caso em que o usuário não foi encontrado
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado para o servidor.");
                }
            } else {
                userRepository.save(entity.getUsuario());
            }
            return repository.save(entity);
        } catch (Exception e) {
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar o servidor.", e);
        }
    }


    @Override
    protected Servidor updateEntity(@PathVariable String id, @RequestBody @Valid Servidor entity) {
        return servidorService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(@PathVariable String id) {
        servidorService.delete(id);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users currentUser = (Users) userDetails;
        Servidor existingEntity = findEntityById(id);
        if (!currentUser.canEditEntity(existingEntity.getUsuario())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        try {
            deleteEntity(id);
        } catch (Exception e) {
            handleException(e);
        }
        return ResponseEntity.noContent().build();
    }


    @Override
    protected Servidor findEntityById(@PathVariable  String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Servidor não encontrado"));
    }

    @Override
    protected Servidor toEntity(ServidorResponseDTO dto) {
        Users usuario = null;

        if (dto.getUsuarioId() != null && !dto.getUsuarioId().isEmpty()) {
            usuario = userRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            logger.debug("Usuário encontrado: {}", usuario.getId());
        } else {

            try {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                usuario = userService.criarUsuarioParaServidor(
                        new Servidor(
                                dto.getId(),
                                dto.getName(),
                                dto.getCpf(),
                                dto.getDataNascimento(),
                                null,
                                dto.isAtivo()),
                        userDetails);
                logger.debug("Usuário criado: {}", usuario.getId());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar usuário para o servidor.", e);
            }
        }
        return new Servidor(
                        dto.getId(),
                        dto.getName(),
                        dto.getCpf(),
                        dto.getDataNascimento(),
                usuario,
                dto.isAtivo());
    }



    @Override
    @Transactional
    protected ServidorResponseDTO toResponseDTO(Servidor entity, UserDetails userDetails) {
        UserResponseDTO userResponseDTO = null;
        if (entity.getUsuario() != null) {
            userResponseDTO = new UserResponseDTO(
                    entity.getUsuario().getId(),
                    entity.getUsuario().getLogin(),
                    entity.getUsuario().getRole(),
                    entity.getUsuario().getAtivo()
            );
            userResponseDTO = populateAdminFields(userResponseDTO, entity.getUsuario(), userDetails);
        }

        String filename = entity.getFilename();

        String fileDownloadUri = null;
        if (filename != null && !filename.isEmpty()) {
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("servidor/download/")
                    .path(entity.getId())
                    .toUriString();
        }

        ServidorResponseDTO dto = new ServidorResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.isAtivo(),
                entity.getCpf(),
                entity.getDataNascimento(),
                entity.getUsuario() != null ? entity.getUsuario().getId() : null,
                entity.getUsuario() != null ? entity.getUsuario().getUsername() : null,
                filename,
                fileDownloadUri
        );
        return populateAdminFields(dto, entity, userDetails);
    }

    @Operation(summary = "Download de Arquivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo recuperado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Arquivo não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERINTENDENT', 'DIRECTOR', 'COORDINATOR', 'EMPLOYEE')")
    @GetMapping(value = "/download/{id}")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> serveFile(@PathVariable String id) {
        try {
            Servidor servidor = servidorService.findById(id);
            if (servidor.getFileData() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Arquivo não encontrado");
            }

            // Verifique se o nome do arquivo está definido
            String filename = servidor.getFilename();
            if (filename == null || filename.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Nome do arquivo não definido");
            }

            // Garantir que a extensão do arquivo seja preservada
            String contentType = "application/octet-stream";
            String extension = "";
            int i = filename.lastIndexOf('.');
            if (i > 0) {
                extension = filename.substring(i + 1);
                contentType = URLConnection.guessContentTypeFromName(filename);
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(new ByteArrayResource(servidor.getFileData()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @Operation(summary = "Upload de Arquivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo enviado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Entrada inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERINTENDENT', 'DIRECTOR', 'COORDINATOR', 'EMPLOYEE')")
    @PostMapping(value = "/upload/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> handleFileUpload(@PathVariable String id, @RequestPart("file") MultipartFile file) {
        try {

            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            Servidor updatedServidor = servidorService.updateFile(id, file, originalFilename);

            // Normalizar o nome do arquivo usando o método do ServidorService
            String normalizedFilename = servidorService.normalizeFilename(originalFilename);

            Servidor savedServidor = servidorService.storeFile(id, file, normalizedFilename);

            return ResponseEntity.ok("Arquivo enviado com sucesso: " + normalizedFilename);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Falha ao enviar o arquivo.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body("Servidor não encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno do servidor.");
        }
    }

    // Método para exclusão de arquivo
    @Operation(summary = "Exclusão de Arquivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Arquivo não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERINTENDENT', 'DIRECTOR', 'COORDINATOR', 'EMPLOYEE')")
    @DeleteMapping(value = "/deleteFile/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable String id) {
        try {
            servidorService.deleteFile(id);
            return ResponseEntity.ok("Arquivo excluído com sucesso.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body("Arquivo não encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno do servidor.");
        }
    }


    @Operation(summary = "Alteração de Arquivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Arquivo não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERINTENDENT', 'DIRECTOR', 'COORDINATOR', 'EMPLOYEE')")
    @PutMapping(value = "/updateFile/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateFile(@PathVariable String id, @RequestPart("file") MultipartFile file) {
        try {
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            Servidor updatedServidor = servidorService.updateFile(id, file, originalFilename);

            return ResponseEntity.ok("Arquivo alterado com sucesso: " + originalFilename);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Falha ao alterar o arquivo.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body("Arquivo não encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno do servidor.");
        }
    }

}
