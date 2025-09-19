package pe.edu.upeu.asistencia.repositorio;

import javafx.beans.property.SimpleStringProperty;
import pe.edu.upeu.asistencia.enums.EstadoTarea;
import pe.edu.upeu.asistencia.modelo.Tarea;

import java.util.ArrayList;
import java.util.List;

public abstract class TareaRepositorio {
    public List<Tarea> listaTareas = new ArrayList<>();

    public List<Tarea> findAll() {
        // Datos de ejemplo (se cargan una sola vez)
        if (listaTareas.isEmpty()) {
            listaTareas.add(new Tarea(
                    new SimpleStringProperty("Estudiar POO"),
                    new SimpleStringProperty("Repasar clases y objetos"),
                    EstadoTarea.NUEVA
            ));
            listaTareas.add(new Tarea(
                    new SimpleStringProperty("Hacer GUI"),
                    new SimpleStringProperty("Armar JavaFX con FXML"),
                    EstadoTarea.EN_PROCESO
            ));
        }
        return listaTareas;
    }
}

