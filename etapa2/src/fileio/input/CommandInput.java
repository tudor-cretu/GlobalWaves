package fileio.input;

import lombok.Getter;

import java.util.ArrayList;

@Getter
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
