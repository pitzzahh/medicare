package io.github.pitzzahh.medicare.backend.patients.mapper;

import static io.github.pitzzahh.medicare.application.Medicare.getDoctorService;
import static io.github.pitzzahh.util.utilities.SecurityUtil.decrypt;
import io.github.pitzzahh.medicare.backend.patients.model.Symptoms;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import io.github.pitzzahh.medicare.backend.AssignedDoctor;
import org.springframework.jdbc.core.RowMapper;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.ResultSet;

public class DischargedPatientMapper implements RowMapper<Patient> {

    @Override
    public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
        final String[] DATE_CONFINED = decrypt(rs.getString("date_confined")).split("-");
        final String[] DATE_DISCHARGED = decrypt(rs.getString("date_discharged")).split("-");
        return new Patient(
                rs.getInt("id"),
                decrypt(rs.getString("last_name")),
                decrypt(rs.getString("first_name")),
                rs.getString("middle_name").isEmpty() ? "" : decrypt(rs.getString("middle_name")),
                Symptoms.valueOf(decrypt(rs.getString("symptoms"))),
                getDoctorService().getAssignedDoctorById().apply(parseInt(decrypt(rs.getString("doctor_id")))).orElse(new AssignedDoctor()),
                LocalDate.of(parseInt(DATE_CONFINED[0]), parseInt(DATE_CONFINED[1]), parseInt(DATE_CONFINED[2])),
                LocalDate.of(parseInt(DATE_DISCHARGED[0]), parseInt(DATE_DISCHARGED[1]), parseInt(DATE_DISCHARGED[2]))
        );
    }

}
