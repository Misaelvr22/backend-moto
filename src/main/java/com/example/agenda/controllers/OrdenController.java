package com.example.agenda.controllers;

import com.example.agenda.models.Orden;
import com.example.agenda.services.OrdenService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orden")
@CrossOrigin(origins = "*")
public class OrdenController {

    private final OrdenService service;

    public OrdenController(OrdenService service) { this.service = service; }

    @PostMapping("/crear") 
    public Orden crear(@RequestBody Map<String, Object> payload) { 
        System.out.println("Recibiendo payload: " + payload);
        return service.crearDesdePayload(payload); 
    }

    @GetMapping("/listar") public List<Orden> listar() { return service.listar(); }
    
    @GetMapping("/listar-todos") public List<Orden> listarTodos() { return service.listarTodos(); }
    
    @PutMapping("/reactivar/{id}") public String reactivar(@PathVariable int id) { return service.reactivar(id); }

    @PutMapping("/editar")
    public Orden editar(@RequestBody Map<String, Object> payload) { 
        System.out.println("Recibiendo payload de edición: " + payload);
        return service.editarDesdePayload(payload); 
    }

    @DeleteMapping("/eliminar-logico/{id}") public String elL(@PathVariable int id) { return service.eliminarLogico(id); }

    @DeleteMapping("/eliminar/{id}") public String elF(@PathVariable int id) { return service.eliminarFisico(id); }
}
