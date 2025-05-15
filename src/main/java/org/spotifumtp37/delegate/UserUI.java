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


    private void addSongToPlaylist() {
        String nomePlaylist;
        Playlist playlist;
        System.out.println("Digite o nome da playlist: ");

        nomePlaylist = scanner.nextLine().trim();
        while (!this.modelData.existePlaylist(nomePlaylist)) {
            System.out.println("A playlist não existe. Digite outro nome: ");
            nomePlaylist = scanner.nextLine().trim();
        }
        try {
            playlist = this.modelData.getPlaylist(nomePlaylist);}
        catch (NaoExisteException e) {
            System.out.println("Incorrect name, does not exist");
            return;
        }
        while (true) {
            System.out.println("Indique o nome do albúm da música a adicionar:");
            String nomeAlbum = scanner.nextLine().trim();
            try {
                Album album = this.modelData.getAlbum(nomeAlbum);
                for (Song song : album.getSongs()) {
                    System.out.println(song.getName());
                }
                while (true) {
                    System.out.println("Escolha a musica que deseja adicionar á playlist:");
                    String nomeMusica = scanner.nextLine().trim();
                    try {
                        Song added = this.modelData.getSong(nomeMusica, nomeAlbum);
                        while( playlist.getSongs().contains( added ) ) {
                            System.out.println("Song already in playlist, try again");
                            nomeMusica = scanner.nextLine().trim();
                            added = this.modelData.getSong(nomeMusica, nomeAlbum);

                        }
                        playlist.addSong(added);
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
    }
    private void viewUserPlaylists() {
        this.modelData.getPlaylistMapByCreator(loggedUser).keySet()
                .forEach(System.out::println);
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

    public void changePassword() {

        // Assume 'loggedUserPassword' holds the current password for the logged‐in user
        String loggedUserPassword = this.loggedUser.getPassword();

        String currentPassword;
        while (true) {
            System.out.print("Enter your current password: ");
            currentPassword = scanner.nextLine();
            currentPassword = scanner.nextLine().trim();
            if (currentPassword.equals(loggedUserPassword)) {
                break;  // correct, exit loop
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
                continue;  // ask again
            }

            if (newPassword.isEmpty()) {
                System.out.println("New password cannot be empty. Please try again.");
                continue;
            }

            break;  // everything valid, exit loop
        }

        this.loggedUser.setPassword(newPassword);
        System.out.println("Your password has been successfully changed.");
    }

    public void changeEmail() {
        // Assume 'loggedUserPassword' holds the current password for the logged‐in user
        String loggedUserEmail = this.loggedUser.getEmail();

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



        this.loggedUser.setEmail(newEmail);
        System.out.println("Your Email has been successfully changed.");

        }

    public void changeAdress() {
        String loggedUserAdress = this.loggedUser.getAddress();

        String currentAdress;
        while (true) {
            System.out.print("Enter your current Address: ");
            currentAdress = scanner.nextLine().trim();
            if (currentAdress.equals(loggedUserAdress)) {
                break;  // correct, exit loop
            }
            System.out.println("Incorrect Address. Please try again.");
        }

        String newAddress;
        System.out.print("Enter your new Address: ");
        newAddress = scanner.nextLine().trim();


        this.loggedUser.setAddress(newAddress);
        System.out.println("Your Address has been successfully changed.");

    }



    }
