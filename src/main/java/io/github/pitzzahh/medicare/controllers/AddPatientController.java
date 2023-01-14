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

import static io.github.pitzzahh.medicare.application.Medicare.getPatientService;
import static io.github.pitzzahh.medicare.util.WindowUtil.getParent;
import static io.github.pitzzahh.medicare.util.WindowUtil.loadPage;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import static io.github.pitzzahh.medicare.util.ComponentUtil.*;
import io.github.pitzzahh.medicare.backend.Gender;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.util.Optional;
import javafx.fxml.FXML;

public class AddPatientController {

    @FXML
    public TextField lastName, firstName, middleName, address, phoneNumber;

    @FXML
    public ChoiceBox<Gender> gender;

    @FXML
    public DatePicker birthDate;

    @FXML
    public TextArea symptoms;

    @FXML
    public Button addPatient;

    @FXML
    public void initialize() {
        addPatient.setTooltip(new Tooltip("Click to Add Patient"));
        initGenderChoiceBox(gender);
        resetInputs(lastName, firstName, middleName, address, phoneNumber, gender, birthDate);
    }

    @FXML
    public void onAddPatient(ActionEvent actionEvent) {
        actionEvent.consume();

        if (requiredInput()) return;

        Patient patient = new Patient(
                lastName.getText().trim(),
                firstName.getText().trim(),
                middleName.getText().trim(),
                gender.getSelectionModel().getSelectedItem(),
                birthDate.getValue(),
                address.getText().trim(),
                phoneNumber.getText().trim(),
                symptoms.getText().trim()
        );

        if (getPatientService().doesPatientAlreadyExists(patient)) {
            showAlert("Patient Already Exists", "Patient Already Exists", "Patient already exists in the database");
            return;
        }

        getPatientService().addPatient().accept(patient);
        showAlert("Patient Added", "Patient Added", "Patient has been added successfully");

        int size = getPatientService().getPatients().size();
        Optional<Label> label = getLabel(getParent("dashboard"), "patientsCount");
        label.ifPresent(l -> l.setText(String.valueOf(size)));
        loadPage("patients_panel", "dashboard");
        resetInputs(lastName, firstName, middleName, address, phoneNumber, gender, birthDate);
        symptoms.clear();
    }

    private boolean requiredInput() {
        if (firstName.getText().trim().isEmpty()) {
            showAlert("First Name is Required", "First Name is Required", "First name is required");
            return true;
        }

        if (lastName.getText().trim().isEmpty()) {
            showAlert("Last Name is Required", "Last Name is Required", "Last name is required");
            return true;
        }

        if (birthDate.getValue() == null) {
            showAlert("Birth Date is Required", "Birth Date is Required", "Birth date is required");
            return true;
        }

        if (address.getText().trim().isEmpty()) {
            showAlert("Address is Required", "Address is Required", "Address is required");
            return true;
        }

        if (symptoms.getText().trim().isEmpty()) {
            showAlert("Symptoms is Required", "Symptoms is Required", "Symptoms is required");
            return true;
        }

        return false;
    }
}
