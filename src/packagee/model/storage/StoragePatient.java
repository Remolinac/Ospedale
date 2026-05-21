/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model.storage;

import java.util.HashMap;
import packagee.model.Patient;

/**
 *
 * @author sierr
 */
public class StoragePatient {
    private HashMap<Long, Patient> patients;

    public StoragePatient() {
        this.patients = new HashMap<>();
    }
    
    public Patient getPatient(long id) {
        return this.patients.get(id);
    }
    
    public boolean addPatient(Patient patient) {
            if(this.patients.containsKey(patient.getId())){
                return false;
            }
        this.patients.put(patient.getId(), patient);
        return true;
    }
    
    
   public boolean deletePatient(long id) { 
        if (this.patients.containsKey(id)) {
            this.patients.remove(id);
            return true;
        }
        return false;
    }
   
   public boolean existsByUsername(String username) {
        for (Patient p : this.patients.values()) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                return true; 
            }
        }
        return false;
    }
 
   
   public HashMap<Long, Patient> getAll() {
        return this.patients;
    }
}
