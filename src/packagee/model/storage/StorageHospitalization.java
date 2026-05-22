/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model.storage;

import java.util.HashMap;
import packagee.model.Hospitalization;

/**
 *
 * @author sierr
 */
public class StorageHospitalization {

    private HashMap<String, Hospitalization> hospitalizations;

    public StorageHospitalization() {
        this.hospitalizations = new HashMap<>();
    }

    public Hospitalization getHospitalization(String id) {
        return this.hospitalizations.get(id);
    }

    public boolean addHospitalization(Hospitalization hospitalization) {
        if (this.hospitalizations.containsKey(hospitalization.getId())) {
            return false;
        }
        this.hospitalizations.put(hospitalization.getId(), hospitalization);
        return true;
    }

    public boolean deleteHospitalization(String id) {
        if (this.hospitalizations.containsKey(id)) {
            this.hospitalizations.remove(id);
            return true;
        }
        return false;
    }

    public HashMap<String, Hospitalization> getAll() {
        return this.hospitalizations;
    }

}
