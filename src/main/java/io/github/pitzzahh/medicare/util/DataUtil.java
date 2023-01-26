package io.github.pitzzahh.medicare.util;

import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataUtil {

    private static final ObservableList<Patient> DATA_SOURCE = FXCollections.observableArrayList();

    public static ObservableList<Patient> getDataSource() {
        return DATA_SOURCE;
    }
}
