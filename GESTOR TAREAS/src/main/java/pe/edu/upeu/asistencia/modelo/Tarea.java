package pe.edu.upeu.asistencia.modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pe.edu.upeu.asistencia.enums.EstadoTarea;

public class Tarea {
    private StringProperty titulo;
    private StringProperty descripcion;
    private EstadoTarea estado;

    // <- Aquí con T mayúscula
    public Tarea() {
        this.titulo = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
        this.estado = EstadoTarea.NUEVA;
    }

    public Tarea(StringProperty titulo, StringProperty descripcion, EstadoTarea estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public StringProperty getTitulo() { return titulo; }
    public StringProperty getDescripcion() { return descripcion; }

    public void setTitulo(StringProperty titulo) { this.titulo = titulo; }
    public void setDescripcion(StringProperty descripcion) { this.descripcion = descripcion; }

    public EstadoTarea getEstado() { return estado; }
    public void setEstado(EstadoTarea estado) { this.estado = estado; }
}
