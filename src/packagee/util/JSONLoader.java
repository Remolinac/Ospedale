/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.util;

import packagee.model.Administrator;
import packagee.model.Doctor;
import packagee.model.Patient;
import packagee.model.Specialty;
import packagee.model.storage.StorageHospital;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
/**
 *
 * @author berri
 */
public class JSONLoader {

    public static void loadUsers(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject root = new JSONObject(content);
            JSONArray users = root.getJSONArray("users");
            StorageHospital storage = StorageHospital.getInstance();

            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                String type = user.getString("type");

                switch (type) {
                    case "admin":
                        Administrator admin = new Administrator(
                                user.getLong("id"),
                                user.getString("username"),
                                user.getString("firstname"),
                                user.getString("lastname"),
                                user.getString("password")
                        );
                        storage.setAdmin(admin);
                        break;

                    case "patient":
                        Patient patient = new Patient(
                                user.getLong("id"),
                                user.getString("username"),
                                user.getString("firstname"),
                                user.getString("lastname"),
                                user.getString("password"),
                                user.getString("email"),
                                LocalDate.parse(user.getString("birthdate")),
                                user.getBoolean("gender"),
                                user.getLong("phone"),
                                user.getString("address")
                        );
                        storage.addPatient(patient);
                        break;

                    case "doctor":
                        Specialty specialty = parseSpecialty(user.getString("specialty"));
                        if (specialty == null) break;

                        Doctor doctor = new Doctor(
                                user.getLong("id"),
                                user.getString("username"),
                                user.getString("firstname"),
                                user.getString("lastname"),
                                user.getString("password"),
                                specialty,
                                user.getString("licenceNumber"),
                                user.getString("assignedOffice")
                        );
                        storage.addDoctor(doctor);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo JSON: " + e.getMessage());
        }
    }

    private static Specialty parseSpecialty(String specialty) {
        switch (specialty.toUpperCase()) {
            case "CARDIOLOGY": return Specialty.CARDIOLOGY;
            case "NEUROLOGY": return Specialty.NEUROLOGY;
            case "PEDIATRICS": return Specialty.PEDIATRICS;
            case "DERMATOLOGY": return Specialty.DERMATOLOGY;
            case "ORTHOPEDICS": return Specialty.TRAUMATOLOGY_ORTHOPEDICS;
            case "GYNECOLOGY": return Specialty.GYNECOLOGY_OBSTETRICS;
            case "PSYCHIATRY": return Specialty.PSYCHIATRY;
            case "ONCOLOGY": return Specialty.ONCOLOGY;
            case "GENERAL_MEDICINE": return Specialty.GENERAL_MEDICINE;
            case "OPHTHALMOLOGY": return Specialty.OPHTHALMOLOGY;
            case "INTERNAL_MEDICINE": return Specialty.INTERNAL_MEDICINE;
            default:
                System.out.println("Especialidad no reconocida: " + specialty);
                return null;
        }
    }
}