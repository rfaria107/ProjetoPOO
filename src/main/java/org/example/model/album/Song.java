package org.example.model.album;

public class Song {
    private String name;
    private String artist;
    private String publisher;
    private String lyrics;
    private String musicalNotes;
    private String genre;
    private int durationInSeconds;

    Song(Song other) {
        this.name = other.name;
        this.artist = other.artist;
        this.publisher = other.publisher;
        this.lyrics = other.lyrics;
        this.musicalNotes = other.musicalNotes;
        this.genre = other.genre;
        this.durationInSeconds = other.durationInSeconds;
    }

     Song(String name, String artist, String publisher, String lyrics,
          String musicalNotes, String genre, int durationInSeconds) {
        this.name = name;
        this.artist = artist;
        this.publisher = publisher;
        this.lyrics = lyrics;
        this.musicalNotes = musicalNotes;
        this.genre = genre;
        this.durationInSeconds = durationInSeconds;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getMusicalNotes() {
        return musicalNotes;
    }

    public String getGenre() {
        return genre;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

}
