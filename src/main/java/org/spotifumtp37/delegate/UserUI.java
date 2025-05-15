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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserUI {
    private final Scanner scanner;
    private User loggedUser;
    private SpotifUMData modelData;

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    UserUI(SpotifUMData modelData, User loggedUser, Scanner scanner) {
        this.loggedUser = loggedUser;
        this.modelData = modelData;
        this.scanner = scanner;
    }

    public void showUserMenu() {
        NewMenu userMenu = new NewMenu(new String[]{
                "Listen to Music",
                "Manage Playlists",
                "Update Subscription",
                "Edit Profile"
        });

        userMenu.setPreCondition(2, () -> loggedUser.getSubscriptionPlan().canCreatePlaylist());

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
                "Play from album"
        });


        //playlistMenu.setHandler(1, this::createPlaylist);
        //playlistMenu.setHandler(2, this::viewPlaylists);

        musicSelectionMenu.run();
    }

    public void showUserPlaylistManagementMenu() {
        NewMenu userPlaylistMenu = new NewMenu(new String[]{
                "Create User Playlist",
                "Create Recommended Playlist",
                "View My Playlists",
                "Delete Playlist"
        });

        userPlaylistMenu.setPreCondition(1, () -> this.loggedUser.getSubscriptionPlan().canCreatePlaylist());
        userPlaylistMenu.setPreCondition(2, () -> this.loggedUser.getSubscriptionPlan().canAcessFavouritesList());
        userPlaylistMenu.setPreCondition(4, () -> this.loggedUser.getSubscriptionPlan().canCreatePlaylist());

        userPlaylistMenu.setHandler(1, this::createPlaylist);
        //playlistMenu.setHandler(2, this::);
        userPlaylistMenu.setHandler(3, this::viewUserPlaylists);
        userPlaylistMenu.setHandler(4, this::deletePlaylist);

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


        //playlistMenu.setHandler(1, this::createPlaylist);
        //playlistMenu.setHandler(2, this::viewPlaylists);
        //playlistMenu.setHandler(3, this::addSongToPlaylist);
        //playlistMenu.setHandler(4, this::removeSongFromPlaylist);

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
        this.loggedUser.updatePremiumTop(premiumTop);
        System.out.println("Subscription changed to Premium Top Plan.");

    }

    public void createPlaylist() {
        List<Song> songs = new ArrayList<>();

        System.out.println("Create New Playlist");
        String playlistName;
        scanner.nextLine();
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

        int n = 0;
        System.out.println("How many songs to add initially?: ");
        n = scanner.nextInt();
        scanner.nextLine();

        while (n <= 0) {
            System.out.println("Type a number greater than 0: ");
            n = scanner.nextInt();
            scanner.nextLine();
        }

        while (n > 0) {
            while (true) {
                System.out.println("Type the name of the album of the song you want to add to the playlist: ");
                String nomeAlbum = scanner.nextLine().trim();

                try {
                    Album album = this.modelData.getAlbum(nomeAlbum);
                    for (Song song : album.getSongs()) {
                        System.out.println(song);
                    }
                    while (true) {
                        System.out.println("Type the name of the song you want to add to the playlist: ");
                        String nomeMusica = scanner.nextLine().trim();
                        try {
                            songs.add(this.modelData.getSong(nomeMusica, nomeAlbum));
                            System.out.println("Song added to playlist");
                            break;
                        } catch (NaoExisteException e) {
                            System.out.println("Incorrect name, does not exist");
                        }
                    }
                    break;

                } catch (NaoExisteException e) {
                    System.out.println("Incorrect name, does not exist");
                }
            }
            n--;
        }

        Playlist playlist = new Playlist(this.loggedUser, playlistName, playlistDescription, 0, visibility, songs);
        try {
            this.modelData.addPlaylist(playlist);
            System.out.println("Playlist '" + playlistName + "' created successfully!");
        } catch (JaExisteException e) {
            System.out.println("Error: A playlist with that name already exists.");
        }
    }

    public void viewUserPlaylists() {
        System.out.println("Your Playlists");
        for (Playlist playlist : modelData.getMapPlaylists().values()) {
            if (playlist.getCreator().getName().equals(this.loggedUser.getName())) {
                System.out.println(playlist);
            }
        }
    }

    public void deletePlaylist() {
        System.out.println("Deleting Playlist");
        System.out.print("Enter the name of the playlist to delete: ");
        String name = scanner.nextLine().trim();

        while (!modelData.existePlaylist(name)) {
            System.out.print("Playlist not found. Enter the name of the playlist to delete, or exit to cancel: ");
            name = scanner.nextLine().trim();
            if (name.equalsIgnoreCase("exit")) {
                System.out.println("Operação cancelada.");
                return;
            }
        }
        try {
            Playlist playlistToDelete = this.modelData.getPlaylist(name);
            if (!playlistToDelete.getCreator().equals(this.loggedUser.getName())) {
                System.out.println("Error: You can only delete your own playlists.");
                return;
            }
        } catch (NaoExisteException e) {
            System.out.println("Error: Playlist not found.");
        }
        try {
            this.modelData.removePlaylist(name); // Use this.modelData
            System.out.println("Playlist '" + name + "' deleted successfully!");
        } catch (NaoExisteException e) {
            System.out.println("Error: Playlist not found.");
        }
    }
}
