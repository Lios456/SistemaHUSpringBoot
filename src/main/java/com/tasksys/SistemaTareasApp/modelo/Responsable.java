package com.tasksys.SistemaTareasApp.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Responsable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_responsable;
    private String nombre_responsable;
    private String apellido_responsable;
    @Column(name = "nombre_completo_responsable", insertable = false, updatable = false)
    private String nombreCompleto_responsable;
    private String rol_responsable;
}
