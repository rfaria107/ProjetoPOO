package org.spotifumtp37.model.album;

import org.spotifumtp37.model.playlist.Playable;
import org.spotifumtp37.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a music album containing a collection of songs and associated metadata.
 * The album includes details such as title, artist, release year, genre,
 * and provides functionality to manage the list of songs.
 */
public class Album implements Playable {
    private String title;
    private String artist;
    private int releaseYear;
    private String genre;
    private List<Song> songs;
    private Song currentSong;


    public Album() {
        this.title = "";
        this.artist = "";
        this.releaseYear = 0;
        this.genre = "";
        this.songs = new ArrayList<>();
        this.currentSong = new Song();
    }

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
        Random rand = new Random();
        int randomIndex = rand.nextInt(songs.size()-1);
        this.currentSong = songs.get(randomIndex);

    }

    public Album(Album other) {
        this.title = other.getTitle();
        this.artist = other.getArtist();
        this.releaseYear = other.getReleaseYear();
        this.genre = other.getGenre();
        this.songs = copySongs(other.getSongs());
        Random rand = new Random();
        int randomIndex = rand.nextInt(songs.size()-1);
        this.currentSong = songs.get(randomIndex);    }

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


    public boolean deleteSong(String name) {
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

    public int getTotalDuration() {
        return songs.stream().mapToInt(Song::getDurationInSeconds).sum();
    }
    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }

    @Override
    public void next(User user) {
        if (user.getSubscriptionPlan().podeNavegarPlaylist()){
            currentSong = songs.get((songs.indexOf(currentSong)+1)%songs.size());
            currentSong.incrementTimesPlayed();
        }
        else {
            Random rand = new Random();
            int randomIndex = rand.nextInt(songs.size()-1);
            while (randomIndex == songs.indexOf(currentSong)){
                randomIndex = rand.nextInt(songs.size()-1);
            }
            currentSong = songs.get(randomIndex);
            currentSong.incrementTimesPlayed();
        }
        user.somarPontos();
    }
    @Override
    public void previous(User user) {
        if (user.getSubscriptionPlan().podeNavegarPlaylist()){
            currentSong = songs.get((songs.indexOf(currentSong)-1+songs.size()-1)%songs.size()-1);
            currentSong.incrementTimesPlayed();
        }
        else {
            throw new UnsupportedOperationException("Your subscription does not allow going back in the playlist.");
        }
        user.somarPontos();
    }

    @Override
    public Album clone() {
        return new Album(this);
    }

    @Override
    public void play(User user) {
        this.currentSong.incrementTimesPlayed();
        user.somarPontos();
    }

    @Override
    public void pauseMusic() {
        return;
    }
}