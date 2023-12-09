package app.user;

import app.audio.Collections.Album;
import app.audio.Files.Song;
import app.utils.show.ShowAlbum;

import java.util.ArrayList;

public class Artist extends User {

    public Artist(final String username, final String type, final int age, final String city) {
        super(username, type, age, city);
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


}
