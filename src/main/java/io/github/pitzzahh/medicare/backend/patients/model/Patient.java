package io.github.pitzzahh.medicare.backend.patients.model;

import io.github.pitzzahh.util.utilities.classes.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class Patient extends Person {
    private int patientId;
}
