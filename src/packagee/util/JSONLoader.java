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
                        storage.setAdmin(new Administrator(
                                user.getLong("id"),
                                user.getString("username"),
                                user.getString("firstname"),
                                user.getString("lastname"),
                                user.getString("password")
                        ));
                        break;

                    case "patient":
                        storage.addPatient(new Patient(
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
                        ));
                        break;

                    case "doctor":
                        Specialty specialty = parseSpecialty(user.getString("specialty"));
                        if (specialty == null) {
                            break;
                        }
                        storage.addDoctor(new Doctor(
                                user.getLong("id"),
                                user.getString("username"),
                                user.getString("firstname"),
                                user.getString("lastname"),
                                user.getString("password"),
                                specialty,
                                user.getString("licenceNumber"),
                                user.getString("assignedOffice")
                        ));
                        break;
                }
            }
            System.out.println("JSON cargado correctamente: " + filePath);
        } catch (IOException e) {
            System.out.println("Error leyendo JSON (" + filePath + "): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parseando JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Specialty parseSpecialty(String raw) {
        if (raw == null) {
            return null;
        }
        String s = raw.trim().toUpperCase();
        switch (s) {
            case "ORTHOPEDICS":
                s = "TRAUMATOLOGY_ORTHOPEDICS";
                break;
            case "GYNECOLOGY":
                s = "GYNECOLOGY_OBSTETRICS";
                break;
            case "GENERAL":
                s = "GENERAL_MEDICINE";
                break;
            case "INTERNAL":
                s = "INTERNAL_MEDICINE";
                break;
        }
        try {
            return Specialty.valueOf(s);
        } catch (IllegalArgumentException e) {
            System.out.println("Especialidad no reconocida: " + raw);
            return null;
        }
    }
}
