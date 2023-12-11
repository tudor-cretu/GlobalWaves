package app.utils.show;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ShowPodcast {
    private final String name;
    private final ArrayList<String> episodes;

    public ShowPodcast(final String name, final ArrayList<String> episodes) {
        this.name = name;
        this.episodes = episodes;
    }
}
