/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model;

import java.time.LocalDate;
import java.util.HashMap;

public class Hospitalization implements ISerializable {

    private final String id;
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private String reason;
    private RoomType roomType;
    private String observations;
    private HospitalizationStatus status;
    private static int generateID = 0;

    public Hospitalization(Patient patient, Doctor doctor, LocalDate date,
            String reason, RoomType roomType, String observations) {
        this.patient = patient;
        patient.setHospitalization(this);
        this.doctor = doctor;
        doctor.addHospitalization(this);
        this.date = date;
        this.reason = reason;
        this.roomType = roomType;
        this.observations = observations;
        this.status = HospitalizationStatus.REQUESTED;
        this.id = "H-" + patient.getId() + "-" + String.format("%04d", generateID++);
    }

    public Hospitalization(Patient patient, Doctor doctor, LocalDate date,
            String reason, RoomType roomType, String observations,
            HospitalizationStatus initialStatus) {
        this.patient = patient;
        patient.setHospitalization(this);
        this.doctor = doctor;
        doctor.addHospitalization(this);
        this.date = date;
        this.reason = reason;
        this.roomType = roomType;
        this.observations = observations;
        this.status = initialStatus;
        this.id = "H-" + patient.getId() + "-" + String.format("%04d", generateID++);
    }

    public void setStatus(HospitalizationStatus status) {
        this.status = status;
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

    public String getId() {
        return id;
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
        data.put("date", this.date != null ? this.date.toString() : "");
        data.put("reason", this.reason);
        data.put("roomType", this.roomType != null ? this.roomType.name() : "");
        data.put("observations", this.observations);
        data.put("status", this.status != null ? this.status.name() : "");
        return data;
    }
}
