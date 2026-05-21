/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author edangulo
 */
public class Appointment implements ISerializable{
    
    private final String id;
    private Patient patient;
    private Doctor doctor;
    private Specialty specialty;
    private LocalDateTime datetime;
    private String reason;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getReason() {
        return this.reason;
    }

    private boolean type;
    private ArrayList<Prescription> prescriptions;
    private AppointmentStatus status;
    private String diagnosis;
    private String observations;
    private String recommendedTreatment;
    private String followUp;
    private static int generateID = 0;

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public void setRecommendedTreatment(String recommendedTreatment) {
        this.recommendedTreatment = recommendedTreatment;
    }

    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }

    public Appointment(Patient patient, Doctor doctor, Specialty specialty, LocalDateTime datetime, String reason, boolean type) {
        
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
        
        this.id = "A-" + String.valueOf(patient.getId()) + "-" + String.format("%04d", generateID);
        generateID += 1;
    }
    

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
    

    public String getId() {
        return id;
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

    public boolean isType() {
        return type;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Patient getPatient() {
        return patient;
    }

    public boolean addPrescription(Prescription prescrip) {
        return this.prescriptions.add(prescrip);
    }

    @Override
    public HashMap<String, Object> serialize() {
        HashMap<String, Object> serializedData = new HashMap<>();
        
        serializedData.put("id", this.id);
        serializedData.put("patientId", this.patient != null ? this.patient.getId() : null);
        serializedData.put("doctorId", this.doctor != null ? this.doctor.getId() : null);
        serializedData.put("specialty", this.specialty);
        serializedData.put("datetime", this.datetime);
        serializedData.put("reason", this.reason);
        serializedData.put("type", this.type);
        serializedData.put("status", this.status);
        serializedData.put("prescriptions", this.prescriptions);
        
        return serializedData;
    }
    
}
