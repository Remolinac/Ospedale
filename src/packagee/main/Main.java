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
        JSONLoader.loadUsers("json/users.json");
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }
}