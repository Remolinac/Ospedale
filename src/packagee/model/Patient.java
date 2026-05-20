/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author edangulo
 */
public class Patient extends User implements ISerializable {
    
    private String email;
    private LocalDate birthdate;
    private boolean gender;
    private long phone;
    private String address;
    private ArrayList<Appointment> appointments;
    private Hospitalization hospitalization;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHospitalization(Hospitalization hospitalization) {
        this.hospitalization = hospitalization;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    
    public void addAppointment(Appointment a) {
        this.appointments.add(a);
    }

    public Patient(long id, String username, String firstname, String lastname, String password, String email, LocalDate birthdate, boolean gender, long phone, String address) {
        super(id, username, firstname, lastname, password);
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.appointments = new ArrayList<>();
    }

    @Override
    public HashMap<String, Object> serialize() {
        HashMap<String, Object> serializedData = new HashMap<>();
        
        serializedData.put("id", this.id);
        serializedData.put("username", this.username);
        serializedData.put("firstname", this.firstname);
        serializedData.put("lastname", this.lastname);
        serializedData.put("password", this.password);
        serializedData.put("email", this.email);
        serializedData.put("birthdate", this.birthdate);
        serializedData.put("gender", this.gender);
        serializedData.put("phone", this.phone);
        serializedData.put("address", this.address);
        serializedData.put("hospitalizationId",this.hospitalization != null ? this.hospitalization.getId(): null);
        
        ArrayList<HashMap<String, Object>> serializedAppointments = new ArrayList<>();
        
        for (Appointment a : this.appointments) {
        serializedAppointments.add(a.serialize());
         }
        
        serializedData.put("appointments", serializedAppointments);
        
        return serializedData;
    }
    
}
