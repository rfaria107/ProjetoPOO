package org.spotifumtp37.model.album;

import java.io.Serializable;

public class MultimediaSong extends Song implements Serializable {
    private final boolean multimedia;
    private String videoLink;

    public MultimediaSong(MultimediaSong other) {
        super(other);
        this.multimedia = true;
        this.videoLink = other.videoLink;
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

    @Override
    public MultimediaSong clone() {
        return new MultimediaSong(this);
    }

    @Override
    public String toString() {
        return super.toString() +
                "multimedia=" + multimedia +
                ", videoLink='" + videoLink + '\'' +
                '}';
    }
}
