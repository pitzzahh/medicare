package io.github.pitzzahh.medicare.backend.patients.model;

import java.time.LocalDate;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class DischargedPatient {

    private int patientId;
    private String name;
    private String nameOfDoctor;
    private LocalDate dateConfined;
    private LocalDate dateDischarged;

}
