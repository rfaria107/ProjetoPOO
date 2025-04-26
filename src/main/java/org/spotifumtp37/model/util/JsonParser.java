package org.spotifumtp37.model.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.spotifumtp37.model.album.Album;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonParser {
    private final Gson gson;

    public JsonParser() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Parses a JSON file and converts it into an Album object.
     *
     * @param filePath Path to the JSON file containing album data.
     * @return Deserialized Album object.
     * @throws IOException If there's an error reading the file.
     */
    public Album fromJson(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Album.class); // Deserialize JSON into Album object
        }
    }

    /**
     * Serializes an Album object and writes it to a JSON file.
     *
     * @param album    Album object to serialize.
     * @param filePath Path to the JSON file that will store the album data.
     * @throws IOException If there's an error writing to the file.
     */
    public void toJson(Album album, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(album, writer); // Serialize Album object into JSON
        }
    }
}