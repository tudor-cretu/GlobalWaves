package app.user;

import app.audio.Collections.Album;
import app.audio.Files.Song;
import app.utils.Event;
import app.utils.Merch;
import app.utils.show.ShowAlbum;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Getter
@Setter
public class Artist extends User {
    private ArrayList<Album> albums;
    private ArrayList<Event> events;
    private ArrayList<Merch> merch;

    public Artist(final String username, final String type, final int age, final String city) {
        super(username, type, age, city);
        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merch = new ArrayList<>();
    }

    public void addAlbum(final String username, final String name, final String owner,
                         final int timestamp, final String releaseYear, final ArrayList<Song> songs, final String description) {
        this.getAlbums().add(new Album(username, name, owner, timestamp, releaseYear, songs, description));
    }

    public ArrayList<ShowAlbum> showAlbums() {
        ArrayList<ShowAlbum> showAlbums = new ArrayList<>();
        for (Album album : this.getAlbums()) {
            ShowAlbum showAlbum = new ShowAlbum(album.getName(), album.getSongsNames());
            showAlbums.add(showAlbum);
        }

        return showAlbums;
    }


    public void addEvent(final String name, final String date, final String description) {
        this.getEvents().add(new Event(name, date, description));
    }

    public void addMerch(final String name, final String description, final int priceInt) {
        this.getMerch().add(new Merch(name, description, priceInt));
    }
}
