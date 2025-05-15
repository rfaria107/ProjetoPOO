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
import org.spotifumtp37.util.JsonDataParser;

import java.io.*;
import java.util.*;

public class TextUI {
    private final Scanner scanner;
    private SpotifUMData modelData;
    private final JsonDataParser parser = new JsonDataParser();
    private User loggedUser;

    public TextUI() {
        this.scanner = new Scanner(System.in);
        this.modelData = new SpotifUMData();
    }

    public void run() {
        this.showMainMenu();
    }

    //menus do programa

    private void showMainMenu() {
        NewMenu mainMenu = new NewMenu(new String[]{
                "Login as User",
                "Sign up as a new User",
                "Log in as Admin"
        });

        mainMenu.setHandler(1, this::loginAsUser);
        mainMenu.setHandler(2, this::signUpAsUser);
        mainMenu.setHandler(3, this::loginAsAdmin);

        mainMenu.run();
    }

    private void showAdminMenu() {
        NewMenu adminMenu = new NewMenu(new String[]{
                "Manage Albums",
                "Manage Users",
                "Manage Playlist",
                "Parse data from Json",
                "View System Stats / Execute Queries"
        });

        adminMenu.setHandler(1, this::showAlbumManagementMenu);
        adminMenu.setHandler(2, this::showUserManagementMenu);
        adminMenu.setHandler(3, this::showPlaylistManagementMenu);
        adminMenu.setHandler(4, this::showDataMenu);
        adminMenu.setHandler(5, this::showStatisticsMenu);

        adminMenu.run();
    }

    //submenus do admin
    private void showAlbumManagementMenu() {
        NewMenu albumMenu = new NewMenu(new String[]{
                "Add New Album",
                "Edit Album Details",
                "Add Songs to Album",
                "Remove Songs from Album",
                "Delete Album",
                "View All Albums",
                "Search Albums"
        });

        // Implement handlers here
        albumMenu.run();
    }

    private void showUserManagementMenu() {
        NewMenu userManagementMenu = new NewMenu(new String[]{
                "Add New User",
                "Edit User Details",
                "Manage User Subscriptions",
                "Reset User Password",
                "Delete User",
                "View All Users",
                "Search Users"
        });

        // Implement handlers here
        userManagementMenu.setHandler(1, this::signUpAsUser);

        userManagementMenu.run();
    }

    private void showPlaylistManagementMenu() {
        NewMenu playlistMenu = new NewMenu(new String[]{
                "Delete Playlist",
        });

        // Implement handlers here
        playlistMenu.run();
    }

    private void showDataMenu() {
        NewMenu dataMenu = new NewMenu(new String[]{
                "Import Data from JSON",
                "Export Data to JSON",
                "Serialize System Data",
                "Deserialize System Data",
                "Backup System Data into JSON",
                "Restore from JSON Backup",
                "Clear System Data",
                "DEBUG PRINT DATA"
        });

        dataMenu.setHandler(1, this::loadData);
        dataMenu.setHandler(2, this::saveData);
        dataMenu.setHandler(3, this::serializeData);
        dataMenu.setHandler(4, this::deserializeData);
        dataMenu.setHandler(5, this::backupData);
        dataMenu.setHandler(6, this::restoreData);
        dataMenu.setHandler(7, this::clearSystemData);
        dataMenu.setHandler(8, this::printCurrentData);

        dataMenu.run();
    }

    private void showStatisticsMenu() {
        NewMenu statsMenu = new NewMenu(new String[]{
                "View Most Popular Albums",
                "View Most Active Users",
                "View Subscription Statistics"
        });

        // Implement handlers here
        statsMenu.run();
    }

    private void showUserMenu() {
        NewMenu userMenu = new NewMenu(new String[]{
                "Listen to Music",
                "Manage Playlists",
                "Update Subscription",
                "Edit Profile"
        });

        //userMenu.setHandler(1, this::playAlbum);
        userMenu.setHandler(2, this::showUserPlaylistManagementMenu);
        //userMenu.setHandler(3, this::searchAlbum);

        // Only allow premium users to navigate through songs
        //userMenu.setPreCondition(4, () -> currentUser.getSubscriptionPlan().podeNavegarPlaylist());
        //userMenu.setPreCondition(5, () -> currentUser.getSubscriptionPlan().podeNavegarPlaylist());

        userMenu.run();
    }

    //submenus do user

