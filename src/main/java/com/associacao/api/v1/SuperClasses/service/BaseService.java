package com.associacao.api.v1.SuperClasses.service;
import java.util.List;

public interface BaseService<T, ID> {
    List<T> getAll();
    T register(T entity);
    T update(ID id, T entity) throws Exception;
    void delete(ID id);
}
