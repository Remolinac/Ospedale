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
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author sierr
 */
public class StorageHospital {
    private StorageAppointment storageAppointment;
    private StorageDoctor storageDoctor;
    private StoragePatient storagePatient;
    private StorageHospitalization storageHospitalization;
    
    private static  StorageHospital instance;

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

    //Métodos para Doctores
   public boolean addDoctor(Doctor doctor) {
        return this.storageDoctor.addDoctor(doctor);
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

   //Métodos para pacientes
    public boolean addPatient(Patient patient) {
        return this.storagePatient.addPatient(patient);
    }

    public Patient getPatient(long id) {
        return this.storagePatient.getPatient(id);
    }

    public boolean deletePatient(long id) {
        return this.storagePatient.deletePatient(id);
    }

    public boolean existsByUsername(String username) {
        return this.storagePatient.existsByUsername(username);
    }
    
    //Métodos para citas 
    public boolean addAppointment(Appointment appointment) {
        return this.storageAppointment.addAppointment(appointment);
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
    
    public List<Appointment> getSortedAppointmentsForPatient(long patientId){
        return this.storageAppointment.getSortedAppointmentsForPatient(patientId);
    }
    
    public List<Appointment> getSortedAppointmentsForDoctor(long doctorId, boolean pendingOnly){
        return this.storageAppointment.getSortedAppointmentsForDoctor(doctorId, pendingOnly);
    }
          
    
    //Métodos para hospitalizaciones 
    public boolean addHospitalization(Hospitalization hospitalization) {
        return this.storageHospitalization.addHospitalization(hospitalization);
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
    
    
}
