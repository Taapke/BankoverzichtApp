module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.controller to javafx.fxml;
    opens org.example.model to javafx.fxml;

    exports org.example.model;
    exports org.example;
}
