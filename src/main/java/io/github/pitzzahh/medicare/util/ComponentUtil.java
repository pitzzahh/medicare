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

package io.github.pitzzahh.medicare.util;

import static io.github.pitzzahh.medicare.application.Medicare.getPatientService;
import static io.github.pitzzahh.medicare.application.Medicare.getDoctorService;
import io.github.pitzzahh.medicare.controllers.patients.PatientCardController;
import io.github.pitzzahh.medicare.backend.patients.model.DischargedPatient;
import io.github.pitzzahh.medicare.controllers.doctors.DoctorCardController;
import io.github.pitzzahh.medicare.backend.doctors.model.Specialization;
import static io.github.pitzzahh.medicare.util.ToolTipUtil.initToolTip;
import static io.github.pitzzahh.medicare.util.WindowUtil.getParent;
import io.github.pitzzahh.medicare.backend.patients.model.Symptoms;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import static io.github.pitzzahh.medicare.util.Style.normalStyle;
import io.github.pitzzahh.medicare.backend.doctors.model.Doctor;
import static io.github.pitzzahh.util.utilities.Print.printf;
import io.github.pitzzahh.medicare.backend.AssignedDoctor;
import javafx.scene.control.cell.PropertyValueFactory;
import io.github.pitzzahh.medicare.backend.Person;
import io.github.pitzzahh.medicare.backend.Gender;
import static java.util.Objects.requireNonNull;
import io.github.pitzzahh.medicare.Launcher;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import static java.lang.String.format;
import javafx.scene.image.ImageView;
import java.time.format.FormatStyle;
import java.util.stream.Collectors;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.time.LocalDate;
import java.util.Optional;
import java.time.Period;
import java.util.Arrays;

public interface ComponentUtil {

    /**
     * Used to modify the progress bar from the main window.
     *
     * @param parent the main window parent.
     * @return an {@code Optional<ProgressBar>}.
     */
    static Optional<ProgressBar> getMainProgressBar(Parent parent) {
        return Optional.ofNullable((ProgressBar) parent.lookup("#progressBar"));
    }

    static Optional<Label> getLabel(Parent parent, String id) {
        return Optional.ofNullable((Label) parent.lookup(format("#%s", id)));
    }

    static Optional<TableView<?>> getTable(Parent parent, String id) {
        return Optional.ofNullable((TableView<?>) parent.lookup(format("#%s", id)));
    }

    static Optional<ChoiceBox<?>> getChoiceBox(Parent parent, String id) {
        return Optional.ofNullable((ChoiceBox<?>) parent.lookup(format("#%s", id)));
    }

    static Optional<TextField> getTextField(Parent parent, String id) {
        return Optional.ofNullable((TextField) parent.lookup(format("#%s", id)));
    }

    static Optional<DatePicker> getDatePicker(Parent parent, String id) {
        return Optional.ofNullable((DatePicker) parent.lookup(format("#%s", id)));
    }

    static Optional<VBox> getVBox(Parent parent, String id) {
        return Optional.ofNullable((VBox) parent.lookup(format("#%s", id)));
    }

    static Optional<HBox> getHBox(Parent parent, String id) {
        return Optional.ofNullable((HBox) parent.lookup(format("#%s", id)));
    }

    static void initGenderChoiceBox(ChoiceBox<Gender> choiceBox) {
        choiceBox.getItems().addAll(FXCollections.observableArrayList(Arrays.asList(Gender.values())));
        choiceBox.getSelectionModel().selectFirst();
    }

    static void initSymptomsChoiceBox(ChoiceBox<Symptoms> choiceBox) {
        choiceBox.getItems().addAll(FXCollections.observableArrayList(Arrays.asList(Symptoms.values())));
        choiceBox.getSelectionModel().selectFirst();
    }

    static void initAssignedDoctorChoiceBox(ChoiceBox<AssignedDoctor> choiceBox) {
        choiceBox.getItems().clear();
        if (getDoctorService().getDoctors().isEmpty()) choiceBox.setValue(new AssignedDoctor());
        else choiceBox.getItems().addAll(
                getDoctorService()
                        .getDoctors()
                        .values()
                        .stream()
                        .map(d -> new AssignedDoctor(
                                d.getId(),
                                d.getFirstName().concat(" ").concat(d.getLastName()),
                                d.getSpecialization()
                                )
                        )
                        .collect(Collectors.toList())
        );
        choiceBox.getSelectionModel().selectFirst();
    }

    static void initSpecializationChoiceBox(ChoiceBox<Specialization> choiceBox) {
        choiceBox.getItems().addAll(FXCollections.observableArrayList(Arrays.asList(Specialization.values())));
        choiceBox.getSelectionModel().selectFirst();
    }

