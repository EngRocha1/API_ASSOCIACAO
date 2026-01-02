package com.associacao.api.v1.InformacoesAssentamento.service;
import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.Diretoria;
import com.associacao.api.v1.InformacoesAssentamento.repository.DiretoriaRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DiretoriaService extends AbstractBaseService<Diretoria, String> {

    @Autowired
    public DiretoriaService(DiretoriaRepository repository) {
        super(repository);
    }

    @Override
    public Diretoria findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Diretoria diretoria) {
        diretoria.setAtivo(false);
    }

    @Transactional
    public Diretoria update(String id, Diretoria diretoria) {
        try {
            Diretoria existingDiretoria = validarId(id);
            updateEntityFields(existingDiretoria, diretoria);
            return repository.save(existingDiretoria);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Diretoria existingDiretoria, Diretoria newDiretoria) {
        if (newDiretoria.getName() != null) {
            existingDiretoria.setName(newDiretoria.getName());
        }
        if (newDiretoria.isAtivo() != existingDiretoria.isAtivo()) {
            existingDiretoria.setAtivo(newDiretoria.isAtivo());
        }
    }
}
