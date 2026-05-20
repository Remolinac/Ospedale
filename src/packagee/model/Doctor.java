/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model;

import packagee.model.Appointment;
import packagee.model.User;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author edangulo
 */
public class Doctor extends User implements ISerializable{
    
    private Specialty specialty;
    private String licenceNumber;
    private String assignedOffice;
    private ArrayList<Appointment> appointments;
    private ArrayList<Hospitalization> hospitalizations;

    public Doctor(long id, String username, String firstname, String lastname, String password, Specialty specialty, String licenceNumber, String assignedOffice) {
        super(id, username, firstname, lastname, password);
        hospitalizations = new ArrayList<>();
        this.specialty = specialty;
        this.licenceNumber = licenceNumber;
        this.assignedOffice = assignedOffice;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public String getAssignedOffice() {
        return assignedOffice;
    }

    public ArrayList<Hospitalization> getHospitalizations() {
        return hospitalizations;
    }
    
    //Revisar
    public boolean addHospitalization(Hospitalization hosp){
        return hospitalizations.add(hosp);
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public void setAssignedOffice(String assignedOffice) {
        this.assignedOffice = assignedOffice;
    }

    @Override
    public HashMap<String, Object> serialize() {
      HashMap<String, Object> serializedData = new HashMap<>();
        
        serializedData.put("id", this.id);
        serializedData.put("username", this.username);
        serializedData.put("firstname", this.firstname);
        serializedData.put("lastname", this.lastname);
        serializedData.put("password", this.password);
        serializedData.put("specialty", this.specialty);
        serializedData.put("licenceNumber", this.licenceNumber);
        serializedData.put("assignedOffice", this.assignedOffice);
        
        return serializedData;
    }
    
    
}