    static void resetInputs(
            TextField lastName,
            TextField firstName,
            TextField middleName,
            TextField address,
            TextField phoneNumber,
            ChoiceBox<Gender> gender,
            ChoiceBox<AssignedDoctor> doctor,
            DatePicker birthDate
    ) {
        lastName.clear();
        firstName.clear();
        middleName.clear();
        address.clear();
        phoneNumber.clear();
        gender.getSelectionModel().selectFirst();
        if (doctor != null) doctor.getSelectionModel().selectFirst();
        birthDate.setValue(null);
    }



    static void initPatientCards(VBox cardStorage) {
        cardStorage.getChildren().clear();
        getPatientService().getPatients()
                .values()
                .forEach(patient -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(requireNonNull(Launcher.class.getResource("fxml/patients/patientCard.fxml"), "Cannot find patientCard.fxml"));
                        HBox patientCard = fxmlLoader.load();
                        PatientCardController controller = fxmlLoader.getController();
                        controller.setData(patient);

                        controller.updateOrSaveButton.setOnAction(event ->{
                            event.consume();
                            if (controller.updateOrSaveButton.getText().equals("UPDATE")) {
                                allowPatientInput(controller);
                                controller.gender
                                        .getSelectionModel()
                                        .select(patient.getGender().ordinal());
                                controller.doctor
                                        .getSelectionModel()
                                        .select(patient.getAssignDoctor());
                                controller.symptoms
                                        .getSelectionModel()
                                        .select(patient.getSymptoms().ordinal());
                            }
                            else {
                                getPatientService()
                                        .getPatients()
                                        .values()
                                        .stream()
                                        .filter(p -> p.getPatientId() == patient.getPatientId())
                                        .findAny()
                                        .ifPresent(p -> updatePatient(patient, controller));
                                initPatientCards(cardStorage);
                            }
                        });

                        controller.dischargeButton.setOnAction(actionEvent -> {
                            actionEvent.consume();
                            if (controller.dischargeButton.getText().equals("CANCEL")) {
                                System.out.println("CANCELING");
                                controller.updateOrSaveButton.setText("UPDATE");
                                controller.dischargeButton.setText("DISCHARGE");
                            } else {
                                System.out.println("DISCHARGING");
                                cardStorage.getChildren().remove(patientCard);
                                getPatientService()
                                        .dischargePatientById()
                                        .accept(patient.getPatientId());

                                final String PATIENT_NAME = patient.getMiddleName().isEmpty() ?
                                        patient.getFirstName()
                                                .concat(" ")
                                                .concat(patient.getLastName()) :
                                        patient.getFirstName()
                                                .concat(" ")
                                                .concat(patient.getMiddleName())
                                                .concat(" ")
                                                .concat(patient.getLastName());
                                getPatientService()
                                        .addDischargedPatient()
                                        .accept(
                                                new DischargedPatient(
                                                        patient.getPatientId(),
                                                        PATIENT_NAME,
                                                        patient.getAssignDoctor().getName(),
                                                        patient.getDateConfined(),
                                                        LocalDate.now()
                                                )
                                        );
                                setDashBoardData();
                                setCommonDashboardData("patient_dashboard", "patientsCount", false);
                            }
                            initPatientCards(cardStorage);
                        });
                        cardStorage.getChildren().add(patientCard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    static void initDoctorCards(VBox cardStorage) {
        cardStorage.getChildren().clear();
        getDoctorService().getDoctors()
                .values()
                .forEach(doctor -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(requireNonNull(Launcher.class.getResource("fxml/doctors/doctorCard.fxml"), "Cannot find doctorCard.fxml"));
                        HBox doctorCard = fxmlLoader.load();
                        DoctorCardController controller = fxmlLoader.getController();
                        controller.setData(doctor);

                        controller.updateOrSaveButton.setOnAction(event ->{
                            event.consume();
                            if (controller.updateOrSaveButton.getText().equals("UPDATE")) {
                                allowDoctorInput(controller);
                                controller.gender
                                        .getSelectionModel()
                                        .select(doctor.getGender().ordinal());
                                controller.specialization
                                        .getSelectionModel()
                                        .select(doctor.getSpecialization().ordinal());
                            }
                            else {
                                getDoctorService()
                                        .getDoctors()
                                        .values()
                                        .stream()
                                        .filter(p -> p.getId() == doctor.getId())
                                        .findAny()
                                        .ifPresent(p -> updateDoctor(doctor, controller));
                                initDoctorCards(cardStorage);
                            }
                        });

                        controller.removeButton.setOnAction(actionEvent -> {
                            actionEvent.consume();
                            if (controller.removeButton.getText().equals("CANCEL")) {
                                System.out.println("CANCELING");
                                controller.updateOrSaveButton.setText("UPDATE");
                                controller.removeButton.setText("REMOVE");
                            } else {
                                System.out.println("REMOVING");
                                cardStorage.getChildren().remove(doctorCard);
                                getDoctorService().removeDoctorById().accept(doctor.getId());
                                setDashBoardData();
                                setCommonDashboardData("doctor_dashboard", "doctorsCount", false);
                            }
                            initDoctorCards(cardStorage);
                        });
                        cardStorage.getChildren().add(doctorCard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private static void allowPatientInput(PatientCardController controller) {
        initGenderChoiceBox(controller.gender);
        initAssignedDoctorChoiceBox(controller.doctor);
        initSymptomsChoiceBox(controller.symptoms);
        allowCommonInputs(controller.firstName, controller.middleName, controller.lastName, controller.gender, controller.dateOfBirth, controller.address, controller.phoneNumber);
        controller.doctor.setMouseTransparent(false); // EDITABLE
        controller.symptoms.setMouseTransparent(false); // EDITABLE
        controller.updateOrSaveButton.setText("SAVE");
        controller.dischargeButton.setText("CANCEL");
        controller.updateOrSaveButton.setTooltip(initToolTip("Click to Save Doctor", normalStyle()));
        controller.dischargeButton.setTooltip(initToolTip("Click to Cancel Edit", normalStyle()));
    }

    static void allowCommonInputs(TextField firstName, TextField middleName, TextField lastName, ChoiceBox<Gender> gender, DatePicker dateOfBirth, TextField address, TextField phoneNumber) {
        firstName.setEditable(true);
        middleName.setEditable(true);
        lastName.setEditable(true);
        gender.setMouseTransparent(false); // EDITABLE
        dateOfBirth.setEditable(true);
        dateOfBirth.setMouseTransparent(false);
        address.setEditable(true);
        phoneNumber.setEditable(true);
    }

    private static void allowDoctorInput(DoctorCardController controller) {
        initGenderChoiceBox(controller.gender);
        initSpecializationChoiceBox(controller.specialization);
        allowCommonInputs(controller.firstName, controller.middleName, controller.lastName, controller.gender, controller.dateOfBirth, controller.address, controller.phoneNumber);
        controller.specialization.setMouseTransparent(false); // EDITABLE
        controller.updateOrSaveButton.setText("SAVE");
        controller.removeButton.setText("CANCEL");
        controller.updateOrSaveButton.setTooltip(initToolTip("Click to Save Doctor", normalStyle()));
        controller.removeButton.setTooltip(initToolTip("Click to Cancel Edit", normalStyle()));
    }


    private static void updatePatient(Patient patient, PatientCardController controller) {

        if (requiredInput(
                controller.firstName,
                controller.lastName,
                controller.address,
                controller.dateOfBirth
        )) return;

        controller.updateOrSaveButton.setText("UPDATE");
        controller.dischargeButton.setText("REMOVE");
        controller.updateOrSaveButton.setTooltip(initToolTip("Click to Update Patient", normalStyle()));
        controller.dischargeButton.setTooltip(initToolTip("Click to Discharge Patient", normalStyle()));

        getPatientService()
                .updatePatientById()
                .accept(patient.getPatientId(),
                        new Patient(
                                controller.lastName.getText().trim(),
                                controller.firstName.getText().trim(),
                                controller.middleName.getText().trim().isEmpty() ? "" : controller.middleName.getText().trim(),
                                controller.gender.getValue(),
                                controller.dateOfBirth.getValue(),
                                controller.address.getText().trim(),
                                controller.phoneNumber.getText().trim(),
                                controller.doctor.getValue() == null ? new AssignedDoctor() : controller.doctor.getValue(),
                                patient.getDateConfined(),
                                controller.symptoms.getValue()
                        )
                );

        Alert alert = initAlert("Patient Updated", "Patient Updated", "Patient Updated Successfully");
        showAlertInfo("assets/success.png", "Success graphic not found", alert);
    }

    private static void updateDoctor(Doctor doctor, DoctorCardController controller) {
        if (requiredInput(
                controller.firstName,
                controller.lastName,
                controller.address,
                controller.dateOfBirth
        )) return;

        controller.updateOrSaveButton.setText("UPDATE");
        controller.removeButton.setText("REMOVE");
        controller.updateOrSaveButton.setTooltip(initToolTip("Click to Update Doctor", normalStyle()));
        controller.removeButton.setTooltip(initToolTip("Click to Remove Doctor", normalStyle()));
        getDoctorService()
                .updateDoctorById()
                .accept(doctor.getId(),
                        new Doctor(
                                controller.lastName.getText().trim(),
                                controller.firstName.getText().trim(),
                                controller.middleName.getText().trim().isEmpty() ? "" : controller.middleName.getText().trim(),
                                controller.gender.getValue(),
                                controller.dateOfBirth.getValue(),
                                controller.address.getText().trim(),
                                controller.phoneNumber.getText().trim(),
                                controller.specialization.getValue()
                        )
                );

        Optional<Patient> patientWithDoctorBeingUpdated = getPatientService() // TODO: ALso update patient with doctor being updated
                .getPatients()
                .values()
                .stream()
                .filter(d -> d.getAssignDoctor().getId() == doctor.getId())
                .findAny();

        patientWithDoctorBeingUpdated
                .ifPresent(patient -> patient.setAssignDoctor(new AssignedDoctor(
                doctor.getId(),
                doctor.getMiddleName().isEmpty() ? doctor.getFirstName().concat(" ").concat(doctor.getLastName()) :
                        doctor.getFirstName().concat(" ").concat(doctor.getMiddleName()).concat(" ").concat(doctor.getLastName()),
                doctor.getSpecialization())
        ));

        patientWithDoctorBeingUpdated.ifPresent(patient -> getPatientService().updatePatientById().accept(patient.getPatientId(), patient));

        Alert alert = initAlert("Doctor Updated", "Doctor Updated", "Doctor Updated Successfully");
        showAlertInfo("assets/success.png", "Success graphic not found", alert);
    }

    static boolean requiredInput(
            TextField firstName,
            TextField lastName,
            TextField address,
            DatePicker birthDate
    ) {
        if (firstName.getText().trim().isEmpty()) {
            Alert alert = initAlert("First Name is Required", "First Name is Required", "First name is required");
            showAlertInfo("assets/error.png", "Error graphic not found", alert);
            return true;
        }

        if (lastName.getText().trim().isEmpty()) {
            Alert alert = initAlert("Last Name is Required", "Last Name is Required", "Last name is required");
            showAlertInfo("assets/error.png", "Error graphic not found", alert);
            return true;
        }

        if (birthDate.getValue() == null) {
            Alert alert = initAlert("Birth Date is Required", "Birth Date is Required", "Birth date is required");
            showAlertInfo("assets/error.png", "Error graphic not found", alert);
            return true;
        }

        if (address.getText().trim().isEmpty()) {
            Alert alert = initAlert("Address is Required", "Address is Required", "Address is required");
            showAlertInfo("assets/error.png", "Error graphic not found", alert);
            return true;
        }

        return false;
    }

    static void setCommonData(Person person,
                              TextField firstName,
                              TextField middleName,
                              TextField lastName,
                              TextField age,
                              ChoiceBox<Gender> gender,
                              DatePicker dateOfBirth,
                              TextField address,
                              TextField phoneNumber
    ) {
        firstName.setText(person.getFirstName());
        middleName.setText(person.getMiddleName().isEmpty() ? "" : person.getMiddleName());
        lastName.setText(person.getLastName());
        age.setText(String.valueOf(Period.between(person.getBirthDate(), LocalDate.now()).getYears()));
        gender.setValue(person.getGender());
        String dateString = person.getBirthDate().format(getDateFormatter());
        dateOfBirth.getEditor().setText(dateString);
        address.setText(person.getAddress());
        phoneNumber.setText(person.getPhoneNumber());
    }

    static void setDashBoardData() {
        int patientCount = getPatientService().getPatients().size();
        getLabel(getParent("dashboard"), "patientsCount")
                .ifPresentOrElse(l -> l.setText(String.valueOf(patientCount)), () -> printf("Label [%s] not found", "patientsCount"));

        int doctorsCount = getDoctorService().getDoctors().size();
        getLabel(getParent("dashboard"), "doctorsCount")
                .ifPresentOrElse(l -> l.setText(String.valueOf(doctorsCount)), () -> printf("Label [%s] not found", "doctorsCount"));
    }

    static void setCommonDashboardData(String dashboardName, String countName, boolean isPatient) {
        int count = isPatient ? getPatientService().getPatients().size() : getDoctorService().getDoctors().size();
        getLabel(getParent(dashboardName), countName)
                .ifPresentOrElse(l -> l.setText(String.valueOf(count)), () ->  printf("Label [%s] not found", countName));
    }

    static Alert initAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(requireNonNull(Launcher.class.getResource("css/alert.css"), "Cannot find alert.css").toExternalForm());
        dialogPane.setId("alert-dialog");
        return alert;
    }

    static void showAlertInfo(String name, String errorMessage, Alert alert) {
        ImageView graphic = new ImageView(new Image(requireNonNull(Launcher.class.getResourceAsStream(name), errorMessage)));
        graphic.setFitWidth(50);
        graphic.setFitHeight(50);
        alert.setGraphic(graphic);
        alert.showAndWait();
    }

    static void initTableColumns(TableView<?> table, String[] columns) {
        for (int i = 0; i < columns.length; i++) {
            TableColumn<?, ?> column = table.getColumns().get(i);
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(columns[i]));
        }
    }

    static DateTimeFormatter getDateFormatter() {
        return ComponentUtilFields.formatter;
    }
}

class ComponentUtilFields {
    static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
}
