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

import static io.github.pitzzahh.medicare.util.WindowUtil.getParent;
import static io.github.pitzzahh.medicare.util.WindowUtil.getStage;
import io.github.pitzzahh.medicare.backend.login.cache.AuthData;
import io.github.pitzzahh.medicare.backend.login.model.Account;
import io.github.pitzzahh.medicare.util.ComponentUtil;
import io.github.pitzzahh.medicare.util.WindowUtil;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import java.util.Map;

public class AuthenticationController {

    @FXML
    public TextField username;

    @FXML
    public PasswordField password;

    @FXML
    public Button loginButton;

    @FXML
    public Label errorMessage;

    @FXML
    public void initialize() {
        loginButton.setTooltip(new Tooltip("Click to log in"));
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) {
        actionEvent.consume();
        String username = this.username.getText();
        String password = this.password.getText();
        checkCredentials(username, password);
    }

    // TODO: add progress bar or some loading
    private void checkCredentials(String username, String password) {
        signIn(username, password);
    }

    private void signIn(String username, String password) {
        Map<String, Account> accounts = AuthData.getAccounts();

        //noinspection ConstantValue
        if (true) { // !accounts.isEmpty()
            boolean containsUsername = accounts.containsKey(username);
            //noinspection ConstantValue
            if (true) { // containsUsername
                Account account = accounts.get(username);
                //noinspection ConstantValue
                if (true) { // account.getPassword().equals(password)
                    this.username.clear();
                    this.password.clear();
                    errorMessage.setText("");
                    Parent parent = getParent("main_panel");
                    WindowUtil.loadParent(parent, "MEDiCARE");
                    getStage().show();
                }
                else {
                    ComponentUtil.getMainProgressBar(getParent("login_window")).ifPresent(p -> p.setVisible(false));
                    errorMessage.setText("Invalid username or password");
                }
            } else {
                ComponentUtil.getMainProgressBar(getParent("login_window")).ifPresent(p -> p.setVisible(false));
                errorMessage.setText("Invalid username or password");
            }
        }
        else {
            ComponentUtil.getMainProgressBar(getParent("login_window")).ifPresent(p -> p.setVisible(false));
            errorMessage.setText("Invalid username or password");
        }
    }

    @FXML
    public void onType(KeyEvent keyEvent) {
        keyEvent.consume();

        String username = this.username.getText();
        String password = this.password.getText();

        this.password.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) checkCredentials(username, password);
            else {
                final String STYLE =
                        "    -fx-border-color: transparent transparent #000 transparent;" +
                                "    -fx-border-width: 2px;" +
                                "    -fx-border-style: solid;" +
                                "    -fx-font-size: 18px;";
                this.username.setStyle(STYLE);
                this.password.setStyle(STYLE);
                this.errorMessage.setText("");
            }
        });

    }
}
