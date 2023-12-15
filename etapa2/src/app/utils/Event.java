package app.utils;

import lombok.Getter;

@Getter
public class Event {
    private final String name;
    private final String date;
    private final String description;

    public Event(final String name, final String date, final String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }
}
