package com.jv.estudos.agenda.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jv.estudos.agenda.model.entity.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {

}
