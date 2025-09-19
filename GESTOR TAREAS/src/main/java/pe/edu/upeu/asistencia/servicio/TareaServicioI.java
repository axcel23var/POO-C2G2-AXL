package pe.edu.upeu.asistencia.servicio;

import pe.edu.upeu.asistencia.modelo.Tarea;

import java.util.List;

public interface TareaServicioI {

    void save(Tarea tarea);        // C - Crear

    List<Tarea> findAll();         // R - Leer

    void update(Tarea tarea, int index); // U - Actualizar

    void delete(int index);        // D - Eliminar

    Tarea findById(int index);     // Buscar por índice
}

