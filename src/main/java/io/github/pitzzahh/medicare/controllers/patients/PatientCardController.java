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

import io.github.pitzzahh.medicare.backend.Gender;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import static io.github.pitzzahh.medicare.util.ComponentUtil.setCommonData;
import static io.github.pitzzahh.medicare.util.Style.normalStyle;
import static io.github.pitzzahh.medicare.util.ToolTipUtil.initToolTip;

public class PatientCardController {


    @FXML
    private TextField id, name, age, address, phoneNumber, symptoms;

    @FXML
    private ChoiceBox<Gender> gender;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    public Button removeButton;

    @FXML
    public void initialize() {
        removeButton.setTooltip(initToolTip("Click to Remove Patient", normalStyle()));
        id.setEditable(false);
        name.setEditable(false);
        age.setEditable(false);
        gender.setMouseTransparent(true);
        address.setEditable(false);
        phoneNumber.setEditable(false);
        symptoms.setEditable(false);
        dateOfBirth.setEditable(false);
        dateOfBirth.setMouseTransparent(true);
    }

    public void setData(Patient patient) {
        id.setText(String.valueOf(patient.getPatientId()));
        setCommonData(patient, name, age, gender, dateOfBirth, address, phoneNumber);
        symptoms.setText(patient.getSymptoms());
    }

}
