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

import static io.github.pitzzahh.medicare.backend.login.cache.AuthData.*;
import static io.github.pitzzahh.medicare.application.Medicare.*;
import static io.github.pitzzahh.medicare.util.WindowUtil.*;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainPanelController {

    @FXML
    public Button logoutButton;

    @FXML
    public void initialize() {
        logoutButton.setTooltip(new Tooltip("Click to log out"));
    }

    @FXML
    public void onClickPatients(ActionEvent actionEvent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @FXML
    public void onClickDoctors(ActionEvent actionEvent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @FXML
    public void onClickAbout(ActionEvent actionEvent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @FXML
    public void onLogout(ActionEvent actionEvent) {
        actionEvent.consume();
        logoutSession();
        getAccounts().clear();
        initAccounts.accept(getAccountService().getAccounts());
        loadPage("main_panel", "dashboard");
        getStage().show();
    }
}
