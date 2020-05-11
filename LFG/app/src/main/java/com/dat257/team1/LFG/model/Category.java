package com.dat257.team1.LFG.model;

/**
 * Enum class that categorizes different type of activities.
 *
 * @author Jakobew
 */
public enum Category {

    Swimming(Group.Sports, "Swimming"),
    Gym(Group.Sports, "Gym"),
    Basketball(Group.Sports, "Basketball"),
    Football(Group.Sports, "Football"),
    Tennis(Group.Sports, "Tennis"),
    Sports(Group.Sports, "Sports"),
    Hiking(Group.Sports, "Hiking"),
    Sailing(Group.Sports, "Sailing"),
    Fishing(Group.Sports, "Fishing"),
    Gaming(Group.Gaming, "Gaming"),
    DrinkingParty(Group.Leisure, "Drinking Party"),
    Conference(Group.Conference, "Conference"), Seminar(Group.Conference, "Seminar"),
    Networking_sessions(Group.Conference, "Networking sessions"),
    Expo(Group.Conference, "Expo"),
    Festival(Group.Festival, "Festival"),
    Other(Group.Other, "Other");

    private Group group;
    private final String name;

    Category(Group group, String name) {
        this.group = group;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public enum Group {
        Sports,
        Other,
        Gaming,
        Leisure,
        Conference,
        Festival
    }
}
