/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model.storage;

import java.util.HashMap;
import packagee.model.Doctor;
import java.time.LocalDate;
import packagee.model.Specialty;
/**
 *
 * @author sierr
 */
public class StorageDoctor {

    private HashMap<Long, Doctor> doctors;

    public StorageDoctor() {
        this.doctors = new HashMap<>();
    }
    
    public boolean addDoctor(Doctor doctor) {
            if(this.doctors.containsKey(doctor.getId())){
                return false;
            }
        this.doctors.put(doctor.getId(), doctor);
        return true;
    }
    
    public Doctor getDoctor(long id) {
        return this.doctors.get(id);
    }
    
   public boolean deleteDoctor(long id) { // Cambiamos int por long y el nombre del método
        if (this.doctors.containsKey(id)) {
            this.doctors.remove(id);
            return true;
        }
        return false;
    }
   
   public HashMap<Long, Doctor> getAllDoctors() {
        return this.doctors;
    }
   
   public Doctor getAvailableDoctorBySpecialty(Specialty specialty, LocalDate date) {
    for (Doctor doctor : this.doctors.values()) {
 
        if (doctor.getSpecialty() == specialty) {
            if (doctor.isAvailable(date)) {
                return doctor; 
            }
        }
    }
    return null;
}
  
    
}
