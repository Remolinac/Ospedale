/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import packagee.model.Appointment;
import packagee.model.AppointmentStatus;
import packagee.model.Doctor;
import packagee.model.Patient;
import packagee.model.Prescription;
import packagee.model.Specialty;
import packagee.model.Prescription;
import packagee.model.storage.StorageHospital;
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

        StorageHospital storage = StorageHospital.getInstance();

        Patient patient = storage.getPatient(Long.parseLong(patientId));
        if (patient == null) {
            return new Response(false, "No existe un paciente con ese ID", null);
        }

        Doctor doctor = storage.getDoctor(Long.parseLong(doctorId));
        if (doctor == null) {
            return new Response(false, "No existe un doctor con ese ID", null);
        }

        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);
        LocalDateTime datetime = LocalDateTime.of(localDate, localTime);

        if (!doctor.isAvailable(localDate)) {
            return new Response(false, "El doctor no tiene disponibilidad en ese horario", null);
        }

        Appointment appointment = new Appointment(
                patient, doctor, doctor.getSpecialty(), datetime, reason, true
        );

        storage.addAppointment(appointment);
        return new Response(true, "Cita solicitada correctamente", appointment.serialize());
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

        Specialty specialtyEnum;
        try {
            specialtyEnum = Specialty.valueOf(specialty.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new Response(false, "La especialidad no es válida", null);
        }

        StorageHospital storage = StorageHospital.getInstance();

        Patient patient = storage.getPatient(Long.parseLong(patientId));
        if (patient == null) {
            return new Response(false, "No existe un paciente con ese ID", null);
        }

        LocalDate localDate = LocalDate.parse(date);
        Doctor doctor = storage.getAvailableDoctorBySpecialty(specialtyEnum, localDate);
        if (doctor == null) {
            return new Response(false, "No hay doctores disponibles con esa especialidad en el horario solicitado", null);
        }

        LocalTime localTime = LocalTime.parse(time);
        LocalDateTime datetime = LocalDateTime.of(localDate, localTime);

        Appointment appointment = new Appointment(
                patient, doctor, specialtyEnum, datetime, reason, false
        );

        storage.addAppointment(appointment);
        return new Response(true, "Cita solicitada correctamente", appointment.serialize());
    }

    public Response acceptAppointment(String appointmentId, String doctorId) {

        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            return new Response(false, "El ID de la cita no puede estar vacío", null);
        }

        StorageHospital storage = StorageHospital.getInstance();

        Appointment appointment = storage.getAppointment(appointmentId);
        if (appointment == null) {
            return new Response(false, "No existe una cita con ese ID", null);
        }
        if (appointment.getStatus() != AppointmentStatus.REQUESTED) {
            return new Response(false, "La cita debe estar en estado REQUESTED para ser aceptada", null);
        }

        appointment.setStatus(AppointmentStatus.PENDING);
        return new Response(true, "Cita aceptada correctamente", appointment.serialize());
    }

    public Response completeAppointment(String appointmentId, String doctorId) {

        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            return new Response(false, "El ID de la cita no puede estar vacío", null);
        }

        StorageHospital storage = StorageHospital.getInstance();

        Appointment appointment = storage.getAppointment(appointmentId);
        if (appointment == null) {
            return new Response(false, "No existe una cita con ese ID", null);
        }
        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            return new Response(false, "La cita debe estar en estado PENDING para ser completada", null);
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        return new Response(true, "Cita completada correctamente", appointment.serialize());
    }

    public Response cancelAppointment(String appointmentId, String patientId) {

        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            return new Response(false, "El ID de la cita no puede estar vacío", null);
        }

        StorageHospital storage = StorageHospital.getInstance();

        Appointment appointment = storage.getAppointment(appointmentId);
        if (appointment == null) {
            return new Response(false, "No existe una cita con ese ID", null);
        }
        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            return new Response(false, "No se puede cancelar una cita ya completada", null);
        }

        appointment.setStatus(AppointmentStatus.CANCELED);
        return new Response(true, "Cita cancelada correctamente", appointment.serialize());
    }

    public Response rescheduleAppointment(String appointmentId, String doctorId,
            String newTime, String rescheduleReason) {

        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            return new Response(false, "El ID de la cita no puede estar vacío", null);
        }
        if (!Validator.isValidTime(newTime)) {
            return new Response(false, "La nueva hora no es válida, use formato hh:mm y minutos en 00, 15, 30 o 45", null);
        }

        StorageHospital storage = StorageHospital.getInstance();

        Appointment appointment = storage.getAppointment(appointmentId);
        if (appointment == null) {
            return new Response(false, "No existe una cita con ese ID", null);
        }

        LocalDate sameDate = appointment.getDatetime().toLocalDate();
        LocalTime newLocalTime = LocalTime.parse(newTime);
        appointment.setDatetime(LocalDateTime.of(sameDate, newLocalTime));

        if (rescheduleReason != null && !rescheduleReason.trim().isEmpty()) {
            appointment.setReason(appointment.getReason() + " | Reagendado: " + rescheduleReason);
        }

        return new Response(true, "Cita reagendada correctamente", appointment.serialize());
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

        StorageHospital storage = StorageHospital.getInstance();

        Appointment appointment = storage.getAppointment(appointmentId);
        if (appointment == null) {
            return new Response(false, "No existe una cita con ese ID", null);
        }
        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            return new Response(false, "Solo se pueden prescribir medicamentos en citas en estado PENDING", null);
        }

        new Prescription(appointment, medicationName, dose, administrationRoute,
                treatmentDuration, additionalInstructions, frequency);

        return new Response(true, "Medicamento prescrito correctamente", appointment.serialize());
    }

    public Response getPatientAppointments(String patientId) {

        if (patientId == null || patientId.trim().isEmpty()) {
            return new Response(false, "El ID del paciente no puede estar vacío", null);
        }

        StorageHospital storage = StorageHospital.getInstance();

        Patient patient = storage.getPatient(Long.parseLong(patientId));
        if (patient == null) {
            return new Response(false, "No existe un paciente con ese ID", null);
        }

        List<Appointment> appointments = storage.getSortedAppointmentsForPatient(Long.parseLong(patientId));

        ArrayList<HashMap<String, Object>> serialized = new ArrayList<>();
        for (Appointment a : appointments) {
            serialized.add(a.serialize());
        }

        return new Response(true, "Citas obtenidas correctamente", serialized);
    }

    public Response getDoctorAppointments(String doctorId, boolean pendingOnly) {

        if (doctorId == null || doctorId.trim().isEmpty()) {
            return new Response(false, "El ID del doctor no puede estar vacío", null);
        }

        StorageHospital storage = StorageHospital.getInstance();

        Doctor doctor = storage.getDoctor(Long.parseLong(doctorId));
        if (doctor == null) {
            return new Response(false, "No existe un doctor con ese ID", null);
        }

        List<Appointment> appointments = storage.getSortedAppointmentsForDoctor(Long.parseLong(doctorId), pendingOnly);

        ArrayList<HashMap<String, Object>> serialized = new ArrayList<>();
        for (Appointment a : appointments) {
            serialized.add(a.serialize());
        }

        return new Response(true, "Citas obtenidas correctamente", serialized);
    }
}