/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model;

import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 * @author edangulo
 */
public class Hospitalization implements ISerializable  {
    
    private final String id;
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private String reason;
    private RoomType roomType;
    private String observations;
    private HospitalizationStatus status;
    private static int generateID = 0;

    public void setStatus(HospitalizationStatus status) {
        this.status = status;
    }

    public Hospitalization(Patient patient, Doctor doctor, LocalDate date, String reason, RoomType roomType, String observations) {
        this.patient = patient;
        patient.setHospitalization(this);
        this.doctor = doctor;
        doctor.addHospitalization(this);
        this.date = date;
        this.reason = reason;
        this.roomType = roomType;
        this.observations = observations;
        this.status = HospitalizationStatus.REQUESTED;
        this.id = "H-" + String.valueOf(patient.getId()) + "-" + String.format("%04d", generateID);
        generateID += 1;
    }
    public Hospitalization(Patient patient, Doctor doctor, LocalDate date, String reason, RoomType roomType, String observations, HospitalizationStatus hopsS) {
        this.patient = patient;
        patient.setHospitalization(this);
        this.doctor = doctor;
        doctor.addHospitalization(this);
        this.date = date;
        this.reason = reason;
        this.roomType = roomType;
        this.observations = observations;
        this.status = hopsS;
        this.id = "H-" + String.valueOf(patient.getId()) + "-" + String.format("%04d", generateID);
        generateID += 1;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public String getObservations() {
        return observations;
    }

    public HospitalizationStatus getStatus() {
        return status;
    }
    
    public String getId() {
        return id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
    
    

    @Override
    public HashMap<String, Object> serialize() {
        HashMap<String, Object> serializedData = new HashMap<>();
        
        serializedData.put("id", this.id);
        serializedData.put("patientId", this.patient != null ? this.patient.getId() : null);
        serializedData.put("doctorId", this.doctor != null ? this.doctor.getId() : null);
        serializedData.put("date", this.date);
        serializedData.put("reason", this.reason);
        serializedData.put("roomType", this.roomType);
        serializedData.put("observations", this.observations);
        serializedData.put("status", this.status);
        
        return serializedData;
    }
    
    
    
    
}
