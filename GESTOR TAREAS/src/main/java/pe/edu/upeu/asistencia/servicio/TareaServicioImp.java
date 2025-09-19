package pe.edu.upeu.asistencia.servicio;

import org.springframework.stereotype.Service;
import pe.edu.upeu.asistencia.modelo.Tarea;
import pe.edu.upeu.asistencia.repositorio.TareaRepositorio;

import java.util.List;

@Service
public class TareaServicioImp extends TareaRepositorio implements TareaServicioI {

    @Override
    public void save(Tarea tarea) {
        listaTareas.add(tarea);
    }

    @Override
    public List<Tarea> findAll() {
        if (listaTareas.isEmpty()) {
            return super.findAll();
        }
        return listaTareas;
    }

    @Override
    public void update(Tarea tarea, int index) {
        listaTareas.set(index, tarea);
    }

    @Override
    public void delete(int index) {
        listaTareas.remove(index);
    }

    @Override
    public Tarea findById(int index) {
        return listaTareas.get(index);
    }
}

