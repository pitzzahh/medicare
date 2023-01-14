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
import static io.github.pitzzahh.medicare.util.Style.normalStyle;
import io.github.pitzzahh.medicare.backend.Gender;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
        address.setEditable(false);
        phoneNumber.setEditable(false);
        symptoms.setEditable(false);
        String dateString = dateOfBirth.getValue().format(getDateFormatter());
        dateOfBirth.setEditable(false);
        dateOfBirth.setDisable(true);
        dateOfBirth.getEditor().setText(dateString);
        gender.setDisable(true);
    }

    @FXML
    public void onUpdatePatient(ActionEvent actionEvent) {
        actionEvent.consume();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @FXML
    public void onRemovePatient(ActionEvent actionEvent) {
        actionEvent.consume();
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
