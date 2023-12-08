package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.utils.Enums;

import java.util.ArrayList;

public final class Album extends AudioCollection {
    private final ArrayList<Song> songs;
    private final String description;
    private final String username;
    private int timestamp;
    private String releaseYear;


    public Album(final String username, final String name, final String owner,
                 final int timestamp, final String releaseYear, final ArrayList<Song> songs, final String description) {
        super(name, owner);
        this.songs = songs;
        this.releaseYear = releaseYear;
        this.username = username;
        this.timestamp = timestamp;
        this.description = description;
    }

    public boolean containsSong(final Song song) {
        return songs.contains(song);
    }

    /**
     * Add song.
     *
     * @param song the song
     */
    public void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Remove song.
     *
     * @param song the song
     */
    public void removeSong(final Song song) {
        songs.remove(song);
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }
}
