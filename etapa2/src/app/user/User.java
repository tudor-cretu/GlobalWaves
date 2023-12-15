package app.user;

import app.Admin;
import app.audio.Collections.*;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.utils.*;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static checker.CheckerConstants.LIMITFIVE;

/**
 * The type User.
 */
@Getter
@Setter
public class User extends LibraryEntry {
    private String username;
    private String type;
    private int age;
    private String city;
    private ArrayList<Playlist> playlists;
    private ArrayList<Song> likedSongs;
    private ArrayList<Playlist> followedPlaylists;
    private final Player player;
    private final SearchBar searchBar;
    private boolean lastSearched;
    private Enums.ConnectionStatus connectionStatus;
    private Page currentPage;
    private Artist lastSelectedArtist;
    private Host lastSelectedHost;

    User(final Builder builder) {
        super(builder.username);
        this.username = builder.username;
        this.type = builder.type;
        this.age = builder.age;
        this.city = builder.city;
        this.playlists = builder.playlists;
        this.likedSongs = builder.likedSongs;
        this.followedPlaylists = builder.followedPlaylists;
        this.player = builder.player;
        this.searchBar = builder.searchBar;
        this.lastSearched = builder.lastSearched;
        this.connectionStatus = builder.connectionStatus;
        this.currentPage = builder.currentPage;
        this.lastSelectedArtist = builder.lastSelectedArtist;
        this.lastSelectedHost = builder.lastSelectedHost;
    }

    public static class Builder {
        private final String username;
        private final String type;
        private final int age;
        private final String city;
        private ArrayList<Playlist> playlists = new ArrayList<>();
        private ArrayList<Song> likedSongs = new ArrayList<>();
        private ArrayList<Playlist> followedPlaylists = new ArrayList<>();
        private Player player = new Player();
        private SearchBar searchBar;
        private boolean lastSearched = false;
        private Enums.ConnectionStatus connectionStatus = Enums.ConnectionStatus.ONLINE;
        private Page currentPage = new Page("home");
        private Artist lastSelectedArtist = null;
        private Host lastSelectedHost = null;

        /**
         * Constructs a new builder for creating a {@link User} instance.
         *
         * @param username the username of the user.
         * @param type     the type of the user.
         * @param age      the age of the user.
         * @param city     the city of the user.
         */
        public Builder(final String username, final String type, final int age, final String city) {
            this.username = username;
            this.type = type;
            this.age = age;
            this.city = city;
            this.searchBar = new SearchBar(username);
        }

        /**
         * Sets the playlists for the user being built.
         *
         * @param playlistsToAssign The list of playlists to set.
         * @return This builder for method chaining.
         */
        public Builder playlists(final ArrayList<Playlist> playlistsToAssign) {
            this.playlists = playlistsToAssign;
            return this;
        }

        /**
         * Sets the liked songs for the user being built.
         *
         * @param likedSongsToAssign The list of liked songs to set.
         * @return This builder for method chaining.
         */
        public Builder likedSongs(final ArrayList<Song> likedSongsToAssign) {
            this.likedSongs = likedSongsToAssign;
            return this;
        }

        /**
         * Sets the followed playlists for the user being built.
         *
         * @param followedPlaylistsToAssign The list of followed playlists to set.
         * @return This builder for method chaining.
         */
        public Builder followedPlaylists(final ArrayList<Playlist> followedPlaylistsToAssign) {
            this.followedPlaylists = followedPlaylistsToAssign;
            return this;
        }

        /**
         * Sets the player for the user being built.
         *
         * @param playerToAssign The player to set.
         * @return This builder for method chaining.
         */
        public Builder player(final Player playerToAssign) {
            this.player = playerToAssign;
            return this;
        }

        /**
         * Sets the search bar for the user being built.
         *
         * @param searchBarToAssign The search bar to set.
         * @return This builder for method chaining.
         */
        public Builder searchBar(final SearchBar searchBarToAssign) {
            this.searchBar = searchBarToAssign;
            return this;
        }

        /**
         * Sets the last searched status for the user being built.
         *
         * @param lastSearchedToAssign The last searched status to set.
         * @return This builder for method chaining.
         */
        public Builder lastSearched(final boolean lastSearchedToAssign) {
            this.lastSearched = lastSearchedToAssign;
            return this;
        }

