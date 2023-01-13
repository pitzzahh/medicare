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

package io.github.pitzzahh.medicare.util;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.*;
import java.util.function.Consumer;

import static java.lang.String.format;

public interface WindowUtil {

    /**
     * Add a parent object to the parents map.
     * takes a {@code Map<String, Parent>} of parent
     */
    Consumer<Map<String, Parent>> addParents = WindowUtilFields.parents::putAll;


    EventHandler<KeyEvent> fullScreenEvent = event -> {
        if (KeyCode.F11.equals(event.getCode())) getStage().setFullScreen(!getStage().isFullScreen());
    };

    /**
     * Gets the parent with the specified id.
     * @param id the id of the parent.
     * @return the parent with the specified id.
     */
    static Parent getParent(String id) {
        return Optional.ofNullable(WindowUtilFields.parents.get(id))
                .orElseThrow(() -> new IllegalStateException(format("Cannot find parent with [%s] id", id)));
    }

    /**
     * Sets the center border pane to the specific window.
     * @param parentId the parent id.
     * @param windowToCenter the page id to be put in the center pane.
     */
    static void loadPage(String parentId, String windowToCenter) {
        ((BorderPane)(getParent(parentId)))
                .setCenter(getParent(windowToCenter));
    }

    static void loadParent(Parent parent, String stageTitle) {
        boolean isFullScreen = getStage().isFullScreen();
        Optional<Scene> scene = Optional.ofNullable(parent.getScene());
        getStage().setScene(scene.orElseGet(() -> new Scene(parent)));
        getStage().setWidth(getStage().getScene().getWidth());
        getStage().setHeight(getStage().getScene().getHeight());
        getStage().setTitle(stageTitle);
        if (!isFullScreen) getStage().centerOnScreen();
        getStage().addEventHandler(KeyEvent.KEY_PRESSED, fullScreenEvent);
        getStage().toFront();
        getStage().setFullScreen(isFullScreen);
    }

    static void logoutSession() {
        Parent loginWindow = getParent("auth_window");
        loadParent(loginWindow, "Authentication");
        ComponentUtil.getMainProgressBar(loginWindow).ifPresent(pb -> pb.setVisible(false));
    }

    static Stage getStage() {
        return WindowUtilFields.stage;
    }
    static void setStage(Stage stage) {
        WindowUtilFields.stage = stage;
    }
}

class WindowUtilFields {

    /**
     * The parents array.
     */
    static Map<String, Parent> parents = new HashMap<>();
    static Stage stage;
}
