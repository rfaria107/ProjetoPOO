package org.spotifumtp37.util;

import org.spotifumtp37.model.album.Album;
import org.spotifumtp37.model.album.Song;
import org.spotifumtp37.model.SpotifUMData;

import org.spotifumtp37.model.user.User;


import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Stats {

    public Song getMostPlayedSong() {
        return song.stream()
                .max(Comparator.comparingInt(Song::getTimesPlayed))
                .orElse(null); // devolve null se a lista estiver vazia
    }


    public String getMostListenedArtist() {
        Map<String, Integer> playCountByArtist = new HashMap<>();

        for (Album album : SpotifUMData.getAlbum().values()) {
            for (Song song : album.getSongs()) {
                String artist = song.getArtist();
                int plays = song.getTimesPlayed();
                playCountByArtist.put(artist, playCountByArtist.getOrDefault(artist, 0) + plays);
            }
        }

        return playCountByArtist.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static User getTopListener(List<User> users) {
        User topUser = null;
        int maxHistorySize = -1;

        for (User u : users) {
            int userHistorySize = u.getHistory().size();

            if (userHistorySize > maxHistorySize) {
                maxHistorySize = userHistorySize;
                topUser = u;
            }
        }

        return topUser;
    }


}
