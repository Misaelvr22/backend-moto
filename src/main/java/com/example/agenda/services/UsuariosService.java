package com.example.agenda.services;

import com.example.agenda.models.Usuarios;
import com.example.agenda.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuariosService {

    @Autowired
    UsuariosRepository repo;

    public Usuarios crear(Usuarios u) {
        validacionesUsuario(u, true);
        return repo.save(u);
    }

    public List<Usuarios> listar() { return repo.findByActivoTrue(); }
    
    public List<Usuarios> listarTodos() { return repo.findAll(); }

    public Usuarios editar(Usuarios u) {
        validacionesUsuario(u, false);
        return repo.save(u); 
    }

    public String reactivar(int id) {
        Usuarios u = repo.findById(id).orElse(null);
        if (u != null) {
            u.setActivo(true);
            repo.save(u);
            return "Usuario reactivado";
        }
        return "Usuario no encontrado";
    }

    public String eliminarLogico(int id) {
        Usuarios u = repo.findById(id).orElse(null);
        u.setActivo(false);
        repo.save(u);
        return "Usuario desactivado";
    }

    public String eliminarFisico(int id) {
        repo.deleteById(id);
        return "Usuario eliminado permanentemente";
    }

private void validacionesUsuario(Usuarios u, boolean isNew) {
        if (isNew && repo.existsByNombreUsuario(u.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        if (u.getPassword() == null || u.getPassword().length() < 8) {
            throw new RuntimeException("La contraseña debe tener al menos 8 caracteres");
        }
    }

}
