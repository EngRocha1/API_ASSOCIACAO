package com.example.work3.v1.DadosBancarios.service;
import com.example.work3.v1.DadosBancarios.domain.Listas.Banco;
import com.example.work3.v1.DadosBancarios.repository.BancoRepository;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BancoService extends AbstractBaseService<Banco, String> {

    @Autowired
    public BancoService(BancoRepository repository) {
        super(repository);
    }

    @Override
    public Banco findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Banco banco) {
        banco.setAtivo(false);
    }

    @Transactional
    public Banco update(String id, Banco banco) {
        try {
            Banco existingBanco = validarId(id);
            updateEntityFields(existingBanco, banco);
            return repository.save(existingBanco);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Banco existingBanco, Banco newBanco) {
        if (newBanco.getName() != null) {
            existingBanco.setName(newBanco.getName());
        }
        if (newBanco.getDescricao() != null) {
            existingBanco.setDescricao(newBanco.getDescricao());
        }
        if (newBanco.isAtivo() != existingBanco.isAtivo()) {
            existingBanco.setAtivo(newBanco.isAtivo());
        }
    }
}

