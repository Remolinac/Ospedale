/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.model;

import java.util.HashMap;

/**
 *
 * @author edangulo
 */
public class Administrator extends User implements ISerializable{
    
    public Administrator(long id, String username, String firstname, String lastname, String password) {
        super(id, username, firstname, lastname, password);
    }

    @Override
    public HashMap<String, Object> serialize() {
        HashMap<String, Object> serializedData = new HashMap<>();
        
        serializedData.put("id", this.id);
        serializedData.put("username", this.username);
        serializedData.put("firstname", this.firstname);
        serializedData.put("lastname", this.lastname);
        serializedData.put("password", this.password);
        
        return serializedData;
    }
    
}
