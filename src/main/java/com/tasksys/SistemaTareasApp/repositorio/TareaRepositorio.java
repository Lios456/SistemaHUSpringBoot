package com.tasksys.SistemaTareasApp.repositorio;

import com.tasksys.SistemaTareasApp.modelo.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepositorio extends JpaRepository<Tarea, Integer> {
}
