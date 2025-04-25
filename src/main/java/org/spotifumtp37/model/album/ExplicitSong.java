package org.spotifumtp37.model.album;

public class ExplicitSong extends Song {
    ExplicitSong(Song other) {
        super(other);
    }

    ExplicitSong(String name, String artist, String publisher, String lyrics, String musicalNotes, String genre, int durationInSeconds, Album album) {
        super(name, artist, publisher, lyrics, musicalNotes, genre, durationInSeconds);
    }
}
