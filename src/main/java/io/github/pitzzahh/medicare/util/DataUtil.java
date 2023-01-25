package io.github.pitzzahh.medicare.util;

import io.github.pitzzahh.medicare.backend.patients.model.DischargedPatient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataUtil {

    private static ObservableList<DischargedPatient> dataSource = FXCollections.observableArrayList();

    public static ObservableList<DischargedPatient> getDataSource() {
        return dataSource;
    }
}
