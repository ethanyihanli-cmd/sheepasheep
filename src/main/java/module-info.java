module com.macondo.sheep {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;


    opens com.macondo.sheep to javafx.fxml;
    exports com.macondo.sheep;
    exports com.macondo.sheep.model;
}
