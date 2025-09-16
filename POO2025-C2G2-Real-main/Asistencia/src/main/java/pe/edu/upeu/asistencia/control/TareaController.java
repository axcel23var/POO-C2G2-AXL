package pe.edu.upeu.asistencia.control;

import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.enums.EstadoTarea;
import pe.edu.upeu.asistencia.modelo.Tarea;
import pe.edu.upeu.asistencia.servicio.TareaServicioI;

@Controller
public class TareaController {

    @FXML private TextField txtTitulo, txtDescripcion, txtBuscar;
    @FXML private ComboBox<EstadoTarea> cbxEstado;

    @FXML private TableView<Tarea> tableView;
    private ObservableList<Tarea> listaTareas;

    @FXML private TableColumn<Tarea, String> tituloColum, descripcionColum, estadoColum;
    private TableColumn<Tarea, Void> opcColum;

    @Autowired
    TareaServicioI ts;

    int indexE = -1;

    @FXML
    public void initialize() {
        cbxEstado.getItems().setAll(EstadoTarea.values());
        definirColumnas();
        listarTareas();
    }

    @FXML
    public void limpiarFormulario() {
        txtTitulo.setText("");
        txtDescripcion.setText("");
        cbxEstado.setValue(null);
        txtBuscar.setText("");
        indexE = -1;
    }

    @FXML
    public void registrarTarea() {
        Tarea t = new Tarea();
        t.setTitulo(new SimpleStringProperty(txtTitulo.getText()));
        t.setDescripcion(new SimpleStringProperty(txtDescripcion.getText()));
        t.setEstado(cbxEstado.getSelectionModel().getSelectedItem());

        if (indexE == -1) ts.save(t);
        else { ts.update(t, indexE); indexE = -1; }

        limpiarFormulario();
        listarTareas();
    }

    private void definirColumnas() {
        tituloColum = new TableColumn<>("Título");
        descripcionColum = new TableColumn<>("Descripción");
        estadoColum = new TableColumn<>("Estado");
        opcColum = new TableColumn<>("Opciones");
        opcColum.setPrefWidth(200);

        tableView.getColumns().clear();
        tableView.getColumns().addAll(tituloColum, descripcionColum, estadoColum, opcColum);

        tituloColum.setCellValueFactory(cd -> cd.getValue().getTitulo());
        descripcionColum.setCellValueFactory(cd -> cd.getValue().getDescripcion());
        estadoColum.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getEstado()==null ? "" : cd.getValue().getEstado().toString())
        );

        agregarAccionBotones();
    }

    private void agregarAccionBotones() {
        Callback<TableColumn<Tarea, Void>, TableCell<Tarea, Void>> cellFactory = col -> new TableCell<>() {
            private final Button editarBtn = new Button("Editar");
            private final Button eliminarBtn = new Button("Eliminar");
            {
                editarBtn.setOnAction(e -> {
                    Tarea t = getTableView().getItems().get(getIndex());
                    editarDatos(t, getIndex());
                });
                eliminarBtn.setOnAction(e -> eliminarTarea(getIndex()));
            }
            @Override public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(10, editarBtn, eliminarBtn));
            }
        };
        opcColum.setCellFactory(cellFactory);
    }

    private void listarTareas() {
        listaTareas = FXCollections.observableArrayList(ts.findAll());
        tableView.setItems(listaTareas);
    }

    private void eliminarTarea(int index) {
        ts.delete(index);
        listarTareas();
    }


    @FXML
    public void buscarTarea() {
        String filtro = txtBuscar.getText().toLowerCase();

        if (filtro.isEmpty()) {
            // si el campo está vacío, mostramos todas las tareas
            listaTareas = FXCollections.observableArrayList(ts.findAll());
        } else {
            // filtramos por título o descripción
            listaTareas = FXCollections.observableArrayList(
                    ts.findAll().stream()
                            .filter(t -> t.getTitulo().getValue().toLowerCase().contains(filtro)
                                    || t.getDescripcion().getValue().toLowerCase().contains(filtro))
                            .toList()
            );
        }

        tableView.setItems(listaTareas);
    }


    private void editarDatos(Tarea t, int index) {
        txtTitulo.setText(t.getTitulo().getValue());
        txtDescripcion.setText(t.getDescripcion().getValue());
        cbxEstado.setValue(t.getEstado());
        indexE = index;
    }
}
