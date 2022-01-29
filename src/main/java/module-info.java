module com.example.test {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
//    requires mysql.connector.java;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.mail;

    opens Controller to javafx.fxml;
    exports Controller;
    exports Connection;
    opens Connection to javafx.fxml;
    exports Main;
    opens Main to javafx.fxml;

    opens Models to javafx.base;

}