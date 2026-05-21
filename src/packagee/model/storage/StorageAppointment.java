/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model.storage;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import packagee.model.Appointment;
import packagee.model.AppointmentStatus;
/**
 *
 * @author sierr
 */
public class StorageAppointment {
    private HashMap<String, Appointment> appointments;

    public StorageAppointment() {
        this.appointments = new HashMap<>();
    }
    
    public boolean addAppointment(Appointment appointment) {
            if(this.appointments.containsKey(appointment.getId())){
                return false;
            }
        this.appointments.put(appointment.getId(), appointment);
        return true;
    }
    
    public Appointment getAddAppointment(String id) {
        return this.appointments.get(id);
    }
    
   public boolean deleteAppointment(String id) {
        if (this.appointments.containsKey(id)) {
            this.appointments.remove(id);
            return true;
        }
        return false;
    }
   
   public HashMap<String, Appointment> getAllappointment() {
        return this.appointments;
    }
   
   public List<Appointment> getSortedAppointmentsForPatient(long patientId) {
        return this.appointments.values().stream()
            .filter(a -> a.getPatient() != null && a.getPatient().getId() == patientId)
            .sorted(Comparator.comparing(Appointment::getDatetime))
            .collect(Collectors.toList());
    }

    public List<Appointment> getSortedAppointmentsForDoctor(long doctorId, boolean pendingOnly) {
        return this.appointments.values().stream()
            .filter(a -> a.getDoctor() != null && a.getDoctor().getId() == doctorId)
            .filter(a -> {
                if (!pendingOnly) return true;
                return a.getStatus() != AppointmentStatus.COMPLETED && 
                       a.getStatus() != AppointmentStatus.CANCELED;
            })
            .sorted(Comparator.comparing(Appointment::getDatetime))
            .collect(Collectors.toList());
    }
   
}
