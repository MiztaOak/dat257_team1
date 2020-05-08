package com.dat257.team1.LFG.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id; // since many users can have the same name
    private String name;
    private int phoneNumber;
    private String email;
    private List<User> friendList;


    public User(String id, String name, String email, int phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.friendList = new ArrayList<User>();
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * A method that adds a new friend to the friendList
     *
     * @param friend the friend that want to be added to the friendList
     */
    public void addFriend(User friend) {
        this.friendList.add(friend);
    }

    /**
     * A method that deletes a friend from the friendList
     *
     * @param friend the friend that want to be deleted from the friendList
     */
    public void deleteFriend(User friend) {
        this.friendList.remove(friend);
    }

    public User toUser() { return new User(id, name, email, phoneNumber);}
}
