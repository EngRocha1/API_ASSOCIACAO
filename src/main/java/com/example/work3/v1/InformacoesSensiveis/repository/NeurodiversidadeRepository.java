package com.example.work3.v1.InformacoesSensiveis.repository;

import com.example.work3.v1.InformacoesSensiveis.domain.Listas.Neurodiversidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface NeurodiversidadeRepository extends JpaRepository<Neurodiversidade, String>, CrudRepository<Neurodiversidade, String> {
}