package org.spotifumtp37.model.album;

import java.io.Serializable;

public class MultimediaSong extends Song implements Serializable {
    private final boolean multimedia;
    private String videoLink;

    MultimediaSong(Song other) {
        super(other);
        this.multimedia = true;
        this.videoLink = "";
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    MultimediaSong(String name, String artist, String publisher, String lyrics, String musicalNotes, String genre, int durationInSeconds, String videoLink) {
        super(name, artist, publisher, lyrics, musicalNotes, genre, durationInSeconds);
        this.multimedia = true;
        this.videoLink = videoLink;
    }

    public boolean isMultimedia() {
        return multimedia;
    }
}
