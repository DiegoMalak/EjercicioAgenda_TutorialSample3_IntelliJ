package com.address.controlador;

/*
 * El controlador de RootLayout. El controlador proporciona la funcionalidad
 * básica de la interfaz de usuario. El controlador se conecta con el modelo y la vista.
 * El layout contiene un menu y un espacio donde se pueden colocar otras vistas.
 */

import com.address.AgendaMain;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

public class RootLayoutController {

    //Creamos una variable de tipo MainApp
    private AgendaMain mainApp;

    /*
     * Se llama al main application para dar una referencia de vuelta a si mismo.
     */

    public void setAgendaMain(AgendaMain mainApp) {
        //Referenciamos la clase Main.
        this.mainApp = mainApp;
    }

    /*
     * Crea un nuevo archivo vacío.
     */
    @FXML
    private void handleNew() {
        //Referenciamos la clase Main y llamamos al metodo getPersonData.
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /*
     * Abre un archivo FileChooser para que el usuario seleccione un archivo para cargar.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        //Filtro de extensión
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        //Añadimos el filtro de extensión.
        fileChooser.getExtensionFilters().add(extFilter);

        //Muestra el diálogo de guardar archivo.
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            //Llamamos al metodo loadPersonDataFromFile de la clase Main.
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /*
     * Guarda el archivo actual en el archivo que se especifica en el mainApp.
     */
    @FXML
    private void handleSave() {
        //Referenciamos la clase Main y llamamos al metodo getPersonFilePath.
        File personFile = mainApp.getPersonFilePath();
        //Llamamos al metodo savePersonDataToFile de la clase Main en caso de
        //que personFile no sea null.
        //Si personFile es null, llamamos al metodo handleSaveAs().
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /*
     * Abre un FileChooser para que el usuario especifique un archivo para guardar.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        //Filtro de extensión
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        //Añadimos el filtro de extensión.
        fileChooser.getExtensionFilters().add(extFilter);

        //Muestra el diálogo de guardar archivo
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        //Si el usuario selecciona un archivo, llamamos al metodo savePersonDataToFile
        //de la clase Main.
        if (file != null) {
            //Aseguramos que tiene la extensión correcta
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            //Llamamos al metodo savePersonDataToFile de la clase Main.
            mainApp.savePersonDataToFile(file);
        }
    }

    /*
     * Abre una ventana de dialogo acerca de.
     */
    @FXML
    private void handleAbout() {
        //Creamos un objeto de tipo Alert.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //Establecemos el titulo.
        alert.setTitle("Agenda");
        //Establecemos el encabezado.
        alert.setHeaderText("Acerca de");
        //Establecemos el contenido.
        alert.setContentText("Autor: Diego Rodríguez");

        //Mostramos el diálogo y esperamos a que el usuario lo cierre.
        alert.showAndWait();

    }

    /*
     * Cierra la aplicación.
     */
    @FXML
    private void handleExit() {
        //Llamamos al metodo stop de la clase Main para cerrar la aplicación.
        System.exit(0);
    }

}
