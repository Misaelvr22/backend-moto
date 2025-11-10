package com.example.agenda.repository;

import com.example.agenda.models.Motocicletas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotocicletasRepository extends JpaRepository<Motocicletas, Integer> {
    List<Motocicletas> findByActivoTrue();
}