    private void showMusicSelectionMenu() {
        NewMenu musicSelectionMenu = new NewMenu(new String[]{
                "Play from playlist",
                "Play from album"
        });

        // Only allow premium users to create playlists
        // playlistMenu.setPreCondition(1, () -> currentUser.getSubscriptionPlan().podeCriarPlaylist());

        //playlistMenu.setHandler(1, this::createPlaylist);
        //playlistMenu.setHandler(2, this::viewPlaylists);
        //playlistMenu.setHandler(3, this::addSongToPlaylist);
        //playlistMenu.setHandler(4, this::removeSongFromPlaylist);

        musicSelectionMenu.run();
    }

    private void showUserPlaylistManagementMenu() {
        NewMenu userPlaylistMenu = new NewMenu(new String[]{
                "Create User Playlist",
                //"Create Recommended Playlist",
                "View My Playlists",
                "Add Song to Playlist",
                "Remove Song from Playlist",
                "Change Playlist Status",
                "Delete Playlist"
        });

        userPlaylistMenu.setPreCondition(1, () -> this.loggedUser.getSubscriptionPlan().podeCriarPlaylist());
        userPlaylistMenu.setPreCondition(2, () -> this.loggedUser.getSubscriptionPlan().podeCriarPlaylist());
        userPlaylistMenu.setPreCondition(3, () -> this.loggedUser.getSubscriptionPlan().podeCriarPlaylist());

        userPlaylistMenu.setHandler(1, this::criaPlaylistUser);
        userPlaylistMenu.setHandler(2, this::viewUserPlaylists);
        userPlaylistMenu.setHandler(3, this::addSongToPlaylist);
        //playlistMenu.setHandler(3, this::addSongToPlaylist);
        //playlistMenu.setHandler(4, this::removeSongFromPlaylist);

        userPlaylistMenu.run();
    }

    private void showChangeSubscriptionMenu() {
        NewMenu changeSubscriptionMenu = new NewMenu(new String[]{
                "FreePlan",
                "PremiumBase",
                "PremiumTop"
        });

        changeSubscriptionMenu.setPreCondition(1, () -> !this.loggedUser
                .getSubscriptionPlan()
                .equals(new FreePlan()));

        changeSubscriptionMenu.setPreCondition(1, () -> !this.loggedUser
                .getSubscriptionPlan()
                .equals(new PremiumBase()));

        changeSubscriptionMenu.setPreCondition(1, () -> !this.loggedUser
                .getSubscriptionPlan()
                .equals(new PremiumTop()));

        changeSubscriptionMenu.setHandler(1, this::alteraSubscriptionFreePlan);
        changeSubscriptionMenu.setHandler(2, this::alteraSubscriptionPremiumBasePlan);
        changeSubscriptionMenu.setHandler(3, this::alteraSubscriptionPremiumTopPlan);

        changeSubscriptionMenu.run();
    }


    //handlers

