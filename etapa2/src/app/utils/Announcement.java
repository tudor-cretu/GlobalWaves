package app.utils;

import lombok.Getter;

@Getter
public class Announcement {
    String name;
    String description;

    public Announcement(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
