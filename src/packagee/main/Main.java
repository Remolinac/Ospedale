/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.main;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import packagee.util.JSONLoader;
import packagee.view.LoginView;

/**
 *
 * @author sierr
 */
public class Main {

    public static void main(String[] args) {

        JSONLoader.loadUsers("json/users.json");

        packagee.model.storage.StorageHospital storage = packagee.model.storage.StorageHospital.getInstance();
        System.out.println("Admin cargado: " + storage.getAdmin());
        if (storage.getAdmin() != null) {
            System.out.println("Username: " + storage.getAdmin().getUsername());
            System.out.println("Password: " + storage.getAdmin().getPassword());
        }

        System.setProperty("flatlaf.useNativeLibrary", "false");

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginView().setVisible(true);
            }
        });

    }
}
