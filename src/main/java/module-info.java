module com.example.codiceprogetto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.codiceprogetto;
    opens com.example.codiceprogetto;

    exports com.example.codiceprogetto.logic.utils;
    opens com.example.codiceprogetto.logic.utils;
    exports com.example.codiceprogetto.logic.dao;
    opens com.example.codiceprogetto.logic.dao;
    exports com.example.codiceprogetto.logic.bean;
    opens com.example.codiceprogetto.logic.bean;
    exports com.example.codiceprogetto.logic.view;
    opens com.example.codiceprogetto.logic.view;
    exports com.example.codiceprogetto.logic.graphiccontroller;
    opens com.example.codiceprogetto.logic.graphiccontroller;
    exports com.example.codiceprogetto.logic.appcontroller;
    opens com.example.codiceprogetto.logic.appcontroller;
    exports com.example.codiceprogetto.logic.entities;
    opens com.example.codiceprogetto.logic.entities;
    exports com.example.codiceprogetto.logic.queries;
    opens com.example.codiceprogetto.logic.queries;
}