    public void loginAsUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input your username:");
        String username = scanner.next();
        System.out.print("Input your password:");
        String password = scanner.next();
        //aceder ao model e verificar se a password e username estão corretos
        boolean isAuthenticated = this.authenticateUser(username, password);
        if (isAuthenticated) {
            System.out.println("Login successful! Redirecting to User Menu...");
            loggedUser = this.modelData.getCurrentUserPointer(username);
            showUserMenu(); // Show user menu
        } else {
            System.out.println("Invalid credentials for User!");
        }
    }

    public void signUpAsUser() {
        System.out.println("Sign Up as a New User!");
        // Obtain username and password
        System.out.print("Enter your desired username: ");
        String username = scanner.next();

        System.out.print("Enter your desired password: ");
        String password = scanner.next();

        System.out.print("Confirm your password: ");
        String confirmPassword = scanner.next();

        System.out.print("Enter your email address: ");
        String email = scanner.next();

        System.out.print("Enter your address: ");
        String address = scanner.next();

        // Verify that the user confirmed the password correctly
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match! Please retry signing up.");
            return;
        }

        // Call controller to create the user
        boolean isSuccess = this.registerNewUser(username, password, email, address);

        // Check if account creation was successful
        if (isSuccess) {
            System.out.println("Account created successfully! You can now log in.");
        } else {
            System.out.println("Failed to create account. Username might already exist.");
        }
    }

    public boolean registerNewUser(String username, String password, String email, String address) {
        User user = new User(username, email, address, new FreePlan(), password, 0, new ArrayList<>());
        try {
            modelData.adicionaUser(user);
            return true;
        } catch (JaExisteException e) {
            return false;
        }
    }

    public void loginAsAdmin() {
        System.out.println("Redirecting to Admin Menu...");
        this.showAdminMenu(); // Show admin menu
    }

    public boolean authenticateUser(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }
        try {
            User user = modelData.getUser(username);
            return password.equals(user.getPassword());
        } catch (NaoExisteException e) {
            return false;
        }
    }

    private void saveData() {
        System.out.print("Enter file path to save: ");
        String path = scanner.nextLine();
        try {
            this.saveToJson(path);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        System.out.print("Enter file path to load: ");
        String path = scanner.nextLine();
        try {
            this.loadFromJson(path);
            System.out.println("Data loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private void backupData() {
        try {
            parser.createBackup(modelData);
            System.out.println("Backup created in /data/ with timestamp.");
        } catch (IOException e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }

    private void restoreData() {
        System.out.print("Enter backup folder path to restore from: ");
        String path = scanner.nextLine();
        try {
            this.loadFromJson(path);
            System.out.println("System restored from backup.");
        } catch (IOException e) {
            System.out.println("Could not restore: " + e.getMessage());
        }
    }

    private void clearSystemData() {
        modelData.setMapAlbums(new HashMap<>());
        modelData.setMapUsers(new HashMap<>());
        modelData.setMapPlaylists(new HashMap<>());
        System.out.println(" SpotifUMData has been cleared.");
    }

    public void loadFromJson(String filePath) throws IOException {
        SpotifUMData loaded = parser.fromJsonData(filePath);
        modelData.setMapAlbums(loaded.getMapAlbums());
        modelData.setMapUsers(loaded.getMapUsers());
        modelData.setMapPlaylists(loaded.getMapPlaylists());
    }

    public void saveToJson(String filePath) throws IOException {
        parser.toJsonData(modelData, filePath);
    }

    private void printCurrentData() {
        System.out.println(modelData.toString());
    }


    private void serializeData() {
        System.out.print("Enter file path to save serialized data: ");
        String path = scanner.nextLine();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(modelData);
            System.out.println("Data serialized successfully!");
        } catch (IOException e) {
            System.out.println("Error serializing data: " + e.getMessage());
        }
    }

    private void deserializeData() {
        System.out.print("Enter file path to load serialized data: ");
        String path = scanner.nextLine();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            modelData = (SpotifUMData) in.readObject();
            System.out.println("Data deserialized successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing data: " + e.getMessage());
        }
    }

    private void alteraSubscriptionFreePlan() {
        FreePlan freePlan = new FreePlan();
        this.loggedUser.setSubscriptionPlan(freePlan);
    }

    private void alteraSubscriptionPremiumBasePlan() {
        PremiumBase premiumBase = new PremiumBase();
        this.loggedUser.setSubscriptionPlan(premiumBase);
    }

    private void alteraSubscriptionPremiumTopPlan() {
        PremiumTop premiumTop = new PremiumTop();
        this.loggedUser.updatePremiumTop(premiumTop);
    }

    private void criaPlaylistUser() {
        List<Song> musicas = new ArrayList<>();

        String nomePlaylist;
        System.out.println("Digite o nome da playlist: ");
        nomePlaylist = scanner.nextLine().trim();

        while (this.modelData.existePlaylist(nomePlaylist)) {
            System.out.println("A playlist já existe. Digite outro nome: ");
            nomePlaylist = scanner.nextLine().trim();
        }


        System.out.println("Indique descrição da playlist: ");
        String descricaoPlaylist = scanner.nextLine().trim();
        String estado;
        do {
            System.out.println("Estado (private ou public):");
            estado = scanner.nextLine().trim();
            if (!estado.equalsIgnoreCase("private")
                    && !estado.equalsIgnoreCase("public")) {
                System.out.println("Opção inválida! Digite apenas 'private' ou 'public'.");
            }
        } while (!estado.equalsIgnoreCase("private")
                && !estado.equalsIgnoreCase("public"));

        int n = 0;
        System.out.println("Indique número inicial de músicas: ");
        n = scanner.nextInt();
        scanner.nextLine();

        while (n <= 0) {
            System.out.println("Indique um número válido (>0): ");
            n = scanner.nextInt();
            scanner.nextLine();
        }

        while (n > 0) {
            while (true) {
                System.out.println("Indique o nome do albúm da música:");
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
                            musicas.add(this.modelData.getSong(nomeMusica, nomeAlbum));
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

        Playlist playlist = new Playlist(loggedUser, nomePlaylist, descricaoPlaylist, 0, estado, musicas);
        try {
            this.modelData.adicionaPlaylist(playlist);
            System.out.println("Playlist created successfully!");
        } catch (JaExisteException e) {
            System.out.println(" There is already a playlist with that name. Try again with a different name.");
        }

    }

    private void viewUserPlaylists() {
            this.modelData.getPlaylistMapByCreator(loggedUser).keySet()
                    .forEach(System.out::println);
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
                        playlist.addSong(this.modelData.getSong(nomeMusica, nomeAlbum));
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
}