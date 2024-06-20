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
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTarea = 0;
    private String nombreTarea;
    private String descripcionTarea;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "responsable_Tarea", referencedColumnName = "id_responsable")
    private Responsable responsableTarea;
    private String estadoTarea;

}
