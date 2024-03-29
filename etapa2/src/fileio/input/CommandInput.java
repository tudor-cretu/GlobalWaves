package fileio.input;

import java.util.ArrayList;

public final class CommandInput {
    private String command;
    private String username;
    private Integer timestamp;
    private String type; // song / playlist / podcast / album
    private FiltersInput filters; // pentru search
    private Integer itemNumber; // pentru select
    private Integer repeatMode; // pentru repeat
    private Integer playlistId; // pentru add/remove song
    private String playlistName; // pentru create playlist
    private Integer seed; // pentru shuffle
    private Integer age;
    private String city;
    private String name;
    private String releaseYear;
    private String description;
    private ArrayList<SongInput> songs;
    private String date;
    private String price;
    private ArrayList<EpisodeInput> episodes;
    private String nextPage;

    public CommandInput() {
    }

    public String getCommand() {
        return command;
    }

    public String getUsername() {
        return username;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public FiltersInput getFilters() {
        return filters;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public Integer getRepeatMode() {
        return repeatMode;
    }

    public Integer getPlaylistId() {
        return playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public Integer getSeed() {
        return seed;
    }

    public Integer getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public ArrayList<EpisodeInput> getEpisodes() {
        return episodes;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setTimestamp(final Integer timestamp) {
        this.timestamp = timestamp;
    }

    public void setFilters(final FiltersInput filters) {
        this.filters = filters;
    }

    public void setItemNumber(final Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setRepeatMode(final Integer repeatMode) {
        this.repeatMode = repeatMode;
    }

    public void setPlaylistId(final Integer playlistId) {
        this.playlistId = playlistId;
    }

    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    public void setSeed(final Integer seed) {
        this.seed = seed;
    }

    @Override
    public String toString() {
        return "CommandInput{"
                + "command='" + command + '\''
                + ", username='" + username + '\''
                + ", timestamp=" + timestamp
                + ", type='" + type + '\''
                + ", filters=" + filters
                + ", itemNumber=" + itemNumber
                + ", repeatMode=" + repeatMode
                + ", playlistId=" + playlistId
                + ", playlistName='" + playlistName + '\''
                + ", seed=" + seed
                + '}';
    }
}
