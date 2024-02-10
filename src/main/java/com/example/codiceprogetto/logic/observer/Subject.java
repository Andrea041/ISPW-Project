package com.example.codiceprogetto.logic.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private List<Observer> observers;

    protected Subject() {
        observers = new ArrayList<>();
    }

    public void attach(Observer o) {
        observers.add(o);
    }
    public void detach(Observer o) {
        observers.remove(o);
    }
    protected void notifyObserver() {
        for(Observer o : observers) {
            o.update();
        }
    }
}
