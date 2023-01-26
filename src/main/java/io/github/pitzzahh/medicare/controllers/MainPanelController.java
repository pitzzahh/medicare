/*
 * MIT License
 *
 * Copyright (c) 2023 pitzzahh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.pitzzahh.medicare.controllers;

import static io.github.pitzzahh.medicare.util.ComponentUtil.setCommonDashboardData;
import static io.github.pitzzahh.medicare.application.Medicare.getPatientService;
import static io.github.pitzzahh.medicare.util.ToolTipUtil.initToolTip;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import static io.github.pitzzahh.medicare.util.Style.normalStyle;
import static io.github.pitzzahh.medicare.util.WindowUtil.*;
import javafx.collections.FXCollections;
import java.util.stream.Collectors;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.util.Collection;
import java.util.ArrayList;
import javafx.fxml.FXML;
import java.time.Month;

public class MainPanelController {

    @FXML
    public Button logoutButton;

    @FXML
    public void initialize() {
        logoutButton.setTooltip(initToolTip("Click to logout", normalStyle()));
    }

    @FXML
    public void onClickPatients(ActionEvent actionEvent) {
        actionEvent.consume();
        loadPage("main_panel", "patients_panel");
        loadPage("patients_panel", "patient_dashboard");
        setCommonDashboardData("patient_dashboard", "patientsCount", true);
    }

    @FXML
    public void onClickDoctors(ActionEvent actionEvent) {
        actionEvent.consume();
        loadPage("main_panel", "doctors_panel");
        loadPage("doctors_panel", "doctor_dashboard");
        setCommonDashboardData("doctor_dashboard", "doctorsCount", false);
    }

    @FXML
    public void onClickAbout(ActionEvent actionEvent) {
        actionEvent.consume();
        loadPage("main_panel", "about");
    }

    @FXML
    public void onLogout(ActionEvent actionEvent) {
        actionEvent.consume();
        logoutSession();
        loadPage("main_panel", "dashboard");
    }

    @FXML
    public void onClickStatistics(ActionEvent actionEvent) {
        actionEvent.consume();
        loadPage("main_panel", "statistics");


        Collection<Patient> dischargedPatients = getPatientService()
                .getDischargedPatients()
                .values()
                .stream()
                .map(patient -> new Patient(patient.getSymptoms(), patient.getDateConfined()))
                .collect(Collectors.toList());

        Collection<Patient> patientCollection = getPatientService()
                .getPatients()
                .values()
                .stream()
                .map(patient -> new Patient(patient.getSymptoms(), patient.getDateConfined()))
                .collect(Collectors.toList());

        if (!dischargedPatients.isEmpty()) patientCollection.addAll(dischargedPatients);

        ArrayList<Patient> patients = new ArrayList<>(patientCollection);

        StatisticsController.setMonthData(patients, Month.JANUARY);
        StatisticsController.setMonthData(patients, Month.FEBRUARY);
        StatisticsController.setMonthData(patients, Month.MARCH);
        StatisticsController.setMonthData(patients, Month.APRIL);
        StatisticsController.setMonthData(patients, Month.MAY);
        StatisticsController.setMonthData(patients, Month.JUNE);
        StatisticsController.setMonthData(patients, Month.JULY);
        StatisticsController.setMonthData(patients, Month.AUGUST);
        StatisticsController.setMonthData(patients, Month.SEPTEMBER);
        StatisticsController.setMonthData(patients, Month.OCTOBER);
        StatisticsController.setMonthData(patients, Month.NOVEMBER);
        StatisticsController.setMonthData(patients, Month.DECEMBER);
    }

    @FXML
    public void onClickDischargeHistory(ActionEvent actionEvent) {
        actionEvent.consume();
        loadPage("main_panel", "discharge_panel");
        DischargeController.getCopy()
                .getItems()
                .clear();
        DischargeController.setData(
                getPatientService()
                        .getDischargedPatients()
                        .values()
                        .stream()
                        .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
    }
}
