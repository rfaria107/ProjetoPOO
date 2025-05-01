package org.spotifumtp37.model.playlist;

import org.spotifumtp37.model.user.User;

public interface Playable {
    void next(User user);
    void previous(User user);
    void play(User user);
    void pauseMusic();
}
