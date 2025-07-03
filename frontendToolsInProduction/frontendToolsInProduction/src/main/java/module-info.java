module org.example.frontedToolsInProduction {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;


    opens org.example.frontedToolsInProduction to javafx.fxml;
    exports org.example.frontedToolsInProduction;
    exports org.example.frontedToolsInProduction.model;
    opens org.example.frontedToolsInProduction.model to javafx.fxml, com.google.gson;
}