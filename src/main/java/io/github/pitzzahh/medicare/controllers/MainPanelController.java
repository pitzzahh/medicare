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

import static io.github.pitzzahh.medicare.util.ToolTipUtil.initToolTip;
import static io.github.pitzzahh.medicare.util.Style.normalStyle;
import static io.github.pitzzahh.medicare.util.WindowUtil.*;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
    }

    @FXML
    public void onClickDoctors(ActionEvent actionEvent) {
        actionEvent.consume();
        loadPage("main_panel", "doctors_panel");
        loadPage("doctors_panel", "doctor_dashboard");
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
}
