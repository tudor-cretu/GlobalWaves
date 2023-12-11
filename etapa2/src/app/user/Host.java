package app.user;

import app.audio.Collections.Album;
import app.audio.Collections.Podcast;
import app.utils.Announcement;
import app.utils.show.ShowAlbum;
import app.utils.show.ShowPodcast;

import java.util.ArrayList;

public class Host extends User {
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

    public void addPodcast(Podcast podcast) {
        this.getPodcasts().add(podcast);
    }


    public void addAnnouncement(String name, String description) {
        Announcement announcement = new Announcement(name, description);
        this.getAnnouncements().add(announcement);
    }

    public ArrayList<ShowPodcast> showPodcasts() {
        ArrayList<ShowPodcast> showPodcasts = new ArrayList<>();
        for (Podcast podcast : this.getPodcasts()) {
            ShowPodcast showPodcast = new ShowPodcast(podcast.getName(), podcast.getEpisodesNames());
            showPodcasts.add(showPodcast);
        }

        return showPodcasts;
    }
}
