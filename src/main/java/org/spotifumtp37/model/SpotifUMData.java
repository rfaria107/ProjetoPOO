package org.spotifumtp37.model;

import org.spotifumtp37.model.album.*;
import org.spotifumtp37.model.exceptions.JaExisteException;
import org.spotifumtp37.model.exceptions.NaoExisteException;
import org.spotifumtp37.model.playlist.Playlist;
import org.spotifumtp37.model.user.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class SpotifUMData implements Serializable {
    private Map<String, Album> albums;
    private Map<String, User> users;
    private Map<String, Playlist> playlists;

    public SpotifUMData() {
        this.albums = new HashMap<>();
        this.users = new HashMap<>();
        this.playlists = new HashMap<>();
    }

    public SpotifUMData(SpotifUMData outro) {
        this.albums = new HashMap<>();
        for (Map.Entry<String, Album> entry : outro.albums.entrySet()) {
            this.albums.put(entry.getKey(), entry.getValue().clone());
        }
        this.users = new HashMap<>();
        for (Map.Entry<String, User> entry : outro.users.entrySet()) {
            this.users.put(entry.getKey(), entry.getValue().clone());
        }
        this.playlists = new HashMap<>();
        for (Map.Entry<String, Playlist> entry : outro.playlists.entrySet()) {
            this.playlists.put(entry.getKey(), entry.getValue().clone());
        }
    }

    @Override
    public SpotifUMData clone() {
        return new SpotifUMData(this);
    }

    @Override
    public String toString() {
        return "Albuns: " + albums.toString()
                + "\nUsers: " + users.toString()
                + "\nPlaylists: " + playlists.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpotifUMData that = (SpotifUMData) o;
        return Objects.equals(albums, that.albums)
                && Objects.equals(users, that.users)
                && Objects.equals(playlists, that.playlists);
    }

    public Map<String, Album> getMapAlbums() {
        Map<String, Album> mapCarros = new HashMap<>();
        for (Map.Entry<String, Album> entry : albums.entrySet()) {
            mapCarros.put(entry.getKey(), entry.getValue().clone());
        }
        return mapCarros;
    }

    public Map<String, User> getMapUsers() {
        Map<String, User> mapUsers = new HashMap<>();
        for (Map.Entry<String, User> entry : users.entrySet()) {
            mapUsers.put(entry.getKey(), entry.getValue().clone());
        }
        return mapUsers;
    }

    public User getCurrentUserPointer(String username) {
        return this.users.get(username);
    }

    public Map<String, Playlist> getMapPlaylists() {
        Map<String, Playlist> mapPlaylists = new HashMap<>();
        for (Map.Entry<String, Playlist> entry : playlists.entrySet()) {
            mapPlaylists.put(entry.getKey(), entry.getValue().clone());
        }
        return mapPlaylists;
    }

    public void setMapAlbums(Map<String, Album> mapAlbums) {
        Map<String, Album> newMapCarros = new HashMap<>();
        for (Map.Entry<String, Album> entry : mapAlbums.entrySet()) {
            newMapCarros.put(entry.getKey(), entry.getValue().clone());
        }
        this.albums = newMapCarros;
    }

    public void setMapUsers(Map<String, User> mapUsers) {
        Map<String, User> newMapUsers = new HashMap<>();
        for (Map.Entry<String, User> entry : mapUsers.entrySet()) {
            newMapUsers.put(entry.getKey(), entry.getValue().clone());
        }
        this.users = newMapUsers;
    }

    public void setMapPlaylists(Map<String, Playlist> mapPlaylists) {
        Map<String, Playlist> newMapPlaylists = new HashMap<>();
        for (Map.Entry<String, Playlist> entry : mapPlaylists.entrySet()) {
            newMapPlaylists.put(entry.getKey(), entry.getValue().clone());
        }
        this.playlists = newMapPlaylists;
    }

    public boolean existeAlbum(String title) {
        return albums.containsKey(title);
    }

    public boolean existeMusica(String title, String albumTitle) {
        if (existeAlbum(albumTitle)) {
            Album album = albums.get(albumTitle);
            for (Song song : album.getSongsCopy()) {
                if (song.getName().equals(title)) {
                    return true;
                }
            }
            return false;
        } else return false;
    }

    public boolean existePlaylist(String playlistName) {
        return playlists.containsKey(playlistName);
    }

    public boolean existeUser(String username) {
        return users.containsKey(username);
    }

    public int quantosAlbums() {
        return albums.size();
    }

    public int quantosPlaylists() {
        return playlists.size();
    }

    public int quantosUsers() {
        return users.size();
    }

    public Album getAlbum(String title) throws NaoExisteException {
        if (!existeAlbum(title)) {
            throw new NaoExisteException(title);
        } else {
            return albums.get(title);
        }
    }

    public Song getSong(String title, String albumTitle) throws NaoExisteException {
        if (!existeMusica(title, albumTitle)) {
            throw new NaoExisteException(title);
        } else {
            Album album = albums.get(albumTitle);
            for (Song song : album.getSongsCopy()) {
                if (song.getName().equals(title)) {
                    return song;
                }
            }
        }
        return null;
    }

    public Playlist getPlaylist(String playlistName) throws NaoExisteException {
        if (!existePlaylist(playlistName) || playlists.get(playlistName).isPrivate()) {
            throw new NaoExisteException(playlistName);
        } else {
            return playlists.get(playlistName);
        }
    }

    public Playlist getAnyPlaylist(String playlistName,User creator) throws NaoExisteException {
        if (existePlaylist(playlistName) && this.getPlaylistMapByCreator(creator).containsKey(playlistName)) {
            return playlists.get(playlistName);
        } else {
            throw new NaoExisteException(playlistName);
        }
    }

    public User getUser(String username) throws NaoExisteException {
        if (!existeUser(username)) {
            throw new NaoExisteException(username);
        } else {
            return users.get(username).clone();
        }
    }

    public void adicionaAlbum(Album album) throws JaExisteException {
        if (!albums.containsKey(album.getTitle())) {
            albums.put(album.getTitle(), album.clone());
        } else {
            throw new JaExisteException(album.getTitle());
        }
    }

    public void addPlaylist(Playlist playlist) throws JaExisteException {
        if (!playlists.containsKey(playlist.getPlaylistName())) {
            playlists.put(playlist.getPlaylistName(), playlist.clone());
        } else {
            throw new JaExisteException(playlist.getPlaylistName());
        }
    }

    public void adicionaUser(User user) throws JaExisteException {
        if (!users.containsKey(user.getName())) {
            users.put(user.getName(), user.clone());
        } else {
            throw new JaExisteException(user.getName());
        }
    }

    public void removeAlbum(String title) throws NaoExisteException {
        if (albums.containsKey(title)) {
            albums.remove(title);
        } else {
            throw new NaoExisteException(title);
        }
    }

    public void removePlaylist(String playlistName) throws NaoExisteException {
        if (playlists.containsKey(playlistName)) {
            playlists.remove(playlistName);
        } else {
            throw new NaoExisteException(playlistName);
        }
    }

    public void removeUser(String username) throws NaoExisteException {
        if (users.containsKey(username)) {
            users.remove(username);
        } else {
            throw new NaoExisteException(username);
        }
    }

    public Map<String, Playlist> getPlaylistMapByCreator(User creator) {
        return playlists.entrySet().stream()
                .filter(e -> e.getValue().getCreator().equals(creator))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    /**
     * Directly increments the play count for a song in all albums by name and artist
     * @param songName Name of the song to update
     * @param artistName Artist of the song to update
     * @return true if at least one song was updated
     */
    public boolean incrementSongPlayCount(String songName, String artistName) {
        boolean updated = false;

        // Update in albums
        for (Album album : this.albums.values()) {
            for (Song song : album.getSongs()) {
                if (song.getName().equals(songName) && song.getArtist().equals(artistName)) {
                    song.incrementTimesPlayed();
                    updated = true;
                    // For debugging
                    System.out.println("[DEBUG] Incremented song '" + songName +
                            "' by '" + artistName +
                            "' in album '" + album.getTitle() +
                            "'. New count: " + song.getTimesPlayed());
                }
            }
        }

        return updated;
    }



}