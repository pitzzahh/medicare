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

import javafx.scene.control.cell.PropertyValueFactory;
import java.time.format.DateTimeFormatter;
import javafx.scene.effect.GaussianBlur;
import static java.lang.String.format;
import java.time.format.FormatStyle;
import java.util.stream.IntStream;
import javafx.scene.control.*;
import javafx.scene.Parent;
import java.util.Optional;

public interface ComponentUtil {
    /**
     * Used to get a table from a parent node.
     *
     * @param parentId the parent parentId.
     * @param tableId  the id of the table.
     * @return an {@code Optional<TableView<?>>}.
     */
    static Optional<TableView<?>> getTable(String parentId, String tableId) {
        return Optional.ofNullable((TableView<?>) WindowUtil.getParent(parentId).lookup(format("#%s", tableId)));
    }

    /**
     * Used to modify the progress bar from the main window.
     *
     * @param parent the main window parent.
     * @return an {@code Optional<ProgressBar>}.
     */
    static Optional<ProgressBar> getMainProgressBar(Parent parent) {
        return Optional.ofNullable((ProgressBar) parent.lookup("#progressBar"));
    }

    /**
     * Used to modify the message label from the main window.
     *
     * @param parent the main window parent.
     * @return an {@code Optional<Label>}.
     */
    static Optional<Label> getLabel(Parent parent, String id) {
        return Optional.ofNullable((Label) parent.lookup(format("#%s", id)));
    }

    static Optional<ChoiceBox<?>> getChoiceBox(Parent parent, String name) {
        return Optional.ofNullable((ChoiceBox<?>) parent.lookup(format("#%s", name)));
    }

    static void initTableColumns(TableView<?> table, String[] columns) {
        IntStream.range(0, columns.length).forEachOrdered(i -> {
            TableColumn<?, ?> column = table.getColumns().get(i);
            column.setStyle("-fx-alignment: center;");
            column.setCellValueFactory(new PropertyValueFactory<>(columns[i]));
        });
    }

    /**
     * Used to create a gaussian blur effect.
     *
     * @param radius the radius of the blur.
     * @return a {@code GaussianBlur}.
     */
    static GaussianBlur gaussianBlur(double radius) {
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(radius);
        return gaussianBlur;
    }

}

class ComponentUtilFields {
    static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
}