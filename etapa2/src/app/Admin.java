package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.User;
import app.utils.Enums;
import app.utils.Event;
import app.utils.Merch;
import app.utils.show.ShowAlbum;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * The type Admin.
 */
@Setter
@Getter
public final class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Artist> artists = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static List<Album> albums = new ArrayList<>();
    private static int timestamp = 0;
    private static final int LIMIT = 5;

    private Admin() {
    }

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getType(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }


    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                        episodeInput.getDuration(),
                        episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public static ArrayList<Album> getAlbums() {
        return new ArrayList<>(albums);
    }

    /**
     * Gets artists.
     *
     * @return the artists
     */
    public static ArrayList<Artist> getArtists() {
        return new ArrayList<>(artists);
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets artist.
     *
     * @param username the username
     * @return the artist
     */
    private static Artist getArtist(String username) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (!Objects.equals(user.getType(), "artist") && user.getConnectionStatus().equals("ONLINE")) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    private static List<String> getOfflineUsers() {
        List<String> offlineUsernames = new ArrayList<>();
        for (User user : users) {
            if (user.getConnectionStatus().equals("OFFLINE")) {
                offlineUsernames.add(user.getUsername());
            }
        }
        return offlineUsernames;
    }

    public static List<String> getUsers() {
        List<String> simpleUserUsernames = new ArrayList<>();
        for (User user : users) {
            if (user.getType() == null || user.getType().equals("user")) {
                simpleUserUsernames.add(user.getUsername());
            }
        }
        List<String> artistUsernames = new ArrayList<>();
        for (Artist artist : artists) {
            artistUsernames.add(artist.getUsername());
        }
        List<String> hostUsernames = new ArrayList<>();
        // TO BE ADDED
        List<String> allUsernames = new ArrayList<>();
        allUsernames.addAll(simpleUserUsernames);
        allUsernames.addAll(artistUsernames);
        allUsernames.addAll(hostUsernames);
        return allUsernames;
    }

    public static String addUser(String username, String type, Integer age, String city) {
        User toBeAddedUser = new User(username, type, age, city);
        toBeAddedUser.setConnectionStatus(Enums.ConnectionStatus.ONLINE);
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return "The username " + username + " is already taken.";
            }
        }
        if (type.equals("artist")) {
            artists.add(new Artist(username, type, age, city));
        }
        users.add(toBeAddedUser);
        return "The username " + username + " has been added successfully.";
    }

    public static String addAlbum(String username, String name, int timestamp,
                                  String releaseYear, ArrayList<SongInput> albumSongs, String description) {
        User user = getUser(username);
        ArrayList<Song> tracks = new ArrayList<>();
        for (SongInput song : albumSongs) {
            Song track = new Song(song.getName(), song.getDuration(), song.getAlbum(),
                    song.getTags(), song.getLyrics(), song.getGenre(), song.getReleaseYear(), song.getArtist());
            for (Song auxTrack : tracks) {
                if (auxTrack.getName().equals(track.getName())) {
                    return username + " has the same song at least twice in this album.";
                }
            }
            tracks.add(track);
            songs.add(track);
        }
        if (user == null) {
            return "The username " + username + " doesn't exist.";
        }
        if (user.getType() != null && !user.getType().equals("artist")) {
            return username + " is not an artist.";
        }
        for (Song track : tracks) {
            if (!track.getArtist().equals(username)) {
                return "The artist of the song " + track.getName() + " is not " + username + "!";
            }
        }

        String owner = username;
        if (Objects.equals(user.getType(), "artist")) {
            Artist artist = getArtist(username);
            if (artist != null) {
                for (Album album : artist.getAlbums()) {
                    if (album.getName().equals(name)) {
                        return username + " has another album with the same name.";
                    }
                }
                artist.addAlbum(username, name, owner, timestamp, releaseYear, tracks, description);
            }
        }

        Album album = new Album(username, name, owner, timestamp, releaseYear, tracks, description);
        albums.add(album);
        return username + " has added new album successfully.";
    }

    public static ArrayList<ShowAlbum> showAlbums(String username) {
        Artist artist = getArtist(username);

        if (artist == null) {
            return null;
        }
        return artist.showAlbums();
    }

    public static String addEvent(String username, String name, String date, String description) {
        if (!verifyArtist(username).equals("is artist.")) {
            return verifyArtist(username);
        } else {
            Artist artist = getArtist(username);
            if (artist != null) {
                for (Event event : artist.getEvents()) {
                    if (event.getName().equals(name)) {
                        return username + " has another event with the same name.";
                    }
                }

                int day = Integer.parseInt(date.split("-")[0]);
                if (day < 1 || day > 31) {
                    return "Event for " + username + " does not have a valid date.";
                }
                int month = Integer.parseInt(date.split("-")[1]);
                if (month < 1 || month > 12) {
                    return "Event for " + username + " does not have a valid date.";
                }
                if (month == 2 && day > 29) {
                    return "Event for " + username + " does not have a valid date.";
                }
                int year = Integer.parseInt(date.split("-")[2]);
                if (year < 1900 || year > 2023) {
                    return "Event for " + username + " does not have a valid date.";
                }

                artist.addEvent(name, date, description);
            }
            return username + " has added new event successfully.";
        }
    }

    public static String addMerch(String username, String name, String price, String description) {
        if (!verifyArtist(username).equals("is artist.")) {
            return verifyArtist(username);
        } else {
            Artist artist = getArtist(username);
            if (artist != null) {
                for (Merch merch : artist.getMerch()) {
                    if (merch.getName().equals(name)) {
                        return username + " has merchandise with the same name.";
                    }
                }
                int priceInt = Integer.parseInt(price);
                if (priceInt < 0) {
                    return "Price for merchandise can not be negative.";
                }
                artist.addMerch(name, description, priceInt);
            }
            return username + " has added new merchandise successfully.";
        }
    }

    /***
     * Verify if the user is an artist.
     * @param username - name of user
     * @return a string with the result of the verification
     */
    public static String verifyArtist(String username) {
        User user = getUser(username);
        if (user == null) {
            return "The username " + username + " doesn't exist.";
        }
        if (user.getType() != null && !user.getType().equals("artist")) {
            return username + " is not an artist.";
        }
        Artist artist = new Artist(username, user.getType(), user.getAge(), user.getCity());
        boolean found = false;
        for (Artist auxArtist : artists) {
            if (auxArtist.getUsername().equals(artist.getUsername())) {
                found = true;
                break;
            }
        }
        if (!found) {
            return username + " is not an artist.";
        } else return "is artist.";
    }

    public static String deleteUser(String username) {
        User user = getUser(username);
        if (user == null) {
            return "The username " + username + " doesn't exist.";
        }


    }

    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        artists = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        albums = new ArrayList<>();
        timestamp = 0;
    }
}
