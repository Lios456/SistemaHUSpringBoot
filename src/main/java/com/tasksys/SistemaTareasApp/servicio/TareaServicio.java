package com.tasksys.SistemaTareasApp.servicio;

import com.tasksys.SistemaTareasApp.modelo.Tarea;
import com.tasksys.SistemaTareasApp.repositorio.TareaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TareaServicio implements ITareaServicio{

    @Autowired
    private TareaRepositorio tarearepositorio;
    @Override
    public List<Tarea> listarTareas() {
        return tarearepositorio.findAll();
    }

    @Override
    public void insertarTarea(Tarea tarea) {
            tarearepositorio.save(tarea);
    }

    @Override
    public void eliminarTarea(Tarea tarea) {
            tarearepositorio.delete(tarea);
    }

    @Override
    public Tarea buscarTarea(int id) {
        return tarearepositorio.findById(id).orElse(null);
    }
}
