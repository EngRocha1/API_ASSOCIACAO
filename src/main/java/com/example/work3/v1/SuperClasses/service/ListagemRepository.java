package com.example.work3.v1.SuperClasses.service;
import com.example.work3.v1.SuperClasses.classes.Listagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ListagemRepository extends JpaRepository<Listagem, String>, CrudRepository<Listagem, String> {
}
