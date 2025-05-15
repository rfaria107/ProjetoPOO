package org.spotifumtp37.model.album;

import java.io.Serializable;

public class ExplicitSong extends Song implements Serializable {
    ExplicitSong(Song other) {
        super(other);
    }

    ExplicitSong(String name, String artist, String publisher, String lyrics, String musicalNotes, String genre, int durationInSeconds, Album album) {
        super(name, artist, publisher, lyrics, musicalNotes, genre, durationInSeconds);
    }
}
