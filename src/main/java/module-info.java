module com.example.codiceprogetto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.codiceprogetto;
    opens com.example.codiceprogetto;
    exports com.example.codiceprogetto.homepage;
    opens com.example.codiceprogetto.homepage to javafx.fxml;
    exports com.example.codiceprogetto.browseProduct;
    opens com.example.codiceprogetto.browseProduct to javafx.fxml;
    exports com.example.codiceprogetto.selectcobra;
    opens com.example.codiceprogetto.selectcobra to javafx.fxml;
    exports com.example.codiceprogetto.shoppingcart;
    opens com.example.codiceprogetto.shoppingcart to javafx.fxml;
    exports com.example.codiceprogetto.loggingform;
    opens com.example.codiceprogetto.loggingform;
    exports com.example.codiceprogetto.utils;
    opens com.example.codiceprogetto.utils;
    //exports com.example.codiceprogetto.Entities;
    //opens com.example.codiceprogetto.Entities;
}