package com.associacao.api.v1.SuperClasses.service;

import com.associacao.api.Exceptions.*;
import com.example.work3.Exceptions.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractBaseService<T, ID> implements BaseService<T, ID> {

    protected JpaRepository<T, ID> repository;
    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public AbstractBaseService(JpaRepository<T, ID> repository) {
        this.repository = repository;
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public T register(T entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public T update(ID id, T entity) throws Exception {
        validarId(id);
        try {
            return repository.save(entity);
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(ID id) {
        T entity = validarId(id);
        try {
            setAtivoFalse(entity);
            repository.save(entity);
         } catch (Exception e) {
            handleException(e);
        }
    }

    protected abstract void setAtivoFalse(T entity);
    public T validarId(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityClass.getSimpleName() + " não encontrada com id: " + id));
    }

    public abstract T findById(ID id);

    protected void handleException(Exception e) {
        if (e instanceof NotFoundException ||
                e instanceof UnauthorizedAccessException ||
                e instanceof AlreadyExistsException) {
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
        } else {
            ExceptionHandlers.handleUnexpectedException(e);
        }
    }
}
