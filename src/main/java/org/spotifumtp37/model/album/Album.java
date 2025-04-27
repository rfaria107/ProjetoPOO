package org.spotifumtp37.model.album;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a music album containing a collection of songs and associated metadata.
 * The album includes details such as title, artist, release year, genre,
 * and provides functionality to manage the list of songs.
 */
public class Album {
    private String title;
    private String artist;
    private int releaseYear;
    private String genre;
    private final List<Song> songs;

    /**
     * Construtor parametrizado do álbum...
     *
     * @param title
     * @param artist
     * @param releaseYear
     * @param genre
     * @param songs
     */
    public Album(String title, String artist, int releaseYear, String genre, List<Song> songs) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.songs = copySongs(songs);
    }

    public Album() {
        this.title = "";
        this.artist = "";
        this.releaseYear = 0;
        this.genre = "";
        this.songs = new ArrayList<>();
    }

    public Album(Album other) {
        this.title = other.getTitle();
        this.artist = other.getArtist();
        this.releaseYear = other.getReleaseYear();
        this.genre = other.getGenre();
        this.songs = copySongs(other.getSongs());
    }

    public List<Song> copySongs(List<Song> songs) {
        List<Song> copy = new ArrayList<>();
        for (Song song : songs) {
            copy.add(new Song(song));
        }
        return copy;
    }

    public void addSong(String name, String publisher, String lyrics, String musicalNotes, String genre, int durationInSeconds) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Song name cannot be null or empty.");
        }
        if (durationInSeconds <= 0) {
            throw new IllegalArgumentException("Song duration must be positive.");
        }
        if (songs.stream().anyMatch(song -> song.getName().equalsIgnoreCase(name))) {
            throw new IllegalArgumentException("A song with the same name already exists in the album.");
        }

        Song song = new Song(name, this.artist, publisher, lyrics, musicalNotes, genre, durationInSeconds);
        songs.add(song);
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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