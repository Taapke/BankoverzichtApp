module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.controller to javafx.fxml;
    exports org.example;
}
