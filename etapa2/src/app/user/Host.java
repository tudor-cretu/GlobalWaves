package app.user;

import app.audio.Collections.Podcast;
import app.utils.Announcement;
import app.utils.show.ShowPodcast;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public final class Host extends User {
    private final ArrayList<Podcast> podcasts;
    private final ArrayList<Announcement> announcements;

    private Host(final Builder builder) {
        super(builder);
        this.podcasts = builder.podcasts;
        this.announcements = builder.announcements;
    }

    @Getter
    public static class Builder extends User.Builder {
        private ArrayList<Podcast> podcasts = new ArrayList<>();
        private ArrayList<Announcement> announcements = new ArrayList<>();

        /**
         * Constructor for the builder.
         *
         * @param username The username of the host being built.
         * @param type     The type of the host being built.
         * @param age      The age of the host being built.
         * @param city     The city of the host being built.
         */
        public Builder(final String username, final String type, final int age, final String city) {
            super(username, type, age, city);
        }

        /**
         * Sets the list of podcasts for the host being built.
         *
         * @param podcastsToAssign The list of podcasts to set.
         * @return This builder for method chaining.
         */
        public Builder podcasts(final ArrayList<Podcast> podcastsToAssign) {
            this.podcasts = podcastsToAssign;
            return this;
        }

        /**
         * Sets the list of announcements for the host being built.
         *
         * @param announcementsToAssign The list of announcements to set.
         * @return This builder for method chaining.
         */
        public Builder announcements(final ArrayList<Announcement> announcementsToAssign) {
            this.announcements = announcementsToAssign;
            return this;
        }

        /**
         * Builds the host.
         *
         * @return The host.
         */
        @Override
        public Host build() {
            return new Host(this);
        }
    }

    /**
     * Add podcast to the host podcasts list.
     *
     * @param podcast the podcast
     */
    public void addPodcast(final Podcast podcast) {
        this.getPodcasts().add(podcast);
    }


    /**
     * Add announcement to the host announcements list.
     *
     * @param name        the announcement name
     * @param description the announcement description
     */
    public void addAnnouncement(final String name, final String description) {
        Announcement announcement = new Announcement(name, description);
        this.getAnnouncements().add(announcement);
    }

    /**
     * Show the host's podcasts.
     *
     * @return the host's podcasts
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
