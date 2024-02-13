package com.example.codiceprogetto.logic.observer;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
    List<Observer> observers = new ArrayList<>();
    public void attach(Observer o);
    public void detach(Observer o);
    public void notifyObserver();
}
