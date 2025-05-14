package org.spotifumtp37.model.playlist;

import org.spotifumtp37.model.album.Song;
import org.spotifumtp37.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Playlist implements Playable{
    private User creator;
    private String playlistName;
    private String playlistDescription;
    private int numberOfFollowers;
    private String status; // public or private
    private List<Song> songs;
    private Song currentSong;

    public Playlist(User creator, String playlistName, String playlistDescription, int numberOfFollowers, String status, List<Song> songs) {
        this.creator = creator.clone();
        this.playlistName = playlistName;
        this.playlistDescription = playlistDescription;
        this.numberOfFollowers = numberOfFollowers;
        this.status = status;
        this.songs = new ArrayList<>();
        for(Song song: songs){
            this.songs.add(song);
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(songs.size()-1);
        this.currentSong = songs.get(randomIndex);
    }

    public Playlist (Playlist other){
        this.creator = other.creator.clone();
        this.playlistName = other.playlistName;
        this.playlistDescription = other.playlistDescription;
        this.numberOfFollowers = other.numberOfFollowers;
        this.status = other.status;
        this.songs = new ArrayList<>();
        for(Song song: other.songs){
            this.songs.add(song);
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(songs.size()-1);
        this.currentSong = songs.get(randomIndex);
    }

    @Override
    public Playlist clone() {
        return new Playlist(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        Playlist other = (Playlist) obj;
        return Objects.equals(creator, other.creator) && Objects.equals(playlistName, other.playlistName)
                && Objects.equals(playlistDescription, other.playlistDescription) && numberOfFollowers == other.numberOfFollowers
                && Objects.equals(status, other.status) && Objects.equals(songs, other.songs)
                && Objects.equals(currentSong, other.currentSong);
    }

    @Override
    public String toString() {
        return "Playlist{" + "creator: {'" + creator + "} \'" + ", playlistName='" + playlistName + '\''
                + ", playlistDescription='" + playlistDescription + '\'' + ", numberOfFollowers=" + numberOfFollowers
                + ", status='" + status + '\'' + ", songs=" + songs + '}' + "currentsong: {'" + currentSong + "'}";
    }

    public User getCreator() {
        return creator.clone();
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public String getPlaylistDescription() {
        return playlistDescription;
    }

    public int getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public String getStatus() {
        return status;
    }

    public List<Song> getSongs() {
        List<Song> copy = new ArrayList<>();
        for( Song song: songs){
            copy.add(song);
        }
        return copy;
    }

    public Song getCurrentSong() {
        return currentSong.clone();
    }

    public void setCreatorUsername(User creator) {
        this.creator = creator.clone();
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public void setPlaylistDescription(String playlistDescription) {
        this.playlistDescription = playlistDescription;
    }

    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSongs(List<Song> songs) {
        for (Song song : songs) {
            this.songs.add(song);
        }
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong.clone();
    }

    public boolean isPrivate (){
        return status.equalsIgnoreCase("private");
    }

    public boolean isPublic (){
        return status.equalsIgnoreCase("public");
    }

    @Override
    public void next(User user) {
        if (user.getSubscriptionPlan().podeNavegarPlaylist()){
            currentSong = songs.get((songs.indexOf(currentSong)+1)%songs.size());
            currentSong.incrementTimesPlayed();
        }
        else {
            Random rand = new Random();
            int randomIndex = rand.nextInt(songs.size()-1);
            while (randomIndex == songs.indexOf(currentSong)){
               randomIndex = rand.nextInt(songs.size()-1);
            }
            currentSong = songs.get(randomIndex);
            currentSong.incrementTimesPlayed();
        }
        user.somarPontos();
    }
    @Override
    public void previous(User user) {
        if (user.getSubscriptionPlan().podeNavegarPlaylist()){
            currentSong = songs.get((songs.indexOf(currentSong)-1+songs.size()-1)%songs.size()-1);
            currentSong.incrementTimesPlayed();
        }
        else {
            throw new UnsupportedOperationException("Your subscription does not allow going back in the playlist.");
        }
        user.somarPontos();
    }

    public void addSong(Song song){
        if (creator.getSubscriptionPlan().podeCriarPlaylist()){
            if(!songs.contains(song)){
            songs.add(song.clone());
            }
            else {
                throw new UnsupportedOperationException("This song is already in the playlist.");
            }
        }
        else {
            throw new UnsupportedOperationException("Your subscription does not allow adding songs to the playlist.");
        }
    }
    public void deleteSong(Song song){
        if (creator.getSubscriptionPlan().podeCriarPlaylist()){
            if (songs.contains(song)){
                songs.remove(song.clone());
            }
            else {
                throw new UnsupportedOperationException("This song is not in the playlist.");
            }
        }
        else {
            throw new UnsupportedOperationException("Your subscription does not allow deleting songs from the playlist.");
        }
    }

    @Override
    public void play(User user) {
        this.currentSong.incrementTimesPlayed();
        user.somarPontos();
        user.updateHistory(this.currentSong);
    }

    @Override
    public void pauseMusic() {
        return;
    }
}
