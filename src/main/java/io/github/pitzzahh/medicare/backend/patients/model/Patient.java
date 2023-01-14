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

package io.github.pitzzahh.medicare.backend.patients.model;

import io.github.pitzzahh.medicare.backend.Gender;
import io.github.pitzzahh.medicare.backend.Person;
import java.time.LocalDate;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends Person {
    private int patientId;
    private String symptoms;

    public Patient(int patientId, String lastName, String firstName, String middleName, Gender gender, LocalDate birthDate, String address, String phoneNumber, String symptoms) {
        this.patientId = patientId;
        super.setLastName(lastName);
        super.setFirstName(firstName);
        super.setMiddleName(middleName);
        super.setGender(gender);
        super.setBirthDate(birthDate);
        super.setAddress(address);
        super.setPhoneNumber(phoneNumber);
        this.symptoms = symptoms;
    }

    public Patient(String lastName, String firstName, String middleName, Gender gender, LocalDate birthDate, String address, String phoneNumber, String symptoms) {
        super.setLastName(lastName);
        super.setFirstName(firstName);
        super.setMiddleName(middleName);
        super.setGender(gender);
        super.setBirthDate(birthDate);
        super.setAddress(address);
        super.setPhoneNumber(phoneNumber);
        this.symptoms = symptoms;
    }
}
