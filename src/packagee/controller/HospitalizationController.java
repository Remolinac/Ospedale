/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import packagee.model.Appointment;
import packagee.model.AppointmentStatus;
import packagee.model.Doctor;
import packagee.model.Hospitalization;
import packagee.model.HospitalizationStatus;
import packagee.model.Patient;
import packagee.model.RoomType;
import packagee.model.storage.StorageHospital;
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

        StorageHospital storage = StorageHospital.getInstance();

        Patient patient = storage.getPatient(Long.parseLong(patientId));
        if (patient == null) {
            return new Response(false, "No existe un paciente con ese ID", null);
        }

        Doctor doctor = storage.getDoctor(Long.parseLong(doctorId));
        if (doctor == null) {
            return new Response(false, "No existe un doctor con ese ID", null);
        }

        RoomType roomTypeEnum;
        try {
            roomTypeEnum = RoomType.valueOf(roomType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new Response(false, "El tipo de habitación no es válido", null);
        }

        Hospitalization hospitalization = new Hospitalization(
                patient, doctor, LocalDate.parse(date), reason, roomTypeEnum, observations
        );

        storage.addHospitalization(hospitalization);
        return new Response(true, "Hospitalización solicitada correctamente", hospitalization.serialize());
    }

    public Response approveHospitalization(String hospitalizationId, String doctorId) {

        if (hospitalizationId == null || hospitalizationId.trim().isEmpty()) {
            return new Response(false, "El ID de la hospitalización no puede estar vacío", null);
        }

        StorageHospital storage = StorageHospital.getInstance();

        Hospitalization hospitalization = storage.getHospitalization(hospitalizationId);
        if (hospitalization == null) {
            return new Response(false, "No existe una hospitalización con ese ID", null);
        }
        if (hospitalization.getStatus() != HospitalizationStatus.REQUESTED) {
            return new Response(false, "La hospitalización debe estar en estado REQUESTED para ser aprobada", null);
        }

        hospitalization.setStatus(HospitalizationStatus.ONGOING);
        return new Response(true, "Hospitalización aprobada correctamente", hospitalization.serialize());
    }

    public Response denyHospitalization(String hospitalizationId, String doctorId) {

        if (hospitalizationId == null || hospitalizationId.trim().isEmpty()) {
            return new Response(false, "El ID de la hospitalización no puede estar vacío", null);
        }

        StorageHospital storage = StorageHospital.getInstance();

        Hospitalization hospitalization = storage.getHospitalization(hospitalizationId);
        if (hospitalization == null) {
            return new Response(false, "No existe una hospitalización con ese ID", null);
        }
        if (hospitalization.getStatus() != HospitalizationStatus.REQUESTED) {
            return new Response(false, "La hospitalización debe estar en estado REQUESTED para ser denegada", null);
        }

        hospitalization.setStatus(HospitalizationStatus.CANCELED);
        return new Response(true, "Solicitud de hospitalización denegada correctamente", hospitalization.serialize());
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

        StorageHospital storage = StorageHospital.getInstance();

        Appointment appointment = storage.getAppointment(appointmentId);
        if (appointment == null) {
            return new Response(false, "No existe una cita con ese ID", null);
        }
        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            return new Response(false, "La cita debe estar en estado PENDING para hospitalizar desde ella", null);
        }

        RoomType roomTypeEnum;
        try {
            roomTypeEnum = RoomType.valueOf(roomType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new Response(false, "El tipo de habitación no es válido", null);
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);

        Doctor doctor = storage.getDoctor(Long.parseLong(doctorId));
        if (doctor == null) {
            return new Response(false, "No existe un doctor con ese ID", null);
        }

        Hospitalization hospitalization = new Hospitalization(
                appointment.getPatient(), doctor,
                LocalDate.parse(date), reason, roomTypeEnum, observations,
                HospitalizationStatus.ONGOING
        );

        storage.addHospitalization(hospitalization);
        return new Response(true, "Paciente hospitalizado y cita completada correctamente", hospitalization.serialize());
    }

    public Response getHospitalizations(String patientId) {

        if (patientId == null || patientId.trim().isEmpty()) {
            return new Response(false, "El ID del paciente no puede estar vacío", null);
        }

        StorageHospital storage = StorageHospital.getInstance();

        Patient patient = storage.getPatient(Long.parseLong(patientId));
        if (patient == null) {
            return new Response(false, "No existe un paciente con ese ID", null);
        }

        ArrayList<HashMap<String, Object>> serialized = new ArrayList<>();
        for (Hospitalization h : storage.getAllHospitalizations().values()) {
            if (h.getPatient().getId() == patient.getId()) {
                serialized.add(h.serialize());
            }
        }

        return new Response(true, "Hospitalizaciones obtenidas correctamente", serialized);
    }
}