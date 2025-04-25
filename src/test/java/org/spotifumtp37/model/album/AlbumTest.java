package org.spotifumtp37.model.album;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    @Test
    void addSong_ShouldAddSongToAlbum_WhenAllParametersAreValid() {
        // Arrange
        Album album = new Album("Test Album", "Test Artist", 2023, "Pop");
        String name = "Test Song";
        String publisher = "Test Publisher";
        String lyrics = "Test Lyrics";
        String musicalNotes = "C D E";
        String genre = "Pop";
        int durationInSeconds = 180;

        // Act
        album.addSong(name, publisher, lyrics, musicalNotes, genre, durationInSeconds);

        // Assert
        List<Song> songs = album.getSongs();
        assertEquals(1, songs.size());
        assertEquals(name, songs.get(0).getName());
        assertEquals("Test Artist", songs.get(0).getArtist());
        assertEquals(durationInSeconds, songs.get(0).getDurationInSeconds());
    }

    @Test
    void addSong_ShouldThrowException_WhenSongNameIsNull() {
        // Arrange
        Album album = new Album("Test Album", "Test Artist", 2023, "Pop");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            album.addSong(null, "Test Publisher", "Test Lyrics", "C D E", "Pop", 180);
        });
        assertEquals("Song name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void addSong_ShouldThrowException_WhenSongNameIsEmpty() {
        // Arrange
        Album album = new Album("Test Album", "Test Artist", 2023, "Pop");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            album.addSong("", "Test Publisher", "Test Lyrics", "C D E", "Pop", 180);
        });
        assertEquals("Song name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void addSong_ShouldThrowException_WhenSongDurationIsNonPositive() {
        // Arrange
        Album album = new Album("Test Album", "Test Artist", 2023, "Pop");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            album.addSong("Test Song", "Test Publisher", "Test Lyrics", "C D E", "Pop", 0);
        });
        assertEquals("Song duration must be positive.", exception.getMessage());
    }

    @Test
    void addSong_ShouldThrowException_WhenDuplicateSongNameExists() {
        // Arrange
        Album album = new Album("Test Album", "Test Artist", 2023, "Pop");
        album.addSong("Test Song", "Test Publisher", "Test Lyrics", "C D E", "Pop", 180);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            album.addSong("Test Song", "Test Publisher", "Test Lyrics", "C D E", "Pop", 180);
        });
        assertEquals("A song with the same name already exists in the album.", exception.getMessage());
    }

    @Test
    void addSong_ShouldHandleCaseInsensitiveDuplicateSongNames() {
        // Arrange
        Album album = new Album("Test Album", "Test Artist", 2023, "Pop");
        album.addSong("Test Song", "Test Publisher", "Test Lyrics", "C D E", "Pop", 180);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            album.addSong("tEsT sOnG", "Test Publisher", "Test Lyrics", "C D E", "Pop", 180);
        });
        assertEquals("A song with the same name already exists in the album.", exception.getMessage());
    }
}