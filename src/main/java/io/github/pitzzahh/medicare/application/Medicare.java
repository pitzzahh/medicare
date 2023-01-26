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

package io.github.pitzzahh.medicare.application;

import static io.github.pitzzahh.medicare.util.ComponentUtil.getMainProgressBar;
import io.github.pitzzahh.medicare.backend.patients.service.PatientService;
import static io.github.pitzzahh.medicare.backend.db.DatabaseConnection.*;
import io.github.pitzzahh.medicare.backend.doctors.service.DoctorService;
import io.github.pitzzahh.medicare.backend.login.service.AccountService;
import io.github.pitzzahh.medicare.backend.patients.dao.PatientDAOImpl;
import io.github.pitzzahh.medicare.backend.doctors.dao.DoctorDAOImpl;
import io.github.pitzzahh.medicare.backend.login.dao.AccountDAOImp;
import io.github.pitzzahh.medicare.backend.patients.dao.PatientDAO;
import io.github.pitzzahh.medicare.backend.doctors.dao.DoctorDAO;
import io.github.pitzzahh.medicare.backend.db.DatabaseConnection;
import io.github.pitzzahh.medicare.backend.login.dao.AccountDAO;
import static io.github.pitzzahh.medicare.util.WindowUtil.*;
import static java.util.Objects.requireNonNull;
import io.github.pitzzahh.medicare.Launcher;
import static javafx.fxml.FXMLLoader.load;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Medicare extends Application {

    private static final AccountDAO ACCOUNT_DAO = new AccountDAOImp();
    private static final PatientDAO PATIENT_DAO = new PatientDAOImpl();
    private static final DoctorDAO DOCTOR_DAO = new DoctorDAOImpl();
    private static final AccountService ACCOUNT_SERVICE = new AccountService(ACCOUNT_DAO);
    private static final PatientService PATIENT_SERVICE = new PatientService(PATIENT_DAO);
    private static final DoctorService DOCTOR_SERVICE = new DoctorService(DOCTOR_DAO);

    private static final DatabaseConnection DATABASE_CONNECTION = new DatabaseConnection();
    private final Class<Launcher> aClass = Launcher.class;

    @Override
    public void start(Stage primaryStage) throws IOException {
        initParents();
        Parent parent = getParent("auth_window");
        setStage(primaryStage);
        getStage().initStyle(StageStyle.DECORATED);
        getStage().getIcons().add(new Image(requireNonNull(aClass.getResourceAsStream("assets/logo.png"), "Icon not found")));
        loadParent(parent, "Authentication");
        getMainProgressBar(parent).ifPresent(p -> p.setVisible(false));
        getStage().setWidth(1200);
        getStage().setHeight(700);
        getStage().centerOnScreen();
        getStage().show();
    }

    /**
     * Initializes the parents.
     * The window is loaded from the FXML file.
     *
     * @throws IOException if the parent cannot be loaded.
     */
    private void initParents() throws IOException {

        Parent loginPage = load(requireNonNull(aClass.getResource("fxml/auth/auth.fxml"), "Cannot find auth.fxml"));
        Parent mainPanel = load(requireNonNull(aClass.getResource("fxml/mainPanel.fxml"), "Cannot find mainPanel.fxml"));
        Parent dashboard = load(requireNonNull(aClass.getResource("fxml/dashboard.fxml"), "Cannot find dashboard.fxml"));
        Parent patientsPanel = load(requireNonNull(aClass.getResource("fxml/patients/patientsPanel.fxml"), "Cannot find patientsPanel.fxml"));
        Parent addPatient = load(requireNonNull(aClass.getResource("fxml/patients/addPatient.fxml"), "Cannot find addPatient.fxml"));
        Parent cardHolder = load(requireNonNull(aClass.getResource("fxml/cardHolder.fxml"), "Cannot find cardHolder.fxml"));
        Parent doctorsPanel = load(requireNonNull(aClass.getResource("fxml/doctors/doctorsPanel.fxml"), "Cannot find doctorsPanel.fxml"));
        Parent addDoctor = load(requireNonNull(aClass.getResource("fxml/doctors/addDoctor.fxml"), "Cannot find addDoctor.fxml"));
        Parent aboutPanel = load(requireNonNull(aClass.getResource("fxml/about/about.fxml"), "Cannot find about.fxml"));

        Parent patientsDashboard = load(requireNonNull(aClass.getResource("fxml/patientDashboard.fxml"), "Cannot find patientDashboard.fxml"));
        Parent doctorDashboard = load(requireNonNull(aClass.getResource("fxml/doctorDashboard.fxml"), "Cannot find doctorDashboard.fxml"));

        Parent dischargedPatientPanel = load(requireNonNull(aClass.getResource("fxml/discharge/discharge.fxml"), "Cannot find discharge.fxml"));
        Parent statistics = load(requireNonNull(aClass.getResource("fxml/statistics/statistics.fxml"), "Cannot find statistics.fxml"));

        loginPage.setId("auth_window");
        mainPanel.setId("main_panel");
        dashboard.setId("dashboard");
        patientsPanel.setId("patients_panel");
        addPatient.setId("add_patient");
        cardHolder.setId("card_holder");
        doctorsPanel.setId("doctors_panel");
        addDoctor.setId("add_doctor");
        aboutPanel.setId("about");

        patientsDashboard.setId("patient_dashboard");
        doctorDashboard.setId("doctor_dashboard");
        dischargedPatientPanel.setId("discharge_panel");
        statistics.setId("statistics");

        HashMap<String, Parent> parents = new HashMap<>(
                Stream.of(loginPage, mainPanel, dashboard, patientsPanel,
                                addPatient, cardHolder, addDoctor, doctorsPanel,
                                aboutPanel, patientsDashboard, doctorDashboard,
                                dischargedPatientPanel, statistics)
                        .collect(Collectors.toMap(Parent::getId, Function.identity()))
        );

        addParents.accept(parents);
    }

    public static AccountService getAccountService() {
        return ACCOUNT_SERVICE;
    }

    public static PatientService getPatientService() {
        return PATIENT_SERVICE;
    }

    public static DoctorService getDoctorService() {
        return DOCTOR_SERVICE;
    }

    public static void main(String[] args) throws IOException {
        if (doesNotExist()) createDatabaseFile();
        DATABASE_CONNECTION
                .setDriverClassName("org.sqlite.JDBC")
                .setUrl("jdbc:sqlite:".concat(getDatabaseFile().getPath()))
                .setDataSource();
        createTables();
        launch(args);
    }
}
