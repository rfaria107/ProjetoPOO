package org.example;

import org.example.model.album.Album;

public class Main {
    public static void main(String[] args) {

        //testes do album e musica
        Album album = new Album("Jar Of Flies", "Alice In Chain", 1994, "Rock");

        // Add Songs
        album.addSong("Rotten Apple", "Sony", "what i see is unreal", "D,G,C", "Rock", 3658);
        album.addSong("Nutshell", "Sony", "we chase misprinted lies", "C,D,A", "Rock", 2419);

        // Print songs
        System.out.println("Songs in Album: " + album.getTitle());
        album.getSongs().forEach(song -> System.out.println(song.getName()));

        System.out.println("Total Album DUration: " + album.getTotalDuration() + " seconds");

    }
}