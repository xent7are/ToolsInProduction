module org.example.frontendToolsInProduction {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;


    opens org.example.frontendToolsInProduction to javafx.fxml;
    exports org.example.frontendToolsInProduction;
    exports org.example.frontendToolsInProduction.model;
    opens org.example.frontendToolsInProduction.model to javafx.fxml, com.google.gson;
}