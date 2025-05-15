package org.spotifumtp37.delegate;

import org.spotifumtp37.model.SpotifUMData;
import org.spotifumtp37.model.album.Song;
import org.spotifumtp37.model.playlist.Playable;
import org.spotifumtp37.model.user.User;

import java.util.Scanner;
import java.io.IOException;

public class PlayerUI {

    private final Scanner scanner;
    private final SpotifUMData modelData;

    public PlayerUI(Scanner scanner, SpotifUMData modelData) {
        this.scanner = scanner;
        this.modelData = modelData;
    }

    /**
     * Play a playable: shows lyrics and reacts to next/prev/stop.
     */
    public void playSong(Playable playable, User user) throws IOException {
        boolean continuePlaying = true;

        while (continuePlaying) {

            playable.play(user);

            Song song = playable.getCurrentSong();
            System.out.printf("Now playing \"%s\" by %s%n", song.getName(), song.getArtist());


            System.out.println("Lyrics:\n" + song.getLyrics());
            System.out.println("\nControls: n=next, p=prev, s=stop");

            boolean waitingForInput = true;
            while (waitingForInput) {
                System.out.print("Command: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;
                char cmd = input.charAt(0);
                switch (cmd) {
                    case 'n': // next
                        playable.next(user);
                        System.out.println("Skipping to next...");
                        waitingForInput = false;
                        break;
                    case 'p': // prev
                        try {
                            playable.previous(user);
                            System.out.println("Going to previous...");
                        } catch (UnsupportedOperationException e) {
                            System.out.println("Cannot go back.");
                        }
                        waitingForInput = false;
                        break;
                    case 's': // stop
                        System.out.println("Stopped.");
                        continuePlaying = false;
                        waitingForInput = false;
                        break;
                    default:
                        System.out.println("Unknown command. Try n (next), p (prev), s (stop).");
                }
            }
            System.out.println("Track finished.\n");

        }
    }
}