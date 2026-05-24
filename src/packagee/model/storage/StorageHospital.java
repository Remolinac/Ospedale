/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model.storage;

import packagee.model.Doctor;
import packagee.model.Appointment;
import packagee.model.Hospitalization;
import packagee.model.Patient;
import packagee.model.Specialty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import packagee.model.Administrator;
import packagee.util.Observable;

public class StorageHospital extends Observable {

    private StorageAppointment storageAppointment;
    private StorageDoctor storageDoctor;
    private StoragePatient storagePatient;
    private StorageHospitalization storageHospitalization;
    private Administrator admin;

    private static StorageHospital instance;

    private StorageHospital() {
        this.storageAppointment = new StorageAppointment();
        this.storageDoctor = new StorageDoctor();
        this.storagePatient = new StoragePatient();
        this.storageHospitalization = new StorageHospitalization();
    }

    public static StorageHospital getInstance() {
        if (instance == null) {
            instance = new StorageHospital();
        }
        return instance;
    }

    // ── Doctores ──
    public boolean addDoctor(Doctor doctor) {
        boolean result = this.storageDoctor.addDoctor(doctor);
        if (result) {
            notifyObservers("DOCTOR_ADDED");
        }
        return result;
    }

    public Doctor getDoctor(long id) {
        return this.storageDoctor.getDoctor(id);
    }

    public boolean deleteDoctor(long id) {
        return this.storageDoctor.deleteDoctor(id);
    }

    public HashMap<Long, Doctor> getAllDoctors() {
        return this.storageDoctor.getAllDoctors();
    }

    public Doctor getAvailableDoctorBySpecialty(Specialty specialty, LocalDate date) {
        return this.storageDoctor.getAvailableDoctorBySpecialty(specialty, date);
    }

    public boolean existsByUsernameInDoctors(String username) {
        for (Doctor d : this.storageDoctor.getAllDoctors().values()) {
            if (d.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    // ── Pacientes ──
    public boolean addPatient(Patient patient) {
        boolean result = this.storagePatient.addPatient(patient);
        if (result) {
            notifyObservers("PATIENT_ADDED");
        }
        return result;
    }

    public Patient getPatient(long id) {
        return this.storagePatient.getPatient(id);
    }

    public boolean deletePatient(long id) {
        return this.storagePatient.deletePatient(id);
    }

    public HashMap<Long, Patient> getAllPatients() {
        return this.storagePatient.getAll();
    }

    public boolean existsByUsername(String username) {
        return this.storagePatient.existsByUsername(username);
    }

    // ── Citas ──
    public boolean addAppointment(Appointment appointment) {
        boolean result = this.storageAppointment.addAppointment(appointment);
        if (result) {
            notifyObservers("APPOINTMENT_ADDED");
        }
        return result;
    }

    public Appointment getAppointment(String id) {
        return this.storageAppointment.getAddAppointment(id);
    }

    public boolean deleteAppointment(String id) {
        return this.storageAppointment.deleteAppointment(id);
    }

    public HashMap<String, Appointment> getAllAppointments() {
        return this.storageAppointment.getAllappointment();
    }

    public List<Appointment> getSortedAppointmentsForPatient(long patientId) {
        return this.storageAppointment.getSortedAppointmentsForPatient(patientId);
    }

    public List<Appointment> getSortedAppointmentsForDoctor(long doctorId, boolean pendingOnly) {
        return this.storageAppointment.getSortedAppointmentsForDoctor(doctorId, pendingOnly);
    }

    // ── Hospitalizaciones ──
    public boolean addHospitalization(Hospitalization hospitalization) {
        boolean result = this.storageHospitalization.addHospitalization(hospitalization);
        if (result) {
            notifyObservers("HOSPITALIZATION_ADDED");
        }
        return result;
    }

    public Hospitalization getHospitalization(String id) {
        return this.storageHospitalization.getHospitalization(id);
    }

    public boolean deleteHospitalization(String id) {
        return this.storageHospitalization.deleteHospitalization(id);
    }

    public HashMap<String, Hospitalization> getAllHospitalizations() {
        return this.storageHospitalization.getAll();
    }

    // ── Admin ──
    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    public Administrator getAdmin() {
        return this.admin;
    }
}
