package org.spotifumtp37.model.user;

import org.spotifumtp37.model.album.Song;

import java.time.LocalDateTime;
import java.util.Objects;

public class History {
    private Song song;
    private LocalDateTime time;

    public History(Song song, LocalDateTime time) {
        this.song = song;
        this.time = time;
    }

    public History() {
        this.time = null;
        this.song = null;
    }

    public History ( History history ) {
        this.song = history.song;
        this.time = history.time;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    @Override
    public String toString() {
        return "History{" +
                "song=" + song +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return Objects.equals(song, history.song) && Objects.equals(time, history.time);
    }

    @Override
    public History clone() {
        return new History(this);
    }
}
