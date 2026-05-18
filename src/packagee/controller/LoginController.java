/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.controller;

import packagee.util.Response;

/**
 *
 * @author sierr
 */
public class LoginController {

    public Response login(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            return new Response(false, "El nombre de usuario no puede estar vacío", null);
        }

        if (password == null || password.trim().isEmpty()) {
            return new Response(false, "La contraseña no puede estar vacía", null);
        }

        // TODO: buscar usuario en Hospital (cuando María tenga el DataStore)
        // User user = Hospital.getInstance().findUserByUsername(username);
        // if (user == null) return new Response(false, "Usuario no encontrado", null);
        // if (!user.getPassword().equals(password)) return new Response(false, "Contraseña incorrecta", null);
        // return new Response(true, "Login exitoso", user.getRole()); // rol: "ADMIN", "PATIENT", "DOCTOR"

        return new Response(false, "Login no implementado aún", null);
    }
}