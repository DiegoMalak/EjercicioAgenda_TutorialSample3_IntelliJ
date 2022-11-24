package com.address.controlador;

import com.address.model.Person;
import com.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Diálogo para editar los detalles de una persona.
public class PersonEditDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    //Inicializamos la clase com.address.controlador.PersonEditDialogController
    // Este metodo es llamada automaticamente
    // despues de que el fxml es cargado.
    @FXML
    private void initialize() {
    }

    //Establecemos el escenario de este diálogo.
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    //Establecemos la persona a editar en el diálogo.
    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }

    //Devuelve true si el usuario hizo clic en OK, de lo contrario false.
    public boolean isOkClicked() {
        return okClicked;
    }

    //Llamado cuando el usuario hace clic en OK.
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    //Llamado cuando el usuario hace clic en cancelar.
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    //Valida la entrada del usuario en los campos de texto.
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Nombre no válido!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Apellido no válido!\n";
        }

        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "Dirección no válida!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "Código postal no válido!\n";
        } else {
            //Intenta parsear el código postal en un int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Código postal no válido (debe ser un entero)!\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "Ciudad no válida!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "Fecha de nacimiento no válida!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "Fecha de nacimiento no válida. Use el formato dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            //Muestra el mensaje de error.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Campos no válidos");
            alert.setHeaderText("Por favor, corrija los campos no válidos");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
