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

package io.github.pitzzahh.medicare.backend.patients.dao;

import static io.github.pitzzahh.medicare.backend.db.DatabaseConnection.getJDBC;
import io.github.pitzzahh.medicare.backend.patients.mapper.PatientMapper;
import static io.github.pitzzahh.util.utilities.SecurityUtil.encrypt;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map;

public class PatientDAOImpl implements PatientDAO {
    @Override
    public Map<Integer, Patient> getPatients() {
        return getJDBC().query("SELECT * FROM p4t13nt$", new PatientMapper())
                .stream()
                .collect(Collectors.toMap(Patient::getPatientId, Function.identity()));
    }

    @Override
    public Consumer<Patient> addPatient() {
        final String QUERY = "INSERT INTO p4t13nt$(last_name, first_name, middle_name, gender, birthdate, address, phone_number, symptoms) VALUES (?,?,?,?,?,?,?,?)";
        return patient -> getJDBC().update(
                QUERY,
                encrypt(patient.getLastName()),
                encrypt(patient.getFirstName()),
                patient.getMiddleName().trim().isEmpty() ? null : encrypt(patient.getMiddleName()),
                encrypt(patient.getGender().name()),
                encrypt(patient.getBirthDate().toString()),
                encrypt(patient.getAddress()),
                patient.getPhoneNumber().trim().isEmpty() ? null : encrypt(patient.getPhoneNumber()),
                encrypt(patient.getSymptoms())
        );
    }

    @Override
    public Consumer<Integer> removePatientById() {
        return id -> getJDBC().update("DELETE FROM p4t13nt$ WHERE id = ?", id);
    }
}
