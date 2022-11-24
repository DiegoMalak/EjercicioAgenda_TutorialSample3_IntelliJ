package com.address.model;

import com.address.util.LocalDateAdapter;
import javafx.beans.property.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

public class Person {

    //Creamos las variables que vamos a utilizar
    //Creamos un StringProperty para que nos permita modificar el nombre
    private final StringProperty firstName;
    //Creamos un StringProperty para que nos permita modificar el apellido
    private final StringProperty lastName;
    //Creamos un StringProperty para que nos permita modificar el calle
    private final StringProperty street;
    //Creamos un IntegerProperty para que nos permita modificar el codigo postal
    private final IntegerProperty postalCode;
    //Creamos un StringProperty para que nos permita modificar la ciudad
    private final StringProperty city;
    //Creamos un ObjectProperty para que nos permita modificar la fecha de nacimiento
    private final ObjectProperty<LocalDate> birthday;


public Person() {
    //Inicializamos las variables
        this(null, null);
    }
//Creamos un constructor para que nos permita modificar el nombre y el apellido
public Person(String firstName, String lastName) {
    //Inicializamos las variables
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial dummy data, just for convenient testing.
    //Inicializamos las variables
        //Inicializamps la street con 'some street'
        this.street = new SimpleStringProperty("some street");
        //Inicializamos el codigo postal a '1234'
        this.postalCode = new SimpleIntegerProperty(1234);
        //Inicializamos la ciudad a "some city"
        this.city = new SimpleStringProperty("some city");
        //Inicializamos la fecha de nacimiento a "1999-02-21"
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }

    public String getFirstName() {
    //Creamos un metodo para que nos devuelva el nombre
        return firstName.get();
    }

    public void setFirstName(String firstName) {
    //Creamos un metodo para que nos permita modificar el nombre
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
    //Creamos un metodo para que nos devuelva el nombre
        return firstName;
    }

    public String getLastName() {
    //Creamos un metodo para que nos devuelva el apellido
        return lastName.get();
    }

    public void setLastName(String lastName) {
    //Creamos un metodo para que nos permita modificar el apellido
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
    //Creamos un metodo para que nos devuelva el apellido
        return lastName;
    }

    public String getStreet() {
    //Creamos un metodo para que nos devuelva la calle
        return street.get();
    }

    public void setStreet(String street) {
    //Creamos un metodo para que nos permita modificar la calle
        this.street.set(street);
    }

    public StringProperty streetProperty() {
    //Creamos un metodo para que nos devuelva la calle
        return street;
    }

    public int getPostalCode() {
    //Creamos un metodo para que nos devuelva el codigo postal
        return postalCode.get();
    }

    public void setPostalCode(int postalCode) {
    //Creamos un metodo para que nos permita modificar el codigo postal
        this.postalCode.set(postalCode);
    }

    public IntegerProperty postalCodeProperty() {
    //Creamos un metodo para que nos devuelva el codigo postal
        return postalCode;
    }

    public String getCity() {
    //Creamos un metodo para que nos devuelva la ciudad
        return city.get();
    }

    public void setCity(String city) {
    //Creamos un metodo para que nos permita modificar la ciudad
        this.city.set(city);
    }

    public StringProperty cityProperty() {
    //Creamos un metodo para que nos devuelva la ciudad
        return city;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthday() {
    //Creamos un metodo para que nos devuelva la fecha de nacimiento
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
    //Creamos un metodo para que nos permita modificar la fecha de nacimiento
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
    //Creamos un metodo para que nos devuelva la fecha de nacimiento
        return birthday;
    }

}



