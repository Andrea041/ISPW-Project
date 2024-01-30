package com.example.codiceprogetto.logic.observer;

public abstract class Subject {
    public abstract void attach(Observer o);
    public abstract void detach(Observer o);
    protected abstract void notifyObserver();
}
