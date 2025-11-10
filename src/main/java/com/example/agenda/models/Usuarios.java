package com.example.agenda.models;

import com.example.agenda.utils.ROL;
import jakarta.persistence.*;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_usuario")
    int idUsuario;

    @Column (name = "nombre_usuario", nullable = false, length = 40, unique = true)
    String nombreUsuario;

    @Column (name = "nombre_pila", nullable = true, length = 40)
    String nombrePila;

    @Column (name = "apellido_completo", nullable = true, length = 40)
    String apellidoCompleto;

    @Column (name = "password", nullable = false, length = 40)
    String password;

    @Column (name = "rol", nullable = false)
    ROL rol;

    @Column(name = "activo")
    boolean activo = true;


}
