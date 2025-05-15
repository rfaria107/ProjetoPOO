package org.spotifumtp37.model.album;

import java.io.Serializable;

public class MultimediaSong extends Song implements Serializable {
    MultimediaSong(Song other) {
        super(other);
    }

    MultimediaSong(String name, String artist, String publisher, String lyrics, String musicalNotes, String genre, int durationInSeconds, Album album) {
        super(name, artist, publisher, lyrics, musicalNotes, genre, durationInSeconds);
    }
}
