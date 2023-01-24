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

package io.github.pitzzahh.medicare.controllers.doctors;

import static io.github.pitzzahh.medicare.application.Medicare.getDoctorService;
import io.github.pitzzahh.medicare.backend.doctors.model.Specialization;
import static io.github.pitzzahh.medicare.util.WindowUtil.loadPage;
import io.github.pitzzahh.medicare.backend.doctors.model.Doctor;
import static io.github.pitzzahh.medicare.util.ComponentUtil.*;
import io.github.pitzzahh.medicare.backend.Gender;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;

public class AddDoctorController {

    @FXML
    public TextField lastName, firstName, middleName, address, phoneNumber;

    @FXML
    public ChoiceBox<Gender> gender;

    @FXML ChoiceBox<Specialization> specialization;

    @FXML
    public DatePicker birthDate;

    @FXML
    public Button addDoctor;

    @FXML
    public void initialize() {
        addDoctor.setTooltip(new Tooltip("Click to Add Doctors Data"));
        initGenderChoiceBox(gender);
        initSpecializationChoiceBox(specialization);
        resetInputs(lastName, firstName, middleName, address, phoneNumber, gender, null, birthDate);
    }

    @FXML
    public void onAddDoctor(ActionEvent actionEvent) {
        actionEvent.consume();

        if (requiredInput(
                firstName,
                lastName,
                address,
                birthDate
        )) return;

        // TODO: finish

        Doctor doctor = new Doctor(
                lastName.getText().trim(),
                firstName.getText().trim(),
                middleName.getText().trim(),
                gender.getSelectionModel().getSelectedItem(),
                birthDate.getValue(),
                address.getText().trim(),
                phoneNumber.getText().trim(),
                specialization.getValue()
        );

        if (getDoctorService().doesDoctorAlreadyExists(doctor)) {
            Alert alert = initAlert("Doctor Already Exists", "Doctor Already Exists", "Doctor already exists in the database");
            showAlertInfo("assets/error.png", "Error graphic not found", alert);
            return;
        }

        getDoctorService().addDoctor().accept(doctor);
        Alert alert = initAlert("Doctor Added", "Doctor Added", "Doctor has been added successfully");
        showAlertInfo("assets/success.png", "Success graphic not found", alert);

        setDashBoardData();
        setCommonDashboardData("doctor_dashboard", "doctorsCount", false);

        loadPage("patients_panel", "doctor_dashboard");
        resetInputs(lastName, firstName, middleName, address, phoneNumber, gender, null, birthDate);
        getDoctorService().getDoctors().put(doctor.getId(), doctor);
        initGenderChoiceBox(gender);
        initSpecializationChoiceBox(specialization);
    }
}
