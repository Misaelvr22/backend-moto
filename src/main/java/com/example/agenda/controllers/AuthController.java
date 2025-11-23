package com.example.agenda.controllers;

import com.example.agenda.models.Usuarios;
import com.example.agenda.repository.UsuariosRepository;
import com.example.agenda.utils.ROL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    UsuariosRepository repo;

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Usuarios u) {
    Usuarios user = repo.findByNombreUsuario(u.getNombreUsuario());
    if (user == null) {
        return ResponseEntity.status(401)
            .body(java.util.Map.of("error", "Usuario no encontrado"));
    }
    if (!user.getPassword().equals(u.getPassword())) {
        return ResponseEntity.status(401)
            .body(java.util.Map.of("error", "Contraseña incorrecta"));
    }
    return ResponseEntity.ok(user);
}
    @PostConstruct
    public void initAdmin() {
        Usuarios u = repo.findByNombreUsuarioAndPassword("admin", "tallermoto1234");
        if (u == null) {
            Usuarios admin = new Usuarios();
            admin.setNombreUsuario("admin");
            admin.setNombrePila("Julio Cesar");
            admin.setApellidoCompleto("Hernandez Castro");
            admin.setPassword("tallermoto1234");
            admin.setRol(ROL.ADMINISTRADOR);
            admin.setActivo(true);
            repo.save(admin);
        }
    }
}