        /**
         * Sets the connection status for the user being built.
         *
         * @param connectionStatusToAssign The connection status to set.
         * @return This builder for method chaining.
         */
        public Builder connectionStatus(final Enums.ConnectionStatus connectionStatusToAssign) {
            this.connectionStatus = connectionStatusToAssign;
            return this;
        }

        /**
         * Sets the current page for the user being built.
         *
         * @param currentPageToAssign The current page to set.
         * @return This builder for method chaining.
         */
        public Builder currentPage(final Page currentPageToAssign) {
            this.currentPage = currentPageToAssign;
            return this;
        }

        /**
         * Sets the last selected artist for the user being built.
         *
         * @param lastSelectedArtistToAssign The last selected artist to set.
         * @return This builder for method chaining.
         */
        public Builder lastSelectedArtist(final Artist lastSelectedArtistToAssign) {
            this.lastSelectedArtist = lastSelectedArtistToAssign;
            return this;
        }

        /**
         * Sets the last selected host for the user being built.
         *
         * @param lastSelectedHostToAssign The last selected host to set.
         * @return This builder for method chaining.
         */
        public Builder lastSelectedHost(final Host lastSelectedHostToAssign) {
            this.lastSelectedHost = lastSelectedHostToAssign;
            return this;
        }

        /**
         * Builds a new instance of {@link User} using the configured builder.
         *
         * @return A new instance of {@link User}.
         */
        public User build() {
            return new User(this);
        }
    }

    /**
     * Search array list.
     *
     * @param filters the filters
     * @param typeToAssign    the type
     * @return the array list
     */
    public ArrayList<String> search(final Filters filters, final String typeToAssign) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        ArrayList<String> results = new ArrayList<>();
        if (connectionStatus == Enums.ConnectionStatus.OFFLINE) {
            results.add(0, "OFFLINE");
        }
        List<LibraryEntry> libraryEntries = searchBar.search(filters, typeToAssign);

