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
    private int likes;

    private Artist(final Builder builder) {
        super(builder);
        this.albums = builder.albums;
        this.events = builder.events;
        this.merch = builder.merch;
        this.likes = builder.likes;
    }

    public static class Builder extends User.Builder {
        private ArrayList<Album> albums = new ArrayList<>();
        private ArrayList<Event> events = new ArrayList<>();
        private ArrayList<Merch> merch = new ArrayList<>();
        private int likes;

        public Builder(final String username, final String type, final int age, final String city) {
            super(username, type, age, city);
        }

        /**
         * Sets the list of albums for the user being built.
         *
         * @param albumsToAssign The list of albums to set.
         * @return This builder for method chaining.
         */
        public Builder albums(final ArrayList<Album> albumsToAssign) {
            this.albums = albumsToAssign;
            return this;
        }

        /**
         * Sets the list of events for the user being built.
         *
         * @param eventsToAssign The list of events to set.
         * @return This builder for method chaining.
         */
        public Builder events(final ArrayList<Event> eventsToAssign) {
            this.events = eventsToAssign;
            return this;
        }

        /**
         * Sets the list of merchandises for the user being built.
         *
         * @param merchToAssign The list of merchandises to set.
         * @return This builder for method chaining.
         */
        public Builder merch(final ArrayList<Merch> merchToAssign) {
            this.merch = merchToAssign;
            return this;
        }

        /**
         * Sets the number of likes for the user being built.
         *
         * @param likesToAssign The number of likes to set.
         * @return This builder for method chaining.
         */
        public Builder likes(final int likesToAssign) {
            this.likes = likesToAssign;
            return this;
        }

        /**
         * Builds the artist.
         * @return The built artist.
         */
        @Override
        public Artist build() {
            return new Artist(this);
        }
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
