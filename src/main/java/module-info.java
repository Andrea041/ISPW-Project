module com.example.codiceprogetto {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.codiceprogetto to javafx.fxml;
    exports com.example.codiceprogetto;
}