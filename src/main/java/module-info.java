module com.dam.gotask {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;
    requires java.sql;
    requires mysql.connector.j;


    opens com.dam.gotask to javafx.fxml;
    exports com.dam.gotask;
}