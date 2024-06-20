package com.tasksys.SistemaTareasApp.controlador;

import com.tasksys.SistemaTareasApp.modelo.Responsable;
import com.tasksys.SistemaTareasApp.modelo.Tarea;
import com.tasksys.SistemaTareasApp.servicio.ResponsableServicio;
import com.tasksys.SistemaTareasApp.servicio.TareaServicio;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class IndexControlador implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);
    @Autowired
    private TareaServicio tareaServicio;
    @Autowired
    private ResponsableServicio responsableServicio;
    @FXML
    private TableView<Tarea> tareaTabla;
    @FXML
    private TableColumn<Tarea, Integer> idTareaColumna;
    @FXML
    private TableColumn<Tarea, String> nombreTareaColumna;
    @FXML
    private TableColumn<Tarea, String> descripcionTareaColumna;
    @FXML
    private TableColumn<Tarea, String> responsableTareaColumna;
    @FXML
    private TableColumn<Tarea, String> estadoTareaColumna;
    @FXML
    private TextField tareaText;
    @FXML
    private ComboBox<String> responsableText;
    @FXML
    private ComboBox<String> estadoText;
    @FXML
    private TextArea descripcionText;
    @FXML
    private final ObservableList<Tarea> tareasLista = FXCollections.observableArrayList();

    //Gestión de responsables
    @FXML
    private TableView<Responsable> responsables_tb;
    @FXML
    private TableColumn<Responsable, Integer> id_res;
    @FXML
    private TableColumn<Responsable, String> nombre_res;
    @FXML
    private TableColumn<Responsable, String> apell_res;
    @FXML
    private TableColumn<Responsable, String> ncom_res;
    @FXML
    private TableColumn<Responsable, String> rol_res;
    @FXML
    private final ObservableList<Responsable> responsablesLista = FXCollections.observableArrayList();
    @FXML
    private TextField nombrertx;
    @FXML
    private TextField apellidortx;
    @FXML
    private ComboBox<String> rolrtx;

    private int idrtx;
    private int idTarea;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarTareas();
        listarResponsablescombo();
        responsableText.getSelectionModel().select(0);
        estadoText.setItems(FXCollections.observableArrayList(
                "Pendiente",
                "Iniciado",
                "En Progreso",
                "Finalizado"));
        estadoText.getSelectionModel().select(0);

        /*---------------------------------------------------------------------------------------------*/
        responsables_tb.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listarResponsables();
        rolrtx.setItems(FXCollections.observableArrayList("Dev",
                "Scrum Master",
                "Product Owner"));
        rolrtx.getSelectionModel().select(0);
    }

    private void configurarColumnas() {
        idTareaColumna.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        nombreTareaColumna.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        descripcionTareaColumna.setCellValueFactory(new PropertyValueFactory<>("descripcionTarea"));
        responsableTareaColumna.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getResponsableTarea().getNombreCompleto_responsable())
        );
        estadoTareaColumna.setCellValueFactory(new PropertyValueFactory<>("estadoTarea"));

        //Config Responsable
        id_res.setCellValueFactory(new PropertyValueFactory<>("id_responsable"));
        nombre_res.setCellValueFactory(new PropertyValueFactory<>("nombre_responsable"));
        rol_res.setCellValueFactory(new PropertyValueFactory<>("rol_responsable"));
        apell_res.setCellValueFactory(new PropertyValueFactory<>("apellido_responsable"));
        ncom_res.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto_responsable"));
    }

    private void listarTareas() {
        tareasLista.clear();
        tareasLista.addAll(tareaServicio.listarTareas());
        tareaTabla.setItems(tareasLista);
    }

    public void agregarTarea() {
        if(tareaText.getText().isEmpty() || Objects.equals(tareaText.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe ingresar el nombre");
            alert.showAndWait();
            return;
        }
        if(responsableText.getValue().isEmpty() || Objects.equals(responsableText.getValue(), "")
        || responsableServicio.buscarResponsable(responsableText.getSelectionModel().getSelectedIndex()+1) == null ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se seleccionó ningún responsable");
            alert.showAndWait();
            return;
        }
        try{
            var tarea = new Tarea();
            tarea.setNombreTarea(tareaText.getText());
            tarea.setResponsableTarea(responsableServicio.buscarResponsable(
                    responsableText.getSelectionModel().getSelectedIndex()+1));
            tarea.setDescripcionTarea(descripcionText.getText());
            tarea.setEstadoTarea(estadoText.getSelectionModel().getSelectedItem());
            this.tareaServicio.insertarTarea(tarea);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Agregar Tarea");
            alert.setContentText("Tarea agregada con exito");
            alert.showAndWait();
            listarTareas();
            limpiarForm();
        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(ex.toString());
            alert.showAndWait();
            return;
        }

    }

    public void editarTarea() {
        if(tareaText.getText().isEmpty() || Objects.equals(tareaText.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe ingresar el nombre");
            alert.showAndWait();
            return;
        }else{
            tareaServicio.insertarTarea(new Tarea(
                    this.idTarea,
                    tareaText.getText(),
                    descripcionText.getText(),
                    responsableServicio.buscarResponsable(responsableText.getSelectionModel().getSelectedIndex()+1),
                    estadoText.getSelectionModel().getSelectedItem().toString()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Editar Tarea");
            alert.setContentText("Tarea editada con exito");
            alert.showAndWait();
            listarTareas();
            limpiarForm();
        }
    }

    public void cargarTarea(){
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if(tarea == null){
            return;
        }else{
            tareaText.setText(tarea.getNombreTarea());
            descripcionText.setText(tarea.getDescripcionTarea());
            responsableText.getSelectionModel().select(tarea.getResponsableTarea().getNombreCompleto_responsable());
            estadoText.getSelectionModel().select(tarea.getEstadoTarea());
            idTarea = tarea.getIdTarea();
        }
    }

    public void eliminarTarea() {
        if(this.idTarea == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No hay tarea seleccionada");
            alert.showAndWait();
            return;
        }else{
            tareaServicio.eliminarTarea(new Tarea(this.idTarea,
                    null,
                    null,
                    null,
                    null));
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Tarea Eliminada");
            alert.setHeaderText(null);
            alert.setContentText("Se eliminó la tarea exitosamente");
            alert.showAndWait();
            listarTareas();
            limpiarForm();
        }
    }

    public void limpiarForm(){
        responsableText.getSelectionModel().select(0);
        estadoText.getSelectionModel().select(0);
        tareaText.setText("");
        descripcionText.setText("");
    }

    public void refrescarT(){
        listarTareas();
    }

    //Gestión de Responsables

    public void listarResponsables(){
        responsablesLista.clear();
        responsablesLista.addAll(responsableServicio.listarResponsables());
        responsables_tb.setItems(responsablesLista);
    }

    public void agregarResponsable(){
        if(nombrertx.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe ingresar el nombre");
            alert.showAndWait();
            return;
        }
        responsableServicio.insertarResponsable(new Responsable(
                0,
           nombrertx.getText(),
           apellidortx.getText(),
                null,
           rolrtx.getValue().toString()
        ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Agregar Responsable");
        alert.setHeaderText(null);
        alert.setContentText("Responsable agregado con éxito");
        alert.showAndWait();
        listarResponsables();
        listarResponsablescombo();
    }

    public void editarResponsable(){
        if(nombrertx.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe ingresar el nombre del Responsable");
            alert.showAndWait();
            return;
        }
        responsableServicio.insertarResponsable(new Responsable(
                idrtx,
                nombrertx.getText(),
                apellidortx.getText(),
                null,
                rolrtx.getValue().toString()
        ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Agregar Responsable");
        alert.setHeaderText(null);
        alert.setContentText("Responsable modificado con éxito");
        alert.showAndWait();
        listarResponsables();
        listarResponsablescombo();
        limpiarForm();
    }

    public void cargarResponsable(){
        var responsable = responsables_tb.getSelectionModel().getSelectedItem();
        if(responsable == null){
            return;
        }else{
            nombrertx.setText(responsable.getNombre_responsable());
            apellidortx.setText(responsable.getApellido_responsable());
            rolrtx.getSelectionModel().select(responsable.getRol_responsable());
            idrtx = responsable.getId_responsable();
        }
    }

    public void eliminarResponsable(){
        if(this.idrtx == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No hay responsable seleccionado");
            alert.showAndWait();
            return;
        }else{
            responsableServicio.eliminarResponsable(new Responsable(idrtx,
                    null,
                    null,
                    null,
                    null));
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Responsable Eliminado");
            alert.setHeaderText(null);
            alert.setContentText("Se eliminó el responsable exitósamente");
            alert.showAndWait();
            listarResponsables();
            limpiarForm();
            listarResponsablescombo();
        }
    }

    public void listarResponsablescombo(){
        responsableText.setItems(FXCollections.observableArrayList(
                this.responsableServicio.listarResponsables().stream()
                        .map(Responsable::getNombreCompleto_responsable)
                        .collect(Collectors.toList())
        ));
    }


}

