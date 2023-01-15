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

package io.github.pitzzahh.medicare.backend.patients.cache;

import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.Map;

public final class PatientData {

    private static final Map<Integer, Patient> PATIENTS = new HashMap<>();

    private static final ObservableList<Patient> MEMBERS_OBSERVABLE_LIST = FXCollections.observableArrayList();

    public static Consumer<Map<Integer, Patient>> initPatients = PATIENTS::putAll;

    public static Map<Integer, Patient> getPatients() {
        return PATIENTS;
    }

    public static ObservableList<Patient> getMembersDataSource() {
        return MEMBERS_OBSERVABLE_LIST;
    }
}