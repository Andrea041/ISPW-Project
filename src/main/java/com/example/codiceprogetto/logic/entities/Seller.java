package com.example.codiceprogetto.logic.entities;

import com.example.codiceprogetto.logic.enumeration.UserType;

public class Seller extends User{
    public Seller(String email, String password, UserType userType, String name, String surname) {
        super(email, password, userType, name, surname);
    }
}
