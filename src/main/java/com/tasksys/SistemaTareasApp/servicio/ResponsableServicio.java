package com.tasksys.SistemaTareasApp.servicio;

import com.tasksys.SistemaTareasApp.modelo.Responsable;
import com.tasksys.SistemaTareasApp.repositorio.ResponsableRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponsableServicio implements IResponsableServicio {

    @Autowired
    private ResponsableRepositorio responsableRepositorio;
    @Override
    public List<Responsable> listarResponsables() {
        return responsableRepositorio.findAll();
    }

    @Override
    public void insertarResponsable(Responsable responsable) {
        responsableRepositorio.save(responsable);
    }

    @Override
    public void eliminarResponsable(Responsable responsable) {
        responsableRepositorio.delete(responsable);
    }

    @Override
    public Responsable buscarResponsable(int id) {
        return responsableRepositorio.findById(id).orElse(null);
    }
}
