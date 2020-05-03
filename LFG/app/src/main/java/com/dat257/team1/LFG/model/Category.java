package com.dat257.team1.LFG.model;

/**
 * Enum class that categorizes different type of activities.
 * @author Jakobew
 */
public enum Category {

    Football (Group.Sports), Tennis (Group.Sports), Sports (Group.Sports), Hiking (Group.Sports), Sailing (Group.Sports), Fishing (Group.Sports),

    Other (Group.Other),

    Gaming (Group.Gaming),

    DrinkingParty (Group.Leisure),

    Conference (Group.Conference), Seminar (Group.Conference), Networking_sessions (Group.Conference), Expo (Group.Conference),

    Festival (Group.Festival);

    private Group group;

    Category(Group group) {
        this.group = group;
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
