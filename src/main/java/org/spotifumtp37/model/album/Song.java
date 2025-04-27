package org.spotifumtp37.model.album;

public class Song {
    private String name;
    private String artist;
    private String publisher;
    private String lyrics;
    private String musicalNotes;
    private String genre;
    private int durationInSeconds;

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

    Song(Song other) {
        this.name = other.getName();
        this.artist = other.getArtist();
        this.publisher = other.getPublisher();
        this.lyrics = other.getLyrics();
        this.musicalNotes = other.getMusicalNotes();
        this.genre = other.getGenre();
        this.durationInSeconds = other.getDurationInSeconds();
    }

    Song(){
        this.name = "";
        this.artist = "";
        this.publisher = "";
        this.lyrics = "";
        this.musicalNotes = "";
        this.genre = "";
        this.durationInSeconds = 0;
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

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Song song)) return false;

        return getDurationInSeconds() == song.getDurationInSeconds() && getName().equals(song.getName()) && getArtist().equals(song.getArtist());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getArtist().hashCode();
        result = 31 * result + getDurationInSeconds();
        return result;
    }

    @Override
    public Song clone(){
        return new Song(this);
    }
}
