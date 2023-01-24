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

package io.github.pitzzahh.medicare.controllers.patients;

import static io.github.pitzzahh.medicare.application.Medicare.getDoctorService;
import static io.github.pitzzahh.medicare.application.Medicare.getPatientService;
import static io.github.pitzzahh.medicare.util.WindowUtil.loadPage;
import io.github.pitzzahh.medicare.backend.patients.model.Symptoms;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import static io.github.pitzzahh.medicare.util.ComponentUtil.*;
import io.github.pitzzahh.medicare.backend.AssignedDoctor;
import io.github.pitzzahh.medicare.backend.Gender;
import static java.util.Objects.requireNonNull;
import io.github.pitzzahh.medicare.Launcher;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.control.*;
import javafx.fxml.FXML;

public class AddPatientController {

    @FXML
    public TextField lastName, firstName, middleName, address, phoneNumber;

    @FXML
    public ChoiceBox<Gender> gender;

    @FXML
    private ChoiceBox<AssignedDoctor> doctor;

    @FXML
    private ChoiceBox<Symptoms> symptoms;

    @FXML
    public DatePicker birthDate;

    @FXML
    public Button addPatient;

    @FXML
    public void initialize() {
        addPatient.setTooltip(new Tooltip("Click to Add Patient"));
        initGenderChoiceBox(gender);
        initSymptomsChoiceBox(symptoms);
        initAssignedDoctorChoiceBox(doctor);
        resetInputs(lastName, firstName, middleName, address, phoneNumber, gender, doctor, birthDate);
    }

    @FXML
    public void onAddPatient(ActionEvent actionEvent) {
        actionEvent.consume();

        if (requiredInput(
                firstName,
                lastName,
                address,
                birthDate
        )) return;
        AssignedDoctor doc = doctor.getValue();

        Patient patient = new Patient(
                lastName.getText().trim(),
                firstName.getText().trim(),
                middleName.getText().trim(),
                gender.getSelectionModel().getSelectedItem(),
                birthDate.getValue(),
                address.getText().trim(),
                phoneNumber.getText().trim(),
                doctor.getSelectionModel().isEmpty() ? null : getDoctorService().getAssignedDoctorById().apply(doc.getId()).orElse(null),
                symptoms.getSelectionModel().getSelectedItem()
        );

        if (getPatientService().doesPatientAlreadyExists(patient)) {
            Alert alert = initAlert("Patient Already Exists", "Patient Already Exists", "Patient already exists in the database");
            ImageView graphic = new ImageView(new Image(requireNonNull(Launcher.class.getResourceAsStream("assets/error.png"), "Error graphic not found")));
            graphic.setFitWidth(50);
            graphic.setFitHeight(50);
            alert.setGraphic(graphic);
            alert.showAndWait();
            return;
        }

        getPatientService().addPatient().accept(patient);
        Alert alert = initAlert("Patient Added", "Patient Added", "Patient has been added successfully");
        ImageView graphic = new ImageView(new Image(requireNonNull(Launcher.class.getResourceAsStream("assets/success.png"), "Success graphic not found")));
        graphic.setFitWidth(50);
        graphic.setFitHeight(50);
        alert.setGraphic(graphic);
        alert.showAndWait();

        setDashBoardData();
        setCommonDashboardData("patient_dashboard", "patientsCount", true);

        loadPage("patients_panel", "patient_dashboard");
        resetInputs(lastName, firstName, middleName, address, phoneNumber, gender, doctor, birthDate);
        getPatientService().getPatients().put(patient.getPatientId(), patient);
        initGenderChoiceBox(gender);
        initSymptomsChoiceBox(symptoms);
        initAssignedDoctorChoiceBox(doctor);
    }

}
