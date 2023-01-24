package io.github.pitzzahh.medicare.backend.patients.model;

public enum Symptoms {

    ALLERGIES("Allergies"),
    COLDS_AND_FLU("Colds and Flu"),
    DIARRHEA("Diarrhea"),
    FEVER("Fever"),
    HEADACHES("Headaches"),
    STOMACH_ACHES("Stomach Aches");

    private final String DESC;

    Symptoms(String description) {
        this.DESC = description;
    }

    @Override
    public String toString() {
        return DESC;
    }
}
