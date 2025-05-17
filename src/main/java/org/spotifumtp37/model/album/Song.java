package org.spotifumtp37.model.album;

import java.io.Serializable;
import java.util.Objects;

public class Song implements Serializable {
    private String name;
    private String artist;
    private String publisher;
    private String lyrics;
    private String musicalNotes;
    private String genre;
    private int durationInSeconds;
    private int timesPlayed;

    Song() {
        this.name = "";
        this.artist = "";
        this.publisher = "";
        this.lyrics = "";
        this.musicalNotes = "";
        this.genre = "";
        this.durationInSeconds = 0;
        this.timesPlayed = 0;
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
        this.timesPlayed = 0;
    }

    Song(Song other) {
        this.name = other.getName();
        this.artist = other.getArtist();
        this.publisher = other.getPublisher();
        this.lyrics = other.getLyrics();
        this.musicalNotes = other.getMusicalNotes();
        this.genre = other.getGenre();
        this.durationInSeconds = other.getDurationInSeconds();
        this.timesPlayed = other.getTimesPlayed();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getMusicalNotes() {
        return musicalNotes;
    }

    public void setMusicalNotes(String musicalNotes) {
        this.musicalNotes = musicalNotes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public void incrementTimesPlayed() {
        this.timesPlayed++;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Song other = (Song) obj;
        return Objects.equals(name, other.name) && Objects.equals(artist, other.artist)
                && Objects.equals(publisher, other.publisher) && durationInSeconds == other.durationInSeconds
                && Objects.equals(lyrics, other.lyrics) && Objects.equals(musicalNotes, other.musicalNotes)
                && Objects.equals(genre, other.genre) && Objects.equals(timesPlayed, other.timesPlayed);
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getArtist().hashCode();
        result = 31 * result + getDurationInSeconds();
        return result;
    }

    @Override
    public Song clone() {
        return new Song(this);
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", publisher='" + publisher + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", musicalNotes='" + musicalNotes + '\'' +
                ", genre='" + genre + '\'' +
                ", durationInSeconds=" + durationInSeconds +
                ", timesPlayed=" + timesPlayed +
                '}';
    }

    public boolean isExplicit() {
        return false;
    }
    public boolean isMultimedia() {
        return false;
    }

}
