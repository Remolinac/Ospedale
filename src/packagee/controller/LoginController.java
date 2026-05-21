/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.controller;

import java.util.HashMap;
import packagee.model.Administrator;
import packagee.model.Doctor;
import packagee.model.Patient;
import packagee.model.storage.StorageHospital;
import packagee.util.Response;

/**
 *
 * @author sierr
 */
public class LoginController {

    public Response login(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            return new Response(false, "El nombre de usuario no puede estar vacío", null);
        }
        if (password == null || password.trim().isEmpty()) {
            return new Response(false, "La contraseña no puede estar vacía", null);
        }

        StorageHospital storage = StorageHospital.getInstance();

        // Verificar administrador
        Administrator admin = storage.getAdmin();
        if (admin != null && admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            HashMap<String, Object> data = admin.serialize();
            data.put("role", "ADMIN");
            return new Response(true, "Login exitoso", data);
        }

        // Buscar en pacientes
        for (Patient patient : storage.getAllPatients().values()) {
            if (patient.getUsername().equals(username) && patient.getPassword().equals(password)) {
                HashMap<String, Object> data = patient.serialize();
                data.put("role", "PATIENT");
                return new Response(true, "Login exitoso", data);
            }
        }

        // Buscar en doctores
        for (Doctor doctor : storage.getAllDoctors().values()) {
            if (doctor.getUsername().equals(username) && doctor.getPassword().equals(password)) {
                HashMap<String, Object> data = doctor.serialize();
                data.put("role", "DOCTOR");
                return new Response(true, "Login exitoso", data);
            }
        }

        return new Response(false, "Usuario o contraseña incorrectos", null);
    }
}