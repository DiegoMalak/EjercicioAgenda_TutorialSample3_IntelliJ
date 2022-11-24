module com.address {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires java.xml.bind;

    opens com.address to javafx.fxml;
    exports com.address;
    exports com.address.controlador;
    exports com.address.util;
    opens com.address.model to javafx.fxml, java.xml.bind;
    opens com.address.controlador to javafx.fxml;

}