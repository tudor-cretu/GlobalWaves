package app.user;

import app.audio.Collections.Podcast;
import app.utils.Announcement;
import app.utils.show.ShowPodcast;

import java.util.ArrayList;

public final class Host extends User {
    private final ArrayList<Podcast> podcasts;
    private final ArrayList<Announcement> announcements;

    public Host(final String username, final String type, final int age, final String city) {
        super(username, type, age, city);
        this.podcasts = new ArrayList<>();
        this.announcements = new ArrayList<>();
    }

    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * Add podcast to the host podcasts list.
     * @param podcast the podcast
     */
    public void addPodcast(final Podcast podcast) {
        this.getPodcasts().add(podcast);
    }


    /**
     * Add announcement to the host announcements list.
     * @param name the announcement name
     * @param description the announcement description
     */
    public void addAnnouncement(final String name, final String description) {
        Announcement announcement = new Announcement(name, description);
        this.getAnnouncements().add(announcement);
    }

    /**
     * Show the host's podcasts.
     * @return  the host's podcasts
     */
    public ArrayList<ShowPodcast> showPodcasts() {
        ArrayList<ShowPodcast> showPodcasts = new ArrayList<>();
        for (Podcast podcast : this.getPodcasts()) {
            ShowPodcast showPodcast = new ShowPodcast(podcast.getName(),
                    podcast.getEpisodesNames());
            showPodcasts.add(showPodcast);
        }

        return showPodcasts;
    }
}
