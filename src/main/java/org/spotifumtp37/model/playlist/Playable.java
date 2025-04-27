package org.spotifumtp37.model.playlist;

public interface Playable {
    void next();
    void previous();
    void play();
    void pause();
    void addSong();
    void deleteSong();
}
