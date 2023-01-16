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
import io.github.pitzzahh.medicare.backend.Person;
import io.github.pitzzahh.medicare.backend.Gender;
import static java.util.Objects.requireNonNull;
import io.github.pitzzahh.medicare.Launcher;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import static java.lang.String.format;
import java.time.format.FormatStyle;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
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

    /**
     * Used to modify the message label from the main window.
     *
     * @param parent the main window parent.
     * @return an {@code Optional<Label>}.
     */
    static Optional<Label> getLabel(Parent parent, String id) {
        return Optional.ofNullable((Label) parent.lookup(format("#%s", id)));
    }

    static void initGenderChoiceBox(ChoiceBox<Gender> choiceBox) {
        choiceBox.getItems().addAll(FXCollections.observableArrayList(Arrays.asList(Gender.values())));
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
            DatePicker birthDate
    ) {
        lastName.clear();
        firstName.clear();
        middleName.clear();
        address.clear();
        phoneNumber.clear();
        gender.getSelectionModel().selectFirst();
        birthDate.setValue(null);
    }

    static Alert showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(requireNonNull(Launcher.class.getResource("css/alert.css"), "Cannot find alert.css").toExternalForm());
        dialogPane.setId("alert-dialog");
        return alert;
    }

    static void initPatientCards(VBox cardStorage) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(requireNonNull(Launcher.class.getResource("fxml/patients/patientCard.fxml"), "Cannot find patientCard.fxml"));
        cardStorage.getChildren().clear();
        getPatientService().getPatients()
                .values()
                .forEach(patient -> {
                    try {
                        HBox patientCard = fxmlLoader.load();
                        PatientCardController patientCardController = fxmlLoader.getController();
                        patientCardController.setData(patient);

                        patientCardController.removeButton.setOnAction(actionEvent -> {
                            actionEvent.consume();
                            getPatientService().getPatients().remove(patient.getPatientId());
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

    static void initDoctorCards(VBox cardStorage) { // TODO: test
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(requireNonNull(Launcher.class.getResource("fxml/doctors/doctorCard.fxml"), "Cannot find doctorCard.fxml"));
        cardStorage.getChildren().clear();
        getDoctorService().getDoctors()
                .values()
                .forEach(doctor -> {
                    try {
                        HBox doctorCard = fxmlLoader.load();
                        DoctorCardController doctorCardController = fxmlLoader.getController();
                        doctorCardController.setData(doctor);
                        doctorCardController.removeButton.setOnAction(actionEvent -> {
                            actionEvent.consume();
                            getDoctorService().getDoctors().remove(doctor.getId());
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
            DatePicker birthDate,
            TextArea symptoms,
            boolean isAddPatient
    ) {
        if (firstName.getText().trim().isEmpty()) {
            Alert alert = showAlert("First Name is Required", "First Name is Required", "First name is required");
            ImageView graphic = new ImageView(new Image(requireNonNull(Launcher.class.getResourceAsStream("assets/error.png"), "Error graphic not found")));
            graphic.setFitWidth(50);
            graphic.setFitHeight(50);
            alert.setGraphic(graphic);
            alert.showAndWait();
            return true;
        }

        if (lastName.getText().trim().isEmpty()) {
            Alert alert = showAlert("Last Name is Required", "Last Name is Required", "Last name is required");
            ImageView graphic = new ImageView(new Image(requireNonNull(Launcher.class.getResourceAsStream("assets/error.png"), "Error graphic not found")));
            graphic.setFitWidth(50);
            graphic.setFitHeight(50);
            alert.setGraphic(graphic);
            alert.showAndWait();
            return true;
        }

        if (birthDate.getValue() == null) {
            Alert alert = showAlert("Birth Date is Required", "Birth Date is Required", "Birth date is required");
            ImageView graphic = new ImageView(new Image(requireNonNull(Launcher.class.getResourceAsStream("assets/error.png"), "Error graphic not found")));
            graphic.setFitWidth(50);
            graphic.setFitHeight(50);
            alert.setGraphic(graphic);
            alert.showAndWait();
            return true;
        }

        if (address.getText().trim().isEmpty()) {
            Alert alert = showAlert("Address is Required", "Address is Required", "Address is required");
            ImageView graphic = new ImageView(new Image(requireNonNull(Launcher.class.getResourceAsStream("assets/error.png"), "Error graphic not found")));
            graphic.setFitWidth(50);
            graphic.setFitHeight(50);
            alert.setGraphic(graphic);
            alert.showAndWait();
            return true;
        }

        if (isAddPatient) {
            if (symptoms.getText().trim().isEmpty()) {
                Alert alert = showAlert("Symptoms is Required", "Symptoms is Required", "Symptoms is required");
                ImageView graphic = new ImageView(new Image(requireNonNull(Launcher.class.getResourceAsStream("assets/error.png"), "Error graphic not found")));
                graphic.setFitWidth(50);
                graphic.setFitHeight(50);
                alert.setGraphic(graphic);
                alert.showAndWait();
                return true;
            }
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

    static DateTimeFormatter getDateFormatter() {
        return ComponentUtilFields.formatter;
    }
}

class ComponentUtilFields {
    static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
}
