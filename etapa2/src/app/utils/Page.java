package app.utils;

import lombok.Getter;

@Getter
public class Page {
    private final String type; // home, likedContent, artist, host

    public Page(final String type) {
        this.type = type;
    }
}
