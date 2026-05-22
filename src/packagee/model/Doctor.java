/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;

/**
 *
 * @author edangulo
 */
public class Doctor extends User implements ISerializable {

    private Specialty specialty;
    private String licenceNumber;
    private String assignedOffice;
    private ArrayList<Appointment> appointments;
    private ArrayList<Hospitalization> hospitalizations;

    public Doctor(long id, String username, String firstname, String lastname, String password, Specialty specialty, String licenceNumber, String assignedOffice) {
        super(id, username, firstname, lastname, password);
        appointments = new ArrayList<>();
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

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public void setAssignedOffice(String assignedOffice) {
        this.assignedOffice = assignedOffice;
    }

    public boolean addHospitalization(Hospitalization hosp) {
        return hospitalizations.add(hosp);
    }

    public boolean addAppointment(Appointment app) {
        return appointments.add(app);
    }

    public boolean isAvailable(LocalDate date) {
        for (Appointment appointment : this.appointments) {
            if (appointment.getDatetime().equals(date)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public HashMap<String, Object> serialize() {
        HashMap<String, Object> serializedData = new HashMap<>();

        serializedData.put("id", this.id);
        serializedData.put("username", this.username);
        serializedData.put("firstname", this.firstname);
        serializedData.put("lastname", this.lastname);
        serializedData.put("specialty", this.specialty);
        serializedData.put("licenceNumber", this.licenceNumber);
        serializedData.put("assignedOffice", this.assignedOffice);

        return serializedData;
    }

}
