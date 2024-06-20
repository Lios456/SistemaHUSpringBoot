package com.tasksys.SistemaTareasApp.servicio;

import com.tasksys.SistemaTareasApp.modelo.Responsable;

import java.util.List;

public interface IResponsableServicio {
    public List<Responsable> listarResponsables();
    public void insertarResponsable(Responsable responsable);
    public void eliminarResponsable(Responsable responsable);
    public Responsable buscarResponsable(int id);
}
