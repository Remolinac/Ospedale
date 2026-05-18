/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * @author berri
 */
public class Validator {

    // 1. Email: formato XXXXX@XXXXX.com
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$");
    }

    // 2. Teléfono: exactamente 10 dígitos
    public static boolean isValidPhone(String phone) {
        return phone.matches("^\\d{10}$");
    }

    // 3. Licencia Doctor: L-XXXXXXXXXX MTL
    public static boolean isValidLicense(String license) {
        return license.matches("^L-\\d{10} MTL$");
    }

    // 4. Oficina Doctor: O-XXX
    public static boolean isValidOffice(String office) {
        return office.matches("^O-\\d{3}$");
    }

    // 5. Fecha: AAAA-MM-DD y que sea una fecha real
    public static boolean isValidDate(String date) {
        if (!date.matches("^\\d{4}-\\d{2}-\\d{2}$")) return false;
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // 6. ID: exactamente 12 dígitos y mayor que 0
    public static boolean isValidId(String id) {
        if (!id.matches("^\\d{12}$")) return false;
        return !id.equals("000000000000");
    }

    // 7. Hora: formato hh:mm en 24h, minutos solo en {00, 15, 30, 45}
    public static boolean isValidTime(String time) {
        if (!time.matches("^([01]\\d|2[0-3]):[0-5]\\d$")) return false;
        String minutes = time.split(":")[1];
        return minutes.equals("00") || minutes.equals("15")
                || minutes.equals("30") || minutes.equals("45");
    }

    // 8. Contraseña: no vacía y que coincida con la confirmación
    public static boolean isValidPassword(String password, String confirm) {
        return password != null && !password.isEmpty() && password.equals(confirm);
    }
}