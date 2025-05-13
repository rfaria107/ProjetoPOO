package org.spotifumtp37.model.user;
import org.spotifumtp37.model.album.Song;

import java.time.LocalDateTime;

public class History  {

        private User user;
        private Song song ;
        private LocalDateTime timestamp;

        public History (User user, Song song, LocalDateTime timestamp) {
            this.user = user;
            this.song = song;
            this.timestamp = timestamp;
        }

        public User getUser() {
            return user;
        }

        public Song getSong() {
            return song;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        @Override
        public String toString() {
            return "Play{" +
                    "user='" + user + '\'' +
                    ", song='" + song + '\'' +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }


