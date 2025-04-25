package org.example.model.album;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private final String title;
    private final String artist;
    private final int releaseYear;
    private final String genre;
    private final List<Song> songs;


    public Album(String title, String artist, int releaseYear, String genre) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.songs = new ArrayList<>();
    }


    public Song addSong(String name, String publisher, String lyrics, String musicalNotes,
                         String genre, int durationInSeconds) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Song name cannot be null or empty.");
        }
        if (durationInSeconds <= 0) {
            throw new IllegalArgumentException("Song duration must be positive.");
        }
        if (songs.stream().anyMatch(song -> song.getName().equalsIgnoreCase(name))) {
            throw new IllegalArgumentException("A song with the same name already exists in the album.");
        }

        // Create and add song to the Album
        Song song = new Song(name, this.artist, publisher, lyrics, musicalNotes, genre, durationInSeconds);
        songs.add(song);
        return song;
    }


    public boolean removeMusic(String name) {
        return songs.removeIf(song -> song.getName().equals(name));
    }

    public List<Song> getSongs() {
        List<Song> copy = new ArrayList<>();
        for (Song song : this.songs) {
            copy.add(new Song(song));
        }
        return copy;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    //nao deve ser necessario, so pra fazer uns testes
    public int getTotalDuration() {
        return songs.stream().mapToInt(Song::getDurationInSeconds).sum();
    }
}