package com.example.work3.v1.Servidor.Service;

import com.example.work3.v1.Servidor.dto.ServidorResponseDTO;
import com.example.work3.v1.Users.domain.Users;
import com.example.work3.v1.Users.repository.UserRepository;
import com.example.work3.v1.Users.service.UserService;
import com.example.work3.Exceptions.ResourceNotFoundException;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import com.example.work3.v1.Servidor.domain.Servidor;
import com.example.work3.v1.Servidor.repository.ServidorRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.example.work3.Exceptions.ExceptionHandlerMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;


@Service
public class ServidorService extends AbstractBaseService<Servidor, String> {

    private static final Logger logger = LoggerFactory.getLogger(ServidorService.class);

    public ServidorService(
            ServidorRepository repository,
            UserRepository userRepository,
            UserService userService)
    {
        super(repository);
        this.userService = userService;
        this.userRepository = userRepository;
    }
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    private ServidorRepository servidorRepository;

    public Servidor buscarServidorPorCpf(String cpf) {
        return servidorRepository.findByCpf(cpf);
    }

    public Servidor buscarServidorPorUsuarioId(String usuarioId) {
        return servidorRepository.findByUsuario(usuarioId);
    }

    @Override
    public Servidor findById(String id) {
        try {
            return validarId(id);
        } catch (Exception e) {
            // Log de erro
            logger.error("Erro ao buscar o servidor com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public Servidor validarId(String id) {
        try {
            Optional<Servidor> optionalServidor = repository.findById(id);
            if (optionalServidor.isPresent()) {
                return optionalServidor.get();
            } else {
                throw new ResourceNotFoundException("Servidor não encontrado");
            }
        } catch (Exception e) {
            // Log de erro
            logger.error("Erro ao validar o ID do servidor: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public Servidor storeFile(String id, MultipartFile file, String filename) throws IOException {
        try
        {
            Servidor servidor = findById(id);
            String normalizedFilename = normalizeFilename(filename);
            servidor.setFilename(normalizedFilename);
            servidor.setFileData(file.getBytes());
            return repository.save(servidor);
        }
        catch (IOException e) {
            logger.error("Erro ao armazenar o arquivo para o servidor com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao armazenar o arquivo para o servidor com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
    }
    }

    public Servidor updateFile(String id, MultipartFile file, String filename) throws IOException {
        try {
            if (file.isEmpty()) { logger.error("Falha ao atualizar o arquivo: O arquivo está vazio.");
                throw new IOException("O arquivo está vazio.");
            } Servidor servidor = findById(id);
            if (servidor == null) {
                logger.error("Falha ao atualizar o arquivo: Servidor com ID {} não encontrado.", id);
                throw new ResourceNotFoundException("Servidor não encontrado");
            } String normalizedFilename = normalizeFilename(filename);
            servidor.setFilename(normalizedFilename);
            servidor.setFileData(file.getBytes());
            Servidor updatedServidor = repository.save(servidor);
            logger.debug("Arquivo atualizado com sucesso para o Servidor com ID: {}", id);
            return updatedServidor;
        }
        catch (IOException e) {
            logger.error("Erro ao atualizar o arquivo para o Servidor com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e); throw e;
        }
        catch (ResourceNotFoundException e) {
            logger.error("Erro ao atualizar o arquivo: {}", e.getMessage());
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw e;
        }
        catch (Exception e) { logger.error("Erro inesperado ao atualizar o arquivo para o Servidor com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
    }
    }


    public String normalizeFilename(String filename) {
        if (filename == null) {
            return null;
        }
        // Remover acentos
        String normalized = Normalizer.normalize(filename, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        // Substituir caracteres especiais por _
        normalized = normalized.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        return normalized;
    }



    public Optional<Servidor> getFile(String id) {
        try {
            return Optional.ofNullable(findById(id));
        } catch (Exception e) {
            // Log de erro
            logger.error("Erro ao buscar o arquivo do servidor com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void deleteFile(String id) {
        try {
            Servidor servidor = findById(id);
            servidor.setFileData(null);
            servidor.setFilename(null);
            repository.save(servidor);
        } catch (Exception e) {
            // Log de erro
            logger.error("Erro ao deletar o arquivo do servidor com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }


       private ServidorResponseDTO convertToDTO(Servidor servidor) {
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/servidor/")
                .path(servidor.getId())
                .path("/download")
                .toUriString();

        return ServidorResponseDTO.builder()
                .id(servidor.getId())
                .name(servidor.getName())
                .cpf(servidor.getCpf())
                .dataNascimento(servidor.getDataNascimento())
                .usuarioId(servidor.getUsuario().getId())
                .usuarioNome(servidor.getUsuario().getLogin())
                .filename(servidor.getFilename())
                .fileDownloadUri(fileDownloadUri)
                .build();
    }

        @Override
        public Servidor update(String id, Servidor servidor) {
            try {
                // Adicione logs detalhados aqui
                logger.debug("Iniciando atualização do servidor com ID: {}", id);
                logger.debug("Dados recebidos: {}", servidor);

                Servidor existingServidor = validarId(id);

                // Verificar se o usuarioId está vazio ou nulo
                if (servidor.getUsuario().getId() == null || servidor.getUsuario().getId().isEmpty()) {
                    // Buscar o usuarioId da tabela Servidor
                    String usuarioId = existingServidor.getUsuario().getId();
                    servidor.getUsuario().setId(usuarioId);
                    logger.warn("usuarioId está vazio ou nulo. Usando usuarioId existente: {}", usuarioId);
                } else {
                    // Validar se o usuarioId corresponde a um usuário válido
                    Optional<Users> usuario = userRepository.findById(servidor.getUsuario().getId());
                    if (usuario.isEmpty()) {
                        logger.warn("usuarioId inválido: {}", servidor.getUsuario().getId());
                    }
                }

                updateEntityFields(existingServidor, servidor);

                // Log de sucesso
                logger.debug("Atualização concluída com sucesso para o servidor com ID: {}", id);
                return repository.save(existingServidor);
            } catch (Exception e) {
                // Log de erro
                logger.error("Erro ao atualizar o servidor com ID: {}", id, e);
                ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
            }
        }

    public void updateEntityFields(Servidor existingServidor, Servidor newServidor) {
        try {
            logger.debug("Atualizando campos da entidade Servidor com ID: {}", existingServidor.getId());

            if (newServidor.getName() != null) {
                existingServidor.setName(newServidor.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingServidor.getName());
            }

            if (newServidor.getCpf() != null && !newServidor.getCpf().isEmpty()) {
                existingServidor.setCpf(newServidor.getCpf());
                logger.debug("Campo 'cpf' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'cpf' não fornecido. Mantendo valor existente: {}", existingServidor.getCpf());
            }

            if (newServidor.getDataNascimento() != null) {
                existingServidor.setDataNascimento(newServidor.getDataNascimento());
                logger.debug("Campo 'dataNascimento' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'dataNascimento' não fornecido. Mantendo valor existente: {}", existingServidor.getDataNascimento());
            }

            if (newServidor.getUsuario() != null && newServidor.getUsuario().getId() != null) {
                existingServidor.getUsuario().setId(newServidor.getUsuario().getId());
                logger.debug("Campo 'usuarioId' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'usuarioId' não fornecido. Mantendo valor existente: {}", existingServidor.getUsuario().getId());
            }

            if (newServidor.getUsuario() != null && newServidor.getUsuario().getUsername() != null) {
                existingServidor.getUsuario().setLogin(newServidor.getUsuario().getUsername());
                logger.debug("Campo 'usuarioNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'usuarioNome' não fornecido. Mantendo valor existente: {}", existingServidor.getUsuario().getLogin());
            }

            if (newServidor.getFilename() != null) {
                existingServidor.setFilename(newServidor.getFilename());
                logger.debug("Campo 'filename' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'filename' não fornecido. Mantendo valor existente: {}", existingServidor.getFilename());
            }

            logger.debug("Campos da entidade Servidor atualizados com sucesso para o ID: {}", existingServidor.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Servidor com ID: {}", existingServidor.getId(), e);
            } else if (e.getMessage().contains("cpf")) {
                logger.error("Erro ao atualizar o campo 'cpf' da entidade Servidor com ID: {}", existingServidor.getId(), e);
            } else if (e.getMessage().contains("dataNascimento")) {
                logger.error("Erro ao atualizar o campo 'dataNascimento' da entidade Servidor com ID: {}", existingServidor.getId(), e);
            } else if (e.getMessage().contains("usuarioId")) {
                logger.error("Erro ao atualizar o campo 'usuarioId' da entidade Servidor com ID: {}", existingServidor.getId(), e);
            } else if (e.getMessage().contains("usuarioNome")) {
                logger.error("Erro ao atualizar o campo 'usuarioNome' da entidade Servidor com ID: {}", existingServidor.getId(), e);
            } else if (e.getMessage().contains("filename")) {
                logger.error("Erro ao atualizar o campo 'filename' da entidade Servidor com ID: {}", existingServidor.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Servidor com ID: {}", existingServidor.getId(), e);
            }
            throw e;
        }
    }



@Override
protected void setAtivoFalse(Servidor servidor) {
    servidor.setAtivo(false);
    if (servidor.getUsuario() != null) {
        servidor.getUsuario().setAtivo(false);
        userRepository.save(servidor.getUsuario());
        logger.debug("Usuário com ID {} marcado como inativo com sucesso.", servidor.getUsuario().getId());
    }
}

    @Override
    @Transactional
    public void delete(String id) {
        Servidor servidor = validarId(id);
        try {
            setAtivoFalse(servidor);
            repository.save(servidor);
            logger.debug("Servidor com ID {} marcado como inativo com sucesso.", id);
        } catch (Exception e) {
            logger.error("Erro ao marcar o servidor com ID: {} como inativo", id, e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao marcar o servidor como inativo.", e);
        }
    }

    
}


