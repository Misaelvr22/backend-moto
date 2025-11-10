package com.example.agenda.controllers;

import com.example.agenda.models.Motocicletas;
import com.example.agenda.services.MotocicletasService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/motocicletas")
@CrossOrigin(origins = "*")
public class MotocicletasController {

    private final MotocicletasService service;

    public MotocicletasController(MotocicletasService service) { this.service = service; }

    @PostMapping("/crear") 
    public Motocicletas crear(@RequestBody Motocicletas m) { 
        return service.crear(m); 
    }

    @GetMapping("/listar") 
    public List<Motocicletas> listar() { 
        try {
            List<Motocicletas> motos = service.listar();
            System.out.println("Motocicletas encontradas: " + motos.size());
            return motos;
        } catch (Exception e) {
            System.err.println("Error al listar motocicletas: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/listar-todos") public List<Motocicletas> listarTodos() { return service.listarTodos(); }
    
    @PutMapping("/editar") public Motocicletas editar(@RequestBody Motocicletas m) { return service.editar(m); }

    @DeleteMapping("/eliminar-logico/{id}") public String elL(@PathVariable int id) { return service.eliminarLogico(id); }

    @DeleteMapping("/eliminar/{id}") public String elF(@PathVariable int id) { return service.eliminarFisico(id); }
}
