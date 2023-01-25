package io.github.pitzzahh.medicare.controllers;

import static io.github.pitzzahh.medicare.util.ComponentUtil.initTableColumns;
import io.github.pitzzahh.medicare.backend.patients.model.DischargedPatient;
import io.github.pitzzahh.medicare.util.DataUtil;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import java.net.URL;

public class DischargeController implements Initializable {

    @FXML
    public TableView<DischargedPatient> table;

    public static TableView<DischargedPatient> copy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        copy = table;
    }

    public static void setData(ObservableList<DischargedPatient> data) {
        DataUtil.getDataSource()
                .addAll(data);
        initTableColumns(copy, new String[]{"patientId", "name", "nameOfDoctor", "dateConfined", "dateDischarged"});
        copy.setItems(DataUtil.getDataSource());
        copy.getSelectionModel().clearSelection();
    }

    public static TableView<DischargedPatient> getCopy() {
        return copy;
    }
}
