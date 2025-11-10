package com.example.agenda.services;

import com.example.agenda.models.Clientes;
import com.example.agenda.models.Motocicletas;
import com.example.agenda.models.Orden;
import com.example.agenda.repository.ClientesRepository;
import com.example.agenda.repository.MotocicletasRepository;
import com.example.agenda.repository.OrdenRepository;
import com.example.agenda.utils.STATUS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrdenService {

    @Autowired
    OrdenRepository repo;
    
    @Autowired
    ClientesRepository clientesRepo;
    
    @Autowired
    MotocicletasRepository motocicletasRepo;

    public Orden crear(Orden o) {
        System.out.println("Creando orden con cliente ID: " + (o.getCliente() != null ? o.getCliente().getIdCliente() : "null"));
        System.out.println("Creando orden con moto ID: " + (o.getMotocicleta() != null ? o.getMotocicleta().getIdMotocicleta() : "null"));
        
        // Buscar el cliente existente
        if (o.getCliente() != null && o.getCliente().getIdCliente() > 0) {
            Clientes cliente = clientesRepo.findById(o.getCliente().getIdCliente()).orElse(null);
            if (cliente != null) {
                System.out.println("Cliente encontrado: " + cliente.getNombreCliente());
                o.setCliente(cliente);
            }
        }
        
        // Buscar la motocicleta existente
        if (o.getMotocicleta() != null && o.getMotocicleta().getIdMotocicleta() > 0) {
            Motocicletas moto = motocicletasRepo.findById(o.getMotocicleta().getIdMotocicleta()).orElse(null);
            if (moto != null) {
                System.out.println("Motocicleta encontrada: " + moto.getMarca() + " " + moto.getModelo());
                o.setMotocicleta(moto);
            }
        }
        
        Orden ordenGuardada = repo.save(o);
        System.out.println("Orden guardada con ID: " + ordenGuardada.getIdOrden());
        return ordenGuardada;
    }
    
    public Orden crearDesdePayload(Map<String, Object> payload) {
        try {
            System.out.println("=== Iniciando creaci\u00f3n de orden ===");
            System.out.println("Payload recibido: " + payload);
            
            Orden orden = new Orden();
            System.out.println("1. Seteando descripcionServicio");
            orden.setDescripcionServicio((String) payload.get("descripcionServicio"));
            System.out.println("2. Seteando refacciones");
            orden.setRefacciones((String) payload.get("refacciones"));
            System.out.println("3. Seteando status: " + payload.get("status"));
            orden.setStatus(STATUS.valueOf((String) payload.get("status")));
            System.out.println("4. Seteando activo");
            orden.setActivo(true);
            
            // Manejar costo
            System.out.println("5. Manejando costo");
            if (payload.containsKey("costo")) {
                Object costoObj = payload.get("costo");
                System.out.println("   Costo obj: " + costoObj + " (" + (costoObj != null ? costoObj.getClass().getName() : "null") + ")");
                if (costoObj != null) {
                    if (costoObj instanceof Number) {
                        orden.setCosto(((Number) costoObj).doubleValue());
                    } else if (costoObj instanceof String && !((String) costoObj).isEmpty()) {
                        orden.setCosto(Double.parseDouble((String) costoObj));
                    }
                }
            }
            
            // Parsear fechas
            System.out.println("6. Parseando fechas");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("   FechaEntrada: " + payload.get("fechaEntrada"));
            orden.setFechaEntrada(sdf.parse((String) payload.get("fechaEntrada")));
            
            String fechaEntregaStr = (String) payload.get("fechaEntrega");
            System.out.println("   FechaEntrega: " + fechaEntregaStr);
            if (fechaEntregaStr != null && !fechaEntregaStr.isEmpty()) {
                orden.setFechaEntrega(sdf.parse(fechaEntregaStr));
            }
            
            // Buscar cliente
            System.out.println("7. Buscando cliente");
            @SuppressWarnings("unchecked")
            Map<String, Object> clienteMap = (Map<String, Object>) payload.get("cliente");
            if (clienteMap != null) {
                System.out.println("   ClienteMap: " + clienteMap);
                int idCliente = (Integer) clienteMap.get("idCliente");
                System.out.println("   Buscando cliente con ID: " + idCliente);
                Clientes cliente = clientesRepo.findById(idCliente).orElse(null);
                System.out.println("   Cliente encontrado: " + (cliente != null ? cliente.getNombreCliente() : "null"));
                orden.setCliente(cliente);
            }
            
            // Buscar motocicleta
            System.out.println("8. Buscando motocicleta");
            @SuppressWarnings("unchecked")
            Map<String, Object> motoMap = (Map<String, Object>) payload.get("motocicleta");
            if (motoMap != null) {
                System.out.println("   MotoMap: " + motoMap);
                int idMoto = (Integer) motoMap.get("idMotocicleta");
                System.out.println("   Buscando moto con ID: " + idMoto);
                Motocicletas moto = motocicletasRepo.findById(idMoto).orElse(null);
                System.out.println("   Moto encontrada: " + (moto != null ? moto.getMarca() + " " + moto.getModelo() : "null"));
                orden.setMotocicleta(moto);
            }
            
            System.out.println("9. Guardando orden en base de datos");
            Orden ordenGuardada = repo.save(orden);
            System.out.println("10. Orden guardada exitosamente con ID: " + ordenGuardada.getIdOrden());
            return ordenGuardada;
        } catch (Exception e) {
            System.err.println("Error al crear orden: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al crear orden", e);
        }
    }

    public List<Orden> listar() { return repo.findByActivoTrue(); }
    
    public List<Orden> listarTodos() { return repo.findAll(); }

    public Orden editar(Orden o) { return repo.save(o); }
    
    public String reactivar(int id) {
        Orden o = repo.findById(id).orElse(null);
        if (o != null) {
            o.setActivo(true);
            repo.save(o);
            return "Orden reactivada";
        }
        return "Orden no encontrada";
    }
    
    public Orden editarDesdePayload(Map<String, Object> payload) {
        try {
            // Obtener el ID de la orden a editar
            int idOrden = (Integer) payload.get("idOrden");
            Orden orden = repo.findById(idOrden).orElse(null);
            
            if (orden == null) {
                throw new RuntimeException("Orden no encontrada");
            }
            
            // Actualizar campos editables
            if (payload.containsKey("descripcionServicio")) {
                orden.setDescripcionServicio((String) payload.get("descripcionServicio"));
            }
            if (payload.containsKey("refacciones")) {
                orden.setRefacciones((String) payload.get("refacciones"));
            }
            if (payload.containsKey("status")) {
                orden.setStatus(STATUS.valueOf((String) payload.get("status")));
            }
            if (payload.containsKey("costo")) {
                Object costoObj = payload.get("costo");
                if (costoObj != null) {
                    if (costoObj instanceof Number) {
                        orden.setCosto(((Number) costoObj).doubleValue());
                    } else if (costoObj instanceof String && !((String) costoObj).isEmpty()) {
                        orden.setCosto(Double.parseDouble((String) costoObj));
                    }
                }
            }
            
            // Parsear fechas si están presentes
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (payload.containsKey("fechaEntrada")) {
                String fechaEntradaStr = (String) payload.get("fechaEntrada");
                if (fechaEntradaStr != null && !fechaEntradaStr.isEmpty()) {
                    orden.setFechaEntrada(sdf.parse(fechaEntradaStr));
                }
            }
            if (payload.containsKey("fechaEntrega")) {
                String fechaEntregaStr = (String) payload.get("fechaEntrega");
                if (fechaEntregaStr != null && !fechaEntregaStr.isEmpty()) {
                    orden.setFechaEntrega(sdf.parse(fechaEntregaStr));
                }
            }
            
            // Actualizar cliente si se proporciona
            if (payload.containsKey("idCliente")) {
                int idCliente = (Integer) payload.get("idCliente");
                Clientes cliente = clientesRepo.findById(idCliente).orElse(null);
                orden.setCliente(cliente);
            }
            
            // Actualizar motocicleta si se proporciona
            if (payload.containsKey("idMotocicleta")) {
                int idMoto = (Integer) payload.get("idMotocicleta");
                Motocicletas moto = motocicletasRepo.findById(idMoto).orElse(null);
                orden.setMotocicleta(moto);
            }
            
            return repo.save(orden);
        } catch (Exception e) {
            System.err.println("Error al editar orden: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al editar orden: " + e.getMessage(), e);
        }
    }

    public String eliminarLogico(int id) {
        Orden o = repo.findById(id).orElse(null);
        o.setActivo(false);
        repo.save(o);
        return "Orden desactivada";
    }

    public String eliminarFisico(int id) {
        repo.deleteById(id);
        return "Orden eliminada permanentemente";
    }
}
