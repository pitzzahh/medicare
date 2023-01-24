package io.github.pitzzahh.medicare.backend;

import io.github.pitzzahh.medicare.backend.doctors.model.Specialization;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AssignedDoctor {

    private int id;
    private String name;
    private Specialization specialization;

    @Override
    public String toString() {
        return name == null ? "NO DOCTORS YET" : name;
    }
}
