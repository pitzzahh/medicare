module medicare {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires spring.jdbc;
    requires java.sql;
    requires lombok;
    requires util.classes;

    opens io.github.pitzzahh.medicare.controllers to javafx.fxml;
    opens io.github.pitzzahh.medicare.application to javafx.graphics;
    opens io.github.pitzzahh.medicare.controllers.patients to javafx.fxml;
    opens io.github.pitzzahh.medicare.controllers.doctors to javafx.fxml;
    exports io.github.pitzzahh.medicare;
    exports io.github.pitzzahh.medicare.backend.login.dao;
    exports io.github.pitzzahh.medicare.backend.login.model;
    exports io.github.pitzzahh.medicare.backend.login.service;

}