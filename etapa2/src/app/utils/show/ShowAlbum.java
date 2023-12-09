package app.utils.show;

import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ShowAlbum {
    private final String name;
    private final ArrayList<String> songs;

    public ShowAlbum(final String name, final ArrayList<String> songs) {
        this.name = name;
        this.songs = songs;
    }


}
