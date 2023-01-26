package io.github.pitzzahh.medicare.controllers;

import static io.github.pitzzahh.medicare.util.ComponentUtil.initTableColumns;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import io.github.pitzzahh.medicare.util.DataUtil;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import java.net.URL;

public class DischargeController implements Initializable {

    @FXML
    private TableView<Patient> table;

    private static TableView<Patient> copy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        copy = table;
    }

    public static void setData(ObservableList<Patient> data) {
        DataUtil.getDataSource()
                .addAll(data);
        initTableColumns(copy, new String[]{"patientId", "lastName", "firstName", "middleName", "symptoms", "assignedDoctor", "dateConfined", "dateDischarged"});
        copy.setItems(DataUtil.getDataSource());
        copy.getSelectionModel().clearSelection();
    }

    public static TableView<Patient> getCopy() {
        return copy;
    }
}
