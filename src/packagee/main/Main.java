/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.main;

import packagee.util.JSONLoader;
import packagee.view.LoginView;
/**
 *
 * @author sierr
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Directorio actual: " + System.getProperty("user.dir"));
        JSONLoader.loadUsers("json/users.json");

        // Debug temporal - borralo después
        packagee.model.storage.StorageHospital storage = packagee.model.storage.StorageHospital.getInstance();
        System.out.println("Admin cargado: " + storage.getAdmin());
        if (storage.getAdmin() != null) {
            System.out.println("Username: " + storage.getAdmin().getUsername());
            System.out.println("Password: " + storage.getAdmin().getPassword());
        }

        java.awt.EventQueue.invokeLater(() -> new LoginView().setVisible(true));
    }
}