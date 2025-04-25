package org.spotifumtp37.model.album;

public class MultimediaSong extends Song {
    MultimediaSong(Song other) {
        super(other);
    }

    MultimediaSong(String name, String artist, String publisher, String lyrics, String musicalNotes, String genre, int durationInSeconds, Album album) {
        super(name, artist, publisher, lyrics, musicalNotes, genre, durationInSeconds);
    }
}
