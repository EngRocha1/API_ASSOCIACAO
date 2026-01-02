package com.example.work3.v1.InformacoesSensiveis.repository;

import com.example.work3.v1.InformacoesSensiveis.domain.Listas.DoencasCronicasRaras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DoencasCronicasRarasRepository extends JpaRepository<DoencasCronicasRaras, String>, CrudRepository<DoencasCronicasRaras, String> {
}