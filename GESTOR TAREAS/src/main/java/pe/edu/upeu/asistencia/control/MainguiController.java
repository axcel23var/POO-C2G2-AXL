package pe.edu.upeu.asistencia.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class MainguiController {

    @FXML private BorderPane bp;
    @FXML private MenuBar menuBar;

    // Deben coincidir con tu maingui.fxml
    @FXML private TabPane tabPanePrincipal;
    @FXML private MenuItem miAdmTareas;
    @FXML private MenuItem miClose;

    // Menú de estilos (lo conservamos)
    private final Menu menuEstilos = new Menu("Cambiar Estilos");
    private final ComboBox<String> comboEstilo = new ComboBox<>();
    private final CustomMenuItem customMenuItem = new CustomMenuItem(comboEstilo);

    @Autowired
    ApplicationContext context;

    @FXML
    public void initialize() {
        // Estilos
        comboEstilo.getItems().addAll(
                "Estilo por defecto","Estilo Oscuro","Estilo Azul","Estilo Rosado","Estilo Verde"
        );
        comboEstilo.setOnAction(e -> cambiarEstilo());
        customMenuItem.setHideOnClick(false);
        menuEstilos.getItems().add(customMenuItem);
        menuBar.getMenus().add(menuEstilos);
    }

    // === Handlers llamados desde FXML ===
    @FXML
    private void abrirAdmTareas() {
        cargarEnTab("/fxml/main_tarea.fxml", "Reg. Tareas");
    }

    @FXML
    private void closeApp() {
        Stage st = (Stage) bp.getScene().getWindow();
        st.close();
    }

    // === Utilidades ===
    private void cargarEnTab(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setControllerFactory(context::getBean); // Spring
            Parent root = loader.load();

            ScrollPane sp = new ScrollPane(root);
            sp.setFitToWidth(true);
            sp.setFitToHeight(true);
            sp.setMinHeight(Region.USE_COMPUTED_SIZE);
            sp.setMinWidth(Region.USE_COMPUTED_SIZE);

            Tab tab = new Tab(titulo, sp);
            tab.setClosable(true);

            tabPanePrincipal.getTabs().add(tab);
            tabPanePrincipal.getSelectionModel().select(tab);
        } catch (Exception ex) {
            ex.printStackTrace();
            // opcional: mostrar alerta
            Alert a = new Alert(Alert.AlertType.ERROR, "No se pudo cargar: " + fxml);
            a.showAndWait();
        }
    }

    private void cambiarEstilo() {
        String estilo = comboEstilo.getSelectionModel().getSelectedItem();
        Scene scene = bp.getScene();
        if (scene == null) return;
        scene.getStylesheets().clear();
        if ("Estilo Oscuro".equals(estilo)) {
            scene.getStylesheets().add(getClass().getResource("/css/estilo-oscuro.css").toExternalForm());
        } else if ("Estilo Azul".equals(estilo)) {
            scene.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm());
        } else if ("Estilo Rosado".equals(estilo)) {
            scene.getStylesheets().add(getClass().getResource("/css/estilo-rosado.css").toExternalForm());
        } else if ("Estilo Verde".equals(estilo)) {
            scene.getStylesheets().add(getClass().getResource("/css/estilo-verde.css").toExternalForm());
        } // “Estilo por defecto”: no agregamos nada
    }
}
