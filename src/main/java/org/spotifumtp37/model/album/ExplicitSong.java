package org.spotifumtp37.model.album;

import java.io.Serializable;

public class ExplicitSong extends Song implements Serializable {
    private final boolean explicit;

    ExplicitSong(Song other) {
        super(other);
        this.explicit = true;
    }

    ExplicitSong(String name, String artist, String publisher, String lyrics, String musicalNotes, String genre, int durationInSeconds) {
        super(name, artist, publisher, lyrics, musicalNotes, genre, durationInSeconds);
        this.explicit = true;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public boolean isMultimedia() {
        return false;
    }


}
