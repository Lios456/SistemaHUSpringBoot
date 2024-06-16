package com.tasksys.SistemaTareasApp.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String nombreCompleto_responsable = nombre_responsable + " " + apellido_responsable;
    private String rol_responsable;
}
