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

package io.github.pitzzahh.medicare.application;

import static io.github.pitzzahh.medicare.util.ComponentUtil.getMainProgressBar;
import static io.github.pitzzahh.medicare.util.WindowUtil.*;
import io.github.pitzzahh.medicare.util.WindowUtil;
import static java.util.Objects.requireNonNull;
import io.github.pitzzahh.medicare.Launcher;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Map;

public class Medicare extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        initParents();
        Parent parent = getParent("auth_window");
        Scene scene = new Scene(parent);
        setStage(primaryStage);

        getStage().initStyle(StageStyle.DECORATED);
        getStage().getIcons().add(new Image(requireNonNull(Launcher.class.getResourceAsStream("assets/logo.png"), "Icon not found")));
        getStage().setScene(scene);
        getStage().addEventHandler(KeyEvent.KEY_PRESSED, fullScreenEvent);
        WindowUtil.loadParent(parent, "Authentication");
        getStage().centerOnScreen();
        getMainProgressBar(parent).ifPresent(p -> p.setVisible(false));
        getStage().show();
    }

    /**
     * Initializes the parents.
     * The window is loaded from the FXML file.
     *
     * @throws IOException if the parent cannot be loaded.
     */
    private void initParents() throws IOException {
        Parent loginPage = FXMLLoader.load(requireNonNull(Launcher.class.getResource("fxml/auth/auth.fxml"), "Cannot find auth.fxml"));
        Parent mainPanel = FXMLLoader.load(requireNonNull(Launcher.class.getResource("fxml/mainPanel.fxml"), "Cannot find mainPanel.fxml"));

        loginPage.setId("auth_window");
        mainPanel.setId("main_panel");

        WindowUtil.addParents.accept(Map.of(
                loginPage.getId(), loginPage,
                mainPanel.getId(), mainPanel
        ));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
