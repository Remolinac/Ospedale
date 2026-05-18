/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.controller;

import packagee.util.Response;
import packagee.util.Validator;

/**
 *
 * @author sierr
 */

public class DoctorController {

    public Response registerDoctor(String id, String username, String firstname,
            String lastname, String password, String confirm,
            String specialty, String licenseNumber, String assignedOffice) {

        if (!Validator.isValidId(id)) {
            return new Response(false, "El ID debe tener exactamente 12 dígitos y ser mayor que 0", null);
        }
        if (username == null || username.trim().isEmpty()) {
            return new Response(false, "El nombre de usuario no puede estar vacío", null);
        }
        if (!Validator.isValidPassword(password, confirm)) {
            return new Response(false, "La contraseña no puede estar vacía o las contraseñas no coinciden", null);
        }
        if (specialty == null || specialty.trim().isEmpty()) {
            return new Response(false, "La especialidad no puede estar vacía", null);
        }
        if (!Validator.isValidLicense(licenseNumber)) {
            return new Response(false, "La licencia debe tener el formato L-XXXXXXXXXX MTL", null);
        }
        if (!Validator.isValidOffice(assignedOffice)) {
            return new Response(false, "La oficina debe tener el formato O-XXX", null);
        }

        // TODO: verificar que el ID no exista ya en Hospital
        // TODO: verificar que el username no exista ya en Hospital
        // TODO: crear objeto Doctor y guardarlo en Hospital.getInstance()

        return new Response(true, "Doctor registrado correctamente", null);
    }

    public Response updateDoctor(String id, String username, String firstname,
            String lastname, String password, String confirm,
            String specialty, String licenseNumber, String assignedOffice) {

        if (!Validator.isValidId(id)) {
            return new Response(false, "El ID debe tener exactamente 12 dígitos y ser mayor que 0", null);
        }
        if (username == null || username.trim().isEmpty()) {
            return new Response(false, "El nombre de usuario no puede estar vacío", null);
        }
        if (!Validator.isValidPassword(password, confirm)) {
            return new Response(false, "La contraseña no puede estar vacía o las contraseñas no coinciden", null);
        }
        if (specialty == null || specialty.trim().isEmpty()) {
            return new Response(false, "La especialidad no puede estar vacía", null);
        }
        if (!Validator.isValidLicense(licenseNumber)) {
            return new Response(false, "La licencia debe tener el formato L-XXXXXXXXXX MTL", null);
        }
        if (!Validator.isValidOffice(assignedOffice)) {
            return new Response(false, "La oficina debe tener el formato O-XXX", null);
        }

        // TODO: verificar que el doctor exista en Hospital
        // TODO: verificar que el nuevo username no lo tenga otro usuario
        // TODO: actualizar el objeto Doctor en Hospital.getInstance()

        return new Response(true, "Doctor actualizado correctamente", null);
    }
}