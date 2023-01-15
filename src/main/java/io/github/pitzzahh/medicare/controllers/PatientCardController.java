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

import static io.github.pitzzahh.medicare.util.ComponentUtil.getDateFormatter;
import static io.github.pitzzahh.medicare.util.ToolTipUtil.initToolTip;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import static io.github.pitzzahh.medicare.util.Style.normalStyle;
import io.github.pitzzahh.medicare.backend.Gender;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.time.LocalDate;
import javafx.fxml.FXML;
import java.time.Period;

public class PatientCardController {

    @FXML
    private TextField id, name, age, address, phoneNumber, symptoms;

    @FXML
    private ChoiceBox<Gender> gender;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    public Button updateButton, removeButton;

    @FXML
    public void initialize() {
        updateButton.setTooltip(initToolTip("Click to Modify Patient", normalStyle()));
        removeButton.setTooltip(initToolTip("Click to Remove Patient", normalStyle()));
        id.setEditable(false);
        name.setEditable(false);
        age.setEditable(false);
        gender.setDisable(true);
        gender.setMouseTransparent(true);
        address.setEditable(false);
        phoneNumber.setEditable(false);
        symptoms.setEditable(false);
        dateOfBirth.setEditable(false);
    }

    public void setData(Patient patient) {
        id.setText(String.valueOf(patient.getPatientId()));
        name.setText(patient.getFirstName().concat(" ").concat(patient.getLastName()));
        age.setText(String.valueOf(Period.between(patient.getBirthDate(), LocalDate.now()).getYears()));
        gender.setValue(patient.getGender());
        String dateString = patient.getBirthDate().format(getDateFormatter());
        dateOfBirth.getEditor().setText(dateString);
        address.setText(patient.getAddress());
        phoneNumber.setText(patient.getPhoneNumber());
        symptoms.setText(patient.getSymptoms());
    }

}
