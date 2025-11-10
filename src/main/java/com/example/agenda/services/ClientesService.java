package com.example.agenda.services;

import com.example.agenda.models.Clientes;
import com.example.agenda.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientesService {

    @Autowired
    ClientesRepository repo;

    public Clientes crear(Clientes c) { return repo.save(c); }

    public List<Clientes> listar() { return repo.findByActivoTrue(); }
    
    public List<Clientes> listarTodos() { return repo.findAll(); }

    public Clientes editar(Clientes c) { return repo.save(c); }
    
    public String reactivar(int id) {
        Clientes c = repo.findById(id).orElse(null);
        if (c != null) {
            c.setActivo(true);
            repo.save(c);
            return "Cliente reactivado";
        }
        return "Cliente no encontrado";
    }

    public String eliminarLogico(int id) {
        Clientes c = repo.findById(id).orElse(null);
        c.setActivo(false);
        repo.save(c);
        return "Cliente desactivado";
    }

    public String eliminarFisico(int id) {
        repo.deleteById(id);
        return "Cliente eliminado permanentemente";
    }
}
