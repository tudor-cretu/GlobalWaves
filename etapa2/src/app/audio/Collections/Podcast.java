package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Episode;

import java.util.ArrayList;
import java.util.List;

public final class Podcast extends AudioCollection {
    private final List<Episode> episodes;

    public Podcast(final String name, final String owner, final List<Episode> episodes) {
        super(name, owner);
        this.episodes = episodes;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    @Override
    public int getNumberOfTracks() {
        return episodes.size();
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return episodes.get(index);
    }

    /**
     * Gets the episode names of a podcast
     * @return    the list of episode names
     */
    public ArrayList<String> getEpisodesNames() {
        ArrayList<String> episodesNames = new ArrayList<>();
        for (Episode episode : this.getEpisodes()) {
            episodesNames.add(episode.getName());
        }

        return episodesNames;
    }
}
