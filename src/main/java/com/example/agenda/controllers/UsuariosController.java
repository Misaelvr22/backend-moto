package com.example.agenda.controllers;

import com.example.agenda.models.Usuarios;
import com.example.agenda.services.UsuariosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuariosController {

    private final UsuariosService service;

    public UsuariosController(UsuariosService service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody Usuarios u) {
        try {
            Usuarios usuario = service.crear(u);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/listar")
    public List<Usuarios> listar() {
        return service.listar();
    }
    
    @GetMapping("/listar-todos")
    public List<Usuarios> listarTodos() {
        return service.listarTodos();
    }
    
    @PutMapping("/reactivar/{id}")
    public String reactivar(@PathVariable int id) {
        return service.reactivar(id);
    }

    @PutMapping("/editar")
    public Usuarios editar(@RequestBody Usuarios u) {
        return service.editar(u);
    }

    // 🔹 Para el usuario normal (borrado lógico)
    @DeleteMapping("/eliminar-logico/{id}")
    public String eliminarLogico(@PathVariable int id) {
        return service.eliminarLogico(id);
    }

    // 🔹 Para administrador (borrado físico)
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        return service.eliminarFisico(id);
    }
}
