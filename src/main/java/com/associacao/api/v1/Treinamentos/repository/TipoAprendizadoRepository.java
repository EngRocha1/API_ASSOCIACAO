package com.associacao.api.v1.Treinamentos.repository;
import com.associacao.api.v1.Treinamentos.domain.Listas.TipoAprendizado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TipoAprendizadoRepository extends JpaRepository<TipoAprendizado, String>, CrudRepository<TipoAprendizado, String> {
}
