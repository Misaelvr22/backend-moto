package com.example.agenda.services;

import com.example.agenda.models.Clientes;
import com.example.agenda.models.Motocicletas;
import com.example.agenda.repository.ClientesRepository;
import com.example.agenda.repository.MotocicletasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MotocicletasService {

    @Autowired
    MotocicletasRepository repo;
    
    @Autowired
    ClientesRepository clientesRepo;

    public Motocicletas crear(Motocicletas m) {
        // Buscar el cliente existente para evitar que JPA intente crear uno nuevo
        if (m.getCliente() != null && m.getCliente().getIdCliente() > 0) {
            Clientes cliente = clientesRepo.findById(m.getCliente().getIdCliente()).orElse(null);
            if (cliente != null) {
                m.setCliente(cliente);
            }
        }
        return repo.save(m);
    }

    public List<Motocicletas> listar() { return repo.findByActivoTrue(); }
    
    public List<Motocicletas> listarTodos() { return repo.findAll(); }

    public Motocicletas editar(Motocicletas m) { return repo.save(m); }

    public String eliminarLogico(int id) {
        Motocicletas m = repo.findById(id).orElse(null);
        m.setActivo(false);
        repo.save(m);
        return "Motocicleta desactivada";
    }

    public String eliminarFisico(int id) {
        repo.deleteById(id);
        return "Motocicleta eliminada permanentemente";
    }
}
