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

public class AppointmentController {

    public Response requestAppointmentByDoctor(String patientId, String doctorId,
            String date, String time, String reason) {

        if (!Validator.isValidDate(date)) {
            return new Response(false, "La fecha no es válida, use el formato AAAA-MM-DD", null);
        }
        if (!Validator.isValidTime(time)) {
            return new Response(false, "La hora no es válida, use formato hh:mm y minutos en 00, 15, 30 o 45", null);
        }
        if (reason == null || reason.trim().isEmpty()) {
            return new Response(false, "La razón de la cita no puede estar vacía", null);
        }

        // TODO: buscar paciente por patientId en Hospital
        // TODO: buscar doctor por doctorId en Hospital
        // TODO: verificar que el doctor tenga disponibilidad en esa fecha y hora
        // TODO: la especialidad de la cita debe ser la especialidad del doctor
        // TODO: generar ID automático formato A-{patientId}-NNNN
        // TODO: crear objeto Appointment y guardarlo

        return new Response(true, "Cita solicitada correctamente", null);
    }

    public Response requestAppointmentBySpecialty(String patientId, String specialty,
            String date, String time, String reason) {

        if (!Validator.isValidDate(date)) {
            return new Response(false, "La fecha no es válida, use el formato AAAA-MM-DD", null);
        }
        if (!Validator.isValidTime(time)) {
            return new Response(false, "La hora no es válida, use formato hh:mm y minutos en 00, 15, 30 o 45", null);
        }
        if (reason == null || reason.trim().isEmpty()) {
            return new Response(false, "La razón de la cita no puede estar vacía", null);
        }
        if (specialty == null || specialty.trim().isEmpty()) {
            return new Response(false, "La especialidad no puede estar vacía", null);
        }

        // TODO: buscar paciente por patientId en Hospital
        // TODO: buscar doctor disponible con esa especialidad en esa fecha y hora
        // TODO: si no hay doctor disponible retornar Response(false, ...)
        // TODO: generar ID automático formato A-{patientId}-NNNN
        // TODO: crear objeto Appointment y guardarlo

        return new Response(true, "Cita solicitada correctamente", null);
    }

    public Response acceptAppointment(String appointmentId, String doctorId) {

        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            return new Response(false, "El ID de la cita no puede estar vacío", null);
        }

        // TODO: buscar cita por appointmentId en Hospital
        // TODO: verificar que la cita esté en estado REQUESTED
        // TODO: cambiar estado a PENDING
        // TODO: appointment.setStatus(AppointmentStatus.PENDING)

        return new Response(true, "Cita aceptada correctamente", null);
    }

    public Response completeAppointment(String appointmentId, String doctorId) {

        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            return new Response(false, "El ID de la cita no puede estar vacío", null);
        }

        // TODO: buscar cita por appointmentId en Hospital
        // TODO: verificar que la cita esté en estado PENDING
        // TODO: cambiar estado a COMPLETED
        // TODO: appointment.setStatus(AppointmentStatus.COMPLETED)

        return new Response(true, "Cita completada correctamente", null);
    }

    public Response cancelAppointment(String appointmentId, String patientId) {

        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            return new Response(false, "El ID de la cita no puede estar vacío", null);
        }

        // TODO: buscar cita por appointmentId en Hospital
        // TODO: verificar que la cita NO esté en estado COMPLETED
        // TODO: cambiar estado a CANCELED
        // TODO: appointment.setStatus(AppointmentStatus.CANCELED)

        return new Response(true, "Cita cancelada correctamente", null);
    }

    public Response rescheduleAppointment(String appointmentId, String doctorId,
            String newTime, String rescheduleReason) {

        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            return new Response(false, "El ID de la cita no puede estar vacío", null);
        }
        if (!Validator.isValidTime(newTime)) {
            return new Response(false, "La nueva hora no es válida, use formato hh:mm y minutos en 00, 15, 30 o 45", null);
        }

        // TODO: buscar cita por appointmentId en Hospital
        // TODO: verificar que la cita sea válida
        // TODO: actualizar la hora (misma fecha, nueva hora)
        // TODO: añadir rescheduleReason a la razón original de la cita

        return new Response(true, "Cita reagendada correctamente", null);
    }

    public Response prescribeMedication(String appointmentId, String doctorId,
            String medicationName, double dose, String administrationRoute,
            int treatmentDuration, String additionalInstructions, int frequency) {

        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            return new Response(false, "El ID de la cita no puede estar vacío", null);
        }
        if (medicationName == null || medicationName.trim().isEmpty()) {
            return new Response(false, "El nombre del medicamento no puede estar vacío", null);
        }

        // TODO: buscar cita por appointmentId en Hospital
        // TODO: verificar que la cita esté en estado PENDING (no COMPLETED ni CANCELED)
        // TODO: crear objeto Prescription y asociarlo a la cita

        return new Response(true, "Medicamento prescrito correctamente", null);
    }

    public Response getPatientAppointments(String patientId) {

        if (patientId == null || patientId.trim().isEmpty()) {
            return new Response(false, "El ID del paciente no puede estar vacío", null);
        }

        // TODO: buscar paciente por patientId en Hospital
        // TODO: obtener lista de citas ordenadas descendentemente por fecha y hora
        // TODO: serializar cada cita como Map<String, Object> — NUNCA pasar objeto Appointment
        // TODO: return new Response(true, "Citas obtenidas", listaSerializada)

        return new Response(true, "Citas obtenidas correctamente", null);
    }

    public Response getDoctorAppointments(String doctorId, boolean pendingOnly) {

        if (doctorId == null || doctorId.trim().isEmpty()) {
            return new Response(false, "El ID del doctor no puede estar vacío", null);
        }

        // TODO: buscar doctor por doctorId en Hospital
        // TODO: obtener lista de citas, si pendingOnly filtrar solo PENDING
        // TODO: ordenar descendentemente por fecha y hora
        // TODO: serializar cada cita como Map<String, Object> — NUNCA pasar objeto Appointment
        // TODO: return new Response(true, "Citas obtenidas", listaSerializada)

        return new Response(true, "Citas obtenidas correctamente", null);
    }
}