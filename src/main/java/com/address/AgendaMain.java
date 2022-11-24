package com.address;

import com.address.model.Person;
import com.address.model.PersonListWrapper;
import com.address.controlador.PersonEditDialogController;
import com.address.controlador.PersonOverviewController;
import com.address.controlador.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.util.prefs.Preferences;

import java.io.File;
import java.io.IOException;

public class AgendaMain extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    //Creamos una lista observable de personas
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    //Creamos un constructor para que nos permita añadir personas a la lista
    public AgendaMain() {
        // Añadir algunos datos de ejemplo
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }

    //Devuelve los datos como una lista observable de personas
    public ObservableList<Person> getPersonData() {
        //Devuelve la lista observable de personas
        return personData;
    }

    @Override
    public void start(Stage stage) throws IOException {
        //Guardamos la referencia al stage principal
        this.primaryStage = stage;
        this.primaryStage.setTitle("Agenda");
        //Añadimos el icono de la aplicación.
        //Debo introducir la ruta de la imagen entera, ya que poniendo solo desde la carpeta
        //resources no me lo coge, por eso empiezo desde la carpeta src.
        this.primaryStage.getIcons().add(new Image("file:src/main/resources/images/address_icon.png"));
        //Ponemos el metodo initRootLayout para que se ejecute al iniciar la aplicacion
        initRootLayout();
        //Ponemos el metodo showAgendaOverview para que se ejecute al iniciar la aplicacion
        showPersonOverview();
    }

    /*
     * Inicializa el layout raiz
     */
    private void initRootLayout() {
        //Generamos un try-catch para que nos muestre el error en caso de que no se encuentre el archivo
        //De esta forma no se nos cerrara la aplicacion y podremos ver el error.
        try {
            //Cargamos el layout raiz desde el archivo fxml
            FXMLLoader loader = new FXMLLoader();
            //Le indicamos la ruta del archivo fxml.
            loader.setLocation(AgendaMain.class.getResource("RootLayout.fxml"));
            //Cargamos el layout raiz.
            rootLayout = (BorderPane) loader.load();

            //Mostramos la escena que contiene el layout raiz
            Scene scene = new Scene(rootLayout);
            //Añadimos la escena al stage.
            primaryStage.setScene(scene);

            //Damos el acceso al controlador a la clase main.
            RootLayoutController controller = loader.getController();
            controller.setAgendaMain(this);

            //Mostramos el stage.
            primaryStage.show();

        //En caso de que no se encuentre el archivo nos mostrara el error
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Intentamos cargar el ultimo archivo abierto.
        File file = getPersonFilePath();
        //Si el archivo es diferente de null, lo cargamos.
        if (file != null) {
            //Cargamos el archivo.
            loadPersonDataFromFile(file);
        }
    }

    private void showPersonOverview() {
        try {
            //Cargamos el layout de la agenda desde el archivo fxml
            FXMLLoader loader = new FXMLLoader();
            //Cargamos el archivo PersonOverview.fxml
            loader.setLocation(AgendaMain.class.getResource("PersonOverview.fxml"));
            //Creamos un AnchorPane para que nos muestre el contenido del archivo PersonOverview.fxml
            AnchorPane personOverview = loader.load();

            //Ponemos el contenido del AnchorPane en el centro del layout raiz
            rootLayout.setCenter(personOverview);
            //Creamos un objeto de la clase PersonOverviewController
            PersonOverviewController controller = loader.getController();
            //Le pasamos la lista de personas al controlador
            controller.setMain(this);
        //En caso de que no se encuentre el archivo nos mostrara el error
        } catch (IOException e) {
            //Mostramos el error en la consola.
            e.printStackTrace();
        }
    }

    public boolean showPersonEditDialog(Person person) {
        try {
            //Cargamos el fichero fxml y creamos un nuevo stage para el dialogo popup.
            FXMLLoader loader = new FXMLLoader();
            //Cargamos el archivo PersonEditDialog.fxml
            loader.setLocation(AgendaMain.class.getResource("PersonEditDialog.fxml"));
            //Creamos un AnchorPane para que nos muestre el contenido del archivo PersonEditDialog.fxml
            AnchorPane page = loader.load();

            //Creamos el dialogo Stage.
            Stage dialogStage = new Stage();
            //Ponemos el titulo del dialogo.
            dialogStage.setTitle("Editar Persona");
            //Ponemos el icono del dialogo.
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //Ponemos el icono de la aplicacion.
            dialogStage.initOwner(primaryStage);
            //Ponemos la imagen del icono.
            Scene scene = new Scene(page);
            //Ponemos la escena en el dialogo.
            dialogStage.setScene(scene);

            //Ponemos la persona en el controlador
            PersonEditDialogController controller = loader.getController();
            //Le pasamos el stage al controlador
            controller.setDialogStage(dialogStage);
            //Le pasamos la persona al controlador
            controller.setPerson(person);

            //Mostramos el dialogo y esperamos a que el usuario lo cierre
            dialogStage.showAndWait();

            //Devuelve true si el usuario pulsa el boton OK, false en caso contrario
            return controller.isOkClicked();

        } catch (IOException e) {
            //En caso de que no se encuentre el archivo nos mostrara el error
            e.printStackTrace();

            return false;
        }
    }

    /*
     * Devuelve el archivo de preferencias de la persona, es decir,
     * el archivo que se abrio por ultima vez.
     * La preferencia se lee del registro especifico del sistema operativo.
     * Si no se encuentra tal preferencia, se devuelve null.
     */
    public File getPersonFilePath() {
        //Obtenemos las preferencias del registro del sistema operativo.
        Preferences prefs = Preferences.userNodeForPackage(AgendaMain.class);
        //Obtenemos el valor de la preferencia, si no existe devolvemos null.
        String filePath = prefs.get("filePath", null);
        //Si el valor es null, devolvemos null.
        //Si no, devolvemos el archivo.
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /*
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * Pone la ruta del archivo actualmente cargado. La ruta se almacena en el registro
     * especifico del sistema operativo.
     */
    public void setPersonFilePath(File file) {
        //Tenemos que importar a mano la clase Preferences
        //import java.util.prefs.Preferences;
        Preferences prefs = Preferences.userNodeForPackage(AgendaMain.class);
        //Si el archivo es null, borramos el path.
        //Si no, guardamos el path en el registro.
        if (file != null) {
            //Guardamos el path en el registro.
            prefs.put("filePath", file.getPath());

            //Actualiza el titulo de la ventana
            primaryStage.setTitle("Agenda - " + file.getName());
        } else {
            //Borramos el path del registro.
            prefs.remove("filePath");

            //Actualiza el titulo de la ventana
            primaryStage.setTitle("Agenda");
        }
    }

    /*
     * Carga los datos de la persona del archivo especificado. La ruta del archivo
     * se almacena en el registro del sistema.
     */
    //ATENCION: Tenemos que importar a mano la clase JAXBException
    //Para ello lo que debemos hacer es añadir una dependencia en el archivo pom.xml
    //La dependencia que debemos añadir es la siguiente:
    // <dependency>
    //     <groupId>javax.xml.bind</groupId>
    //     <artifactId>jaxb-api</artifactId>
    //     <version>2.3.3</version>
    // </dependency>
    //Luego nos iremos al PersonListWrapper.java y añadiremos la siguiente linea:
    //import javax.xml.bind.annotation.XmlRootElement;
    //import javax.xml.bind.annotation.XmlElement;
    //Y allí seguiremos las instrucciones que indico mediante comentarios.
    public void loadPersonDataFromFile(File file) {
        try {
            //Creamos un objeto JAXBContext para poder leer el archivo xml
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            //Creamos un objeto Unmarshaller para poder deserializar el archivo xml
            Unmarshaller um = context.createUnmarshaller();

            //Leemos el archivo xml y lo guardamos en un objeto PersonListWrapper
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            //Limpiamos la lista de personas
            personData.clear();
            //Añadimos las personas del archivo xml a la lista de personas
            personData.addAll(wrapper.getPersons());

            //Guardamos la ruta del archivo en el registro del sistema
            setPersonFilePath(file);

        } catch (Exception e) { //Coge cualquier excepcion.
            //Utilizamos la clase Alert para mostrar un mensaje de error ya que
            //la que nos recomienda el tutorial que es la clase Dialog no funciona.
            //Alert es la que hemos usado en otros puntos del tutorial, y funciona
            //perfectamente.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            //Ponemos el titulo del mensaje.
            alert.setTitle("Error");
            //Ponemos el header del mensaje.
            alert.setHeaderText("No se pudo cargar los datos");
            //Ponemos el contenido del mensaje.
            alert.setContentText("No se pudo cargar los datos de la persona del archivo:\n"
                    + file.getPath());

            //Mostramos el mensaje y esperamos a que el usuario lo cierre.
            alert.showAndWait();
        }
    }

    /*
     * Guarda los datos de la persona en el archivo especificado. La ruta del archivo
     * se almacena en el registro del sistema.
     */

    //Esto es como el anterior metodo, debemos haber seguido las instrucciones que
    //indico en el anterior metodo. Y tambien debemos haber importado a mano la clase.
    public void savePersonDataToFile(File file) {
        try {
            //Creamos un objeto JAXBContext para poder leer el archivo xml
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            //Creamos un objeto Marshaller para poder serializar el archivo xml
            Marshaller m = context.createMarshaller();
            //Le indicamos que queremos que el archivo xml este formateado
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //Creamos un objeto PersonListWrapper para poder guardar los datos de la
            // lista de personas
            PersonListWrapper wrapper = new PersonListWrapper();
            //Añadimos los datos de la lista de personas al objeto PersonListWrapper
            wrapper.setPersons(personData);

            //Guardamos los datos en el archivo xml
            m.marshal(wrapper, file);

            //Guardamos la ruta del archivo en el registro del sistema
            setPersonFilePath(file);
        } catch (Exception e) { //Coge cualquier excepcion.
            //Utilizamos la clase Alert para mostrar un mensaje de error ya que
            //la que nos recomienda el tutorial que es la clase Dialog no funciona.
            //Alert es la que hemos usado en otros puntos del tutorial, y funciona
            //perfectamente.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            //Ponemos el titulo del mensaje.
            alert.setTitle("Error");
            //Ponemos el header del mensaje.
            alert.setHeaderText("No se pudo guardar los datos");
            //Ponemos el contenido del mensaje.
            alert.setContentText("No se pudo guardar los datos de la persona en el archivo:\n"
                    + file.getPath());

            //Mostramos el mensaje y esperamos a que el usuario lo cierre.
            alert.showAndWait();
        }
    }



    /*
     * Devuelve el escenario principal
     * @return
     */
    public Stage getPrimaryStage() {
        //Devolvemos el primaryStage
        return primaryStage;
    }

    public static void main(String[] args) {
        //Llamamos al metodo launch para que se ejecute la aplicacion
        launch();
    }
}