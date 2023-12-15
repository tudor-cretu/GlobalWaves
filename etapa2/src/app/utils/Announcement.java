package app.utils;

import lombok.Getter;

@Getter
public class Announcement {
    private final String name;
    private final String description;

    public Announcement(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

}
