package com.example.agenda.controllers;

import com.example.agenda.models.Clientes;
import com.example.agenda.services.ClientesService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClientesController {

    private final ClientesService service;

    public ClientesController(ClientesService service) { this.service = service; }

    @PostMapping("/crear") public Clientes crear(@RequestBody Clientes c) { return service.crear(c); }

    @GetMapping("/listar") public List<Clientes> listar() { return service.listar(); }
    
    @GetMapping("/listar-todos") public List<Clientes> listarTodos() { return service.listarTodos(); }

    @PutMapping("/editar") public Clientes editar(@RequestBody Clientes c) { return service.editar(c); }
    
    @PutMapping("/reactivar/{id}") public String reactivar(@PathVariable int id) { return service.reactivar(id); }

    @DeleteMapping("/eliminar-logico/{id}") public String elL(@PathVariable int id) { return service.eliminarLogico(id); }

    @DeleteMapping("/eliminar/{id}") public String elF(@PathVariable int id) { return service.eliminarFisico(id); }
}
