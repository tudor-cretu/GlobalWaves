package app.user;

import app.audio.Collections.Album;
import app.audio.Files.Song;
import app.utils.Event;
import app.utils.Merch;
import app.utils.show.ShowAlbum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public final class Artist extends User {
    private ArrayList<Album> albums;
    private ArrayList<Event> events;
    private ArrayList<Merch> merch;

    public Artist(final String username, final String type, final int age, final String city) {
        super(username, type, age, city);
        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merch = new ArrayList<>();
    }

    /**
     * Add album to the artist albums list.
     * @param username the artist's username
     * @param name the album name
     * @param owner the album owner
     * @param timestamp the album timestamp
     * @param releaseYear the album release year
     * @param songs the album songs
     * @param description the album description
     */
    public void addAlbum(final String username, final String name, final String owner,
                         final int timestamp, final String releaseYear,
                         final ArrayList<Song> songs, final String description) {
        this.getAlbums().add(new Album(username, name, owner, timestamp,
                releaseYear, songs, description));
    }

    /**
     * Show the artist's albums.
     * @return  the artist's albums
     */
    public ArrayList<ShowAlbum> showAlbums() {
        ArrayList<ShowAlbum> showAlbums = new ArrayList<>();
        for (Album album : this.getAlbums()) {
            ShowAlbum showAlbum = new ShowAlbum(album.getName(), album.getSongsNames());
            showAlbums.add(showAlbum);
        }

        return showAlbums;
    }

    /**
     * Add event to the artist events list.
     * @param name the event name
     * @param date the event date
     * @param description the event description
     */
    public void addEvent(final String name, final String date, final String description) {
        this.getEvents().add(new Event(name, date, description));
    }

    /**
     * Add merch to the artist merch list.
     * @param name the merch name
     * @param description the merch description
     * @param priceInt the merch price
     */
    public void addMerch(final String name, final String description, final int priceInt) {
        this.getMerch().add(new Merch(name, description, priceInt));
    }
}
