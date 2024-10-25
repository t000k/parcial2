module org.example.parcial2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires mysql.connector.j;

    //opens org.example.parcial2;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens org.example.parcial2.models to javafx.base;
    opens org.example.parcial2 to javafx.fxml;
    exports org.example.parcial2;
}