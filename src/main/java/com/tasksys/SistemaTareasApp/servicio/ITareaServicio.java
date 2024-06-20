package com.tasksys.SistemaTareasApp.servicio;

import com.tasksys.SistemaTareasApp.modelo.Tarea;

import java.util.List;

public interface ITareaServicio {
    public List<Tarea> listarTareas();
    public void insertarTarea(Tarea tarea);
    public void eliminarTarea(Tarea tarea);
    public Tarea buscarTarea(int id);
}