        for (LibraryEntry libraryEntry : libraryEntries) {
            results.add(libraryEntry.getName());
        }
        return results;
    }

    /**
     * Select string.
     *
     * @param itemNumber the item number
     * @return the string
     */
    public String select(final int itemNumber) {
        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        LibraryEntry selected = searchBar.select(itemNumber);

        if (selected == null) {
            return "The selected ID is too high.";
        }

        for (Artist artist : Admin.getArtists()) {
            if (Objects.equals(selected.getName(), artist.getName())) {
                currentPage = new Page("artist");
                lastSelectedArtist = artist;
                return "Successfully selected " + selected.getName() + "'s page.";
            }
        }

        for (Host host : Admin.getHosts()) {
            if (Objects.equals(selected.getName(), host.getName())) {
                currentPage = new Page("host");
                lastSelectedHost = host;
                return "Successfully selected " + selected.getName() + "'s page.";
            }
        }

        return "Successfully selected %s.".formatted(selected.getName());
    }

    /**
     * Load string.
     *
     * @return the string
     */
    public String load() {
        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }

        if (!searchBar.getLastSearchType().equals("song")
            && ((AudioCollection) searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }

    /**
     * Play pause string.
     *
     * @return the string
     */
    public String playPause() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }

        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }

    /**
     * Repeat string.
     *
     * @return the string
     */
    public String repeat() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch (repeatMode) {
            case NO_REPEAT -> {
                repeatStatus = "no repeat";
            }
            case REPEAT_ONCE -> {
                repeatStatus = "repeat once";
            }
            case REPEAT_ALL -> {
                repeatStatus = "repeat all";
            }
            case REPEAT_INFINITE -> {
                repeatStatus = "repeat infinite";
            }
            case REPEAT_CURRENT_SONG -> {
                repeatStatus = "repeat current song";
            }
            default -> {
                repeatStatus = "";
            }
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    /**
     * Shuffle string.
     *
     * @param seed the seed
     * @return the string
     */
    public String shuffle(final Integer seed) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!player.getType().equals("playlist") && !player.getType().equals("album")) {
            return "The loaded source is not a playlist or an album.";
        }

        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }

    /**
     * Forward string.
     *
     * @return the string
     */
    public String forward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }

    /**
     * Backward string.
     *
     * @return the string
     */
    public String backward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }

    /**
     * Like string.
     *
     * @return the string
     */
    public String like() {
        if (Objects.equals(getConnectionStatus(), "OFFLINE")) {
            return username + " is offline.";
        }
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }

    /**
     * Next string.
     *
     * @return the string
     */
    public String next() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Prev string.
     *
     * @return the string
     */
    public String prev() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Create playlist string.
     *
     * @param name      the name
     * @param timestamp the timestamp
     * @return the string
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }

        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }

    /**
     * Add remove in playlist string.
     *
     * @param id the id
     * @return the string
     */
    public String addRemoveInPlaylist(final int id) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (id > playlists.size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = playlists.get(id - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    /**
     * Switch playlist visibility string.
     *
     * @param playlistId the playlist id
     * @return the string
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    /**
     * Show playlists array list.
     *
     * @return the array list
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * Follow string.
     *
     * @return the string
     */
    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String typeToAssign = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!typeToAssign.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(username)) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    /**
     * Gets player stats.
     *
     * @return the player stats
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    /**
     * Show preferred songs array list.
     *
     * @return the array list
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     * Gets preferred genre.
     *
     * @return the preferred genre
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    /**
     * Simulate time.
     *
     * @param time the time
     */
    public void simulateTime(final int time) {
        if (!this.getPlayer().userOffline) {
            player.simulatePlayer(time);
        }
    }

    /**
     * Switch connection status from online to offline and vice-versa
     * @return the string
     */
    public String switchConnectionStatus() {
        if (Objects.equals(type, "artist") || Objects.equals(type, "host")) {
            return username + " is not a normal user.";
        }
        if (connectionStatus == Enums.ConnectionStatus.OFFLINE) {
            connectionStatus = Enums.ConnectionStatus.ONLINE;
            player.userOffline = false;
        } else if (connectionStatus == Enums.ConnectionStatus.ONLINE) {
            connectionStatus = Enums.ConnectionStatus.OFFLINE;
            player.pause();
            player.simulatePlayer(getPlayerStats().getRemainedTime());
            player.userOffline = true;
        }
        return username + " has changed status successfully.";
    }

    /**
     * Gets connection status.
     * @return the connection status
     */
    public String getConnectionStatus() {
        return connectionStatus.toString();
    }

    /**
     * Print the page that the user is on.
     * @return the current page.
     */
    public String printCurrentPage() {
        String message = null;
        if (connectionStatus == Enums.ConnectionStatus.OFFLINE) {
            return username + " is offline.";
        }

        if (Objects.equals(currentPage.getType(), "home")) {
            message = printHomePage();
        }

        if (Objects.equals(currentPage.getType(), "artist")) {
            message = printArtistPage();
        }

        if (Objects.equals(currentPage.getType(), "host")) {
            message = printHostPage();
        }
        if (Objects.equals(currentPage.getType(), "likedcontent")) {
            message = printLikedContentPage();
        }
        return message;
    }

    /**
     * Prints the home page
     * @return the home page
     */
    public String printHomePage() {
        String likedSongsNames = "[";
        String followedPlaylistsNames = "[";

        List<Song> top5LikedSongs = new ArrayList<>(likedSongs);
        top5LikedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<Song> top5SortedSongs = top5LikedSongs.subList(
                0, Math.min(LIMITFIVE, top5LikedSongs.size()));
        for (Song likedSong : top5SortedSongs) {
            likedSongsNames = likedSongsNames.concat(likedSong.getName());
            if (top5SortedSongs.indexOf(likedSong) != top5SortedSongs.size() - 1) {
                likedSongsNames = likedSongsNames.concat(", ");
            }
        }

        List<Playlist> top5FollowedPlaylists = new ArrayList<>(followedPlaylists);
        top5FollowedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers).reversed());
        List<Playlist> top5SortedPlaylists = top5FollowedPlaylists.subList(
                0, Math.min(LIMITFIVE, top5FollowedPlaylists.size()));
        for (Playlist followedPlaylist : top5SortedPlaylists) {
            followedPlaylistsNames = followedPlaylistsNames.concat(followedPlaylist.getName());
            if (top5SortedPlaylists.indexOf(followedPlaylist) != top5SortedPlaylists.size() - 1) {
                followedPlaylistsNames = followedPlaylistsNames.concat(", ");
            }
        }
        likedSongsNames = likedSongsNames.concat("]");
        followedPlaylistsNames = followedPlaylistsNames.concat("]");
        return "Liked songs:\n\t" + likedSongsNames
                + "\n\nFollowed playlists:\n\t" + followedPlaylistsNames;
    }

    /**
     * Prints the artist page
     * @return the artist page
     */
    public String printArtistPage() {
        String albumNames = "[";
        String eventDetails = "[";
        String merchDetails = "[";
        for (Artist artist : Admin.getArtists()) {
            if (searchBar.getLastSelected() != null) {
                if (Objects.equals(artist.getName(),
                        searchBar.getLastSelected().getName())) {
                    for (Album album : artist.getAlbums()) {
                        albumNames = albumNames.concat(album.getName());
                        if (artist.getAlbums().indexOf(album)
                                != artist.getAlbums().size() - 1) {
                            albumNames = albumNames.concat(", ");
                        }
                    }
                    for (Event event : artist.getEvents()) {
                        eventDetails = eventDetails.concat(
                                event.getName() + " - " + event.getDate()
                                        + ":\n\t" + event.getDescription());
                        if (artist.getEvents().indexOf(event)
                                != artist.getEvents().size() - 1) {
                            eventDetails = eventDetails.concat(", ");
                        }
                    }
                    for (Merch merch : artist.getMerch()) {
                        merchDetails = merchDetails.concat(
                                merch.getName() + " - " + merch.getPrice()
                                        + ":\n\t" + merch.getDescription());
                        if (artist.getMerch().indexOf(merch) != artist.getMerch().size() - 1) {
                            merchDetails = merchDetails.concat(", ");
                        }
                    }
                }
            }
        }
        albumNames = albumNames.concat("]");
        eventDetails = eventDetails.concat("]");
        merchDetails = merchDetails.concat("]");
        return "Albums:\n\t" + albumNames + "\n\nMerch:\n\t"
                + merchDetails + "\n\nEvents:\n\t" + eventDetails;
    }

    /**
     * Prints the host page
     * @return  the host page
     */
    public String printHostPage() {
        String message = "Podcasts:\n\t[";
        String episodeDetails = "[";
        String announcementDetails = "[";
        for (Host host : Admin.getHosts()) {
            if (lastSelectedHost != null) {
                for (Podcast podcast : lastSelectedHost.getPodcasts()) {
                    message = message.concat(podcast.getName() + ":\n\t");
                    for (Episode episode : podcast.getEpisodes()) {
                        episodeDetails = episodeDetails.concat(
                                episode.getName() + " - " + episode.getDescription());
                        if (podcast.getEpisodes().indexOf(episode)
                                != podcast.getEpisodes().size() - 1) {
                            episodeDetails = episodeDetails.concat(", ");
                        }
                    }
                    message = message.concat(episodeDetails + "]\n");
                    episodeDetails = "[";
                    if (host.getPodcasts().indexOf(podcast) != host.getPodcasts().size() - 1) {
                        message = message.concat(", ");
                    }
                }
                message = message.concat("]\n\nAnnouncements:\n\t");
                if (host.getAnnouncements() != null) {
                    for (Announcement announcement : host.getAnnouncements()) {
                        announcementDetails = announcementDetails.concat(
                                announcement.getName() + ":\n\t"
                                        + announcement.getDescription());
                        if (host.getAnnouncements().indexOf(announcement)
                                != host.getAnnouncements().size() - 1) {
                            announcementDetails = announcementDetails.concat("\n, ");
                        }
                    }
                }
                message = message.concat(announcementDetails + "\n]");
                break;
            }
        }
        return message;
    }

    /**
     * Prints the liked content page
     * @return  the liked content page
     */
    public String printLikedContentPage() {
        String message = "Liked songs:\n\t[";
        for (Song song : likedSongs) {
            message = message.concat(song.getName() + " - " + song.getArtist());
            if (likedSongs.indexOf(song) != likedSongs.size() - 1) {
                message = message.concat(", ");
            }
        }
        message = message.concat("]\n\nFollowed playlists:\n\t[");
        for (Playlist playlist : followedPlaylists) {
            message = message.concat(playlist.getName() + " - " + playlist.getOwner());
            if (followedPlaylists.indexOf(playlist) != followedPlaylists.size() - 1) {
                message = message.concat(", ");
            }
        }
        message = message.concat("]");
        return message;
    }

    /**
     * Changes the page from Home to LikedContent and vice-versa
     * @param nextPage the next page
     * @return  the string
     */
    public String changePage(final String nextPage) {
        currentPage = new Page(nextPage.toLowerCase());
        if (!nextPage.equals("Home") && !nextPage.equals("LikedContent")) {
            return username + " is trying to access a non-existent page.";
        }
        return username + " accessed " + nextPage + " successfully.";
    }
}

