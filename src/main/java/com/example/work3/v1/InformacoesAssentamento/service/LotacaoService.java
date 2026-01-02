package com.example.work3.v1.InformacoesAssentamento.service;
import com.example.work3.v1.InformacoesAssentamento.domain.Listas.Lotacao;
import com.example.work3.v1.InformacoesAssentamento.repository.LotacaoRepository;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LotacaoService extends AbstractBaseService<Lotacao, String> {

    @Autowired
    public LotacaoService(LotacaoRepository repository) {
        super(repository);
    }

    @Override
    public Lotacao findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Lotacao lotacao) {
        lotacao.setAtivo(false);
    }

    @Transactional
    public Lotacao update(String id, Lotacao lotacao) {
        try {
            Lotacao existingLotacao = validarId(id);
            updateEntityFields(existingLotacao, lotacao);
            return repository.save(existingLotacao);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Lotacao existingLotacao, Lotacao newLotacao) {
        if (newLotacao.getName() != null) {
            existingLotacao.setName(newLotacao.getName());
        }
        if (newLotacao.isAtivo() != existingLotacao.isAtivo()) {
            existingLotacao.setAtivo(newLotacao.isAtivo());
        }
    }
}
