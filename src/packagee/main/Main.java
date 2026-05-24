/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.main;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import packagee.controller.AppointmentController;
import packagee.controller.DataController;
import packagee.controller.DoctorController;
import packagee.controller.HospitalizationController;
import packagee.controller.LoginController;
import packagee.controller.PatientController;
import packagee.model.storage.StorageHospital;
import packagee.util.JSONLoader;
import packagee.view.LoginView;

/**
 *
 * @author sierr
 */
public class Main {

    public static void main(String[] args) {

        // 1. Cargar los usuarios desde tu JSON
        JSONLoader.loadUsers("src/Users/UsersJSON.json");

        // 2. Instanciar el almacenamiento (el modelo centralizado)
        StorageHospital storage = StorageHospital.getInstance();

        // 3. Instanciar todos los controladores inyectándoles el storage (Cumpliendo SOLID)
        LoginController loginController = new LoginController(storage);
        PatientController patientController = new PatientController(storage);
        DoctorController doctorController = new DoctorController(storage);
        AppointmentController appointmentController = new AppointmentController(storage);
        HospitalizationController hospitalizationController = new HospitalizationController(storage);
        DataController dataController = new DataController(storage);

        // 4. Configurar el diseño visual (FlatLaf)
        System.setProperty("flatlaf.useNativeLibrary", "false");
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF: " + ex.getMessage());
        }

        // 5. Crear y mostrar el formulario inyectando los controladores
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Aquí le pasamos todos los controladores a tu vista inicial
                new LoginView(
                    loginController, 
                    patientController, 
                    doctorController, 
                    appointmentController, 
                    hospitalizationController, 
                    dataController
                ).setVisible(true);
            }
        });
    }
}
