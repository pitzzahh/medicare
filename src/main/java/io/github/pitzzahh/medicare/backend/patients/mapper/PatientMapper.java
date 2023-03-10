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

package io.github.pitzzahh.medicare.backend.patients.mapper;

import io.github.pitzzahh.medicare.backend.doctors.model.Specialization;
import static io.github.pitzzahh.util.utilities.SecurityUtil.decrypt;
import io.github.pitzzahh.medicare.backend.patients.model.Symptoms;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import io.github.pitzzahh.medicare.backend.AssignedDoctor;
import io.github.pitzzahh.util.utilities.SecurityUtil;
import io.github.pitzzahh.medicare.backend.Gender;
import org.springframework.jdbc.core.RowMapper;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.util.Optional;

public class PatientMapper implements RowMapper<Patient> {

    @Override
    public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
        final String[] BIRTHDATE = decrypt(rs.getString("birthdate")).split("-");
        final String[] DATE_CONFINED = decrypt(rs.getString("date_confined")).split("-");
        return new Patient(
                rs.getInt("id"),
                decrypt(rs.getString("last_name")),
                decrypt(rs.getString("first_name")),
                Optional.ofNullable(rs.getString("middle_name"))
                        .map(SecurityUtil::decrypt)
                        .orElse(""),
                Gender.valueOf(decrypt(rs.getString("gender"))),
                LocalDate.of(parseInt(BIRTHDATE[0]), parseInt(BIRTHDATE[1]), parseInt(BIRTHDATE[2])),
                decrypt(rs.getString("address")),
                Optional.ofNullable(rs.getString("phone_number"))
                        .map(SecurityUtil::decrypt)
                        .orElse(""),
                new AssignedDoctor(
                        parseInt(rs.getString("doctor_id").isEmpty() ? "0" : decrypt(rs.getString("doctor_id"))),
                        rs.getString("doctor_name").isEmpty() ? "" : decrypt(rs.getString("doctor_name")),
                        rs.getString("doctor_specialization").isEmpty() ? null :
                                Specialization.valueOf(decrypt(rs.getString("doctor_specialization")))
                ),
                LocalDate.of(parseInt(DATE_CONFINED[0]), parseInt(DATE_CONFINED[1]), parseInt(DATE_CONFINED[2])),
                Symptoms.valueOf(decrypt(rs.getString("symptoms")))
        );
    }
}
