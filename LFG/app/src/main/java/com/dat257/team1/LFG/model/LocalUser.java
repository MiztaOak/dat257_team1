package com.dat257.team1.LFG.model;

import java.util.ArrayList;
import java.util.List;

public class LocalUser extends User {
    private List<User> friends;

    public LocalUser(String id, String name, String email, String phoneNumber,List<User> friends) {
        super(id, name, email, phoneNumber);
        this.friends = friends;
    }

    public LocalUser(String id, String name, String email, String phoneNumber) {
        super(id, name, email, phoneNumber);
        friends = new ArrayList<>();
    }
}
