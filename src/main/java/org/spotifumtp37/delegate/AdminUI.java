package org.spotifumtp37.delegate;

import org.spotifumtp37.model.SpotifUMData;
import org.spotifumtp37.model.album.Album;
import org.spotifumtp37.model.album.Song;
import org.spotifumtp37.model.exceptions.JaExisteException;
import org.spotifumtp37.model.exceptions.NaoExisteException;
import org.spotifumtp37.model.playlist.Playlist;
import org.spotifumtp37.model.user.User;
import org.spotifumtp37.util.JsonDataParser;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AdminUI {
    private final JsonDataParser parser;
    private final Scanner scanner;
    private SpotifUMData modelData;

    public AdminUI(SpotifUMData modelData, Scanner scanner) {
        this.modelData = modelData;
        this.scanner = scanner;
        this.parser = new JsonDataParser();
    }


    public void showAdminMenu() {
        NewMenu adminMenu = new NewMenu(new String[]{
                "Manage Albums",
                "Manage Users",
                "Manage Playlist",
                "Manage Data",
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
    public void showAlbumManagementMenu() {
        NewMenu albumMenu = new NewMenu(new String[]{
                "Add New Album",
                "Delete Album",
                "View All Albums"
        });


        albumMenu.setHandler(1, this::createAlbum);
        albumMenu.setHandler(2, this::deleteAlbum);

        albumMenu.run();
    }

    public void showUserManagementMenu() {
        NewMenu userManagementMenu = new NewMenu(new String[]{
                "Add New User",
                "Edit User Details",
                "Manage User Subscriptions",
                "Reset User Password",
                "Delete User",
                "View All Users",
                "Search Users"
        });


        //userManagementMenu.setHandler(1, this::signUpAsUser);

        userManagementMenu.run();
    }

    public void showPlaylistManagementMenu() {
        NewMenu playlistManagementMenu = new NewMenu(new String[]{
                "Create Playlist",
                "Delete Playlist",
                "View All Playlists",
        });

        playlistManagementMenu.setHandler(1, this::createPlaylistAdmin);
        playlistManagementMenu.setHandler(2, this::deletePlaylistAdmin);

        playlistManagementMenu.run();
    }

    public void showDataMenu() {
        NewMenu dataMenu = new NewMenu(new String[]{
                "Import Data from JSON",
                "Export Data to JSON",
                "Serialize System Data",
                "Deserialize System Data",
                "Clear System Data",
                "DEBUG PRINT DATA"
        });

        dataMenu.setHandler(1, this::loadData);
        dataMenu.setHandler(2, this::saveData);
        dataMenu.setHandler(3, this::serializeData);
        dataMenu.setHandler(4, this::deserializeData);
        dataMenu.setHandler(5, this::clearSystemData);
        dataMenu.setHandler(6, this::printCurrentData);

        dataMenu.run();
    }

    public void showStatisticsMenu() {
        NewMenu statsMenu = new NewMenu(new String[]{
                "View Most Popular Albums",
                "View Most Active Users",
                "View Subscription Statistics"
        });

        // Implement handlers here
        statsMenu.run();
    }

    //handlers

    public void saveData() {
        System.out.print("Enter file path to save: ");
        String path = scanner.nextLine();
        try {
            this.saveToJson(path);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadData() {
        System.out.print("Enter file path to load: ");
        String path = scanner.nextLine();
        try {
            this.loadFromJson(path);
            System.out.println("Data loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
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

    public void serializeData() {
        System.out.print("Enter filename to save serialized data (data/serial/spotifumdata.ser suggested): ");
        String filename = scanner.nextLine().trim();

        try (FileOutputStream fileOut = new FileOutputStream(filename)) {
            try (ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(modelData);
                System.out.println("Data serialized successfully!");
            } catch (IOException e) {
                System.out.println("Error serializing data: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error serializing data: " + e.getMessage());
        }
    }

    public void deserializeData() {
        System.out.print("Enter filename to load serialized data (data/serial/spotifumdata.ser suggested): ");
        String filename = scanner.nextLine().trim();

        try (FileInputStream fileIn = new FileInputStream(filename)) {
            try (ObjectInputStream in = new ObjectInputStream(fileIn)) {
                SpotifUMData deserializedData = (SpotifUMData) in.readObject();
                this.modelData.setMapAlbums(deserializedData.getMapAlbums());
                this.modelData.setMapUsers(deserializedData.getMapUsers());
                this.modelData.setMapPlaylists(deserializedData.getMapPlaylists());

                System.out.println("Data deserialized successfully!");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error deserializing data: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error deserializing data: " + e.getMessage());
        }
    }

    public void clearSystemData() {
        modelData.setMapAlbums(new HashMap<>());
        modelData.setMapUsers(new HashMap<>());
        modelData.setMapPlaylists(new HashMap<>());
        System.out.println(" SpotifUMData has been cleared.");
    }

    public void printCurrentData() {
        System.out.println(modelData.toString());
    }

    public void createAlbum() {
        System.out.println("=== Criar Novo Álbum ===");

        String titulo;
        System.out.print("Título do álbum: ");
        titulo = scanner.nextLine().trim();
        while (this.modelData.existeAlbum(titulo)) {
            System.out.print("Já existe álbum com esse título. Informe outro: ");
            titulo = scanner.nextLine().trim();
        }

        System.out.print("Artista: ");
        String artista = scanner.nextLine().trim();

        int ano = 0;
        do {
            System.out.print("Ano de lançamento (ex: 1973): ");
            try {
                ano = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, informe um número inteiro.");
            }
            if (ano <= 1900 || ano > LocalDate.now().getYear()) {
                System.out.println("Por favor, informe um ano válido.");
                ano = 0;
            }
        } while (ano == 0);

        System.out.print("Gênero musical: ");
        String genero = scanner.nextLine().trim();

        Album album = new Album(titulo, artista, ano, genero, new ArrayList<>());

        int nFaixas = 0;
        do {
            System.out.print("Quantas faixas deseja adicionar? ");
            try {
                nFaixas = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                nFaixas = 0;
            }
            if (nFaixas <= 0) {
                System.out.println("Por favor, informe um número maior que zero.");
            }
        } while (nFaixas <= 0);

        for (int i = 1; i <= nFaixas; i++) {
            System.out.printf("---- Faixa %d/%d ----%n", i, nFaixas);

            System.out.print("Nome da música: ");
            String nomeMusica = scanner.nextLine().trim();

            System.out.print("Editora: ");
            String editora = scanner.nextLine().trim();

            System.out.print("Letra (ou breve resumo): ");
            String letra = scanner.nextLine().trim();

            System.out.print("Notas musicais: ");
            String musicalNotes = scanner.nextLine().trim();

            System.out.print("Género musical da faixa: ");
            String generoMusica = scanner.nextLine().trim();

            int duracao = 0;
            do {
                System.out.print("Duração (segundos): ");
                try {
                    duracao = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    duracao = 0;
                }
                if (duracao <= 0) {
                    System.out.println("Por favor, informe um valor positivo.");
                }
            } while (duracao <= 0);

            album.addSong(nomeMusica, editora, letra, musicalNotes, generoMusica, duracao);
            System.out.println("Faixa adicionada!");
        }

        try {
            this.modelData.adicionaAlbum(album);
            System.out.println("Álbum criado com sucesso!");
        } catch (JaExisteException e) {
            // não deve acontecer pois checamos no início, mas só por precaução:
            System.out.println("Erro: álbum já existe. Tente novamente com outro título.");
        }
    }

    public void deleteAlbum() {
        System.out.println("Admin: Delete Album");
        System.out.print("Title of the album to delete: ");
        String titulo = scanner.nextLine().trim();

        // Confirma existência
        while (!modelData.existeAlbum(titulo)) {
            System.out.print("Album not found. Enter another title (or 'exit' to cancel): ");
            titulo = scanner.nextLine().trim();
            if (titulo.equalsIgnoreCase("exit")) {
                System.out.println("Operation canceled.");
                return;
            }
        }

        // Confirmação final
        System.out.printf("Are you sure you want to delete the album '%s'? (y/n): ", titulo);
        String confirma = scanner.nextLine().trim();
        if (!confirma.equalsIgnoreCase("y")) {
            System.out.println("Operation Cancelled.");
            return;
        }

        // Apaga
        try {
            modelData.removeAlbum(titulo);
            System.out.println("Album deleted successfully.");
        } catch (NaoExisteException e) {
            // nao deve acontecer pois já checkamos, mas...
            System.out.println("Error album not found.");
        }
    }

    public void createPlaylistAdmin() {
        System.out.println("Admin: Create Playlist");
        // scanner.nextLine(); // Consume leftover newline if necessary

        User playlistOwner = null;
        System.out.print("Enter username of the playlist owner (leave blank for a general/system playlist): ");
        String ownerUsername = scanner.nextLine().trim();
        if (!ownerUsername.isEmpty()) {
            try {
                playlistOwner = modelData.getUser(ownerUsername);
            } catch (NaoExisteException e) {
                System.out.println("User '" + ownerUsername + "' not found. Playlist will be general if you proceed without a valid owner.");
                // Optionally, ask again or default to null owner
            }
        }

        // The rest of the playlist creation logic (name, description, songs)
        // is similar to UserUI.createPlaylist but uses playlistOwner (which could be null)
        // For example:
        List<Song> musicas = new ArrayList<>();
        System.out.println("Enter the name for the new playlist: ");
        String nomePlaylist = scanner.nextLine().trim();

        while (this.modelData.existePlaylist(nomePlaylist)) {
            System.out.println("A playlist with this name already exists. Please choose another name: ");
            nomePlaylist = scanner.nextLine().trim();
        }
        // ... (description, state, adding songs, etc.)
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
        // ... song adding logic ...


        Playlist playlist = new Playlist(playlistOwner, nomePlaylist, descricaoPlaylist, 0, estado, musicas);
        try {
            this.modelData.addPlaylist(playlist);
            String ownerInfo = (playlistOwner != null) ? " for user " + playlistOwner.getName() : " (general)";
            System.out.println("Playlist '" + nomePlaylist + "'" + ownerInfo + " created successfully!");
        } catch (JaExisteException e) {
            System.out.println("Error: A playlist with that name already exists.");
        }
    }


    public void deletePlaylistAdmin() {
        System.out.println("Admin: Delete Playlist");
        // scanner.nextLine(); // Consume leftover newline if necessary
        System.out.print("Enter the name of the playlist to delete: ");
        String nome = scanner.nextLine().trim();

        if (!modelData.existePlaylist(nome)) {
            System.out.println("Playlist not found.");
            return;
        }

        System.out.printf("Are you sure you want to delete the playlist '%s'? (s/n): ", nome);
        String confirma = scanner.nextLine().trim();
        if (!confirma.equalsIgnoreCase("s")) {
            System.out.println("Operation cancelled.");
            return;
        }

        try {
            modelData.removePlaylist(nome); // Admin can remove any playlist
            System.out.println("Playlist '" + nome + "' deleted successfully!");
        } catch (NaoExisteException e) {
            // This case should ideally be caught by existePlaylist check above,
            // but good for robustness.
            System.out.println("Error: Playlist not found during deletion attempt.");
        }
    }

}
