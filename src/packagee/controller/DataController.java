/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    private final StorageHospital storage;

    // Inyección de dependencias
    public DataController(StorageHospital storage) {
        this.storage = storage;
    }

    public Response getAllPatients() {
        ArrayList<HashMap<String, Object>> serialized = new ArrayList<>();
        for (Patient patient : storage.getAllPatients().values()) {
            serialized.add(patient.serialize());
        }
        return new Response(true, "Pacientes obtenidos correctamente", serialized);
    }

    public Response getAllDoctors() {
        ArrayList<HashMap<String, Object>> serialized = new ArrayList<>();
        for (Doctor doctor : storage.getAllDoctors().values()) {
            serialized.add(doctor.serialize());
        }
        return new Response(true, "Doctores obtenidos correctamente", serialized);
    }
    public Response getSpecialties() {
    // Si tus especialidades están en un enum, conviértelas a una lista
    List<String> specialties = Arrays.asList("GENERAL_MEDICINE", "CARDIOLOGY", "PEDIATRICS", "NEUROLOGY", "TRAUMATOLOGY_ORTHOPEDICS", "GYNECOLOGY_OBSTETRICS", "DERMATOLOGY", "PSYCHIATRY", "ONCOLOGY", "OPHTHALMOLOGY", "INTERNAL_MEDICINE");
    return new Response(true, "Especialidades obtenidas", specialties);
}

    public Response getAllAppointments() {
        ArrayList<HashMap<String, Object>> serialized = new ArrayList<>();
        for (Appointment appointment : storage.getAllAppointments().values()) {
            serialized.add(appointment.serialize());
        }
        return new Response(true, "Citas obtenidas correctamente", serialized);
    }

    public Response getAllHospitalizations() {
        ArrayList<HashMap<String, Object>> serialized = new ArrayList<>();
        for (Hospitalization hospitalization : storage.getAllHospitalizations().values()) {
            serialized.add(hospitalization.serialize());
        }
        return new Response(true, "Hospitalizaciones obtenidas correctamente", serialized);
    }
}
