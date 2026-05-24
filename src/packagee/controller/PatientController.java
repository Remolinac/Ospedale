/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.controller;

import java.time.LocalDate;
import packagee.model.Patient;
import packagee.model.storage.StorageHospital;
import packagee.util.Response;
import packagee.util.Validator;

/**
 *
 * @author sierr
 */
public class PatientController {
    private final StorageHospital storage;

    // Inyección de dependencias
    public PatientController(StorageHospital storage) {
        this.storage = storage;
    }

    public Response registerPatient(String id, String username, String firstname,
            String lastname, String password, String confirm,
            String email, String phone, String birthdate,
            String gender, String address) {

        if (!Validator.isValidId(id)) return new Response(false, "El ID debe tener exactamente 12 dígitos y ser mayor que 0", null);
        if (username == null || username.trim().isEmpty()) return new Response(false, "El nombre de usuario no puede estar vacío", null);
        if (!Validator.isValidPassword(password, confirm)) return new Response(false, "La contraseña no puede estar vacía o las contraseñas no coinciden", null);
        if (!Validator.isValidEmail(email)) return new Response(false, "El email no tiene el formato correcto", null);
        if (!Validator.isValidPhone(phone)) return new Response(false, "El teléfono debe tener exactamente 10 dígitos", null);
        if (!Validator.isValidDate(birthdate)) return new Response(false, "La fecha de nacimiento no es válida, use el formato AAAA-MM-DD", null);
        if (address == null || address.trim().isEmpty()) return new Response(false, "La dirección no puede estar vacía", null);

        if (storage.getPatient(Long.parseLong(id)) != null) {
            return new Response(false, "Ya existe un paciente con ese ID", null);
        }
        if (storage.existsByUsername(username)) {
            return new Response(false, "El nombre de usuario ya está en uso", null);
        }

        boolean isMale = gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("M");
        Patient patient = new Patient(
                Long.parseLong(id), username, firstname, lastname, password,
                email, LocalDate.parse(birthdate), isMale, Long.parseLong(phone), address
        );

        storage.addPatient(patient);
        return new Response(true, "Paciente registrado correctamente", patient.serialize());
    }

    public Response updatePatient(String id, String username, String firstname,
            String lastname, String password, String confirm,
            String email, String phone, String birthdate,
            String gender, String address) {

        if (!Validator.isValidId(id)) return new Response(false, "El ID debe tener exactamente 12 dígitos y ser mayor que 0", null);
        if (username == null || username.trim().isEmpty()) return new Response(false, "El nombre de usuario no puede estar vacío", null);
        if (!Validator.isValidPassword(password, confirm)) return new Response(false, "La contraseña no puede estar vacía o las contraseñas no coinciden", null);
        if (!Validator.isValidEmail(email)) return new Response(false, "El email no tiene el formato correcto", null);
        if (!Validator.isValidPhone(phone)) return new Response(false, "El teléfono debe tener exactamente 10 dígitos", null);
        if (!Validator.isValidDate(birthdate)) return new Response(false, "La fecha de nacimiento no es válida, use el formato AAAA-MM-DD", null);
        if (address == null || address.trim().isEmpty()) return new Response(false, "La dirección no puede estar vacía", null);

        Patient patient = storage.getPatient(Long.parseLong(id));
        if (patient == null) {
            return new Response(false, "No existe un paciente con ese ID", null);
        }

        if (!patient.getUsername().equals(username) && storage.existsByUsername(username)) {
            return new Response(false, "El nombre de usuario ya está en uso", null);
        }

        boolean isMale = gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("M");
        patient.setUsername(username);
        patient.setFirstname(firstname);
        patient.setLastname(lastname);
        patient.setPassword(password);
        patient.setEmail(email);
        patient.setPhone(Long.parseLong(phone));
        patient.setBirthdate(LocalDate.parse(birthdate));
        patient.setGender(isMale);
        patient.setAddress(address);

        return new Response(true, "Paciente actualizado correctamente", patient.serialize());
    }
}
