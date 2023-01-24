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
import io.github.pitzzahh.medicare.controllers.doctors.DoctorCardController;
import io.github.pitzzahh.medicare.backend.doctors.model.Specialization;
import static io.github.pitzzahh.medicare.util.WindowUtil.getParent;
import io.github.pitzzahh.medicare.backend.patients.model.Symptoms;
import io.github.pitzzahh.medicare.backend.AssignedDoctor;
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

    static Optional<ChoiceBox<?>> getChoiceBox(Parent parent, String id) {
        return Optional.ofNullable((ChoiceBox<?>) parent.lookup(format("#%s", id)));
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
        choiceBox.getItems().addAll(
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
                        PatientCardController patientCardController = fxmlLoader.getController();
                        patientCardController.setData(patient);

                        patientCardController.removeButton.setOnAction(actionEvent -> {
                            actionEvent.consume();
                            cardStorage.getChildren().remove(patientCard);
                            getPatientService().removePatientById().accept(patient.getPatientId());
                            setDashBoardData();
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
                        DoctorCardController doctorCardController = fxmlLoader.getController();
                        doctorCardController.setData(doctor);

                        doctorCardController.removeButton.setOnAction(actionEvent -> {
                            actionEvent.consume();
                            cardStorage.getChildren().remove(doctorCard);
                            getDoctorService().removeDoctorById().accept(doctor.getId());
                            setDashBoardData();
                        });

                        cardStorage.getChildren().add(doctorCard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }


    static boolean requiredInput(
            TextField firstName,
            TextField lastName,
            TextField address,
            DatePicker birthDate
    ) {
        if (firstName.getText().trim().isEmpty()) {
            Alert alert = initAlert("First Name is Required", "First Name is Required", "First name is required");
            showAlertInfo("assets/success.png", "Success graphic not found", alert);
            return true;
        }

        if (lastName.getText().trim().isEmpty()) {
            Alert alert = initAlert("Last Name is Required", "Last Name is Required", "Last name is required");
            showAlertInfo("assets/success.png", "Success graphic not found", alert);
            return true;
        }

        if (birthDate.getValue() == null) {
            Alert alert = initAlert("Birth Date is Required", "Birth Date is Required", "Birth date is required");
            showAlertInfo("assets/success.png", "Success graphic not found", alert);
            return true;
        }

        if (address.getText().trim().isEmpty()) {
            Alert alert = initAlert("Address is Required", "Address is Required", "Address is required");
            showAlertInfo("assets/success.png", "Success graphic not found", alert);
            return true;
        }

        return false;
    }

    static void setCommonData(Person person,
                              TextField name,
                              TextField age,
                              ChoiceBox<Gender> gender,
                              DatePicker dateOfBirth,
                              TextField address,
                              TextField phoneNumber
    ) {
        name.setText(person.getFirstName().concat(" ").concat(person.getLastName()));
        age.setText(String.valueOf(Period.between(person.getBirthDate(), LocalDate.now()).getYears()));
        gender.setValue(person.getGender());
        String dateString = person.getBirthDate().format(getDateFormatter());
        dateOfBirth.getEditor().setText(dateString);
        address.setText(person.getAddress());
        phoneNumber.setText(person.getPhoneNumber());
    }

    static void setDashBoardData() {
        int patientCount = getPatientService().getPatients().size();
        Optional<Label> patientsCountLabel = getLabel(getParent("dashboard"), "patientsCount");
        patientsCountLabel.ifPresent(l -> l.setText(String.valueOf(patientCount)));

        int doctorsCount = getDoctorService().getDoctors().size();
        Optional<Label> doctorsCountLabel = getLabel(getParent("dashboard"), "doctorsCount");
        doctorsCountLabel.ifPresent(l -> l.setText(String.valueOf(doctorsCount)));
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

    static DateTimeFormatter getDateFormatter() {
        return ComponentUtilFields.formatter;
    }
}

class ComponentUtilFields {
    static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
}
