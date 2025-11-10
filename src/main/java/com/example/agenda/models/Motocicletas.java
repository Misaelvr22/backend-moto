package com.example.agenda.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Motocicletas")

public class Motocicletas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motocicleta")
    int idMotocicleta;

    @Column(name = "marca")
    String marca;

    @Column(name = "modelo")
    String modelo;

    @Column(name = "serie")
    String serie;

    @Column(name = "year")
    int year;

    @Column(name = "placa")
    String placa;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Clientes cliente;

    @Column(name = "activo")
    boolean activo = true;

}
