package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.utils.Announcement;
import app.utils.Enums;
import app.utils.Event;
import app.utils.Merch;
import app.utils.show.ShowAlbum;
import app.utils.show.ShowPodcast;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static checker.CheckerConstants.*;

/**
 * The type Admin.
 */
@Setter
@Getter
public final class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Artist> artists = new ArrayList<>();
    private static List<Host> hosts = new ArrayList<>();
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
            users.add(new User.Builder(userInput.getUsername(),
                    userInput.getType(), userInput.getAge(),
                    userInput.getCity()).build());
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
     * Gets hosts.
     *
     * @return the hosts
     */
    public static ArrayList<Host> getHosts() {
        return new ArrayList<>(hosts);
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
    private static Artist getArtist(final String username) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }

    /** Gets host.
     *
     * @param username the username
     * @return the host
     */
    private static Host getHost(final String username) {
        for (Host host : hosts) {
            if (host.getUsername().equals(username)) {
                return host;
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

    /**
     * Gets the usernames of all online users.
     * @return      the list of usernames
     */
    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (!Objects.equals(user.getType(), "host")
                    && !Objects.equals(user.getType(), "artist")
                    && user.getConnectionStatus().equals("ONLINE")) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    /**
     * Gets the usernames of all users.
     * @return      the list of usernames
     */
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
        for (Host host : hosts) {
            hostUsernames.add(host.getUsername());
        }
        List<String> allUsernames = new ArrayList<>();
        allUsernames.addAll(simpleUserUsernames);
        allUsernames.addAll(artistUsernames);
        allUsernames.addAll(hostUsernames);
        return allUsernames;
    }

    /**
     * Add user.
     * @param username  the username
     * @param type      the type
     * @param age       the age
     * @param city      the city
     * @return          the message of the operation
     */
    public static String addUser(final String username, final String type,
                                 final Integer age, final String city) {
        User toBeAddedUser = new User.Builder(username, type, age, city).build();
        toBeAddedUser.setConnectionStatus(Enums.ConnectionStatus.ONLINE);
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return "The username " + username + " is already taken.";
            }
        }
        if (type.equals("artist")) {
            artists.add(new Artist.Builder(username, type, age, city).build());
        }

        if (type.equals("host")) {
            hosts.add(new Host.Builder(username, type, age, city).build());
        }
        users.add(toBeAddedUser);
        return "The username " + username + " has been added successfully.";
    }

    /**
     * Add album of an artist.
     * @param username      the username
     * @param name          the name of the album
     * @param timestamp     the timestamp
     * @param releaseYear   the release year of the album
     * @param albumSongs    the songs of the album
     * @param description   the description of the album
     * @return              the message of the operation
     */
    public static String addAlbum(final String username, final String name, final int timestamp,
                                  final String releaseYear,
                                  final ArrayList<SongInput> albumSongs, final String description) {
        User user = getUser(username);
        String message = verifyArtist(username);
        if (message.equals("is artist.")) {
            Artist artist = getArtist(username);
            if (artist != null) {
                for (Album album : artist.getAlbums()) {
                    if (album.getName().equals(name)) {
                        return username + " has another album with the same name.";
                    }
                }
            }
            ArrayList<Song> tracks = new ArrayList<>();
            for (SongInput song : albumSongs) {
                Song track = new Song(song.getName(), song.getDuration(), song.getAlbum(),
                        song.getTags(), song.getLyrics(),
                        song.getGenre(), song.getReleaseYear(), song.getArtist());
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

            if (Objects.equals(user.getType(), "artist")) {
                artist = getArtist(username);
                if (artist != null) {
                    for (Album album : artist.getAlbums()) {
                        if (album.getName().equals(name)) {
                            return username + " has another album with the same name.";
                        }
                    }
                    artist.addAlbum(username, name, username,
                            timestamp, releaseYear, tracks, description);
                }
            }

            Album album = new Album(username, name,
                    username, timestamp, releaseYear, tracks, description);
            albums.add(album);
            return username + " has added new album successfully.";
        }
        return message;
    }

    /**
     * Show the albums of an artist
     * @param username  the username of the artist
     * @return          the array list of albums
     */
    public static ArrayList<ShowAlbum> showAlbums(final String username) {
        Artist artist = getArtist(username);

        if (artist == null) {
            return null;
        }
        return artist.showAlbums();
    }

    /**
     * Add event of an artist.
     * @param username          the username
     * @param name              the name of the event
     * @param date              the date of the event
     * @param description       the description of the event
     * @return                  the message of the operation
     */
    public static String addEvent(final String username,
                                  final String name, final String date,
                                  final String description) {
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
                if (day < MIN_DAY || day > MAX_DAY) {
                    return "Event for " + username + " does not have a valid date.";
                }
                int month = Integer.parseInt(date.split("-")[1]);
                if (month < MIN_MONTH || month > MAX_MONTH) {
                    return "Event for " + username + " does not have a valid date.";
                }
                if (month == FEB && day > MAX_DAY_FEB) {
                    return "Event for " + username + " does not have a valid date.";
                }
                int year = Integer.parseInt(date.split("-")[2]);
                if (year < MIN_DATE || year > MAX_DATE) {
                    return "Event for " + username + " does not have a valid date.";
                }

                artist.addEvent(name, date, description);
            }
            return username + " has added new event successfully.";
        }
    }

    /**
     * Add merch of an artist.
     *
     * @param username    the username
     * @param name        the name
     * @param price       the price
     * @param description the description
     * @return            the message of the operation
     */
    public static String addMerch(final String username,
                                  final String name, final String price,
                                  final String description) {
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
    public static String verifyArtist(final String username) {
        User user = getUser(username);
        if (user == null) {
            return "The username " + username + " doesn't exist.";
        }
        if (user.getType() != null && !user.getType().equals("artist")) {
            return username + " is not an artist.";
        }
        Artist artist = new Artist.Builder(username, user.getType(),
                user.getAge(), user.getCity()).build();
        boolean found = false;
        for (Artist auxArtist : artists) {
            if (auxArtist.getUsername().equals(artist.getUsername())) {
                found = true;
                break;
            }
        }
        if (!found) {
            return username + " is not an artist.";
        } else {
            return "is artist.";
        }
    }

    /**
     * Verify if the user is a host.
     * @param username      the username
     * @return              the message of the operation
     */
    public static String verifyHost(final String username) {
        User user = getUser(username);
        if (user == null) {
            return "The username " + username + " doesn't exist.";
        }
        if (user.getType() != null && !user.getType().equals("host")) {
            return username + " is not a host.";
        }
        Host host = new Host.Builder(username, user.getType(),
                user.getAge(), user.getCity()).build();
        boolean found = false;
        for (Host auxHost : hosts) {
            if (auxHost.getUsername().equals(host.getUsername())) {
                found = true;
                break;
            }
        }
        if (!found) {
            return username + " is not a host.";
        } else {
            return "is host.";
        }
    }

    /**
     * Delete a user.
     * @param username  the username
     * @return          the message of the operation
     */
    public static String deleteUser(final String username) {
        User user = getUser(username);
        if (user == null) {
            return "The username " + username + " doesn't exist.";
        }

        if (Objects.equals(user.getType(), "artist")) {
            return deleteArtist(username);
        }

        if (Objects.equals(user.getType(), "host")) {
            return deleteHost(username);
        }

        // check if a user is currently listening to a song
        // from a playlist of the user to be deleted
        for (User auxUser : users) {
            for (Playlist playlist : user.getPlaylists()) {
                if (playlist.getOwner().equals(username)) {
                    for (Song song : playlist.getSongs()) {
                        if (auxUser.getPlayer().getCurrentAudioFile() != null
                                && auxUser.getPlayer().getCurrentAudioFile().getName() != null
                                && Objects.equals(auxUser.getPlayer()
                                        .getCurrentAudioFile().getName(), song.getName())) {
                            return username + " can't be deleted.";
                        }
                    }
                }
            }
        }

        for (Playlist playlist : user.getPlaylists()) {
            for (User auxUser : users) {
                auxUser.getFollowedPlaylists().remove(playlist);
            }
        }

        for (Playlist playlist : user.getFollowedPlaylists()) {
            playlist.setFollowers(playlist.getFollowers() - 1);
        }
        user.getPlaylists().clear();
        users.remove(user);
        return username + " was successfully deleted.";
    }

    /**
     * Delete an artist.
     * @param         username the name of the artist
     * @return        the message of the operation
     */
    public static String deleteArtist(final String username) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                for (Album album : artist.getAlbums()) {
                    for (String songName : album.getSongsNames()) {
                        for (User auxUser : users) {
                            if (auxUser.getPlayer().getCurrentAudioFile() != null
                                    && auxUser.getPlayer()
                                    .getCurrentAudioFile().getName() != null
                                    && Objects.equals(auxUser.getPlayer()
                                    .getCurrentAudioFile().getName(), songName)) {
                                return username + " can't be deleted.";
                            }
                        }
                    }
                }
                for (User auxUser : users) {
                    if (Objects.equals(auxUser.getCurrentPage().getType(), "artist")) {
                        return username + " can't be deleted.";
                    }
                }
                for (Album album : artist.getAlbums()) {
                    for (Song song : album.getSongs()) {
                        for (User auxUser : users) {
                            auxUser.getLikedSongs().remove(song);
                        }
                        songs.remove(song);
                    }
                }
            }
        }
        return username + " was successfully deleted.";
    }

    /**
     * Delete a host.
     * @param         username the username of the host
     * @return        the message of the operation
     */
    private static String deleteHost(final String username) {
        for (Host host : hosts) {
            if (host.getUsername().equals(username)) {
                for (Podcast podcast : host.getPodcasts()) {
                    for (Episode episode : podcast.getEpisodes()) {
                        for (User auxUser : users) {
                            if (auxUser.getPlayer().getCurrentAudioFile() != null
                                    && auxUser.getPlayer()
                                    .getCurrentAudioFile().getName() != null
                                    && Objects.equals(auxUser.getPlayer()
                                            .getCurrentAudioFile().getName(),
                                    episode.getName())) {
                                return username + " can't be deleted.";
                            }
                        }
                    }
                }
                for (User auxUser : users) {
                    if (Objects.equals(auxUser.getCurrentPage().getType(), "host")) {
                        return username + " can't be deleted.";
                    }
                }
            }
        }
        return username + " was successfully deleted.";
    }

    /**
     * Gets the top 5 Albums on the platform
     * based on the number of likes
     * @return              the top 5 Albums
     */
    public static ArrayList<String> getTop5Albums() {
        for (Album album : albums) {
            for (Song song : album.getSongs()) {
                for (User user : users) {
                    if (user.getLikedSongs().contains(song)) {
                        album.setLikes(album.getLikes() + 1);
                    }
                }
            }
        }

        Collections.sort(albums, Comparator.comparingInt(Album::getLikes)
                .reversed().thenComparing(Album::getName));

        ArrayList<String> top5Albums = new ArrayList<>();
        int counter = 0;
        for (Album album : albums) {
            if (counter < LIMIT) {
                top5Albums.add(album.getName());
                counter++;
            } else {
                break;
            }
        }

        return top5Albums;
    }

    /**
     * Gets the top 5 artists on the platform based on
     * the number of likes
     * @return             the top 5 artists
     */
    public static ArrayList<String> getTop5Artists() {
        for (Artist artist : artists) {
            for (Album album : artist.getAlbums()) {
                for (Song song : album.getSongs()) {
                    for (User user : users) {
                        if (user.getLikedSongs().contains(song)) {
                            artist.setLikes(artist.getLikes() + 1);
                        }
                    }
                }
            }
        }

        Collections.sort(artists, Comparator.comparingInt(Artist::getLikes)
                .reversed().thenComparing(Artist::getUsername));

        ArrayList<String> top5Artists = new ArrayList<>();
        int counter = 0;
        for (Artist artist : artists) {
            if (counter < LIMIT) {
                top5Artists.add(artist.getUsername());
                counter++;
            } else {
                break;
            }
        }

        return top5Artists;
    }

    /**
     * Add a podcast for a host.
     * @param username      the username of the host
     * @param name          the name of the podcast to be added
     * @param episodes      the episodes of the podcast
     * @return              the message of the operation
     */
    public static String addPodcast(final String username,
                                    final String name, final ArrayList<EpisodeInput> episodes) {
        String message = verifyHost(username);
        if (message.equals("is host.")) {

            List<Episode> toBeAddedEpisodes = new ArrayList<>();
            for (EpisodeInput episodeInput : episodes) {
                Episode episode = new Episode(episodeInput.getName(),
                        episodeInput.getDuration(), episodeInput.getDescription());
                if (toBeAddedEpisodes.contains(episode)) {
                    return username + " has the same episode in this podcast.";
                }
                toBeAddedEpisodes.add(episode);
            }
            Podcast podcast = new Podcast(name, username, toBeAddedEpisodes);

            for (Host host : hosts) {
                if (host.getUsername().equals(username)) {
                    for (Podcast hostPodcast : host.getPodcasts()) {
                        if (hostPodcast.getName().equals(name)) {
                            return username + " has another podcast with the same name.";
                        }
                    }
                    host.addPodcast(podcast);
                }
            }
            podcasts.add(podcast);

            return username + " has added new podcast successfully.";
        }
        return message;
    }

    /**
     * Show the podcasts of a host
     * @param username  the username
     * @return          the array list of podcasts
     */
    public static ArrayList<ShowPodcast> showPodcasts(final String username) {
        Host host = getHost(username);
        if (host == null) {
            return null;
        }
        return host.showPodcasts();
    }

    /**
     * Add announcement of a host.
     * @param username      the username
     * @param name          the name of the announcement
     * @param description   the description of the announcement
     * @return              the message of the operation
     */
    public static String addAnnouncement(final String username,
                                         final String name, final String description) {
        String message = verifyHost(username);
        if (message.equals("is host.")) {
            Host host = getHost(username);
            if (host != null && host.getAnnouncements() != null) {
                for (Announcement announcement : host.getAnnouncements()) {
                    if (announcement.getName().equals(name)) {
                        return username + " has already added an announcement with this name.";
                    }
                }
                host.addAnnouncement(name, description);
            }
            return username + " has successfully added new announcement.";
        }
        return message;
    }

    /**
     * Remove announcement of a host.
     * @param username      the username
     * @param name          the name of the announcement
     * @return              the message of the operation
     */
    public static String removeAnnouncement(final String username, final String name) {
        String message = verifyHost(username);
        if (message.equals("is host.")) {
            for (Host host : hosts) {
                if (host.getUsername().equals(username)) {
                    for (Announcement announcement : host.getAnnouncements()) {
                        if (announcement.getName().equals(name)) {
                            host.getAnnouncements().remove(announcement);
                            return username + " has successfully deleted the announcement.";
                        }
                    }
                    return username + " has no announcement with the given name.";
                }
            }
        }
        return message;
    }

    /**
     * Remove album of an artist.
     * @param username    the username
     * @param name        the name of the album
     * @return            the message of the operation
     */
    public static String removeAlbum(final String username, final String name) {
        String message = verifyArtist(username);
        if (message.equals("is artist.")) {
            for (Artist artist : artists) {
                if (artist.getUsername().equals(username)) {
                    for (Album album : artist.getAlbums()) {
                        if (album.getName().equals(name)) {
                            for (Song song : album.getSongs()) {
                                for (User user : users) {
                                    if (user.getPlayer().getCurrentAudioFile() != null
                                            && user.getPlayer().getCurrentAudioFile()
                                            .getName() != null && Objects.equals(
                                            user.getPlayer().getCurrentAudioFile()
                                            .getName(), song.getName())) {
                                        return username + " can't delete this album.";
                                    }
                                    for (Playlist playlist : user.getPlaylists()) {
                                        if (playlist.getSongs().contains(song)) {
                                            return username + " can't delete this album.";
                                        }
                                    }
                                    user.getLikedSongs().remove(song);
                                }
                                songs.remove(song);
                            }
                            artist.getAlbums().remove(album);
                            albums.remove(album);
                            return username + " has successfully deleted the album.";
                        }
                    }
                    return username + " doesn't have an album with the given name.";
                }
            }
        }
        return message;
    }

    /**
     * Remove podcast of a host.
     * @param username    the username
     * @param name        the name of the podcast
     * @return            the message of the operation
     */
    public static String removePodcast(final String username, final String name) {
        String message = verifyHost(username);
        if (message.equals("is host.")) {
            for (Host host : hosts) {
                if (host.getUsername().equals(username)) {
                    for (Podcast podcast : host.getPodcasts()) {
                        if (podcast.getName().equals(name)) {
                            for (User user : users) {
                                for (Episode episode : podcast.getEpisodes()) {
                                    if (user.getPlayer().getCurrentAudioFile() != null
                                            && user.getPlayer()
                                            .getCurrentAudioFile().getName() != null
                                            && Objects.equals(user.getPlayer()
                                            .getCurrentAudioFile().getName(), episode.getName())) {
                                        return username + " can't delete this podcast.";
                                    }
                                }
                            }
                            host.getPodcasts().remove(podcast);
                            podcasts.remove(podcast);
                            return username + " deleted the podcast successfully.";
                        }
                    }
                    return username + " doesn't have a podcast with the given name.";
                }
            }
        }
        return message;
    }

    /**
     * Remove event of an artist.
     * @param username      the username
     * @param name          the name of the event
     * @return              the message of the operation
     */
    public static String removeEvent(final String username, final String name) {
        String message = verifyArtist(username);
        if (message.equals("is artist.")) {
            for (Artist artist : artists) {
                if (artist.getUsername().equals(username)) {
                    for (Event event : artist.getEvents()) {
                        if (event.getName().equals(name)) {
                            artist.getEvents().remove(event);
                            return username + " deleted the event successfully.";
                        }
                    }
                    return username + " doesn't have an event with the given name.";
                }
            }
        }
        return message;
    }

    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        artists = new ArrayList<>();
        hosts = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        albums = new ArrayList<>();
        timestamp = 0;
    }
}
