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
public class PatientController {

    public Response registerPatient(String id, String username, String firstname,
            String lastname, String password, String confirm,
            String email, String phone, String birthdate,
            String gender, String address) {

        if (!Validator.isValidId(id)) {
            return new Response(false, "El ID debe tener exactamente 12 dígitos y ser mayor que 0", null);
        }
        if (username == null || username.trim().isEmpty()) {
            return new Response(false, "El nombre de usuario no puede estar vacío", null);
        }
        if (!Validator.isValidPassword(password, confirm)) {
            return new Response(false, "La contraseña no puede estar vacía o las contraseñas no coinciden", null);
        }
        if (!Validator.isValidEmail(email)) {
            return new Response(false, "El email no tiene el formato correcto", null);
        }
        if (!Validator.isValidPhone(phone)) {
            return new Response(false, "El teléfono debe tener exactamente 10 dígitos", null);
        }
        if (!Validator.isValidDate(birthdate)) {
            return new Response(false, "La fecha de nacimiento no es válida, use el formato AAAA-MM-DD", null);
        }
        if (address == null || address.trim().isEmpty()) {
            return new Response(false, "La dirección no puede estar vacía", null);
        }

        // TODO: verificar que el ID no exista ya en Hospital
        // TODO: verificar que el username no exista ya en Hospital
        // TODO: crear objeto Patient y guardarlo en Hospital.getInstance()

        return new Response(true, "Paciente registrado correctamente", null);
    }

    public Response updatePatient(String id, String username, String firstname,
            String lastname, String password, String confirm,
            String email, String phone, String birthdate,
            String gender, String address) {

        if (!Validator.isValidId(id)) {
            return new Response(false, "El ID debe tener exactamente 12 dígitos y ser mayor que 0", null);
        }
        if (username == null || username.trim().isEmpty()) {
            return new Response(false, "El nombre de usuario no puede estar vacío", null);
        }
        if (!Validator.isValidPassword(password, confirm)) {
            return new Response(false, "La contraseña no puede estar vacía o las contraseñas no coinciden", null);
        }
        if (!Validator.isValidEmail(email)) {
            return new Response(false, "El email no tiene el formato correcto", null);
        }
        if (!Validator.isValidPhone(phone)) {
            return new Response(false, "El teléfono debe tener exactamente 10 dígitos", null);
        }
        if (!Validator.isValidDate(birthdate)) {
            return new Response(false, "La fecha de nacimiento no es válida, use el formato AAAA-MM-DD", null);
        }
        if (address == null || address.trim().isEmpty()) {
            return new Response(false, "La dirección no puede estar vacía", null);
        }

        // TODO: verificar que el paciente exista en Hospital
        // TODO: verificar que el nuevo username no lo tenga otro usuario
        // TODO: actualizar el objeto Patient en Hospital.getInstance()

        return new Response(true, "Paciente actualizado correctamente", null);
    }
}
