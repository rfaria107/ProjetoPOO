package org.spotifumtp37.model.album;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {

    private Song song;

    @BeforeEach
    void setUp() {
        song = new Song("Halo", "Beyonce", "Columbia", "Remember those walls...", "C G Am F", "Pop", 240);
    }

    @Test
    void getName() {
        assertEquals("Halo", song.getName());
    }

    @Test
    void setName() {
        song.setName("Single Ladies");
        assertEquals("Single Ladies", song.getName());
    }

    @Test
    void getArtist() {
        assertEquals("Beyonce", song.getArtist());
    }

    @Test
    void setArtist() {
        song.setArtist("Adele");
        assertEquals("Adele", song.getArtist());
    }

    @Test
    void getPublisher() {
        assertEquals("Columbia", song.getPublisher());
    }

    @Test
    void setPublisher() {
        song.setPublisher("Sony");
        assertEquals("Sony", song.getPublisher());
    }

    @Test
    void getLyrics() {
        assertEquals("Remember those walls...", song.getLyrics());
    }

    @Test
    void setLyrics() {
        song.setLyrics("Hello, it's me...");
        assertEquals("Hello, it's me...", song.getLyrics());
    }

    @Test
    void getMusicalNotes() {
        assertEquals("C G Am F", song.getMusicalNotes());
    }

    @Test
    void setMusicalNotes() {
        song.setMusicalNotes("F#m D A E");
        assertEquals("F#m D A E", song.getMusicalNotes());
    }

    @Test
    void getGenre() {
        assertEquals("Pop", song.getGenre());
    }

    @Test
    void setGenre() {
        song.setGenre("Rock");
        assertEquals("Rock", song.getGenre());
    }

    @Test
    void getDurationInSeconds() {
        assertEquals(240, song.getDurationInSeconds());
    }

    @Test
    void setDurationInSeconds() {
        song.setDurationInSeconds(180);
        assertEquals(180, song.getDurationInSeconds());
    }

    @Test
    void getTimesPlayed() {
        assertEquals(0, song.getTimesPlayed());
    }

    @Test
    void setTimesPlayed() {
        song.setTimesPlayed(5);
        assertEquals(5, song.getTimesPlayed());
    }

    @Test
    void incrementTimesPlayed() {
        song.incrementTimesPlayed();
        song.incrementTimesPlayed();
        assertEquals(2, song.getTimesPlayed());
    }

    @Test
    void testEquals() {
        Song copy = new Song(song);
        assertEquals(song, copy);

        Song diff = new Song("Other", "Other", "Other", "", "", "Pop", 240);
        assertNotEquals(song, diff);
        assertNotEquals(song, null);
        assertNotEquals(song, "notASong");
    }

    @Test
    void testHashCode() {
        Song copy = new Song(song);
        assertEquals(song.hashCode(), copy.hashCode());
    }

    @Test
    void testClone() {
        Song cloned = song.clone();
        assertEquals(song, cloned);
        assertNotSame(song, cloned);
    }

    @Test
    void testToString() {
        String str = song.toString();
        assertTrue(str.contains("name='Halo'"));
        assertTrue(str.contains("artist='Beyonce'"));
    }
}