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

public class HospitalizationController {

    public Response requestHospitalization(String patientId, String doctorId,
            String date, String reason, String roomType, String observations) {

        if (!Validator.isValidDate(date)) {
            return new Response(false, "La fecha no es válida, use el formato AAAA-MM-DD", null);
        }
        if (reason == null || reason.trim().isEmpty()) {
            return new Response(false, "La razón no puede estar vacía", null);
        }
        if (roomType == null || roomType.trim().isEmpty()) {
            return new Response(false, "El tipo de habitación no puede estar vacío", null);
        }

        // TODO: buscar paciente por patientId en Hospital
        // TODO: buscar doctor por doctorId en Hospital
        // TODO: generar ID automático formato H-{patientId}-NNNN
        // TODO: crear objeto Hospitalization con estado REQUESTED
        // TODO: guardarlo en Hospital.getInstance()

        return new Response(true, "Hospitalización solicitada correctamente", null);
    }

    public Response approveHospitalization(String hospitalizationId, String doctorId) {

        if (hospitalizationId == null || hospitalizationId.trim().isEmpty()) {
            return new Response(false, "El ID de la hospitalización no puede estar vacío", null);
        }

        // TODO: buscar hospitalización por hospitalizationId en Hospital
        // TODO: verificar que esté en estado REQUESTED
        // TODO: cambiar estado a ONGOING
        // TODO: hospitalization.setStatus(HospitalizationStatus.ONGOING)

        return new Response(true, "Hospitalización aprobada correctamente", null);
    }

    public Response denyHospitalization(String hospitalizationId, String doctorId) {

        if (hospitalizationId == null || hospitalizationId.trim().isEmpty()) {
            return new Response(false, "El ID de la hospitalización no puede estar vacío", null);
        }

        // TODO: buscar hospitalización por hospitalizationId en Hospital
        // TODO: verificar que esté en estado REQUESTED
        // TODO: cambiar estado a CANCELED
        // TODO: hospitalization.setStatus(HospitalizationStatus.CANCELED)

        return new Response(true, "Solicitud de hospitalización denegada correctamente", null);
    }

    public Response hospitalizeFromAppointment(String appointmentId, String doctorId,
            String date, String reason, String roomType, String observations) {

        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            return new Response(false, "El ID de la cita no puede estar vacío", null);
        }
        if (!Validator.isValidDate(date)) {
            return new Response(false, "La fecha no es válida, use el formato AAAA-MM-DD", null);
        }
        if (reason == null || reason.trim().isEmpty()) {
            return new Response(false, "La razón no puede estar vacía", null);
        }
        if (roomType == null || roomType.trim().isEmpty()) {
            return new Response(false, "El tipo de habitación no puede estar vacío", null);
        }

        // TODO: buscar cita por appointmentId en Hospital
        // TODO: verificar que la cita esté en estado PENDING
        // TODO: cambiar estado de la cita a COMPLETED
        // TODO: generar ID automático formato H-{patientId}-NNNN
        // TODO: crear objeto Hospitalization con estado ONGOING directamente
        // TODO: guardarlo en Hospital.getInstance()

        return new Response(true, "Paciente hospitalizado y cita completada correctamente", null);
    }

    public Response getHospitalizations(String patientId) {

        if (patientId == null || patientId.trim().isEmpty()) {
            return new Response(false, "El ID del paciente no puede estar vacío", null);
        }

        // TODO: buscar paciente por patientId en Hospital
        // TODO: obtener hospitalización del paciente
        // TODO: serializar como Map<String, Object> — NUNCA pasar objeto Hospitalization
        // TODO: return new Response(true, "Hospitalización obtenida", datosSerializados)

        return new Response(true, "Hospitalización obtenida correctamente", null);
    }
}