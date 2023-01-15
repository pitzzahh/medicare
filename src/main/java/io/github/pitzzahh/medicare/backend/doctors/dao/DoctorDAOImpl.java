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
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Consumer<Integer> removeDoctorById() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
