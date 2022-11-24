package com.address.model;

/*
 * Clase de ayuda para envolver una lista de personas. Esto se utiliza para guardar la
 * lista de personas en XML.
 */

import java.util.List;

//Cuando añado estos imports me da error en el metodo loadPersonDataFromFile de la clase Main.
//Lo que hago es poner el cursor sobre el error y le doy a la opcion de Add... y me añade los imports.
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//Esta version no soporta el JAXB. Por lo que debemos modificar el pom.xml y una vez hecho esto
//añadimos los imports que faltan a mano. Y pasamos a hacer los add posicionandose sobre el error.

//Esta clase es la que se encarga de guardar los datos en XML.
@XmlRootElement(name = "persons")
public class PersonListWrapper {
    //Creamos una lista de personas.
    private List<Person> persons;

    @XmlElement(name = "person")
    //Creamos un metodo para que nos devuelva la lista de personas.
    public List<Person> getPersons() {
        return persons;
    }

    //Creamos un metodo para que nos permita modificar la lista de personas.
    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

}
