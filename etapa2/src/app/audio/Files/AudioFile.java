package app.audio.Files;

import app.audio.LibraryEntry;
import lombok.Getter;

@Getter
public abstract class AudioFile extends LibraryEntry {
    private final Integer duration;

    public AudioFile(final String name, final Integer duration) {
        super(name);
        this.duration = duration;
    }

    /**
     * Gets duration of an audio file
     * @return      the duration
     */
    public Integer getDuration() {
        return duration;
    }
}
