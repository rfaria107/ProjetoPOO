package org.spotifumtp37.model.playlist;

import org.spotifumtp37.model.album.Song;
import org.spotifumtp37.model.user.User;

import java.io.Serializable;

public interface Playable extends Serializable {
    void next(User user);

    void previous(User user);

    void play(User user);

    Song getCurrentSong();

}
