package com.example.agenda.models;

import com.example.agenda.utils.STATUS;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Orden")


public class Orden {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_orden", nullable = false)
   int idOrden;

   @Column(name = "descripcion_servicio", nullable = false)
   String descripcionServicio;

   @Column(name = "refacciones", nullable = false)
   String refacciones;

   @Column(name = "fecha_entrada", nullable = false)
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
   Date fechaEntrada;

   @Column(name = "fecha_entrega")
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
   Date fechaEntrega;

    @Column(name = "status", nullable = false)
    STATUS status;
    
    @Column(name = "costo")
    Double costo;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
   @JsonManagedReference
   private Clientes cliente;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "id_motocicleta", referencedColumnName = "id_motocicleta")
   @JsonManagedReference
   private Motocicletas motocicleta;

   @Column(name = "activo")
   boolean activo = true;

}
