/*package org.spotifumtp37.util;

import org.spotifumtp37.model.album.Album;
import org.spotifumtp37.model.album.Song;
import org.spotifumtp37.model.SpotifUMData;

import org.spotifumtp37.model.playlist.Playlist;
import org.spotifumtp37.model.user.History;
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
    public static User getUserWithMostPoints(List<User> users) {
        User topUser = null;
        double maxPoints = -Double.MAX_VALUE;

        for (User u : users) {
            double userPoints = u.getPontos();

            // Adiciona os pontos de acordo com o plano de subscrição
            if (u.getSubscriptionPlan() instanceof FreePlan) {
                userPoints = u.getSubscriptionPlan().adicionaPontos(userPoints);  // Adiciona pontos para FreePlan
            } else if (u.getSubscriptionPlan() instanceof PremiumBase) {
                userPoints = u.getSubscriptionPlan().adicionaPontos(userPoints);  // Adiciona pontos para PremiumBase
            } else if (u.getSubscriptionPlan() instanceof PremiumTop) {
                userPoints = u.getSubscriptionPlan().adicionaPontos(userPoints);  // Adiciona pontos para PremiumTop
            }


            if (userPoints > maxPoints) {
                maxPoints = userPoints;
                topUser = u;
            }
        }

        return topUser;  // Retorna o utilizador com mais pontos
    }

    Map<String, Integer> generoContador = new HashMap<>();

    public static String generoMaisReproduzido(List<History> historico) {
        Map<String, Integer> contadorGeneros = new HashMap<>();

        for (History h : historico) {
            Song s = h.getSong();
            String genero = s.getGenre();

            contadorGeneros.put(genero, contadorGeneros.getOrDefault(genero, 0) + 1);
        }

        // Encontrar o género com maior número de reproduções
        String generoMaisReproduzido = null;
        int max = 0;

        for (Map.Entry<String, Integer> entry : contadorGeneros.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                generoMaisReproduzido = entry.getKey();
            }
        }

        return generoMaisReproduzido;
    }

    public static int contarPlaylistsPublicas(List<Playlist> playlists) {
        int contador = 0;

        for (Playlist p : playlists) {
            if (p.isPublic()) {
                contador++;
            }
        }

        return contador;
    }

    public static User utilizadorComMaisPlaylists(List<User> users) {
        User topUser = null;
        int max = 0;

        for (User u : users) {
            int numPlaylists = u.getPlaylists().size ();

            if (numPlaylists > max) {
                max = numPlaylists;
                topUser = u;
            }
        }

        return topUser;
    }


}
*/