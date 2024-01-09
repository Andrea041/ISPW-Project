module com.example.codiceprogetto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.codiceprogetto;
    opens com.example.codiceprogetto;
    exports com.example.codiceprogetto.HomePage;
    opens com.example.codiceprogetto.HomePage to javafx.fxml;
    exports com.example.codiceprogetto.BrowseProduct;
    opens com.example.codiceprogetto.BrowseProduct to javafx.fxml;
    exports com.example.codiceprogetto.SelectCobra;
    opens com.example.codiceprogetto.SelectCobra to javafx.fxml;
    exports com.example.codiceprogetto.ShoppingCart;
    opens com.example.codiceprogetto.ShoppingCart to javafx.fxml;
    exports com.example.codiceprogetto.LoggingForm;
    opens com.example.codiceprogetto.LoggingForm;
    exports com.example.codiceprogetto.utils;
    opens com.example.codiceprogetto.utils;
    //exports com.example.codiceprogetto.Entities;
    //opens com.example.codiceprogetto.Entities;
}