package app.utils;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;

import java.util.ArrayList;

public class Page {
    String type; // home, likedContent, artist, host
    ArrayList<Song> likedSongs;
    ArrayList<Playlist> followedPlaylists;
    ArrayList<Album> albums;
    ArrayList<Event> events;
    ArrayList<Merch> merch;

    public Page(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Song> getLikedSongs() {
        return likedSongs;
    }

    public ArrayList<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }

}
