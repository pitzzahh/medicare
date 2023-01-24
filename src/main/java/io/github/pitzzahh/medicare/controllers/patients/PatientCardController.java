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

import static io.github.pitzzahh.medicare.util.ToolTipUtil.initToolTip;
import io.github.pitzzahh.medicare.backend.patients.model.Symptoms;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import static io.github.pitzzahh.medicare.util.Style.normalStyle;
import static io.github.pitzzahh.medicare.util.ComponentUtil.*;
import io.github.pitzzahh.medicare.backend.AssignedDoctor;
import io.github.pitzzahh.medicare.backend.Gender;
import javafx.scene.control.*;
import javafx.fxml.FXML;

public class PatientCardController {

    @FXML
    public TextField id, firstName, middleName, lastName, age, address, phoneNumber;

    @FXML
    public ChoiceBox<Gender> gender;

    @FXML
    public DatePicker dateOfBirth;

    @FXML
    public ChoiceBox<AssignedDoctor> doctor;

    @FXML
    public ChoiceBox<Symptoms> symptoms;

    @FXML
    public Button updateOrSaveButton, removeButton;

    @FXML
    public void initialize() {
        updateOrSaveButton.setTooltip(initToolTip("Click to Update Patient", normalStyle()));
        removeButton.setTooltip(initToolTip("Click to Remove Patient", normalStyle()));
        id.setEditable(false);
        firstName.setEditable(false);
        middleName.setEditable(false);
        lastName.setEditable(false);
        age.setEditable(false);
        gender.setMouseTransparent(true);
        address.setEditable(false);
        phoneNumber.setEditable(false);
        dateOfBirth.setEditable(false);
        dateOfBirth.setMouseTransparent(true);
        doctor.setMouseTransparent(true);
        symptoms.setMouseTransparent(true);
    }

    public void setData(Patient patient) {
        id.setText(String.valueOf(patient.getPatientId()));
        setCommonData(patient, firstName, middleName, lastName, age, gender, dateOfBirth, address, phoneNumber);
        doctor.getItems().clear();
        doctor.setValue(patient.getAssignDoctor());
        symptoms.setValue(patient.getSymptoms());
    }

}
