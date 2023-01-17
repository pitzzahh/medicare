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

import static io.github.pitzzahh.medicare.controllers.CardHolderController.getCardStorage;
import static io.github.pitzzahh.medicare.application.Medicare.getPatientService;
import static io.github.pitzzahh.medicare.application.Medicare.getDoctorService;
import io.github.pitzzahh.medicare.controllers.patients.PatientCardController;
import io.github.pitzzahh.medicare.controllers.doctors.DoctorCardController;
import io.github.pitzzahh.medicare.backend.doctors.model.Specialization;
import static io.github.pitzzahh.medicare.util.WindowUtil.getParent;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import io.github.pitzzahh.medicare.backend.doctors.model.Doctor;
import io.github.pitzzahh.medicare.backend.Person;
import io.github.pitzzahh.medicare.backend.Gender;
import static java.util.Objects.requireNonNull;
import io.github.pitzzahh.medicare.Launcher;
import java.time.format.DateTimeFormatter;
import javafx.collections.ObservableList;
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

    static Optional<TextField> getTextField(Parent parent, String id) {
        return Optional.ofNullable((TextField) parent.lookup(format("#%s", id)));
    }

    static Optional<DatePicker> getDatePicker(Parent parent, String id) {
        return Optional.ofNullable((DatePicker) parent.lookup(format("#%s", id)));
    }

    @SuppressWarnings("unchecked")
    static Optional<ChoiceBox<Object>> getChoiceBox(Parent parent, String id) {
        return Optional.ofNullable((ChoiceBox<Object>) parent.lookup(format("#%s", id)));
    }

    static Optional<Label> getLabel(Parent parent, String id) {
        return Optional.ofNullable((Label) parent.lookup(format("#%s", id)));
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

                        patientCardController.updateButton.setOnAction(actionEvent -> {
                            actionEvent.consume();
                            getPatientService()
                                    .getPatients()
                                    .values()
                                    .stream()
                                    .filter(p -> p.getPatientId() == patient.getPatientId())
                                    .findAny()
                                    .ifPresent(patientObject -> updatePatient(patientObject, patientCard));
                        });
                        cardStorage.getChildren().add(patientCard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    static void initDoctorCards(VBox cardStorage) {
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

                        doctorCardController.updateButton.setOnAction(actionEvent -> {
                            actionEvent.consume();
                            getDoctorService()
                                    .getDoctors()
                                    .values()
                                    .stream()
                                    .filter(p -> p.getId() == doctor.getId())
                                    .findAny()
                                    .ifPresent(doctorObject -> updateDoctor(doctorObject, doctorCard));
                        });
                        cardStorage.getChildren().add(doctorCard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    static void updateDoctor(Doctor doctor, HBox parent) {
        System.out.println("UPDATING DOCTOR");
        changeCommonInputsState(parent);
        getChoiceBox(parent, "specialization").ifPresent(choiceBox -> choiceBox.setMouseTransparent(false));
        update(null, doctor, parent, false);
    }

    static void updatePatient(Patient patient, HBox parent) {
        System.out.println("UPDATING PATIENT");
        changeCommonInputsState(parent);
        getTextField(parent, "symptoms").ifPresent(textField -> textField.setEditable(true));
        update(patient, null, parent, true);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static void update(Patient patient, Doctor doctor, HBox parent, boolean isPatient) {
        Optional<VBox> buttonsParent = getVBox(parent, "buttonsParent");
        if (buttonsParent.isPresent()) {
            Optional<HBox> updateButtonBox = getHBox(buttonsParent.get(), "updateButtonBox");

            Button saveButton = new Button();
            saveButton.setMinWidth(150);
            saveButton.setMinHeight(30);
            saveButton.setText("SAVE");
            saveButton.setStyle("-fx-background-color: #E0FBFC;\n" +
                    "    -fx-background-radius: 5px;\n" +
                    "    -fx-text-fill: #000;\n" +
                    "    -fx-font-size: 16px;\n" +
                    "    -fx-font-weight: bold;");

            Optional<Button> updateButton = updateButtonBox
                    .map(HBox::getChildren)
                    .stream()
                    .map(e -> (Button) e.get(0))
                    .findAny();

            updateButtonBox
                    .map(HBox::getChildren)
                    .ifPresent(ObservableList::clear);

            saveButton.setOnAction(actionEvent -> {
                actionEvent.consume();

                updateButtonBox
                        .map(HBox::getChildren)
                        .ifPresent(ObservableList::clear);
                updateButtonBox
                        .map(HBox::getChildren)
                        .ifPresent(box -> box.add(updateButton.orElseThrow(() -> new IllegalStateException("Update button Parent is not present"))));

                TextField nameTextField = getTextField(parent, "name").get();

                final String[] name = nameTextField.getText().split(" ");
                Gender gender = (Gender) getChoiceBox(parent, "gender").get().getValue();
                LocalDate dateOfBirth = getDatePicker(parent, "dateOfBirth").get().getValue();
                String address = getTextField(parent, "address").get().getText();
                String phoneNumber = getTextField(parent, "phoneNumber").get().getText();

                System.out.println("name = " + Arrays.toString(name));
                System.out.println("gender = " + gender);
                System.out.println("dateOfBirth = " + dateOfBirth);
                System.out.println("address = " + address);
                System.out.println("phoneNumber = " + phoneNumber);

                if (isPatient) {
                    getPatientService().updatePatientById().accept(patient.getPatientId(),
                            new Patient(
                                    name.length == 4 ? name[3] : name.length == 3 ? name[2] : name.length == 2 ? name[1] : name[0],
                                    name.length >= 4 ? name[0].concat(" ").concat(name[1]) : name[0],
                                    name.length == 4 ? name[2] : name.length == 3 ? name[1] : "",
                                    gender,
                                    dateOfBirth == null ? patient.getBirthDate() : dateOfBirth,
                                    address,
                                    phoneNumber,
                                    getTextField(parent, "symptoms").get().getText()
                            )
                    );

                    Alert alert = showAlert("Patient Updated", "Patient Updated", "Patient has been updated successfully");
                    showAlertInfo("assets/success.png", "Success graphic not found", alert);

                } else {

                    getDoctorService().updateDoctorById().accept(doctor.getId(),
                            new Doctor(
                                    name.length == 4 ? name[3] : name.length == 3 ? name[2] : name.length == 2 ? name[1] : name[0],
                                    name.length >= 4 ? name[0].concat(" ").concat(name[1]) : name[0],
                                    name.length == 4 ? name[2] : name.length == 3 ? name[1] : "",
                                    gender,
                                    dateOfBirth == null ? patient.getBirthDate() : dateOfBirth,
                                    address,
                                    phoneNumber,
                                    (Specialization) getChoiceBox(parent, "specialization").get().getValue()
                            )
                    );
                    Alert alert = showAlert("Doctor Updated", "Doctor Updated", "Doctor has been updated successfully");
                    showAlertInfo("assets/success.png", "Success graphic not found", alert);
                }
                initDoctorCards(getCardStorage());
                initPatientCards(getCardStorage());
            });

            updateButtonBox
                    .map(HBox::getChildren)
                    .ifPresent(hBox -> hBox.add(saveButton));
            setDashBoardData();
        }
    }

    private static void changeCommonInputsState(HBox parent) {
        getTextField(parent, "name").ifPresent(textField -> textField.setEditable(true));
        getChoiceBox(parent, "gender").ifPresent(choiceBox -> choiceBox.setMouseTransparent(false));
        getDatePicker(parent, "dateOfBirth").ifPresent(datePicker -> {
            datePicker.setEditable(true);
            datePicker.setMouseTransparent(false);
        });
        getTextField(parent, "address").ifPresent(textField -> textField.setEditable(true));
        getTextField(parent, "phoneNumber").ifPresent(textField -> textField.setEditable(true));
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
