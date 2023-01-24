package io.github.pitzzahh.medicare.backend.patients.model;

public enum Symptoms {

    ALLERGIES("Allergies"),
    COLDS_AND_FLU("Colds and Flu"),
    DIARRHEA("Diarrhea"),
    FEVER("Fever"),
    HEADACHES("Headaches"),
    STOMACH_ACHES("Stomach Aches"),
    ASTHMA("Asthma"),
    BACK_PAIN("Back Pain"),
    BLOOD_PRESSURE("Blood Pressure"),
    BLOOD_SUGAR("Blood Sugar"),
    BODY_PAIN("Body Pain"),
    CHEST_PAIN("Chest Pain"),
    CHILLS("Chills"),
    CONSTIPATION("Constipation"),
    COUGH("Cough"),
    DEPRESSION("Depression"),
    DIZZINESS("Dizziness"),
    EAR_PAIN("Ear Pain"),
    FATIGUE("Fatigue"),
    JOINT_PAIN("Joint Pain"),
    LOSS_OF_APPETITE("Loss of Appetite"),
    MUSCLE_PAIN("Muscle Pain"),
    NAUSEA("Nausea"),
    RASH("Rash"),
    SLEEPLESSNESS("Sleeplessness"),
    SNEEZING("Sneezing"),
    SORE_THROAT("Sore Throat"),
    VOMITING("Vomiting"),
    WEIGHT_GAIN("Weight Gain"),
    WEIGHT_LOSS("Weight Loss");

    private final String DESC;

    Symptoms(String description) {
        this.DESC = description;
    }

    @Override
    public String toString() {
        return DESC;
    }
}
