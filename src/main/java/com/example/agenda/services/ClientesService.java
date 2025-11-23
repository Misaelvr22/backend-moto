package com.example.agenda.services;

import com.example.agenda.models.Clientes;
import com.example.agenda.repository.ClientesRepository;
import com.example.agenda.repository.MotocicletasRepository;
import com.example.agenda.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientesService {

    @Autowired
    ClientesRepository repo;
    
    @Autowired
    MotocicletasRepository motocicletasRepo;
    
    @Autowired
    OrdenRepository ordenRepo;

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
        // Verificar si el cliente existe
        Clientes c = repo.findById(id).orElse(null);
        if (c == null) {
            throw new RuntimeException("Cliente no encontrado");
        }
        
        // Verificar si tiene motocicletas asociadas
        long motosCount = motocicletasRepo.findAll().stream()
            .filter(m -> m.getCliente() != null && m.getCliente().getIdCliente() == id)
            .count();
        
        if (motosCount > 0) {
            throw new RuntimeException("No se puede eliminar el cliente porque tiene " + motosCount + " motocicleta(s) asociada(s). Elimínelas primero.");
        }
        
        // Verificar si tiene órdenes asociadas
        long ordenesCount = ordenRepo.findAll().stream()
            .filter(o -> o.getCliente() != null && o.getCliente().getIdCliente() == id)
            .count();
        
        if (ordenesCount > 0) {
            throw new RuntimeException("No se puede eliminar el cliente porque tiene " + ordenesCount + " orden(es) asociada(s). Elimínelas primero.");
        }
        
        // Si no hay dependencias, eliminar
        repo.deleteById(id);
        return "Cliente eliminado permanentemente";
    }
}
