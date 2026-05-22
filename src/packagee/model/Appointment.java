/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Appointment implements ISerializable {

    private final String id;
    private Patient patient;
    private Doctor doctor;
    private Specialty specialty;
    private LocalDateTime datetime;
    private String reason;
    private boolean type;
    private ArrayList<Prescription> prescriptions;
    private AppointmentStatus status;
    private String diagnosis;
    private String observations;
    private String recommendedTreatment;
    private String followUp;
    private static int generateID = 0;

    public Appointment(Patient patient, Doctor doctor, Specialty specialty,
            LocalDateTime datetime, String reason, boolean type) {
        this.patient = patient;
        this.patient.addAppointment(this);
        this.doctor = doctor;
        this.doctor.addAppointment(this);
        this.specialty = specialty;
        this.datetime = datetime;
        this.reason = reason;
        this.type = type;
        this.status = AppointmentStatus.REQUESTED;
        this.prescriptions = new ArrayList<>();
        this.id = "A-" + patient.getId() + "-" + String.format("%04d", generateID++);
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public void setRecommendedTreatment(String rt) {
        this.recommendedTreatment = rt;
    }

    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }

    public String getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public String getReason() {
        return reason;
    }

    public boolean isType() {
        return type;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getObservations() {
        return observations;
    }

    public String getRecommendedTreatment() {
        return recommendedTreatment;
    }

    public String getFollowUp() {
        return followUp;
    }

    public boolean addPrescription(Prescription p) {
        return prescriptions.add(p);
    }

    @Override
    public HashMap<String, Object> serialize() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", this.id);
        data.put("patientId", this.patient != null ? this.patient.getId() : null);
        data.put("patientName", this.patient != null
                ? this.patient.getFirstname() + " " + this.patient.getLastname() : "");
        data.put("doctorId", this.doctor != null ? this.doctor.getId() : null);
        data.put("doctorName", this.doctor != null
                ? this.doctor.getFirstname() + " " + this.doctor.getLastname() : "");
        data.put("specialty", this.specialty != null ? this.specialty.name() : "");
        data.put("datetime", this.datetime != null ? this.datetime.toString() : "");
        data.put("reason", this.reason);
        data.put("type", this.type ? "In-person" : "Remote");
        data.put("status", this.status != null ? this.status.name() : "");
        data.put("diagnosis", this.diagnosis);
        data.put("observations", this.observations);
        data.put("recommendedTreatment", this.recommendedTreatment);
        data.put("followUp", this.followUp);
        return data;
    }
}
