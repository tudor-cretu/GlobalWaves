package app.utils;

import lombok.Getter;

@Getter
public class Event {
    String name;
    String date;
    String description;

    public Event(String name, String date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }
}
