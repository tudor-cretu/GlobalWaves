package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;

public final class Album extends AudioCollection {
    @Getter
    private final ArrayList<Song> songs;
    private final String description;
    private final String username;
    private int timestamp;
    private String releaseYear;
    @Getter
    private int likes;


    public Album(final String username, final String name, final String owner,
                 final int timestamp, final String releaseYear,
                 final ArrayList<Song> songs, final String description) {
        super(name, owner);
        this.songs = songs;
        this.releaseYear = releaseYear;
        this.username = username;
        this.timestamp = timestamp;
        this.description = description;
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    /**
     * Gets the song names of an album
     * @return      the list of song names
     */
    public ArrayList<String> getSongsNames() {
        ArrayList<String> songsNames = new ArrayList<>();
        for (Song song : songs) {
            songsNames.add(song.getName());
        }
        return songsNames;
    }

    public void setLikes(final int likes) {
        this.likes = likes;
    }

}
