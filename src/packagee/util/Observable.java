/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sierr
 */
public class Observable {

    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    protected void notifyObservers(String event) {
        for (Observer o : observers) {
            o.update(event);
        }
    }
}
