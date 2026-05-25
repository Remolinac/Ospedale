/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.controller;

import packagee.model.Doctor;
import packagee.model.Specialty;
import packagee.model.storage.StorageHospital;
import packagee.util.Response;
import packagee.util.Validator;

/**
 *
 * @author sierr
 */
public class DoctorController {

    private final StorageHospital storage;

    // Inyección de dependencias
    public DoctorController(StorageHospital storage) {
        this.storage = storage;
    }

    public Response registerDoctor(String id, String username, String firstname,
            String lastname, String password, String confirm,
            String specialty, String licenseNumber, String assignedOffice) {

        if (!Validator.isValidId(id)) return new Response(false, "El ID debe tener exactamente 12 dígitos y ser mayor que 0", null);
        if (username == null || username.trim().isEmpty()) return new Response(false, "El nombre de usuario no puede estar vacío", null);
        if (!Validator.isValidPassword(password, confirm)) return new Response(false, "La contraseña no puede estar vacía o las contraseñas no coinciden", null);
        if (specialty == null || specialty.trim().isEmpty() || specialty.equals("Select one")) return new Response(false, "La especialidad no puede estar vacía", null);
        if (!Validator.isValidLicense(licenseNumber)) return new Response(false, "La licencia debe tener el formato L-XXXXXXXXXX MTL", null);
        if (!Validator.isValidOffice(assignedOffice)) return new Response(false, "La oficina debe tener el formato O-XXX", null);

        if (storage.getDoctor(Long.parseLong(id)) != null) {
            return new Response(false, "Ya existe un doctor con ese ID", null);
        }
        if (storage.existsByUsername(username) || storage.existsByUsernameInDoctors(username)) {
            return new Response(false, "El nombre de usuario ya está en uso", null);
        }

        Specialty specialtyEnum;
        try {
            specialtyEnum = Specialty.valueOf(specialty.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new Response(false, "La especialidad no es válida", null);
        }

        Doctor doctor = new Doctor(
                Long.parseLong(id), username, firstname, lastname, password,
                specialtyEnum, licenseNumber, assignedOffice
        );

        storage.addDoctor(doctor);
        return new Response(true, "Doctor registrado correctamente", doctor.serialize());
    }

    public Response updateDoctor(String id, String username, String firstname,
            String lastname, String password, String confirm,
            String specialty, String licenseNumber, String assignedOffice) {

        if (!Validator.isValidId(id)) return new Response(false, "El ID debe tener exactamente 12 dígitos y ser mayor que 0", null);
        if (username == null || username.trim().isEmpty()) return new Response(false, "El nombre de usuario no puede estar vacío", null);
        if (!Validator.isValidPassword(password, confirm)) return new Response(false, "La contraseña no puede estar vacía o las contraseñas no coinciden", null);
        if (specialty == null || specialty.trim().isEmpty() || specialty.equals("Select one")) return new Response(false, "La especialidad no puede estar vacía", null);
        if (!Validator.isValidLicense(licenseNumber)) return new Response(false, "La licencia debe tener el formato L-XXXXXXXXXX MTL", null);
        if (!Validator.isValidOffice(assignedOffice)) return new Response(false, "La oficina debe tener el formato O-XXX", null);

        Doctor doctor = storage.getDoctor(Long.parseLong(id));
        if (doctor == null) {
            return new Response(false, "No existe un doctor con ese ID", null);
        }

        if (!doctor.getUsername().equals(username) && (storage.existsByUsername(username) || storage.existsByUsernameInDoctors(username))) {
            return new Response(false, "El nombre de usuario ya está en uso", null);
        }

        Specialty specialtyEnum;
        try {
            specialtyEnum = Specialty.valueOf(specialty.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new Response(false, "La especialidad no es válida", null);
        }

        doctor.setUsername(username);
        doctor.setFirstname(firstname);
        doctor.setLastname(lastname);
        doctor.setPassword(password);
        doctor.setSpecialty(specialtyEnum);
        doctor.setLicenceNumber(licenseNumber);
        doctor.setAssignedOffice(assignedOffice);

        return new Response(true, "Doctor actualizado correctamente", doctor.serialize());
    }
    public void registerObserver(packagee.util.Observer observer) {
    StorageHospital.getInstance().addObserver(observer);
}

    public void unregisterObserver(packagee.util.Observer observer) {
    StorageHospital.getInstance().removeObserver(observer);
}
}
