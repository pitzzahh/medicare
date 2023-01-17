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

package io.github.pitzzahh.medicare.backend.doctors.service;

import io.github.pitzzahh.medicare.backend.doctors.dao.DoctorDAO;
import io.github.pitzzahh.medicare.backend.doctors.model.Doctor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import java.util.Map;

@AllArgsConstructor
public class DoctorService {

    private final DoctorDAO DAO;


    public Map<Integer, Doctor> getDoctors() {
        return DAO.getDoctors();
    }

    public Consumer<Doctor> addDoctor() {
        return DAO.addDoctor();
    }

    public BiConsumer<Integer, Doctor> updateDoctorById() {
        return DAO.updateDoctorById();
    }

    public Consumer<Integer> removeDoctorById() {
        return DAO.removeDoctorById();
    }

    public boolean doesDoctorAlreadyExists(Doctor patient) {
        return getDoctors()
                .values()
                .stream()
                .anyMatch(d -> d.getFirstName().equals(patient.getFirstName()) || d.getLastName().equals(patient.getLastName()));
    }
}
