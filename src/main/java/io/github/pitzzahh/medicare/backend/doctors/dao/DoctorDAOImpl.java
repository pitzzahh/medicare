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

package io.github.pitzzahh.medicare.backend.doctors.dao;

import static io.github.pitzzahh.medicare.backend.db.DatabaseConnection.getJDBC;
import io.github.pitzzahh.medicare.backend.doctors.mapper.DoctorMapper;
import static io.github.pitzzahh.util.utilities.SecurityUtil.encrypt;
import io.github.pitzzahh.medicare.backend.doctors.model.Doctor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map;

public class DoctorDAOImpl implements DoctorDAO {

    @Override
    public Map<Integer, Doctor> getDoctors() {
        return getJDBC().query("SELECT * FROM d0ct0r$", new DoctorMapper())
                .stream()
                .collect(Collectors.toMap(Doctor::getId, Function.identity()));
    }

    @Override
    public Consumer<Doctor> addDoctor() {
        final String QUERY = "INSERT INTO d0ct0r$$(last_name, first_name, middle_name, gender, birthdate, address, phone_number, specialization) VALUES (?,?,?,?,?,?,?,?)";
        return doctor -> getJDBC().update(
                QUERY,
                encrypt(doctor.getLastName()),
                encrypt(doctor.getFirstName()),
                doctor.getMiddleName().trim().isEmpty() ? null : encrypt(doctor.getMiddleName()),
                encrypt(doctor.getGender().name()),
                encrypt(doctor.getBirthDate().toString()),
                encrypt(doctor.getAddress()),
                doctor.getPhoneNumber().trim().isEmpty() ? null : encrypt(doctor.getPhoneNumber()),
                encrypt(doctor.getSpecialization().toString())
        );
    }

    @Override
    public Consumer<Integer> removeDoctorById() {
        return id -> getJDBC().update("DELETE FROM d0ct0r$ WHERE id = ?", id);
    }
}
