package com.example.agenda.repository;

import com.example.agenda.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
    Usuarios findByNombreUsuarioAndPassword(String nombreUsuario, String password);
    java.util.List<Usuarios> findByActivoTrue();
    boolean existsByNombreUsuario(String nombreUsuario);
}
