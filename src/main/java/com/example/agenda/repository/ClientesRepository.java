package com.example.agenda.repository;

import com.example.agenda.models.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Integer> {
    List<Clientes> findByActivoTrue();
}
