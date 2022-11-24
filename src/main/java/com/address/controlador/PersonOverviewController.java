package com.address.controlador;

import com.address.AgendaMain;
import com.address.model.Person;
import com.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    //Referenciamos la clase Main
    private AgendaMain main;

    //Inicializamos el la clase com.address.controlador.PersonOverviewController
    // Este metodo es llamada automaticamente
    // despues de que el fxml es cargado.
    public PersonOverviewController() {
        //Metemos un constructor vacío.
    }

    @FXML
    private void initialize() {
        //Inicializamos la tabla de personas con las dos columnas
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        //Limpiamos la información de la persona
        showPersonDetails(null);

        //Escuchamos los cambios en la selección y mostramos la información de la persona cuando cambia
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));

    }

    //Llamamos al metodo main para dar una referencia de vuelta a si mismo
    public void setMain(AgendaMain main) {
        //Referenciamos la clase Main
        this.main = main;

        //Añadimos los datos de la observable list a la tabla
        personTable.setItems(main.getPersonData());
    }

    //Rellena todos los campos de texto para mostrar detalles sobre la persona.
    //Si la persona especificada es nula, se borran todos los campos de texto.
    private void showPersonDetails(Person person) {

        if (person != null) {
            //
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());

            //Necesitamos una forma de convertir el cumpleaños de la persona en un String
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));

        } else {
            //Person es nulo, removemos todo el texto.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");

        }

    }

    @FXML
    private void handleDeletePerson() {
        //Obtenemos el indice de la persona seleccionada en la tabla de personas.
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        //Si hay una persona seleccionada, la eliminamos.
        //Si no, mostramos un mensaje de error.
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            //Nada seleccionado en la tabla.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            //Establecemos el titulo, cabecera y contenido del mensaje de error.
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }

    }


    @FXML
    private void handleNewPerson() {
        //Creamos un objeto de tipo Person
        Person tempPerson = new Person();
        //Creamos un booleano para saber si el usuario hizo click en OK
        boolean okClicked = main.showPersonEditDialog(tempPerson);
        //Si el usuario hizo click en OK, añadimos la persona a la tabla
        if (okClicked) {
            //Añadimos la persona a la tabla
            main.getPersonData().add(tempPerson);
        }
    }

    //Llamamos cuando el usuario hace click en el boton Editar. Abre una ventana para editar
    //los detalles de la persona seleccionada.
    @FXML
    private void handleEditPerson() {
        //Obtenemos la persona seleccionada
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        //Si no hay nada seleccionado, mostramos un mensaje de error
        if (selectedPerson != null) {
            //Creamos un booleano para saber si el usuario hizo click en OK
            boolean okClicked = main.showPersonEditDialog(selectedPerson);
            //Si el usuario hizo click en OK, añadimos la persona a la tabla
            if (okClicked) {
                //Añadimos la persona a la tabla
                showPersonDetails(selectedPerson);
            }
        } else {
            //Nada seleccionado.
            //Creamos un mensaje de error.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            //Asignamos el Stage principal.
            alert.initOwner(main.getPrimaryStage());
            //Asignamos el titulo.
            alert.setTitle("No Selection");
            //Asignamos el encabezado.
            alert.setHeaderText("No Person Selected");
            //Asignamos el contenido.
            alert.setContentText("Please select a person in the table.");

            //Mostramos el mensaje y esperamos a que el usuario lo cierre.
            alert.showAndWait();
        }
    }

}












