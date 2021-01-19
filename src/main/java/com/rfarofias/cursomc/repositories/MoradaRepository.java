package com.rfarofias.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rfarofias.cursomc.domain.Morada;

@Repository
public interface MoradaRepository extends JpaRepository<Morada, Integer> {
	
	
}
