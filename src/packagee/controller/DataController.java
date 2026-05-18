/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.controller;

import packagee.util.Response;

/**
 *
 * @author berri
 */

public class DataController {

    public Response getAllPatients() {

        // TODO: obtener lista de pacientes de Hospital.getInstance()
        // TODO: serializar cada paciente como Map<String, Object>
        // Ejemplo de lo que debe retornar cada Map:
        // map.put("id", patient.getId())
        // map.put("username", patient.getUsername())
        // map.put("firstname", patient.getFirstname())
        // map.put("lastname", patient.getLastname())
        // map.put("email", patient.getEmail())
        // map.put("phone", patient.getPhone())
        // NUNCA pasar el objeto Patient directamente

        return new Response(true, "Pacientes obtenidos correctamente", null);
    }

    public Response getAllDoctors() {

        // TODO: obtener lista de doctores de Hospital.getInstance()
        // TODO: serializar cada doctor como Map<String, Object>
        // Ejemplo de lo que debe retornar cada Map:
        // map.put("id", doctor.getId())
        // map.put("username", doctor.getUsername())
        // map.put("firstname", doctor.getFirstname())
        // map.put("lastname", doctor.getLastname())
        // map.put("specialty", doctor.getSpecialty().toString())
        // map.put("licenseNumber", doctor.getLicenceNumber())
        // map.put("assignedOffice", doctor.getAssignedOffice())
        // NUNCA pasar el objeto Doctor directamente

        return new Response(true, "Doctores obtenidos correctamente", null);
    }

    public Response getAllAppointments() {

        // TODO: obtener lista de todas las citas de Hospital.getInstance()
        // TODO: ordenar descendentemente por fecha y hora
        // TODO: serializar cada cita como Map<String, Object>
        // Ejemplo de lo que debe retornar cada Map:
        // map.put("id", appointment.getId())
        // map.put("patientName", appointment.getPatient().getFirstname() + " " + appointment.getPatient().getLastname())
        // map.put("doctorName", appointment.getDoctor().getFirstname() + " " + appointment.getDoctor().getLastname())
        // map.put("specialty", appointment.getSpecialty().toString())
        // map.put("datetime", appointment.getDatetime().toString())
        // map.put("status", appointment.getStatus().toString())
        // NUNCA pasar el objeto Appointment directamente

        return new Response(true, "Citas obtenidas correctamente", null);
    }

    public Response getAllHospitalizations() {

        // TODO: obtener lista de todas las hospitalizaciones de Hospital.getInstance()
        // TODO: serializar cada hospitalización como Map<String, Object>
        // Ejemplo de lo que debe retornar cada Map:
        // map.put("id", hosp.getId())
        // map.put("patientName", hosp.getPatient().getFirstname() + " " + hosp.getPatient().getLastname())
        // map.put("doctorName", hosp.getDoctor().getFirstname() + " " + hosp.getDoctor().getLastname())
        // map.put("date", hosp.getDate().toString())
        // map.put("roomType", hosp.getRoomType().toString())
        // map.put("status", hosp.getStatus().toString())
        // NUNCA pasar el objeto Hospitalization directamente

        return new Response(true, "Hospitalizaciones obtenidas correctamente", null);
    }
}
