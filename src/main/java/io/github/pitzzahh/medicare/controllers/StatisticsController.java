package io.github.pitzzahh.medicare.controllers;

import io.github.pitzzahh.medicare.backend.patients.model.Symptoms;
import io.github.pitzzahh.medicare.backend.patients.model.Patient;
import javafx.scene.chart.LineChart;
import java.util.stream.Collectors;
import javafx.scene.chart.XYChart;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.net.URL;

public class StatisticsController implements Initializable {

    @FXML
    private LineChart<String, Integer> january,
    february,
    march,
    april,
    may,
    june,
    july,
    august,
    september,
    october,
    november,
    december;

    public static LineChart<String, Integer> januaryCopy,
            februaryCopy,
            marchCopy,
            aprilCopy,
            mayCopy,
            juneCopy,
            julyCopy,
            augustCopy,
            septemberCopy,
            octoberCopy,
            novemberCopy,
            decemberCopy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        januaryCopy = january;
        februaryCopy = february;
        marchCopy = march;
        aprilCopy = april;
        mayCopy = may;
        juneCopy = june;
        julyCopy = july;
        augustCopy = august;
        septemberCopy = september;
        octoberCopy = october;
        novemberCopy = november;
        decemberCopy = december;
    }

    public static void setMonthData(List<Patient> patients, Month month) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        Map<Symptoms, List<Patient>> collect = patients.stream()
                .filter(p -> p.getDateConfined().getMonth().equals(month))
                .collect(Collectors.groupingBy(Patient::getSymptoms));

        collect.forEach((key, value) -> series.getData().add(new XYChart.Data<>(key.name(), value.size())));

        if (month == Month.JANUARY) januaryCopy.getData().add(series);
        else if (month == Month.FEBRUARY) februaryCopy.getData().add(series);
        else if (month == Month.MARCH) marchCopy.getData().add(series);
        else if (month == Month.APRIL) aprilCopy.getData().add(series);
        else if (month == Month.MAY) mayCopy.getData().add(series);
        else if (month == Month.JUNE) juneCopy.getData().add(series);
        else if (month == Month.JULY) julyCopy.getData().add(series);
        else if (month == Month.AUGUST) augustCopy.getData().add(series);
        else if (month == Month.SEPTEMBER) septemberCopy.getData().add(series);
        else if (month == Month.OCTOBER) octoberCopy.getData().add(series);
        else if (month == Month.NOVEMBER) novemberCopy.getData().add(series);
        else if (month == Month.DECEMBER) decemberCopy.getData().add(series);
        else throw new IllegalStateException("Invalid Month: " + month);
    }
}
