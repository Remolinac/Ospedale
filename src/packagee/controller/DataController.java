/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import packagee.model.Appointment;
import packagee.model.Doctor;
import packagee.model.Hospitalization;
import packagee.model.Patient;
import packagee.model.storage.StorageHospital;
import packagee.util.Response;

/**
 *
 * @author berri
 */

public class DataController {

    public Response getAllPatients() {

        StorageHospital storage = StorageHospital.getInstance();

        ArrayList<HashMap<String, Object>> serialized = new ArrayList<>();
        for (Patient patient : storage.getAllPatients().values()) {
            serialized.add(patient.serialize());
        }

        return new Response(true, "Pacientes obtenidos correctamente", serialized);
    }

    public Response getAllDoctors() {

        StorageHospital storage = StorageHospital.getInstance();

        ArrayList<HashMap<String, Object>> serialized = new ArrayList<>();
        for (Doctor doctor : storage.getAllDoctors().values()) {
            serialized.add(doctor.serialize());
        }

        return new Response(true, "Doctores obtenidos correctamente", serialized);
    }

    public Response getAllAppointments() {

        StorageHospital storage = StorageHospital.getInstance();

        ArrayList<HashMap<String, Object>> serialized = new ArrayList<>();
        for (Appointment appointment : storage.getAllAppointments().values()) {
            serialized.add(appointment.serialize());
        }

        return new Response(true, "Citas obtenidas correctamente", serialized);
    }

    public Response getAllHospitalizations() {

        StorageHospital storage = StorageHospital.getInstance();

        ArrayList<HashMap<String, Object>> serialized = new ArrayList<>();
        for (Hospitalization hospitalization : storage.getAllHospitalizations().values()) {
            serialized.add(hospitalization.serialize());
        }

        return new Response(true, "Hospitalizaciones obtenidas correctamente", serialized);
    }
}