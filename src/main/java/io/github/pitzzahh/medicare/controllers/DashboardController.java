package io.github.pitzzahh.medicare.controllers;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;

public class DashboardController {

    @FXML
    public HBox patientsCard, doctorsCard;

    @FXML
    private Label patientsCount, doctorsCount;

    private void setData(
            String patientCount,
            String doctorsCount
    ) {

    }
}
