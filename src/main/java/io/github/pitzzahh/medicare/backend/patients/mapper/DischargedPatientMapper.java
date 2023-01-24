package io.github.pitzzahh.medicare.backend.patients.mapper;

import io.github.pitzzahh.medicare.backend.patients.model.DischargedPatient;
import static io.github.pitzzahh.util.utilities.SecurityUtil.decrypt;
import org.springframework.jdbc.core.RowMapper;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;

public class DischargedPatientMapper implements RowMapper<DischargedPatient> {

    @Override
    public DischargedPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
        final String[] DATE_CONFINED = decrypt(rs.getString("date_confined")).split("-");
        final String[] DATE_DISCHARGED = decrypt(rs.getString("date_discharged")).split("-");
        return new DischargedPatient(
                Integer.parseInt(decrypt(rs.getString("id"))),
                decrypt(rs.getString("patient_name")),
                decrypt(rs.getString("name_of_doctor")),
                LocalDate.of(parseInt(DATE_CONFINED[0]), parseInt(DATE_CONFINED[1]), parseInt(DATE_CONFINED[2])),
                LocalDate.of(parseInt(DATE_DISCHARGED[0]), parseInt(DATE_DISCHARGED[1]), parseInt(DATE_DISCHARGED[2]))
        );
    }

}
