package org.spotifumtp37.delegate;

import org.spotifumtp37.model.SpotifUMData;
import org.spotifumtp37.model.album.Album;
import org.spotifumtp37.model.album.Song;
import org.spotifumtp37.model.exceptions.JaExisteException;
import org.spotifumtp37.model.exceptions.NaoExisteException;
import org.spotifumtp37.model.playlist.Playlist;
import org.spotifumtp37.model.subscription.FreePlan;
import org.spotifumtp37.model.subscription.PremiumBase;
import org.spotifumtp37.model.subscription.PremiumTop;
import org.spotifumtp37.model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserUI {
    private final Scanner scanner;
    private User loggedUser;
    private final SpotifUMData modelData;
    private final PlayerUI playerUI; // Added PlayerUI instance

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    UserUI(SpotifUMData modelData, User loggedUser, Scanner scanner) {
        this.loggedUser = loggedUser;
        this.modelData = modelData;
        this.scanner = scanner;
        this.playerUI = new PlayerUI(scanner, modelData); // Initialize PlayerUI
    }

    public void showUserMenu() {
        NewMenu userMenu = new NewMenu(new String[]{
                "Listen to Music",
                "Manage Playlists",
                "Update Subscription",
                "Edit Profile"
        });

        userMenu.setHandler(1, this::showMusicSelectionMenu);
        userMenu.setHandler(2, this::showUserPlaylistManagementMenu);
        userMenu.setHandler(3, this::showChangeSubscriptionMenu);
        userMenu.setHandler(4, this::showUserEditMenu);

        userMenu.run();
    }

    //submenus do user

    public void showMusicSelectionMenu() {
        NewMenu musicSelectionMenu = new NewMenu(new String[]{
                "Play from playlist",
                "Play from album",
                "Play free playlist"
        });

        musicSelectionMenu.setPreCondition(1, () -> this.loggedUser.getSubscriptionPlan().canBrowsePlaylist());
        musicSelectionMenu.setPreCondition(2, () -> this.loggedUser.getSubscriptionPlan().canBrowsePlaylist());

        musicSelectionMenu.setHandler(1, this::showPlayFromPlaylistSelectionMenu);
        musicSelectionMenu.setHandler(2, this::showPlayFromAlbumSelectionMenu);
        // musicSelectionMenu.setHandler(3, this::playFreePlaylist); // To be implemented later

        musicSelectionMenu.run();
    }

    public void showPlayFromPlaylistSelectionMenu() {
        NewMenu playFromPlaylistMenu = new NewMenu(new String[]{
                "View All Playlists",
                "Play from a Specific Playlist"
        });

        playFromPlaylistMenu.setHandler(1, this::viewAllPlaylistsForPlaying);
        playFromPlaylistMenu.setHandler(2, this::playFromSpecificPlaylist);
        // Add pre-conditions if necessary

        playFromPlaylistMenu.run();
    }

    public void showPlayFromAlbumSelectionMenu() {
        NewMenu playFromAlbumMenu = new NewMenu(new String[]{
                "View All Albums",
                "Play from a Specific Album"
        });

        playFromAlbumMenu.setHandler(1, this::viewAllAlbumsForPlaying);
        playFromAlbumMenu.setHandler(2, this::playFromSpecificAlbum);
        // Add pre-conditions if necessary

        playFromAlbumMenu.run();
    }

    private void viewAllPlaylistsForPlaying() {
        System.out.println("Available Playlists:");
        this.modelData.getMapPlaylists().forEach((name, playlist) -> {
            if (playlist.isPublic() || playlist.getCreator().equals(loggedUser)) {
                System.out.println("- " + name + (playlist.isPublic() ? " (Public)" : " (Private)"));
            }
        });
        System.out.println("--- End of Playlist List ---");
    }

    private void playFromSpecificPlaylist() {
        System.out.println("Enter the name of the playlist to play: ");
        scanner.nextLine(); // Consume leftover newline
        String playlistName = scanner.nextLine().trim();
        try {
            Playlist playlist = modelData.getAnyPlaylist(playlistName, loggedUser);
            System.out.println("Playing playlist: " + playlist.getPlaylistName());
            if (playlist.getSongs().isEmpty()) {
                System.out.println("This playlist is empty.");
            } else {
                // Use the new play method with playback controls
                try {
                    playerUI.playSong(playlist, loggedUser);
                } catch (IOException e) {
                    System.out.println("Error playing playlist. Please try again later." + e.getMessage());
                }
            }
        } catch (NaoExisteException e) {
            System.out.println("Playlist not found or not accessible." + e.getMessage());
        }
    }

    private void viewAllAlbumsForPlaying() {
        System.out.println("Available Albums:");
        this.modelData.getMapAlbums().forEach((title, album) -> {
            System.out.println("- " + title + " by " + album.getArtist());
        });
        System.out.println("--- End of Album List ---");
    }

    private void playFromSpecificAlbum() {
        System.out.println("Enter the name of the album to play: ");
        scanner.nextLine(); // Consume leftover newline
        String albumName = scanner.nextLine().trim();
        try {
            Album album = modelData.getAlbum(albumName);
            System.out.println("Playing album: " + album.getTitle());
            if (album.getSongsCopy().isEmpty()) {
                System.out.println("This album has no songs.");
            } else {
                // Use the new play method with playback controls
                try {
                    playerUI.playSong(album, loggedUser);
                } catch (IOException e) {
                    System.out.println("Error playing playlist. Please try again later." + e.getMessage());
                }
            }
        } catch (NaoExisteException e) {
            System.out.println("Album not found.");
        }
    }

    public void showUserPlaylistManagementMenu() {
        NewMenu userPlaylistMenu = new NewMenu(new String[]{
                "Create User Playlist",
                "Add song to playlist",
                "Create Recommended Playlist",
                "View My Playlists",
                "Delete Playlist"
        });

        userPlaylistMenu.setPreCondition(1, () -> this.loggedUser.getSubscriptionPlan().canCreatePlaylist());
        userPlaylistMenu.setPreCondition(2, () -> this.loggedUser.getSubscriptionPlan().canCreatePlaylist());
        userPlaylistMenu.setPreCondition(3, () -> this.loggedUser.getSubscriptionPlan().canAcessFavouritesList());
        userPlaylistMenu.setPreCondition(5, () -> this.loggedUser.getSubscriptionPlan().canCreatePlaylist());

        userPlaylistMenu.setHandler(1, this::createPlaylist);
        userPlaylistMenu.setHandler(2, this::addSongToPlaylist);
        userPlaylistMenu.setHandler(4, this::viewUserPlaylists);
        userPlaylistMenu.setHandler(5, this::deletePlaylist);

        userPlaylistMenu.run();
    }

    private void showChangeSubscriptionMenu() {
        NewMenu changeSubscriptionMenu = new NewMenu(new String[]{
                "FreePlan",
                "PremiumBase",
                "PremiumTop"
        });

        changeSubscriptionMenu.setPreCondition(1, () -> !this.loggedUser.getSubscriptionPlan().equals(new FreePlan()));

        changeSubscriptionMenu.setPreCondition(2, () -> !this.loggedUser.getSubscriptionPlan().equals(new PremiumBase()));

        changeSubscriptionMenu.setPreCondition(3, () -> !this.loggedUser.getSubscriptionPlan().equals(new PremiumTop()));

        changeSubscriptionMenu.setHandler(1, this::alteraSubscriptionFreePlan);
        changeSubscriptionMenu.setHandler(2, this::alteraSubscriptionPremiumBasePlan);
        changeSubscriptionMenu.setHandler(3, this::alteraSubscriptionPremiumTopPlan);

        changeSubscriptionMenu.run();
    }

    private void showUserEditMenu() {
        NewMenu userEditMenu = new NewMenu(new String[]{
                "Change Password",
                "Change Email",
                "Change Address"
        });


        userEditMenu.setHandler(1, this::changePassword);
        userEditMenu.setHandler(2, this::changeEmail);
        userEditMenu.setHandler(3, this::changeAdress);
        userEditMenu.run();
    }


    private void alteraSubscriptionFreePlan() {
        FreePlan freePlan = new FreePlan();
        this.loggedUser.setSubscriptionPlan(freePlan);
        System.out.println("Subscription changed to Free Plan.");

    }

    private void alteraSubscriptionPremiumBasePlan() {
        PremiumBase premiumBase = new PremiumBase();
        this.loggedUser.setSubscriptionPlan(premiumBase);
        System.out.println("Subscription changed to Premium Base Plan.");

    }

    private void alteraSubscriptionPremiumTopPlan() {
        PremiumTop premiumTop = new PremiumTop();
        this.loggedUser.updatePremiumTop(premiumTop); // Assuming updatePremiumTop handles setting the plan
        System.out.println("Subscription changed to Premium Top Plan.");

    }

    public void createPlaylist() {
        List<Song> songs = new ArrayList<>();

        System.out.println("Create New Playlist");
        String playlistName;
        scanner.nextLine(); // Consume the leftover newline
        System.out.println("Enter the name for your new playlist: ");
        playlistName = scanner.nextLine().trim();

        while (this.modelData.existePlaylist(playlistName)) {
            System.out.println("A playlist with this name already exists. Please choose another name: ");
            playlistName = scanner.nextLine().trim();
        }

        System.out.println("Enter a description for the playlist: ");
        String playlistDescription = scanner.nextLine().trim();
        String visibility;
        do {
            System.out.println("Set playlist visibility (private or public): ");
            visibility = scanner.nextLine().trim().toLowerCase();
            if (!visibility.equals("private") && !visibility.equals("public")) {
                System.out.println("Invalid option! Please enter 'private' or 'public'.");
            }
        } while (!visibility.equals("private")
                && !visibility.equals("public"));

        int n;
        System.out.println("How many songs to add initially?: ");
        // Input validation for integer
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a valid number. Please enter an integer:");
            scanner.next(); // consume the invalid input
        }
        n = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline

        while (n <= 0) {
            System.out.println("Type a number greater than 0: ");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a valid number. Please enter an integer:");
                scanner.next(); // consume the invalid input
            }
            n = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline
        }

        while (n > 0) {
            while (true) {
                System.out.println("Type the name of the album of the song you want to add to the playlist: ");
                String nomeAlbum = scanner.nextLine().trim();

                try {
                    Album album = this.modelData.getAlbum(nomeAlbum);
                    System.out.println("Songs in album '" + album.getTitle() + "':");
                    album.getSongsCopy().forEach(song -> System.out.println("- " + song.getName())); // Use getTitulo
                    while (true) {
                        System.out.println("Type the name of the song you want to add to the playlist: ");
                        String nomeMusica = scanner.nextLine().trim();
                        try {
                            songs.add(this.modelData.getSong(nomeMusica, nomeAlbum));
                            System.out.println("Song '" + nomeMusica + "' added to playlist");
                            break;
                        } catch (NaoExisteException e) {
                            System.out.println("Incorrect song name, does not exist in album '" + nomeAlbum + "'. Try again.");
                        }
                    }
                    break;

                } catch (NaoExisteException e) {
                    System.out.println("Incorrect album name, does not exist. Try again.");
                }
            }
            n--;
        }

        Playlist playlist = new Playlist(this.loggedUser, playlistName, playlistDescription, 0, visibility, songs);
        try {
            this.modelData.addPlaylist(playlist);
            System.out.println("Playlist '" + playlistName + "' created successfully!");
        } catch (JaExisteException e) {
            System.out.println("Error: A playlist with that name already exists."); // Should have been caught by existePlaylist check
        }
    }


    private void addSongToPlaylist() {
        String nomePlaylist;
        Playlist playlist;
        System.out.println("Enter the name of the playlist to add a song to: ");
        scanner.nextLine(); // Consume leftover newline if any
        nomePlaylist = scanner.nextLine().trim();

        while (!this.modelData.existePlaylist(nomePlaylist)) {
            System.out.println("Playlist does not exist. Enter another name or type 'exit' to cancel: ");
            nomePlaylist = scanner.nextLine().trim();
            if (nomePlaylist.equalsIgnoreCase("exit")) {
                System.out.println("Operation cancelled.");
                return;
            }
        }
        try {
            playlist = this.modelData.getAnyPlaylist(nomePlaylist, loggedUser);
            // Check if the logged-in user is the creator of the playlist
            if (!playlist.getCreator().equals(loggedUser)) {
                System.out.println("You can only add songs to your own playlists.");
                return;
            }
        } catch (NaoExisteException e) {
            System.out.println("Error: Playlist not found or you do not have permission.");
            return;
        }


        if (!loggedUser.getSubscriptionPlan().canCreatePlaylist()) { // Assuming canCreatePlaylist also covers adding songs
            System.out.println("Your current subscription plan doesn't allow adding songs to playlists.");
            System.out.println("Please upgrade your subscription to access this feature.");
            return;
        }
        while (true) {
            System.out.println("Enter the name of the album of the song to add:");
            String nomeAlbum = scanner.nextLine().trim();
            try {
                Album album = this.modelData.getAlbum(nomeAlbum);
                System.out.println("Songs in album '" + album.getTitle() + "':");
                album.getSongsCopy().forEach(song -> System.out.println("- " + song.getName())); // Use getTitulo

                while (true) {
                    System.out.println("Choose the song you want to add to the playlist '" + playlist.getPlaylistName() + "':");
                    String nomeMusica = scanner.nextLine().trim();
                    try {
                        Song added = this.modelData.getSong(nomeMusica, nomeAlbum);
                        if (playlist.getSongs().contains(added)) { // Check if song is already in playlist
                            System.out.println("Song already in playlist, try again.");
                            // No need to re-prompt for nomeMusica here, loop will do it
                        } else {
                            playlist.addSong(added);
                            System.out.println("Song '" + added.getName() + "' added to playlist '" + playlist.getPlaylistName() + "'.");
                            return; // Exit after successfully adding a song
                        }
                    } catch (NaoExisteException e) {
                        System.out.println("Incorrect song name, does not exist in album '" + nomeAlbum + "'. Try again.");
                    }
                }
            } catch (NaoExisteException e) {
                System.out.println("Incorrect album name, does not exist. Try again.");
            }
        }
    }

    private void viewUserPlaylists() {
        System.out.println("Your Playlists:");
        Map<String, Playlist> userPlaylists = this.modelData.getPlaylistMapByCreator(loggedUser);
        if (userPlaylists.isEmpty()) {
            System.out.println("You haven't created any playlists yet.");
        } else {
            userPlaylists.keySet().forEach(System.out::println);
        }
        System.out.println("--- End of Your Playlists ---");
    }

    public void deletePlaylist() {
        System.out.println("Deleting Playlist");
        System.out.print("Enter the name of the playlist to delete: ");
        scanner.nextLine(); // consume leftover newline
        String name = scanner.nextLine().trim();

        if (!modelData.existePlaylist(name)) {
            System.out.print("Playlist not found. Cannot delete.");
            return;
        }
        try {
            Playlist playlistToDelete = this.modelData.getPlaylist(name); // Assuming this gets any playlist by name
            if (!playlistToDelete.getCreator().equals(this.loggedUser)) {
                System.out.println("Error: You can only delete your own playlists.");
                return;
            }
            this.modelData.removePlaylist(name);
            System.out.println("Playlist '" + name + "' deleted successfully!");
        } catch (NaoExisteException e) {
            // This case should ideally be covered by existePlaylist,
            // but good to have for robustness if getPlaylist has stricter access.
            System.out.println("Error: Playlist not found or you don't have permission to delete it.");
        }
    }

    public void changePassword() {
        String loggedUserPassword = this.loggedUser.getPassword();
        scanner.nextLine(); // Consume leftover newline

        String currentPassword;
        while (true) {
            System.out.print("Enter your current password: ");
            currentPassword = scanner.nextLine().trim();
            if (currentPassword.equals(loggedUserPassword)) {
                break;
            }
            System.out.println("Incorrect password. Please try again.");
        }

        String newPassword;
        while (true) {
            System.out.print("Enter your new password: ");
            newPassword = scanner.nextLine().trim();

            System.out.print("Confirm your new password: ");
            String confirmPassword = scanner.nextLine().trim();

            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");
                continue;
            }

            if (newPassword.isEmpty()) {
                System.out.println("New password cannot be empty. Please try again.");
                continue;
            }

            break;
        }

        this.loggedUser.setPassword(newPassword);
        System.out.println("Your password has been successfully changed.");
    }

    public void changeEmail() {
        String loggedUserEmail = this.loggedUser.getEmail();
        scanner.nextLine(); // Consume leftover newline

        String currentEmail;
        while (true) {
            System.out.print("Enter your current Email: ");
            currentEmail = scanner.nextLine().trim();
            if (currentEmail.equals(loggedUserEmail)) {
                break;  // correct, exit loop
            }
            System.out.println("Incorrect Email. Please try again.");
        }

        String newEmail;
        System.out.print("Enter your new Email: ");
        newEmail = scanner.nextLine().trim();
        while (!newEmail.contains("@")) { // Basic email validation
            System.out.print("Please enter a valid email address: ");
            newEmail = scanner.nextLine().trim();
        }


        this.loggedUser.setEmail(newEmail);
        System.out.println("Your Email has been successfully changed.");

    }

    public void changeAdress() {
        String loggedUserAdress = this.loggedUser.getAddress();
        scanner.nextLine(); // Consume leftover newline

        String currentAdress;
        while (true) {
            System.out.print("Enter your current Address: ");
            currentAdress = scanner.nextLine().trim();
            if (currentAdress.equals(loggedUserAdress)) {
                break;
            }
            System.out.println("Incorrect Address. Please try again.");
        }

        String newAddress;
        System.out.print("Enter your new Address: ");
        newAddress = scanner.nextLine().trim();
        while (newAddress.isEmpty()) {
            System.out.print("Address cannot be empty. Please enter your new address: ");
            newAddress = scanner.nextLine().trim();
        }


        this.loggedUser.setAddress(newAddress);
        System.out.println("Your Address has been successfully changed.");

    }
}