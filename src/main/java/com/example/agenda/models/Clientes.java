package com.example.agenda.models;

import jakarta.persistence.*;
import lombok.*;


@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Clientes")

public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", nullable = false)
    int idCliente;

    @Column(name = "nombre_cliente", nullable = false, length = 40)
    String nombreCliente;

    @Column(name = "numero_cliente", nullable = false, length = 12)
    String numeroCliente;

    @Column(name = "activo")
    boolean activo = true;



}
